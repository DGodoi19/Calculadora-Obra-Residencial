# Calculadora de Materiais para Obra Residencial

[cite_start]Sistema completo (Fullstack) desenvolvido para a disciplina de **Desenvolvimento de Sistemas** na **UniCEUB**[cite: 3, 40]. [cite_start]O projeto utiliza **Java 21/25** e **Spring Boot** integrado ao **Jakarta Faces (JSF)** e **PrimeFaces** por meio do ecossistema **JoinFaces**[cite: 10, 41].

[cite_start]A aplicação realiza cálculos de estimativa de materiais de construção (volume de concreto e quantidade de tijolos) baseados na modelagem de uma planta residencial como grafo **G=(V,A)** e gerencia a persistência e busca do histórico de orçamentos gerados[cite: 17, 18, 24].

## Sobre o Projeto

[cite_start]A aplicação oferece uma interface web minimalista e reativa que permite ao usuário desenhar a planta inserindo paredes dinamicamente (focado em Experiência do Usuário - UX), calcular insumos estruturais com descontos automáticos de vãos (portas e janelas) e salvar/recuperar orçamentos diretamente no banco de dados[cite: 21, 23, 24].

### Principais Funcionalidades

- [cite_start]**Gestão e Leitura da Planta (Etapa 1 — Passo 1):** Cadastro dinâmico de dimensões de paredes (altura, comprimento, largura) com suporte opcional a vãos de porta e janela para uma experiência fluida.
- [cite_start]**Persistência de Orçamentos (Etapa 1 — Passo 2):** Gravação física dos cálculos no banco de dados H2, permitindo a busca reativa do histórico por meio do **Número do Orçamento** ou **Nome do Usuário**.
- [cite_start]**Cálculo de Concreto (Etapa 2):** Estimativa do volume total necessário para as vigas baldrame com base nas estruturas selecionadas[cite: 18].
- [cite_start]**Cálculo de Tijolos (Etapa 3):** Quantidade exata de blocos baseada na área líquida (área bruta menos os descontos de vãos) e nas dimensões do tijolo fornecido[cite: 18].

## Tecnologias Utilizadas

- **Linguagem:** Java 21 / 25
- [cite_start]**Framework Core:** Spring Boot 3.3 [cite: 41]
- [cite_start]**Frontend Web:** Jakarta Faces (JSF 4.0) & PrimeFaces 14 (via JoinFaces 5.3) [cite: 10]
- **Banco de Dados:** H2 Database (In-Memory)
- [cite_start]**ORM / Persistência:** Spring Data JPA / Hibernate [cite: 8]
- **Documentação API:** Swagger UI (SpringDoc OpenAPI 2.5)

---

## Como Executar

```bash
# 1. Clone o repositório
git clone [https://github.com/DGodoi19/Calculadora-Obra-Residencial.git](https://github.com/DGodoi19/Calculadora-Obra-Residencial.git)

# 2. Entre na pasta do projeto
cd Calculadora-Obra-Residencial

# 3. Limpe resquícios e execute com Maven
./mvnw clean spring-boot:run
```
## URLs de Acesso
# Recurso,URL
Interface Web (UI),http://localhost:8080/orcamento.xhtml
Console Banco H2,http://localhost:8080/h2-console
Swagger UI (API),http://localhost:8080/swagger-ui.html

**Configuração do H2 Console: JDBC URL: jdbc:h2:mem:obra_residencial | Usuário: sa | Senha: (vazio)**

## Modelo de Dados (ORM)
Projeto
└── Comodo (nome, padraoParede)
└── Parede (comprimento, largura, altura)
├── Buraco porta  (largura, altura)
└── Buraco janela (largura, altura)
└── Pilar (nome, largura, profundidade, altura)

Orcamento (numeroOrcamento, nomeUsuario, paredesAnalisadas, areaLiquidaTotal, volumeConcretoM3, quantidadeTijolos, dataGeracao)

## Plano de Testes (Etapa 3 — Passo 1 e 2)

Este plano descreve os cenários executados para validar os requisitos de negócio, as fórmulas de engenharia e os critérios de aceitação visual de UX (erros em vermelho).

# Caso de Teste 01: Validação de Campos Obrigatórios (UX de Erros em Vermelho)

Objetivo: Garantir que o sistema impeça submissões inválidas e destaque erros de forma visual clara.  

Passos:

-Acesse http://localhost:8080/orcamento.xhtml

-Deixe o campo "Nome do Usuário / Solicitante" totalmente em branco

-Role até o final da página e clique em "Calcular e Salvar Orçamento"

-Resultado Esperado (Sucesso): O envio é bloqueado pelo ciclo de vida do JSF e um alerta em vermelho vivo surge abaixo do campo informando que ele é obrigatório.

# Caso de Teste 02: Inserção Dinâmica e Cálculo da Área Líquida

Objetivo: Validar a experiência de montagem da planta e a exatidão matemática do desconto de vãos.  
Passos:

-No bloco "2. Cadastro de Dimensões", insira uma parede com: Comprimento = 5.0, Largura = 0.15, Altura = 3.0 (Área bruta: 15m²)

-Adicione os vãos: Porta (0.90 x 2.10 = 1.89m²) e Janela (1.20 x 1.20 = 1.44m²). Total de descontos: 3.33m²

-Clique em "Vincular Parede à Planta".Resultado Esperado (Sucesso): Uma notificação de sucesso é exibida. A parede é salva e aparece listada na tabela do bloco "3", exibindo a Área Líquida exata de 11.67 m² (15.0 - 3.33).

# Caso de Teste 03: Processamento Geral e Persistência do Orçamento

Objetivo: Verificar se o relatório consolidado calcula os insumos corretamente e gera um número identificador de orçamento no banco.  
PDF

Passos:

-Digite seu nome no campo do solicitante.

-Marque a parede criada no teste anterior na tabela de seleção.

-Mantenha os parâmetros padrão: Altura da Viga = 0.40, Comprimento do Tijolo = 0.19, Altura do Tijolo = 0.07.

-Clique em "Calcular e Salvar Orçamento".

-Resultado Esperado (Sucesso): O sistema grava os dados no banco H2, emite uma mensagem azul de sucesso contendo o número gerado (Ex: Registrado com sucesso sob o Nº: 1) e exibe o painel verde do Memorial Descritivo contendo a quantidade exata de tijolos calculada por aproximação superior (Ceiling).

# Caso de Teste 04: Localização e Recuperação de Orçamentos

Objetivo: Garantir que um orçamento salvo possa ser recuperado a partir de seu identificador numérico.  

Passos:

-Atualize a página do navegador (para limpar o estado da tela).

-No bloco "1. Identificação", digite o número 1 no campo "Nº do Orçamento para Localização".

-Clique no botão "Buscar".Resultado Esperado (Sucesso): O sistema realiza o select no banco H2, recupera o registro e preenche instantaneamente na tela o nome do solicitante original e o painel do Memorial com todos os valores numéricos idênticos aos gravados anteriormente.