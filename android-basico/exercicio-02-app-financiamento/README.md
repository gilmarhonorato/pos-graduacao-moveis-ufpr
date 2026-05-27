# [Exercício 2] Simulador de Financiamento de Veículos

## 📝 Enunciado do Projeto
Desenvolva a interface de um simulador de parcelas de financiamento. A tela deve ser estruturada verticalmente e conter campos para o usuário digitar o **Valor do Veículo**, o **Valor da Entrada** e a **Taxa de Juros Mensal** (todos aceitando apenas números decimais). 

Abaixo, adicione um componente para exibir o "Valor a Financiar: R$ 0,00" em tamanho de fonte normal. Logo abaixo, crie um bloco para o usuário selecionar a quantidade de parcelas através de **três botões posicionados lado a lado**: "24x", "36x" e "48x". No rodapé da tela, exiba o "Valor Estimado da Parcela:" com um texto bem grande e destacado.

---

## 💡 Dicas de Implementação
* **Controle de Input:** Garanta que todos os três campos de texto iniciais utilizem a propriedade para abrir o teclado numérico com suporte a decimais (`android:inputType="numberDecimal"`), evitando erros de digitação.
* **Aninhamento Triplo:** Para os três botões de parcelas ("24x", "36x", "48x") ficarem na mesma linha, você precisará de um `LinearLayout` horizontal interno. Defina a largura deles como `wrap_content` ou divida o espaço proporcionalmente utilizando pesos (`android:layout_weight`), se preferir.
* **Destaque do Texto:** O campo de "Valor Estimado da Parcela" deve usar uma aparência de texto grande (`android:textAppearance="@android:style/TextAppearance.Large"`) para chamar a atenção do usuário, simulando a resposta principal do ecrã.

---

## ✅ Critérios de Aceite (Como Testar)
Abra a aba *Preview* (ou *Split*) no Android Studio ou rode o aplicativo em seu emulador/dispositivo real e valide os seguintes pontos:

- [ ] **Empilhamento Vertical:** Os campos de Texto (Valor, Entrada, Juros) estão empilhados perfeitamente na vertical?
- [ ] **Alinhamento dos Botões:** Os botões "24x", "36x" e "48x" aparecem lado a lado, sem que nenhum deles fique cortado na lateral da tela?
- [ ] **Dicas Visuais (Hints):** Todos os campos de texto exibem uma dica clara (ex: `android:hint="Digite o valor da entrada"`) quando estão vazios?
- [ ] **Restrição do Teclado:** O teclado numérico decimal abre-se corretamente ao focar em qualquer um dos campos de inserção de dados?
