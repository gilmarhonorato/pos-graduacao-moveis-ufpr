# 📱 Pós-Graduação em Dispositivos Móveis - UTFPR

Repositório público criado para registrar a trajetória, os códigos e a evolução ao longo da Pós-Graduação em Dispositivos Móveis (UTFPR)

## 🎯 Sobre o Projeto

- **Objetivo:** Documentar estudos, práticas e evolução ao longo da pós-graduação.
- **Formato:** Monorepo Git estruturado para abrigar múltiplos apps e exercícios agrupados por matéria.
- **Visibilidade:** Repositório público visando o compartilhamento de aprendizado e arquitetura com a comunidade de desenvolvedores.
- **Metodologia:** A universidade fornece gravações das aulas, que são utilizadas para revisão e aplicação prática neste repositório. Cada disciplina se torna uma pasta raiz no monorepo. Dentro dela, residem várias aplicações Android (aulas e exercícios), cada uma configurada como um projeto Gradle independente.

## 📚 Disciplinas

Tabela atualizada conforme o avanço dos módulos na pós-graduação. Novas matérias seguem o padrão de nomenclatura `kebab-case` na raiz, subdivididas em `/aulas` e `/exercicios`.


| Diretório           | Disciplina       | Professor | Status         |
| ------------------- | ---------------- | --------- | -------------- |
| `android-basico/`   | Android Básico   | Robison   | Em andamento ⏳ |
| `android-aplicado/` | Android Aplicado | —         | Planejada 🔜   |


## 🏗️ Estrutura do Monorepo

O repositório é organizado para facilitar a navegação entre diferentes contextos e aplicativos completos. Cada pasta de aula ou exercício pode conter um app Android completo (`app/`, `gradle/`, etc.).

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

## ⚙️ Convenções e Configurações

- **Nomenclatura:** Uso estrito de `kebab-case` para pastas (`android-basico`, `aula-01-introducao`).
- **Ordenação:** Numeração de pastas com zero à esquerda (`aula-01`, `exercicio-02`) para manter a linha do tempo cronológica.
- **Documentação:** Cada módulo possui seu próprio `README.md` detalhando as atividades.
- `**.gitignore` global:** Configurado na raiz para ignorar artefatos de compilação do Android (`.gradle/`, `build/`, `.idea/`, `local.properties`) em todas as subpastas simultaneamente.
- `**.cursor/`:** Contexto e regras de inteligência artificial para apoio nas atividades de aprendizado com o editor Cursor.

## 🔄 Fluxo de Trabalho (Git Flow)

Toda nova aula, exercício ou ajuste segue um ciclo rigoroso de integração para manter o rastreamento e o histórico limpo:

1. **Criar Issue:** Mapeia a tarefa a ser realizada (ex: criação da tarefa `#2` para atualizar a documentação principal).
2. **Criar Branch:** O desenvolvimento ocorre em ambiente isolado utilizando o ID da issue (ex: `git checkout -b 2-atualizacao-readme-da-raiz-do-projeto`).
3. **Commit Semântico:** Códigos salvos com referência direta à tarefa utilizando a palavra-chave para fechamento automático da issue (ex: `git commit -m "docs: atualiza documentacao do readme principal (Closes #2)"`).
4. **Merge e Limpeza:** Envio da branch para o servidor (`git push -u origin 2-atualizacao-readme-da-raiz-do-projeto`), realização do merge na branch `main` e exclusão da branch temporária para manter a organização do repositório.

## 📄 Licença

Este projeto está sob a licença estabelecida no arquivo `LICENSE`.