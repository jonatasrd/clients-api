# Client API

---
A API tem a finalidade de expor serviços para criar, editar, consultar, excluir clientes e uma lista de produtos favoritos.

---
### Como rodar localmente

* Pré-requisitos: Instalar o docker, docker-compose e configurar para execução sem sudo;
* Se for a primeira vez, execute o comando `./gradlew setupLocalDb` para construir (com o docker) o ambiente o DB local;
* Execute a script `./gradlew startLocalEnv`, que se encarregará de subir a docker do banco (restart), e executar a aplicação no profile default;
