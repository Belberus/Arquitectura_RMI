// Alberto Martinez y Pablo Piedrafita

import java.rmi.Remote;
import java.rmi.RemoteException;

/*
* Interfaz del servidor A
*/
public interface ServerAInterface extends Remote {

    String dar_fecha() throws RemoteException;

    String dar_hora() throws RemoteException;
}