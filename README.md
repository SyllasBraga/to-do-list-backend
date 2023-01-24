# to-do-list-backend
Projeto para colocar em prática os meus conhecimentos em Java, Spring e MySql.

# Documentação das requisições

[GET] Obter todos os usuários: /usuario

[GET] Obter usuário específico pelo id: /usuario/{idUsuario}

[POST] Criar usuário: /usuario

    {
    "nome": "string",
    "email": "string",
    "senha": "string"
    }

[PUT] Atualizar usuário: /usuario/{idUsuario}

    {
    "nome": "string",
    "login": "string",
    "senha": "string"
    }

[DELETE] Deletar usuário: /usuario/{idUsuario}

[POST] Criar tarefa para usuário específico: /usuario/{idUsuario}/tarefas

    {
    "descricao": "string",
    "statusTarefa": "ENUMS: VENCIDA, CONCLUIDA e PENDENTE",
    "dataCadastro": "date",
    "dataPrazo": "date",
    "dataTermino": date
    }

[PUT] Atualizar tarefa para usuário específico: /usuario/{idUsuario}/tarefas/{idTarefa}

    {
    "descricao": "string",
    "statusTarefa": "ENUMS: VENCIDA, CONCLUIDA e PENDENTE",
    "dataCadastro": "date",
    "dataPrazo": "date",
    "dataTermino": date
    }

[DELETE] Deletar tarefa para usuário específico: /usuario/{idUsuario}/tarefas/{idTarefa}

[PUT] Atualizar status da tarefa para usuário específico: /usuario/{idUsuario}/tarefas/{idTarefa}/updatestatus?status={codStatus}

    codStatus=1, atribuirá a tarefa de CONCLUIDA
    codStatus=2, atribuirá a tarefa de PENDENTE 
    codStatus=3, atribuirá a tarefa de VENCIDA

