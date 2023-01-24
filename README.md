# to-do-list-backend
Projeto para colocar em prática os meus conhecimentos em Java, Spring e MySql.

# Documentação das requisições

[GET] Obter todos os usuários: {url}/usuario

[GET] Obter usuário específico pelo id: {url}/usuario/{idUsuario}

[POST] Criar usuário: {url}/usuario

    {
    "nome": "string",
    "email": "string",
    "senha": "string"
    }

[PUT] Atualizar usuário: {url}/usuario/{idUsuario}

    {
    "nome": "string",
    "login": "string",
    "senha": "string"
    }

[DELETE] Deletar usuário: {url}/usuario/{idUsuario}

[POST] Criar tarefa para usuário específico: {url}/usuario/{idUsuario}/tarefas

    {
    "descricao": "string",
    "statusTarefa": "ENUMS: VENCIDA, CONCLUIDA e PENDENTE",
    "dataCadastro": "date",
    "dataPrazo": "date",
    "dataTermino": date
    }

[PUT] Atualizar tarefa para usuário específico: {url}/usuario/{idUsuario}/tarefas/{idTarefa}

    {
    "descricao": "string",
    "statusTarefa": "ENUMS: VENCIDA, CONCLUIDA e PENDENTE",
    "dataCadastro": "date",
    "dataPrazo": "date",
    "dataTermino": date
    }

[DELETE] Deletar tarefa para usuário específico: {url}/usuario/{idUsuario}/tarefas/{idTarefa}

[PUT] Atualizar status da tarefa para usuário específico: {url}/usuario/{idUsuario}/tarefas/{idTarefa}/updatestatus?status=0
status=1, atribuirá a tarefa de CONCLUIDA
status=2, atribuirá a tarefa de PENDENTE
status=3, atribuirá a tarefa de VENCIDA

