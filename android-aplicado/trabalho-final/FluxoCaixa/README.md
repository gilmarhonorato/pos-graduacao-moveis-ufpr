# FluxoCaixa

Aplicativo nativo Android para controle financeiro pessoal, desenvolvido em Kotlin como trabalho final da disciplina **Android Aplicado** (UTFPR Campus Pato Branco).

O app permite registrar receitas e despesas e visualizar o histórico em uma lista com saldo consolidado.

## Funcionalidades

- Cadastro de lançamentos com valor, descrição, data e tipo (Receita ou Despesa)
- Validação dos campos antes de salvar
- Persistência local em banco SQLite
- Listagem do extrato com RecyclerView e adapter
- Diferenciação visual entre receitas (verde) e despesas (vermelho)
- Exibição do saldo total (receitas − despesas)
- Seleção de data com DatePicker
- Formatação monetária no padrão brasileiro (R$)
- Suporte a idiomas: português (pt-BR) e inglês (padrão)

## Telas

### 1. Lançamento (`MainActivity`)

Porta de entrada do aplicativo. Campos:

| Campo | Descrição |
|-------|-----------|
| Tipo | RadioButton: Receita (Crédito) ou Despesa (Débito) |
| Valor | Campo numérico com máscara em Real (R$) |
| Descrição | Texto curto (ex.: Aluguel, Salário) |
| Data | Seleção via DatePicker |

Ações: **Salvar** (valida e persiste) e **Ver Lançamentos** (abre o extrato).

### 2. Extrato (`ExtratoActivity`)

Exibe o histórico de lançamentos em lista, com descrição, data, valor e tipo. O cabeçalho mostra o saldo atual.

## Arquitetura

O projeto segue o padrão **MVC** (Model-View-Controller):

| Camada | Responsabilidade | Pacotes |
|--------|------------------|---------|
| **Model** | Entidades e regras de negócio | `model/` |
| **View** | Layouts XML e Activities | `res/layout/`, Activities |
| **Controller** | Interação entre View e Model | `MainActivity`, `ExtratoActivity` |

A camada de dados fica em `database/`, com `SQLiteOpenHelper` e repositório para operações CRUD.

## Tecnologias

- Kotlin
- Android SDK 37 (minSdk 26)
- Material Design 3
- SQLite
- RecyclerView
- ViewBinding implícito via `findViewById`

## Estrutura do projeto

```
app/src/main/java/com/exemplo/fluxocaixa/
├── MainActivity.kt              # Tela de lançamento
├── ExtratoActivity.kt           # Tela de extrato
├── adapter/
│   └── LancamentoAdapter.kt     # Adapter da lista
├── database/
│   ├── LancamentoDbHelper.kt    # Criação do banco SQLite
│   └── LancamentoRepositorio.kt # Consultas e persistência
├── model/
│   ├── Lancamento.kt            # Entidade de lançamento
│   └── TipoLancamento.kt        # Enum Receita / Despesa
└── util/
    ├── FormatadorMoeda.kt       # Formatação R$
    ├── MascaraValorBrasileiro.kt
    └── MargemSistema.kt         # Ajuste de margens do sistema
```

## Banco de dados

Tabela `lancamentos`:

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| id | INTEGER | Chave primária autoincremento |
| valor | REAL | Valor monetário |
| descricao | TEXT | Descrição do lançamento |
| data_lancamento | TEXT | Data no formato dd/MM/yyyy |
| tipo | TEXT | RECEITA ou DESPESA |

## Como executar

### Pré-requisitos

- Android Studio (versão recente com suporte ao AGP 9.x)
- JDK 11 ou superior
- Emulador ou dispositivo com Android 8.0 (API 26) ou superior

### Passos

1. Clone o repositório e abra a pasta `FluxoCaixa` no Android Studio
2. Aguarde o Gradle sincronizar as dependências
3. Execute o app no emulador ou dispositivo (`Run ▶`)

```bash
./gradlew assembleDebug
```

O APK de debug será gerado em `app/build/outputs/apk/debug/`.

## Checklist de avaliação

| Item | Status |
|------|--------|
| Tela principal (lançamento) | Implementado |
| Tela de listagem (extrato) | Implementado |
| Consistência dos campos de entrada | Implementado |
| Persistência no banco de dados | Implementado |
| Navegabilidade entre telas | Implementado |
| Organização do código (MVC) | Implementado |
| Adapter na lista | Implementado |
| DatePicker | Implementado |
| Diferenciação crédito/débito | Implementado |
| Apresentação do saldo | Implementado |

## Autores

Trabalho em dupla — Especialização em Programação para Dispositivos Móveis  
Universidade Tecnológica Federal do Paraná — Campus Pato Branco
