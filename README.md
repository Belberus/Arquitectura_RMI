# Arquitectura_RMI

## Versión básica
Para probar la versión básica:
- Lanzar "rmiregistry" desde un ordenador A, estando en la carpeta con los ficheros
- Lanzar "rmiregistry" desde un ordenador B, estando en la carpeta con los ficheros
- En el ordenador A:
  - Lanzar "java ServicesBroker <IPBroker> <IPServerA> <IPServerB>" (El Broker esta listo)
- En el ordenador B:
  - Lanzar "java ServerA <IPServerA> <IPBroker>" (El servidor A esta listo)
  - Lanzar "java ServerB <IPServerB> <IPBroker>" (El servidor B esta listo)
- Ya esta el sistema funcionando

## Versión pro
Para probar la versión pro:
- Lanzar "rmiregistry" desde un ordenador A, estando en la carpeta con los ficheros
- Lanzar "rmiregistry" desde un ordenador B, estando en la carpeta con los ficheros
- En el ordenador A:
	- Lanzar "java ServicesBroker <IPBroker>" (El Broker esta listo)
	- Lanzar "java ServerA <IPServerA> <IPBroker>" (El servidor A esta listo)
	- Lanzar "java ServerB <IPServerB> <IPBroker>" (El servidor B esta listo)
- En el ordenador B:
	- Lanzar "java Client <IPBroker>" (El cliente esta listo)
- Ya esta el sistema funcionando

Para demostrar que los servidores y servicios se añaden de forma dinamica, hacer 4.1 antes que 3.3 y pedir que muestre los servicios.
