# [Exercício 3] Registro de Ponto Eletrônico Diário

## 📝 Enunciado do Projeto
Crie o layout de uma tela de registro de ponto para colaboradores. A tela deve começar com um título fixo no topo: "Registro de Ponto". Abaixo, coloque um campo de texto que permita digitar apenas números inteiros para o usuário inserir a sua **Matrícula**. 

Logo abaixo, organize **dois blocos de botões em linhas separadas**:
* **Linha 1 (Lado a lado):** Botão "Entrada Manhã" e Botão "Saída Manhã".
* **Linha 2 (Lado a lado):** Botão "Entrada Tarde" e Botão "Saída Tarde".

No final da página, coloque um `TextView` centralizado que funcionará como um painel de status, exibindo uma mensagem informativa como: "Último registro: Nenhum".

---

## 💡 Dicas de Implementação
* **Restrição Diferente de Input:** Para o campo de matrícula, utilize a propriedade `android:inputType="number"` para restringir o teclado apenas para números inteiros (sem decimais/vírgulas), já que matrículas não possuem pontos flutuantes.
* **Múltiplos Aninhamentos:** Para estruturar esta tela, o seu `LinearLayout` vertical principal conterá o título, o campo de matrícula, **dois** `LinearLayouts` horizontais independentes (um para os botões da manhã e outro para os botões da tarde) e, por fim, o texto de status.
* **Margens e Espaçamento:** Como esta tela tem muitos botões, lembre-se de usar `wrap_content` para a altura (`android:layout_height`) de todos os elementos para que eles caibam confortavelmente sem se sobrepor ou sumir da tela.

---

## ✅ Critérios de Aceite (Como Testar)
Abra a aba *Preview* (ou *Split*) no Android Studio ou rode o aplicativo em seu emulador/dispositivo real e valide os seguintes pontos:

- [ ] **Restrição de Matrícula:** O campo de matrícula aceita apenas números inteiros e barra a digitação de letras, pontos ou caracteres especiais?
- [ ] **Bloco do Turno da Manhã:** Os botões "Entrada Manhã" e "Saída Manhã" formam uma linha horizontal perfeita?
- [ ] **Bloco do Turno da Tarde:** Os botões "Entrada Tarde" e "Saída Tarde" formam uma segunda linha horizontal logo abaixo da primeira?
- [ ] **Visibilidade do Status:** O texto de status no final da tela está visível e centralizado, garantindo que não foi empurrado para fora dos limites visíveis do dispositivo?

---
*Mãos à obra! Se surgir qualquer dúvida ou se quiser validar a estrutura do seu XML, use os comentários desta Issue.*
