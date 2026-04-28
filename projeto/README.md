# Calculadora de Materiais para Obra Residencial

Atividade Avaliativa — Desenvolvimento de Sistemas (UniCEUB)

## Como executar

```bash
# 1. Clone o repositório
git clone <url-do-repositorio>

# 2. Entre na pasta
cd CalculadoraObraResidencial

# 3. Execute com Maven
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`

---

## Swagger UI

Acesse a documentação interativa:
```
http://localhost:8080/swagger-ui.html
```

Console do banco H2 (dados em memória):
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:obra_residencial
```

---

## Endpoints

### Etapa 1 — Paredes (arestas do grafo G=(V,A))

#### Criar parede
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

#### Listar paredes
`GET /api/paredes`

---

### Etapa 2 — Volume de concreto (viga baldrame)

`POST /api/paredes/calculo/concreto`
```json
{
  "ids": [1, 2, 3],
  "alturaViga": 0.4
}
```

---

### Etapa 3 — Quantidade de tijolos

`POST /api/paredes/calculo/tijolos`
```json
{
  "ids": [1, 2, 3],
  "alturaTijolo": 0.07,
  "comprimentoTijolo": 0.19
}
```

---

### Relatório completo (concreto + tijolos)

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

## Fórmulas implementadas

| Cálculo | Fórmula |
|---|---|
| Volume concreto (viga) | `comprimento × largura × alturaViga` |
| Área líquida da parede | `(comprimento × altura) − área_porta − área_janela` |
| Quantidade de tijolos | `ceil(areaLiquida / (alturaTijolo × comprimentoTijolo))` |
