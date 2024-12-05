# INTEGRAÇÃO IFLEET2 API 
*Este serviço tem por finalidade buscar os dados da api do Ifleet do servidor externo e reunir essas informações em um script SQL 
a fim de movimentar essas informações para o Datalake.* 


# O QUE SERÁ CRIADO?
* Um middleware na arquitetura REST/JSON cuja finalidade é buscar informações de uma API externa.

# OBSERVAÇÕES
* Este projeto será construído com um banco POSTGRESQL a fim de otimizar a compatibilidade/desempenho.
* A criação das tabelas pode ser feita ao habilitar a opção DDL-AUTO: UPDATE do Hibernate.
* No ambiente DEV ainda serão utilizadas variáveis de ambiente.
* No momento este projeto ainda está sendo desenvolvido e eventualmente será disponibilizado em algum serviço cloud.

