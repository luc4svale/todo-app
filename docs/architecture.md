# Arquitetura

## Sumário

- [Visão geral](#visão-geral)
- [Backend](#backend)
- [Frontend](#frontend)
- [Estilos](#estilos)
- [Banco de dados](#banco-de-dados)

## Visão geral

A aplicação roda em dois processos. O backend atende a API REST na porta 3000. O frontend roda no servidor de desenvolvimento do Shadow-CLJS na porta 8000, que é o endereço aberto no navegador. As requisições do frontend à API são feitas com `fetch`, e o backend autoriza essa origem com CORS.

## Backend

```
src/todo/backend/
  core.clj
  http/app.clj
  http/request.clj
  http/response.clj
  http/handlers/todos.clj
  db/core.clj
  db/schema.clj
  db/todos.clj
```

O `core.clj` é o ponto de entrada. Ele prepara o banco e inicia o servidor Jetty.

A pasta `http` reúne a camada HTTP. O `app.clj` monta as rotas do Reitit e a sequência de middlewares, com CORS primeiro e conversão de JSON depois. Os handlers em `handlers/todos.clj` recebem a requisição, validam os dados e chamam o banco. O `request.clj` lê o id da URL e o `response.clj` padroniza os status e o formato dos erros.

A pasta `db` reúne o acesso ao banco. O `core.clj` abre a conexão com o `prod.db` e configura o next.jdbc para devolver mapas com chaves simples, como `:id` e `:created-at`. O `schema.clj` cria a tabela se ela ainda não existir. O `todos.clj` tem as consultas e converte o `completed` do SQLite, que chega como 0 ou 1, em booleano.

Com essa divisão, uma mudança no banco fica restrita a `db` e uma mudança de rota fica restrita a `http`.

## Frontend

```
src/todo/frontend/
  core.cljs
  api.cljs
  state.cljs
  actions.cljs
  views/app.cljs
  views/todos.cljs
  views/filters.cljs
  views/modal.cljs
```

O `core.cljs` monta o React na página e carrega as tarefas. O `api.cljs` faz as chamadas HTTP e transforma erros da API em mensagens. O `state.cljs` guarda o estado da tela num `r/atom` e calcula a lista visível de acordo com o filtro. O `actions.cljs` chama a API e atualiza o estado com a resposta.

A pasta `views` guarda os componentes. O `todos.cljs` tem o formulário e a lista, o `filters.cljs` tem os botões de status e o `modal.cljs` tem o diálogo de confirmação. O `app.cljs` monta a tela com esses componentes.

O campo de nova tarefa tem o texto lido do formulário apenas no envio. Um campo ligado ao estado do Reagent é redesenhado a cada tecla, o que em alguns navegadores separa o til ou o acento agudo da letra.

## Estilos

```
resources/public/
  index.html
  css/base.css
  css/layout.css
  css/form.css
  css/todos.css
  css/filters.css
  css/modal.css
```

O `base.css` define as cores, a fonte e o reset, e cada um dos demais estiliza uma parte da tela. O `index.html` carrega os seis com `<link>`, o que permite ao navegador baixá-los em paralelo.

## Banco de dados

Os dados ficam no arquivo `prod.db`, na raiz do projeto, fora do controle de versão pelo `.gitignore`.

| Coluna | Tipo | Uso |
| --- | --- | --- |
| `id` | INTEGER | chave gerada pelo banco |
| `title` | TEXT | título da tarefa |
| `description` | TEXT | descrição opcional |
| `completed` | BOOLEAN | 0 para pendente e 1 para concluída |
| `created_at` | TEXT | data de criação, usada para ordenar a lista |
