Para probar la versión pro:

1. Lanzar "rmiregistry" desde un ordenador A, estando en la carpeta con los ficheros
2. Lanzar "rmiregistry" desde un ordenador B, estando en la carpeta con los ficheros
3. En el ordenador A:
	3.1 Lanzar "java ServicesBroker <IPBroker>" (El Broker esta listo)
	3.2 Lanzar "java ServerA <IPServerA> <IPBroker>" (El servidor A esta listo)
	3.3 Lanzar "java ServerB <IPServerB> <IPBroker>" (El servidor B esta listo)
4. En el ordenador B:
	4.1 Lanzar "java Client <IPBroker>" (El cliente esta listo)
5. Ya esta el sistema funcionando

Para demostrar que los servidores y servicios se añaden de forma dinamica, hacer 4.1 antes que 3.3 y pedir que muestre los servicios.
