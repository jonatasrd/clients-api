# Client API

---
API com finalidade de expor serviços para criar, editar, consultar, excluir e atualizar a wishlist de clientes.

#### Solução técnica
    Foi desenvolvida em Kotlin utilizando MongoDb como base de dados, visando maior velocidade de resposta de leitura.
    Alguns endpoints recebem as request e colocam em uma fila e são processadas em background, dando maior velocidade de resposta aos usuários;
    Esse mecanismo de mensageria está sendo feito com RabbitMQ.

#### Endpoints disponíveis
    
* Criar cliente - cria e devolve um cliente
* Editar cliente - assíncrono
* Excluir cliente - assíncrono
* Pesquisar clientes - devolve uma lista de clientes por página 
* Pesquisar um cliente por ID - devolve o cliente pesquisado
* Adicionar produtos na wishlist - assíncrono
* Remover produtos da wishlist - assíncrono

Nos endpoints assíncronos os processos serão feitos em background evitando espera nas requests dos clients; 

Os produtos a serem adicionados na wishlist são encontrados na [api products](https://gist.github.com/Bgouveia/9e043a3eba439489a35e70d1b5ea08ec).

---

### Como executar a API

A IDE utilizada para desenvolvimento foi o IntelliJ, e fica como sugestão para avaliação do código (funciona legal com Kotlin);

* Pré-requisitos: ter instalado o docker, gradle e o Java 11.
  
Dentro da pasta do projeto `clients-api` podem ser executados os seguintes comandos no terminal:

* `docker-compose up -d` para construir o ambiente local;
    * `docker ps` para verificar se existem os seguintes containers rodando: `clients_mongodb`, `clients_rabbitmq` e `clients_keycloak`;
* `./gradlew bootRun`, para o start da aplicação (obrigatoriamente ter executado com sucesso o comando acima);

Com a aplicação rodando é hora de testar os endpoints. Para isso deve ser importado o arquivo `LuizaLabs.postman_collection.json`,
que se encontra na raiz do projeto no [Postman](https://www.postman.com/downloads/). Existe um endpoint de Token que deve ser executado antes de fazer as chamadas
para os endpoints (Após executado, ele armazenara o token e adicionará no headers dos endpoints como Bearer Token automaticamente). 
___

### Urls disponíveis

    Clients-api: http://localhost:8080/
    Swagger: http://localhost:8080/swagger-ui.html
    RabbitMQ: http://localhost:15672

___
