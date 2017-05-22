// Alberto Martinez y Pablo Piedrafita

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/*
* Servidor B
*/
public class ServerB implements ServerBInterface {

    private ArrayList<String> listaLibros = new ArrayList<String>();
    private static String IP;   // Direccion donde se encuentra el servidor RMI
    private static String IPBroker; // Direccion donde se encuentra el Broker

    /*
    * Constructor del Servidor B
    */
    public ServerB(){}

    /*
     * Añade el titulo especificado a la coleccion de libros almacenada en el servidor
     */
    public void introducir_libro(String libro) throws RemoteException {
        listaLibros.add(libro);
    }

    /*
    * Devuelve un string con los titulos de libros almacenados en el servidor
    */
    public ArrayList<String> lista_libros() throws RemoteException {
        return listaLibros;
    }

    /*
    * Metodo Main del servidor B, registra el servidor en el registro RMI y luego registra tanto a el como a los servicios
    * ofrecidos en el Broker
    */
    public static void main(String[] args) {
        try{
            // Registramos el servidor en el registro RMI
            IP = args[0];

            System.setProperty("java.rmi.server.hostname",IP);

            
            Registry registryB = LocateRegistry.getRegistry();

            ServerBInterface sB = new ServerB();

            ServerBInterface stub = (ServerBInterface) UnicastRemoteObject.exportObject(sB,0);

            registryB.rebind("//"+IP+":ServerB",stub);
            System.out.println("Servidor B registrado correctamente.");

            // Registramos el servidor y sus servicios en el broker
            IPBroker = args[1];

            Registry registry = LocateRegistry.getRegistry(IPBroker);
            ServicesBrokerInterface broker = (ServicesBrokerInterface) registry.lookup("//"+IPBroker+":ServicesBroker");
            broker.registrar_servidor(IP,"ServerB");

            ArrayList<String> parametros1 = new ArrayList<String>();
            parametros1.add("String nombreLibro");
            broker.registrar_servicio("ServerB","introducir_libro", parametros1,"Void");

            ArrayList<String> parametros2 = new ArrayList<String>();
            parametros2.add("none");
            broker.registrar_servicio("ServerB","lista_libros",parametros2,"ArrayList<String>");

        }catch (RemoteException e){
            System.out.println("Error al registrar el servidor B.");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Es necesario pasar como parametro la IP del servidor donde se hostea el registro RMI y la IP del Broker.");
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
