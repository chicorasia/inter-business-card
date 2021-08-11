# BusinessCard by chicorasia

App desenvolvido no módulo *Criando um app de cartão de visitas em Kotlin* do bootcamp Inter Android Developer na Digital Innovation One, instrutor Igor Rotondo Baglioti.

Além dos conceitos desenvolvidos nas aulas, o app emprega as seguintes tecnologias e soluções:

- **Injeção de dependências por meio da biblioteca Koin.** Adotei essa solução porque tenho bastante familiaridade com a ferramenta e acredito que deixa o código mais enxuto, além de simplificar a injeção de dependências nos ViewModels e outros componentes.
- **Arquitetura MVVM baseada em Fragments**. Optei por organizar o app em Fragments com ViewModel; a MainActivity tem somente com a responsabilidade de manter o NavHostController. Adotei essa solução por ser uma arquitetura mais atual.
- **Android Navigation Component**. Optei por essa solução, em conjunto com os Fragments, porque acredito que dá mais flexibilidade e permite fluxos de navegação mais sofisticados.
- **DataBinding e Listener Bindings**. Adotei essas soluções, sempre que possível, para reduzir o acoplamento do código e ter mais flexibilidade. Com isso, os clickListeners nos Fragments foram substituídos por Listener Bindings nos arquivos de layout XML.
- **Boas práticas de uso de estilos, dimensions, etc.** Procurei adotar melhores práticas para a padronização dos componentes visuais nos arquivos XML, tais como o uso de estilos standard do Material Design, extração de atributos @dimen e @string e outras ferramentas, visando a uniformidade, acessibilidade e manutenabilidade do app.
- **Versionamento segundo princípios de GitFlow.** 
- **Clean Architecture**

****

:computer:chicorialabs.com.br/blog

****

🧡 Inter Android Developer bootcamp 2021

