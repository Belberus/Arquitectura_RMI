// Alberto Martinez y Pablo Piedrafita

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
* Interfaz del servidor Broker
*/
public interface ServicesBrokerInterface extends Remote {

    String ejecutar_servicio(String servicio, String parametros) throws RemoteException;

}
