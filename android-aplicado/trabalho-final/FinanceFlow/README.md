# FinanceFlow

Aplicativo Android de controle de contas a pagar e receber com fluxo de caixa.

Disciplina: Android Aplicado — UTFPR PM-V.

## Funcionalidades

- Cadastro de receitas e despesas com valor, descrição e data
- Extrato com saldo atual (receitas − despesas)
- Cores verde para receita e vermelho para despesa
- Edição e exclusão de lançamentos pelo extrato
- Persistência local com Room (SQLite)

## Arquitetura

```
UI (XML + ViewBinding)
  ExtratoActivity ── ExtratoViewModel
  LancamentoActivity ── LancamentoViewModel
        │
        ▼
  MovimentacaoRepository
        │
        ▼
  MovimentacaoDao → AppDatabase (Room)
```

Padrão MVVM com Repository, Coroutines e Flow.

## Telas

| Tela | Descrição |
|------|-----------|
| Extrato | Tela inicial. Lista movimentações, exibe saldo, FAB para novo lançamento |
| Lançamento | Formulário para incluir ou editar movimentação |

## Como executar

1. Abra o projeto no Android Studio
2. Sincronize o Gradle
3. Execute em emulador ou dispositivo (minSdk 26)

```bash
./gradlew assembleDebug
./gradlew test
./gradlew connectedDebugAndroidTest
```

## Testes

- **Unitários (9):** cálculo de saldo, validação e CRUD no ViewModel
- **Instrumentados (7):** fluxos E2E com Espresso

## Tecnologias

- Kotlin
- Room 2.8.4
- Material 3
- RecyclerView + ListAdapter
- Lifecycle ViewModel
- Coroutines + Flow
- ViewBinding
