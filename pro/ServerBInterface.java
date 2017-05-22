// Alberto Martinez y Pablo Piedrafita

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
* Interfaz del servidor de servicios B
*/
public interface ServerBInterface extends Remote {

    void introducir_libro(String libro) throws RemoteException;

    ArrayList<String> lista_libros() throws RemoteException;
}
