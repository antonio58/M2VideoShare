1º Copiar os ficheiros da pasta JSON para a vossa diretoria do mongo (onde têm o mongo, mongod, mongoimport...)

2º Correr o daemon do mongo 
sudo ./mongod

3º Abrir outro terminal e executar:
sudo ./mongo

> use VideoHub
> db.dropDatabase()
> { "dropped" : "VideoHub", "ok" : 1 }
> exit
bye

3º Executar estes comandos no terminal:

./mongoimport --db VideoHub --collection Delivery --file Delivery.json
./mongoimport --db VideoHub --collection OBU --file OBU.json
./mongoimport --db VideoHub --collection Users --file Users.json
./mongoimport --db VideoHub --collection System --file System.json
./mongoimport --db VideoHub --collection Videos --file Videos.json

5º Confirmar que ficou direito:

./mongo

> show dbs
VideoHub  0.000GB
admin     0.000GB
local     0.000GB
> use VideoHub
switched to db VideoHub
> show collections
Delivery
OBU
System
Users
Videos
> exit
bye