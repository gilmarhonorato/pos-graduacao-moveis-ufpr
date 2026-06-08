# Marcador de Truco

Aplicativo Android para marcação de pontos no jogo de **Truco**, desenvolvido como trabalho final da disciplina **Android Básico** — Especialização em Programação para Dispositivos Móveis, UTFPR Campus Pato Branco.

## Sobre o projeto

O app permite registrar a pontuação de dois jogadores durante uma partida de Truco. Cada jogador pode receber **+1, +3, +6, +9 ou +12** pontos por jogada. Ao atingir **12 pontos ou mais**, o jogador vence a partida, a pontuação da mão é zerada e o contador de vitórias é incrementado.

## Funcionalidades

- **Tela principal:** exibe nomes e pontuação atual dos dois jogadores, com botões de incremento (+1, +3, +6, +9, +12) para cada um
- **Histórico de Jogadas:** mostra quantas partidas cada jogador ganhou desde o último reinício
- **Informar Nomes:** permite personalizar os nomes dos Jogadores 1 e 2
- **Zerar Histórico:** reinicia pontuação e contador de partidas ganhas
- **Fim de partida:** exibe `AlertDialog` quando um jogador atinge 12 pontos ou mais

## Telas

| Tela | Activity | Descrição |
|------|----------|-----------|
| Principal | `MainActivity` | Marcação de pontos e acesso às demais funções |
| Histórico | `HistoryActivity` | Partidas ganhas por jogador |
| Nomes | `EditNamesActivity` | Edição dos nomes dos jogadores |

## Tecnologias

- **Linguagem:** Kotlin
- **UI:** XML Layouts + View Binding
- **Componentes:** Material Design 3, ConstraintLayout, AlertDialog, Snackbar
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 36
- **Build:** Gradle 9.4.1

## Estrutura do projeto

```
trabalho-final/
├── app/
│   └── src/main/
│       ├── java/com/exemplo/marcador_truco/
│       │   ├── MainActivity.kt
│       │   ├── HistoryActivity.kt
│       │   └── EditNamesActivity.kt
│       └── res/
│           ├── layout/
│           ├── values/
│           └── values-pt-rBR/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew
```

## Como executar

### Pré-requisitos

- [Android Studio](https://developer.android.com/studio) (versão recente recomendada)
- JDK 11 ou superior
- Emulador Android ou dispositivo físico com depuração USB ativada

### Pelo Android Studio

1. Clone o repositório
2. Abra a pasta `android-basico/trabalho-final` no Android Studio
3. Aguarde a sincronização do Gradle
4. Selecione um emulador ou dispositivo
5. Clique em **Run** (▶)

### Pelo terminal

```bash
cd android-basico/trabalho-final
./gradlew assembleDebug
```

O APK será gerado em `app/build/outputs/apk/debug/app-debug.apk`.

## Requisitos atendidos (trabalho acadêmico)

| Questão | Descrição |
|---------|-----------|
| 1 | Interface visual com 4 TextViews e 13 botões na tela principal |
| 2 | Lógica de pontuação com incrementos 1, 3, 6, 9 e 12; vitória com ≥ 12 pontos |
| 3 | Tela de histórico com partidas ganhas por jogador |
| 4 | Tela para informar nomes personalizados dos jogadores |
| 5 | Botão para zerar pontuação e contador, com mensagem informativa |

## Observações

- A pontuação e o histórico são armazenados **em memória** (variáveis inteiras)
- O arquivo `local.properties` é gerado automaticamente pelo Android Studio e **não** deve ser versionado (contém o caminho local do SDK)
- Pastas como `build/`, `.gradle/` e `.idea/` também são ignoradas pelo Git

## Identificação

- **Application ID:** `com.exemplo.marcador_truco`
- **Nome do app:** Truco
