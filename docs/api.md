# API

## Sumário

- [Rotas](#rotas)
- [Formato das tarefas](#formato-das-tarefas)
- [Exemplos](#exemplos)
- [Erros](#erros)

## Rotas

A API responde em http://localhost:3000 e troca dados em JSON.

| Método | Rota | O que faz |
| --- | --- | --- |
| `GET` | `/api/hello` | responde `Hello, World!` para testar o servidor |
| `GET` | `/api/todos` | lista as tarefas, da mais nova para a mais antiga |
| `POST` | `/api/todos` | cria uma tarefa |
| `POST` | `/api/todos/:id/toggle` | alterna entre pendente e concluída |
| `DELETE` | `/api/todos/:id` | exclui uma tarefa |

## Formato das tarefas

Cada tarefa é representada no formato abaixo.

```json
{
  "id": 1,
  "title": "Revisar o README",
  "description": null,
  "completed": false,
  "created-at": "2026-11-17T14:20:15.005Z"
}
```

## Exemplos

Para listar as tarefas, rode o comando abaixo.

```bash
curl http://localhost:3000/api/todos
```

A resposta traz a lista dentro de `todos`.

```json
{ "todos": [{ "id": 1, "title": "Revisar o README", "completed": false }] }
```

Para criar uma tarefa, envie o título no corpo. A API responde com status 201 e a tarefa criada.

```bash
curl -X POST http://localhost:3000/api/todos \
     -H "Content-Type: application/json" \
     -d '{"title": "Revisar o README"}'
```

Para marcar ou desmarcar, use a rota de toggle com o id. A resposta traz a tarefa com o `completed` invertido.

```bash
curl -X POST http://localhost:3000/api/todos/1/toggle
```

Para excluir, use `DELETE`. A resposta traz a tarefa excluída.

```bash
curl -X DELETE http://localhost:3000/api/todos/1
```

## Erros

As respostas de erro trazem um campo `error` e o status HTTP correspondente.

| Status | Quando acontece |
| --- | --- |
| 400 | título vazio ou ausente |
| 404 | id inexistente ou que não é um número |

```json
{ "error": "Todo não encontrado" }
```
