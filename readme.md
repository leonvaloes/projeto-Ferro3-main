`` docker-compose up -d``

``docker-compose ps -q db``

``vamos chamar esse valor gedado de id``

``docker cp ativooperante.sql {id}:/ativooperante.sql``

``docker exec -it {id} psql -U postgres -d Ativo-Operante -f /ativooperante.sql``
