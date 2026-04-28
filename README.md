# Calculadora de Materiais para Obra Residencial

Sistema de backend desenvolvido para a disciplina de **Desenvolvimento de Sistemas** na **UniCEUB**. O projeto utiliza **Java 21** e **Spring Boot** para realizar cálculos de estimativa de materiais de construção (volume de concreto e quantidade de tijolos) baseados na modelagem de uma planta residencial como grafo **G=(V,A)**.

## Sobre o Projeto

A aplicação permite que o usuário selecione paredes cadastradas no sistema e informe parâmetros de construção para obter um relatório detalhado de insumos. O diferencial técnico é o cálculo de **área líquida**, que desconta automaticamente os vãos de portas e janelas das superfícies das paredes.

### Principais Funcionalidades

- **Gestão de Paredes (Etapa 1):** Cadastro de dimensões (altura, comprimento, largura) com suporte a porta e janela.
- **Cálculo de Concreto (Etapa 2):** Estimativa de volume para vigas baldrame com base nas paredes selecionadas.
- **Cálculo de Tijolos (Etapa 3):** Quantidade baseada na área líquida e nas dimensões do tijolo informado.
- **Relatório Completo:** Consolida concreto e tijolos em uma única requisição.

## Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.3
- **Banco de Dados:** H2 (in-memory)
- **ORM:** Spring Data JPA / Hibernate
- **Validação:** Bean Validation (Jakarta)
- **Documentação:** Swagger UI (SpringDoc OpenAPI 2.5)

---

## Como Executar

```bash
# 1. Clone o repositório
git clone https://github.com/DGodoi19/Calculadora-Obra-Residencial.git

# 2. Entre na pasta
cd Calculadora-Obra-Residencial

# 3. Execute com Maven
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`

---

## Documentação e Banco

| Recurso | URL |
|---|---|
| Swagger UI | http://localhost:8080/swagger-ui.html |
| OpenAPI JSON | http://localhost:8080/v3/api-docs |
| Console H2 | http://localhost:8080/h2-console |

**Configuração do H2:**
```
JDBC URL: jdbc:h2:mem:obra_residencial
User:     sa
Password: (vazio)
```

---

## Guia de Testes — Ordem Recomendada

Siga a ordem abaixo para testar corretamente no Swagger ou Postman:

### Etapa 1 — Cadastrar Paredes (arestas do grafo G=(V,A))

Primeiro cadastre ao menos 2 ou 3 paredes. Anote os `id`s retornados para usar nas etapas seguintes.

`POST /api/paredes`
```json
{
  "comprimento": 5.0,
  "largura": 0.15,
  "altura": 3.0,
  "porta": { "largura": 0.9, "altura": 2.1 },
  "janela": { "largura": 1.2, "altura": 1.2 }
}
```

Para listar todas as paredes cadastradas:

`GET /api/paredes`

---

### Etapa 2 — Calcular Volume de Concreto (Viga Baldrame)

Use os `id`s das paredes cadastradas na Etapa 1. Informe a altura da viga baldrame em metros.

`POST /api/paredes/calculo/concreto`
```json
{
  "ids": [1, 2, 3],
  "alturaViga": 0.4
}
```

**Fórmula:** `volume = comprimento × largura × alturaViga` (por parede)

---

### Etapa 3 — Calcular Quantidade de Tijolos

Use os `id`s das paredes. Informe as dimensões do tijolo em metros.

`POST /api/paredes/calculo/tijolos`
```json
{
  "ids": [1, 2, 3],
  "alturaTijolo": 0.07,
  "comprimentoTijolo": 0.19
}
```

**Fórmula:** `tijolos = ceil(areaLiquida / (alturaTijolo × comprimentoTijolo))`

> A área líquida desconta automaticamente os vãos de porta e janela.

---

### Relatório Completo (Etapas 2 + 3 em uma só chamada)

`POST /api/calculos/processar`
```json
{
  "paredeIds": [1, 2, 3],
  "alturaVigaBaldrame": 0.4,
  "comprimentoTijolo": 0.19,
  "alturaTijolo": 0.07
}
```

**Resposta:**
```json
{
  "paredesAnalisadas": 3,
  "areaLiquidaTotal": 38.55,
  "volumeConcretoM3": 0.90,
  "quantidadeTijolos": 2891
}
```

---

## Modelo de Dados

```
Projeto
 └── Comodo (nome, padraoParede)
      └── Parede (comprimento, largura, altura)
           ├── Buraco porta  (largura, altura)
           └── Buraco janela (largura, altura)
 └── Pilar (nome, largura, profundidade, altura)
```

A planta é modelada como grafo **G=(V,A)**:
- **Vértices (V)** → Pilares (encontros de paredes)
- **Arestas (A)** → Paredes

## Fórmulas Implementadas

| Etapa | Cálculo | Fórmula |
|---|---|---|
| 2 | Volume de concreto (viga) | `comprimento × largura × alturaViga` |
| 3 | Área líquida da parede | `(comprimento × altura) − área_porta − área_janela` |
| 3 | Quantidade de tijolos | `ceil(areaLiquida / (alturaTijolo × comprimentoTijolo))` |
