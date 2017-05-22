// Alberto Martinez y Pablo Piedrafita

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/*
* Interfaz del servidor Broker
*/
public interface ServicesBrokerInterface extends Remote {

    String ejecutar_servicio(String servicio, String parametros) throws RemoteException;

    void registrar_servidor(String ipRemota, String nombreServidor) throws RemoteException;

    void registrar_servicio(String nombreServidor, String nombreServicio, ArrayList<String> parametros, String tipoRetorno) throws RemoteException;

    String listar_servicios() throws RemoteException;
}
