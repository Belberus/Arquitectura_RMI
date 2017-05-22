// Alberto Martinez, Pablo Piedrafita

import java.rmi.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
* Servidor A
*/
public class ServerA  implements ServerAInterface  {

    private static String IP; // IP donde se encuentra el servidor RMI
    private static String IPBroker; // IP donde se encuentra el broker

    /*
    * Constructor del Servidor A
    */
    public ServerA(){}

    /*
    * Devuelve un string con la fecha actual
    */
    public String dar_fecha(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date).toString();

    }

    /*
    * Devuelve un string con la hora actual
    */
    public String dar_hora(){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(date).toString();
    }

    /*
    * Metodo Main del servidor A. Registra el servidor en el registro RMI y luego tanto a el como a los servicios
    * que ofrece en el Broker
    */
    public static void main(String[] args){
        try{
            // El servidor se registra en RMI
            IP = args[0];

            System.setProperty("java.rmi.server.hostname",IP);

            Registry registryA = LocateRegistry.getRegistry();

            ServerAInterface sA = new ServerA();

            ServerAInterface stub = (ServerAInterface) UnicastRemoteObject.exportObject(sA,0);

            registryA.rebind("//"+IP+":ServerA",stub);
            System.out.println("Servidor A registrado correctamente.");

            // El servidor se registra en el Broker
            IPBroker = args[1];

            Registry registry = LocateRegistry.getRegistry(IPBroker);
            ServicesBrokerInterface broker = (ServicesBrokerInterface) registry.lookup("//"+IPBroker+":ServicesBroker");
            ArrayList<String> parametros = new ArrayList<String>();
            parametros.add("none");
            broker.registrar_servidor(IP,"ServerA");
            broker.registrar_servicio("ServerA","dar_fecha",parametros,"String");
            broker.registrar_servicio("ServerA","dar_hora",parametros,"String");


        }catch (RemoteException e){
            System.out.println("Error al registrar el servidor A." + e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Es necesario pasar como parametro la IP del servidor donde se hostea el registro RMI y la IP del Broker.");
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}