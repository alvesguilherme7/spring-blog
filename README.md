# Blog Fmk

### Passos para Execução

1. Clone <br />
    ``git clone https://gitlab.com/alvesguilherme/desafio.git``

2. Criar banco de dados no postgresql <br />
   `` CREATE DATABASE blogfmk ``
   
3. Entrar no pasta do projeto e executar no terminal <br />
   ``mvn spring-boot:run -Dspring-boot.run.arguments=" --urlbanco=jdbc:postgresql://localhost:5432/blogfmk --usuario=postgres --senha=postgresql"``
   
Mude os argumentos de acesso ao banco, se necessário.


### Requisitos Atendidos
1. Segurança <br />
Permitir o cadastro de usuários e login com autenticação via token JWT.
   
2. Post <br />
Post	Permitir o cadastro e consulta de posts com texto, imagens e links.
Apenas o criador do post poderá ter permissão para excluí-lo.

3. Comentários <br />
Suportar a adição e exclusão de comentários em posts. Os posts
poderão ser visíveis a todos os usuários. Apenas o criador do comentário poderá ter permissão para excluí-lo.

### Requisitos NÃO Atendidos
1. Fotos/Álbum <br />
   Permitir a criação de álbuns de fotos. As fotos dos álbuns poderão ser visíveis a todos os usuários. Apenas o dono de um álbum poderá excluí-lo.
2. Front-end em Angular 8 


### Exemplos Requests
https://www.getpostman.com/collections/db40adbd7ca55a59bade

