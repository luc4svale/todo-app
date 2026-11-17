# Decisões de processo

## Sumário

- [Divisão do trabalho](#divisão-do-trabalho)
- [Diferenças em relação ao tutorial](#diferenças-em-relação-ao-tutorial)
- [Mensagens dos marcos](#mensagens-dos-marcos)
- [Melhorias depois do tutorial](#melhorias-depois-do-tutorial)

## Divisão do trabalho

O backend e o banco de dados ficaram com Antonio Lucas, e o frontend com Izadora. O trabalho seguiu em blocos sequenciais, e o histórico de commits mostra essa ordem.

## Diferenças em relação ao tutorial

No tutorial, o CORS só é configurado depois que o navegador bloqueia a primeira requisição do frontend. Neste projeto, o CORS foi configurado logo depois da API em memória, porque o backend ficou pronto antes do frontend. O vídeo mostra o erro que o tutorial apresenta, com o CORS desligado por alguns segundos.

As rotas de marcar e excluir tarefas também foram implementadas no backend antes da interface, junto com a troca do banco em memória pelo SQLite.

## Mensagens dos marcos

A atividade pede sete mensagens de commit como marcos do histórico. Duas ficaram diferentes.

- O primeiro commit usa o tipo `chore`, que o padrão Conventional Commits reserva para tarefas sem funcionalidade nova, como preparar o repositório.
- O quinto marco foi registrado como `feat: conecta frontend com API do backend`, sem o trecho "CORS corrigido", porque o CORS já estava configurado quando o frontend se conectou à API.

## Melhorias depois do tutorial

O tutorial termina no commit `style: aplica o visual final da interface`. As mudanças seguintes foram feitas com autorização do professor.

- Backend separado em uma camada HTTP e outra de banco de dados.
- API com chaves simples, como `title`, e `completed` booleano. O tutorial devolve chaves com o nome da tabela, como `todos/title`, o que obriga o frontend a conhecer o banco.
- Frontend separado em módulos, com interface redesenhada e CSS dividido em um arquivo por parte da tela.
- Filtros entre pendentes e concluídas e confirmação antes de excluir.
- Correção no campo de nova tarefa, que separava o acento da letra em alguns navegadores.
- Remoção dos comentários didáticos herdados do tutorial.
