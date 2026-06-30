package com.example.CalculadoraObraResidencial.View;

import com.example.CalculadoraObraResidencial.DTO.ParedeRequestDTO;
import com.example.CalculadoraObraResidencial.DTO.ParedeResponseDTO;
import com.example.CalculadoraObraResidencial.DTO.ProjetoCalculoDTO;
import com.example.CalculadoraObraResidencial.DTO.ResultadoCalculoDTO;
import com.example.CalculadoraObraResidencial.DTO.BuracoDTO;
import com.example.CalculadoraObraResidencial.Entities.Orcamento;
import com.example.CalculadoraObraResidencial.Repository.OrcamentoRepository;
import com.example.CalculadoraObraResidencial.Service.MaterialService;
import com.example.CalculadoraObraResidencial.Service.ParedeService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component("orcamentoBean")
@SessionScope
public class OrcamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ParedeService paredeService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private OrcamentoRepository orcamentoRepository; // Repositório para persistência dos dados

    // Cabeçalho e Identificadores do Orçamento (Requisitos do PDF)
    private String nomeUsuario;
    private Long numeroOrcamentoBusca;

    // Campos de entrada para leitura da Planta da Casa (UX)
    private Double comprimento;
    private Double largura;
    private Double altura;
    private Double larguraPorta;
    private Double alturaPorta;
    private Double larguraJanela;
    private Double alturaJanela;

    // Parâmetros Técnicos Construtivos (Valores padrão)
    private Double alturaVigaBaldrame = 0.40;
    private Double comprimentoTijolo = 0.19;
    private Double alturaTijolo = 0.07;

    // Estado da View (Listas de controle da UI)
    private List<ParedeResponseDTO> paredesDisponiveis = new ArrayList<>();
    private List<ParedeResponseDTO> paredesSelecionadas = new ArrayList<>();
    private ResultadoCalculoDTO resultadoFinal;

    @PostConstruct
    public void init() {
        carregarParedes();
    }

    public void carregarParedes() {
        try {
            this.paredesDisponiveis = paredeService.listarTodas();
        } catch (Exception e) {
            this.paredesDisponiveis = new ArrayList<>();
        }
    }

    public void adicionarParede() {
        try {
            ParedeRequestDTO dto = new ParedeRequestDTO();
            dto.setComprimento(comprimento);
            dto.setLargura(largura);
            dto.setAltura(altura);

            if (larguraPorta != null && alturaPorta != null) {
                dto.setPorta(new BuracoDTO(larguraPorta, alturaPorta));
            }
            if (larguraJanela != null && alturaJanela != null) {
                dto.setJanela(new BuracoDTO(larguraJanela, alturaJanela));
            }

            ParedeResponseDTO novaParede = paredeService.criar(dto);

            // Adiciona automaticamente a parede criada à seleção atual do orçamento
            if (this.paredesSelecionadas == null) {
                this.paredesSelecionadas = new ArrayList<>();
            }
            this.paredesSelecionadas.add(novaParede);

            carregarParedes();
            limparCamposParede();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Parede vinculada à planta do imóvel."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Falha ao salvar dimensões da parede."));
        }
    }

    public void processarOrcamento() {
        if (nomeUsuario == null || nomeUsuario.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "O nome do usuário é obrigatório para gerar o orçamento."));
            return;
        }
        if (paredesSelecionadas == null || paredesSelecionadas.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção", "Selecione ao menos uma parede da tabela para calcular a planta."));
            return;
        }

        try {
            // Mapeia os objetos selecionados para a lista de IDs numéricos que o service exige
            List<Long> ids = paredesSelecionadas.stream().map(ParedeResponseDTO::getId).toList();

            ProjetoCalculoDTO calculoDTO = new ProjetoCalculoDTO();
            calculoDTO.setParedeIds(ids);
            calculoDTO.setAlturaVigaBaldrame(alturaVigaBaldrame);
            calculoDTO.setComprimentoTijolo(comprimentoTijolo);
            calculoDTO.setAlturaTijolo(alturaTijolo);

            // Processa as fórmulas matemáticas de engenharia civil
            this.resultadoFinal = materialService.calcularProjetoCompleto(calculoDTO);

            // Instancia a nova entidade Orcamento com os dados corretos (int paredesAnalisadas corrigido)
            Orcamento novoOrcamento = new Orcamento(
                    this.nomeUsuario,
                    this.resultadoFinal.getParedesAnalisadas(),
                    this.resultadoFinal.getAreaLiquidaTotal(),
                    this.resultadoFinal.getVolumeConcretoM3(),
                    this.resultadoFinal.getQuantidadeTijolos()
            );

            // Salva fisicamente o orçamento na tabela do banco de dados H2 via ORM
            novoOrcamento = orcamentoRepository.save(novoOrcamento);

            // Retorna o número do orçamento gerado pelo banco para exibição na tela
            this.numeroOrcamentoBusca = novoOrcamento.getNumeroOrcamento();

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Orçamento Salvo!", "Registrado com sucesso sob o Nº: " + this.numeroOrcamentoBusca));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro no Processamento", e.getMessage()));
        }
    }

    public void buscarOrcamentoExistente() {
        if (numeroOrcamentoBusca == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Informe um número de orçamento válido para buscar."));
            return;
        }

        try {
            // Busca o orçamento pelo identificador no banco H2 (Requisito do Passo 2 da Etapa 1)
            orcamentoRepository.findById(numeroOrcamentoBusca).ifPresentOrElse(o -> {
                this.nomeUsuario = o.getNomeUsuario();
                this.resultadoFinal = new ResultadoCalculoDTO(
                        o.getParedesAnalisadas(),
                        o.getAreaLiquidaTotal(),
                        o.getVolumeConcretoM3(),
                        o.getQuantidadeTijolos()
                );
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Orçamento Nº " + o.getNumeroOrcamento() + " carregado do banco."));
            }, () -> {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não Encontrado", "Nenhum orçamento localizado com o número digitado."));
            });
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na Busca", "Falha ao consultar repositório de dados."));
        }
    }

    private void limparCamposParede() {
        this.comprimento = null;
        this.largura = null;
        this.altura = null;
        this.larguraPorta = null;
        this.alturaPorta = null;
        this.larguraJanela = null;
        this.alturaJanela = null;
    }

    // Getters e Setters para Binding do Jakarta Faces
    public String getNomeUsuario() { return nomeUsuario; }
    public void setNomeUsuario(String nomeUsuario) { this.nomeUsuario = nomeUsuario; }
    public Long getNumeroOrcamentoBusca() { return numeroOrcamentoBusca; }
    public void setNumeroOrcamentoBusca(Long numeroOrcamentoBusca) { this.numeroOrcamentoBusca = numeroOrcamentoBusca; }
    public Double getComprimento() { return comprimento; }
    public void setComprimento(Double comprimento) { this.comprimento = comprimento; }
    public Double getLargura() { return largura; }
    public void setLargura(Double largura) { this.largura = largura; }
    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }
    public Double getLarguraPorta() { return larguraPorta; }
    public void setLarguraPorta(Double larguraPorta) { this.larguraPorta = larguraPorta; }
    public Double getAlturaPorta() { return alturaPorta; }
    public void setAlturaPorta(Double alturaPorta) { this.alturaPorta = alturaPorta; }
    public Double getLarguraJanela() { return larguraJanela; }
    public void setLarguraJanela(Double larguraJanela) { this.larguraJanela = larguraJanela; }
    public Double getAlturaJanela() { return alturaJanela; }
    public void setAlturaJanela(Double alturaJanela) { this.alturaJanela = alturaJanela; }
    public Double getAlturaVigaBaldrame() { return alturaVigaBaldrame; }
    public void setAlturaVigaBaldrame(Double alturaVigaBaldrame) { this.alturaVigaBaldrame = alturaVigaBaldrame; }
    public Double getComprimentoTijolo() { return comprimentoTijolo; }
    public void setComprimentoTijolo(Double comprimentoTijolo) { this.comprimentoTijolo = comprimentoTijolo; }
    public Double getAlturaTijolo() { return alturaTijolo; }
    public void setAlturaTijolo(Double alturaTijolo) { this.alturaTijolo = alturaTijolo; }
    public List<ParedeResponseDTO> getParedesDisponiveis() { return paredesDisponiveis; }
    public List<ParedeResponseDTO> getParedesSelecionadas() { return paredesSelecionadas; }
    public void setParedesSelecionadas(List<ParedeResponseDTO> paredesSelecionadas) { this.paredesSelecionadas = paredesSelecionadas; }
    public ResultadoCalculoDTO getResultadoFinal() { return resultadoFinal; }
}