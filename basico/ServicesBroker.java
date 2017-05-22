// Alberto Martínez y Pablo Piedrafita

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/*
 * Broker que se encargara de gestionar los servicios ofrecidos por los servidores A y B
 */
public class ServicesBroker implements ServicesBrokerInterface{

    private static String IP; // Direccion donde se encuentra el servidor RMI
    private static String IPA; // Direccion del servidor A
    private static String IPB; // Direccion del servidor B
    private static Registry registry;
    private static Registry registryA;
    private static Registry registryB;
    /*
    * Constructor del Servidor Broker
    */
    public ServicesBroker(){}

    /*
    * Ejecuta el servicio especificado con los parametros especificados, siempre y cuando se haya registrado en el Broker
    */
    public String ejecutar_servicio(String servicio, String parametros) {
        // Obtengo los dos servidores A y B
        ServerAInterface sA = null;
        ServerBInterface sB = null;
        try {
            sB = (ServerBInterface) registryB.lookup("//"+IPB+":ServerB");
            sA = (ServerAInterface) registryA.lookup("//"+IPA+":ServerA");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
        // Invocamos el servicio remotamente y devolvemos la respuesta al cliente
        try {
            if (servicio.equals("dar_fecha")) {
                return sA.dar_fecha();
            } else if (servicio.equals("dar_hora")) {
                return sA.dar_hora();
            } else if (servicio.equals("introducir_libro")) {
                sB.introducir_libro(parametros);
                return "Libro introducido.";
            } else if (servicio.equals("lista_libros")) {
                return sB.lista_libros().toString();
            } else return "Servicio no encontrado.";

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
    * Metodo Main del Broker, lo registra en el registro RMI
    */
    public static void main(String[] args) {
        try {
            IP = args[0];
            IPA = args[1];
            IPB = args[2];


            registry = LocateRegistry.getRegistry();
            registryA = LocateRegistry.getRegistry(IPA);
            registryB = LocateRegistry.getRegistry(IPB);
        
            ServicesBrokerInterface s = new ServicesBroker();

            ServicesBrokerInterface stub = (ServicesBrokerInterface) UnicastRemoteObject.exportObject(s,0);

            System.setProperty("java.rmi.server.hostname",IP);
            registry.rebind("//"+IP+":ServicesBroker",stub);

            System.out.println("Broker registrado correctamente.");
        }catch (RemoteException e){
            System.out.println("Error al registrar el broker." + e);
        } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Es necesario pasar como parametro la IP del servidor donde se hostea el registro RMI.");
        }
    }
}
