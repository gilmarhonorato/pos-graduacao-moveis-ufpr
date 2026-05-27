# pos-graduacao-moveis-ufpr

Repositório público para registrar a trajetória da **Pós-Graduação em Dispositivos Móveis (UTFPR/UFPR)**.

Cada disciplina vira uma pasta no monorepo. Dentro dela ficam **várias aplicações Android** (aulas e exercícios), cada uma como projeto Gradle independente.

## Sobre o projeto

- **Objetivo:** documentar estudos, práticas e evolução ao longo da pós.
- **Formato:** monorepo Git com múltiplos apps e exercícios por matéria.
- **Visibilidade:** repositório público para compartilhar o trabalho em aprendizado com a comunidade.

A universidade grava as aulas; uso as gravações para revisar e aplicar na prática neste repositório.

## Disciplinas

Atualize esta tabela sempre que entrar uma nova matéria na pós.

| Pasta | Disciplina | Professor | Status |
|-------|------------|-----------|--------|
| [`android-basico/`](android-basico/) | Android Básico | Robison | Em andamento |
| [`android-aplicado/`](android-aplicado/) | Android Aplicado | — | Planejada |

Novas matérias seguem o mesmo padrão: pasta em `kebab-case` na raiz + `aulas/` + exercícios numerados.

## Estrutura do monorepo

```text
pos-graduacao-moveis-ufpr/
├── android-basico/
│   ├── aulas/
│   │   ├── aula-01-introducao/
│   │   └── aula-02-troca-tela/
│   ├── exercicio-01-app-terrenos/
│   ├── exercicio-02-app-financiamento/
│   ├── exercicio-03-app-ponto-eletronico/
│   └── README.md
├── android-aplicado/
│   ├── aulas/
│   └── README.md
├── scripts/
├── .github/
│   ├── ISSUE_TEMPLATE/
│   └── pull_request_template.md
├── .cursor/
├── .gitignore
└── README.md
```

Cada pasta de aula ou exercício pode conter um app Android completo (`app/`, `gradle/`, etc.).

## Convenções

- Pastas em **kebab-case** (`android-basico`, `aula-01-introducao`).
- Numeração com zero à esquerda (`aula-01`, `exercicio-02`).
- Documentação sempre em **`README.md`**.

## Configuração do repositório

- **`.gitignore`** na raiz: ignora artefatos Android (`.gradle/`, `build/`, `.idea/`, `local.properties`, etc.) em qualquer subpasta.
- **`.cursor/`**: contexto e regras para apoio nas atividades de aprendizado com o Cursor.

## Fluxo de trabalho (issue → branch → PR)

Toda aula ou exercício novo segue o mesmo ciclo para manter rastreabilidade.

1. **Criar issue**
   ```bash
   gh issue create --title "[android-basico] aula-03-navegacao" --body "Implementar aula e exercícios relacionados"
   ```

2. **Criar branch** (use o número da issue)
   ```bash
   git checkout -b "feat/123-android-basico-aula-03-navegacao"
   ```

3. **Desenvolver, commitar e referenciar a issue**
   ```bash
   git add .
   git commit -m "feat(android-basico): concluir aula-03 (#123)"
   ```

4. **Abrir Pull Request e mergear** (fecha a branch após o merge)
   ```bash
   git push -u origin HEAD
   gh pr create --fill
   gh pr merge --squash --delete-branch
   ```

   Alternativas de merge:
   ```bash
   gh pr merge --merge --delete-branch
   gh pr merge --rebase --delete-branch
   ```

5. **Próxima entrega:** nova issue + nova branch

Padrões sugeridos:

- Branch: `feat/<issue-id>-<disciplina>-<aula-ou-exercicio>`
- Commit: `feat(<disciplina>): descrição (#<issue-id>)`

Templates em [`.github/ISSUE_TEMPLATE/`](.github/ISSUE_TEMPLATE/) e [`.github/pull_request_template.md`](.github/pull_request_template.md).

## Como adicionar uma nova disciplina

1. Criar pasta na raiz: `nome-da-disciplina/`
2. Criar `nome-da-disciplina/README.md` e `nome-da-disciplina/aulas/`
3. Atualizar a tabela **Disciplinas** neste arquivo
4. Abrir issue + branch para a primeira aula ou exercício

## Licença

Ver [LICENSE](LICENSE).
