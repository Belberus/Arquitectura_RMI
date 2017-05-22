// Alberto Martinez y Pablo Piedrafita

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

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
    * Metodo Main del servidor B, registra el servidor en el registro RMI
    */
    public static void main(String[] args) {
        try{
            // Registramos el servidor en el registro RMI
            IP = args[0];
            IPBroker = args[1];

            Registry registryB = LocateRegistry.getRegistry();

            ServerBInterface sB = new ServerB();

            ServerBInterface stub = (ServerBInterface) UnicastRemoteObject.exportObject(sB,0);

            System.setProperty("java.rmi.server.hostname",IP);
            registryB.rebind("//"+IP+":ServerB",stub);
            System.out.println("Servidor B registrado correctamente.");

            // Obtenemos el Broker
            Registry registry = LocateRegistry.getRegistry(IPBroker);
            ServicesBrokerInterface s = (ServicesBrokerInterface) registry.lookup("//"+IPBroker+":ServicesBroker");
	
            Scanner teclado = new Scanner(System.in);
            boolean finalizar = false;
            String entrada = "";

            while (!finalizar) {
                System.out.println("LISTA DE SERVICIOS DEL SERVIDOR A:\n" +
                        "       - Nombre: dar_hora; Parametros: none; Return: String \n" +
                        "       - Nombre: dar_fecha; Parametros: none; Return: String \n");

                System.out.println("¿Que desea hacer? \n"+
                        "   1 - Obtener hora actual\n"+
                        "   2 - Obtener fecha actual\n");
                System.out.print("Accion: ");
                entrada = teclado.nextLine();

                if (entrada.equals("1")) {
                    System.out.println(s.ejecutar_servicio("dar_hora",""));
                } else if (entrada.equals("2")) {
                    System.out.println(s.ejecutar_servicio("dar_fecha",""));
                } else System.out.println("Accion no existente.");
            }
        }catch (RemoteException e){
            System.out.println(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Es necesario pasar como parametro la IP del servidor donde se hostea el registro RMI y la IP del Broker.");
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
