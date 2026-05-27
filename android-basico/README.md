# Aula: Introdução ao Desenvolvimento de Aplicativos Móveis (UFPR)

Este documento contém o resumo técnico dos conceitos clássicos de desenvolvimento Android em XML abordados pelo professor em sala de aula.

---

## 1. Resumo Técnico (Conceitos Explicados)

### 📱 Abordagem de Interface Clássica
* **Foco no XML:** Desenvolvimento de UI baseado no modelo clássico.
* **Contexto de Mercado:** Embora o *Jetpack Compose* seja a tecnologia moderna (com MVVM e injeção de dependência), o modelo clássico em XML possui uma curva de aprendizado mais suave para iniciantes, aproxima desenvolvedores que vêm do ambiente Web e ainda é amplamente utilizado no mercado de trabalho.

---

## 🏗️ View Groups (Gerenciadores de Layout)
Componentes invisíveis responsáveis por ditar como as estruturas visuais se organizam na tela.

* **Linear Layout:** O gerenciador mais didático. Organiza os componentes em formato linear (um após o outro). 
  * Possui a propriedade obrigatória `android:orientation`, que define se os elementos serão organizados na **vertical** (um abaixo do outro) ou na **horizontal** (um ao lado do outro).
* **Constraint Layout:** Gerenciador baseado em restrições/âncoras (tanto na horizontal quanto na vertical). É o mais utilizado em projetos reais e será aprofundado em paralelo.

---

## 🖼️ Componentes Visuais Básicos (Views)
* **TextView:** Componente de saída de dados, usado para textos estáticos ou para exibir resultados ao usuário.
* **EditText:** Componente de entrada de dados (caixa de texto), permitindo que o usuário digite informações.
* **Button:** Componente de processamento e interação, onde o usuário clica para disparar uma ação.

---

## 📐 Propriedades Obrigatórias de Layout
Todo componente visual inserido no XML precisa obrigatoriamente ter as dimensões de largura e altura declaradas:
* `android:layout_width`
* `android:layout_height`

### Tamanhos Relativos vs. Fixos:
* `match_parent`: Faz o componente ocupar todo o espaço disponível do elemento "pai" (contêiner).
* `wrap_content`: Ajusta o tamanho do componente estritamente ao tamanho do seu conteúdo interno (texto, imagem, etc.).
* `dp` (Pixel de Densidade): Medida usada para definir tamanhos fixos que se ajustam proporcionalmente à densidade de diferentes telas.

---

## ⚙️ Propriedades Avançadas de Componentes
* `android:inputType="numberDecimal"`: Propriedade do `EditText` que restringe o teclado do usuário para aceitar apenas números e uma única vírgula decimal, evitando que ele digite textos textuais de forma errada.
* `android:hint`: Exibe uma dica cinza dentro do `EditText` que desaparece assim que o usuário começa a digitar.
* `android:textAppearance="@android:style/TextAppearance.Large"`: Modifica a aparência estrutural e aumenta o tamanho da fonte de um `TextView`.

---

## 🔄 Conceitos Estruturais
* **Layouts Híbridos (Aninhamento):** Conceito de colocar um `ViewGroup` dentro de outro `ViewGroup` (por exemplo, um `LinearLayout` Horizontal dentro de um `LinearLayout` Vertical) para alcançar designs complexos, como colocar botões lado a lado.
* **ScrollView:** Componente contêiner usado como primeiro elemento da tela para adicionar uma barra de rolagem automática caso os componentes visuais ultrapassem o tamanho físico do visor do dispositivo.
