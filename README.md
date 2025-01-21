# app-sicredi-sistema-votacao
API Rest de votação para associados

# Objetivo

No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembléias, por votação.
Com isso a solução que a API propõe em resolver é a gestão e controle das sessões de votação.

# Regra de negócios

* Cadastrar uma nova pauta;
* Abrir uma sessão de votação em uma pauta(a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default);
* Receber votos dos associados em pautas(os votos são apenas: 'Sim/Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta).
* Contabilizar os votos e dar o resultado da votação na pauta.


# Tenologias

* Java 17
* Banco de dados (AWS DynamoDB)
* WebFlux (Programação Reativa)
* Spring Boot 3
* Mensageria AWS SQS
* Junit 5 e Project Reactor Test
