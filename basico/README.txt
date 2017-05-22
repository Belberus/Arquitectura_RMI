Para probar la versión básica:

1. Lanzar "rmiregistry" desde un ordenador A, estando en la carpeta con los ficheros
2. Lanzar "rmiregistry" desde un ordenador B, estando en la carpeta con los ficheros
3. En el ordenador A:
	3.1 Lanzar "java ServicesBroker <IPBroker> <IPServerA> <IPServerB>" (El Broker esta listo)
4. En el ordenador B:
	4.1 Lanzar "java ServerA <IPServerA> <IPBroker>" (El servidor A esta listo)
	4.2 Lanzar "java ServerB <IPServerB> <IPBroker>" (El servidor B esta listo)
5. Ya esta el sistema funcionando
