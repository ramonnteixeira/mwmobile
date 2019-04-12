# Minha Garantia Mobile

Este aplicativo tem por objetivo facilitar o gerenciamento de garantias.
Muitas vezes deixamos para outro dia a revisão de um problema, e por muitas vezes perdemos prazo de garantia.
Outro problema recorrente é a perca do cupom fiscal.

Com este aplicativo, você poderá saber quando um produto está para perder sua garantia, e recuperar seus cupoms.


## Contribuidores

Ramon Nunes Teixeira


## Arquitetura do projeto

Foi utilizado o padrão MVC, utilizando uma persistência de banco local no SQLite.
O projeto se encontra internacionalizado para padrao en-US e pt-BR.

A camada Model foi dividido em 3 pacotes

* entity: pacote para manter a modelagem de dados
* repository: dentro dele há interfaces de repositório e subpacotes com implementações específicas. Até o momento foi implementado apenas o jdbc, porem em proximas versões possivelmente entrará repositorios REST.
* util: Utilitarios exclusivos da modelagem, como definições de banco de dados

Foi seguido um padrão semelhante ao do flyway para migração de banco de dados, necessitando apenas incluir novos scripts de acordo com a versão do banco. Ex: "V__002.sql" dentro de assets/migrations


A camada view foi dividida em Scenes e Adapters, para diferenciar classes que manipulam uma tela em específico, ou um componente reutilizavel.

A camada controller não houve divisão.


## Futuras versões

[x] MVP-001: Criar arquitetura com banco de dados local, internacionalização, cadastro básico e uma listagem

[] MVP-002: Definição de layout completa, com componentes de gauge, exibição de imagens.

[] MVP-003: Backup/Restore no google drive

[] MVP-004: Cadastro de garantia extendida e dados de assistência técnica.

[] MVP-005: Sincronização com aplicação cloud no modelo offline-first
