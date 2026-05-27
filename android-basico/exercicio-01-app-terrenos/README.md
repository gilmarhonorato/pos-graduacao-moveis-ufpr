# [Exercício 1] Calculadora de Área de Terrenos (Retângulos)

## 📝 Enunciado do Projeto
Para fixar a estrutura de layouts híbridos, o uso de `LinearLayout`, `EditText` com restrições numéricas, `TextView` com fontes destacadas e `Button` lado a lado, você criará a interface de um aplicativo para calcular a área total de um terreno em metros quadrados e limpar os dados.

Desenvolva um arquivo de layout XML (`activity_main.xml`) estruturado verticalmente. O aplicativo deve permitir que o usuário digite a **Largura** e o **Comprimento** do terreno (ambos aceitando números decimais). Abaixo, deve exibir o rótulo "Resultado:" seguido pelo valor calculado em destaque. No final da tela, devem existir dois botões posicionados lado a lado: um para "Calcular" e outro para "Limpar".

---

## 💡 Dicas de Implementação
* **Contêiner Principal:** Comece seu XML com um `LinearLayout` configurado com a orientação correta para empilhar os blocos de cima para baixo. Não se esqueça de declarar os namespaces (`xmlns:android`, etc.) que o Android Studio gera automaticamente.
* **Dicas de Entrada:** Nos campos onde o usuário vai digitar a largura e o comprimento, utilize a propriedade de dica para indicar o que deve ser digitado (ex: `android:hint="Largura em metros"`) e configure o teclado para aceitar apenas decimais.
* **Ajuste de Botões:** Para colocar os botões de "Calcular" e "Limpar" um ao lado do outro, você precisará abrir um novo `LinearLayout` de orientação específica dentro do seu layout principal (aninhamento). Lembre-se de fechar essa tag após os botões.
* **Dimensões:** Brinque com o `match_parent` nos campos de texto para que eles ocupem a largura total da tela, e utilize o `wrap_content` para a altura dos componentes para que eles não esmaguem uns aos outros.
* **Resultado em Destaque:** No campo que exibirá o número final do cálculo, aplique uma propriedade para que o texto fique visivelmente maior que os rótulos de identificação (`android:textAppearance`).

---

## ✅ Critérios de Aceite (Como Testar)
Abra a aba *Preview* (ou *Split*) no Android Studio ou rode o aplicativo em seu emulador/dispositivo real e valide os seguintes pontos:

- [ ] **Orientação Vertical:** Os campos de Largura, Comprimento e o painel de Resultado estão empilhados perfeitamente um abaixo do outro?
- [ ] **Alinhamento Horizontal dos Botões:** Os botões "Calcular" e "Limpar" estão posicionados lado a lado na mesma linha na parte inferior?
- [ ] **Dicas Visuais (Hints):** Ao olhar para os campos vazios, é possível ler as instruções cinzas indicando onde colocar a largura e o comprimento?
- [ ] **Restrição do Teclado:** Ao clicar em um dos campos de digitação (`EditText`), o teclado que se abre exibe apenas números e o ponto/vírgula decimal?
- [ ] **Destaque do Resultado:** O texto ou número reservado para o resultado final está visivelmente maior que os textos de "Largura" e "Comprimento"?
