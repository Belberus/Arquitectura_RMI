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
import java.util.Scanner;

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
    * Metodo Main del servidor A. Registra el servidor en el registro RMI
    */
    public static void main(String[] args){
        try{
            // El servidor se registra en RMI
            IP = args[0];
            IPBroker = args[1];

            Registry registryA = LocateRegistry.getRegistry();

            ServerAInterface sA = new ServerA();

            ServerAInterface stub = (ServerAInterface) UnicastRemoteObject.exportObject(sA,0);

            System.setProperty("java.rmi.server.hostname",IP);
            registryA.rebind("//"+IP+":ServerA",stub);
            System.out.println("Servidor A registrado correctamente.");

            // Obtenemos el Broker
            Registry registry = LocateRegistry.getRegistry(IPBroker);
            ServicesBrokerInterface s = (ServicesBrokerInterface) registry.lookup("//"+IPBroker+":ServicesBroker");
	
            Scanner teclado = new Scanner(System.in);
            boolean finalizar = false;
            String entrada = "";
			
            while (!finalizar) {
                System.out.println("\n--------------------------------------\n");
                System.out.println("LISTA DE SERVICIOS DEL SERVIDOR B:\n" +
                "       - Nombre: introducir_libro; Parametros: String nombreLibro; Return: Void \n" +
                "       - Nombre: lista_libros; Parametros: none; Return: ArrayList<String> \n");

                System.out.println("¿Que desea hacer? \n"+
                "   1 - Introducir libro\n"+
                "   2 - Obtener lista de libros\n");
                System.out.print("Accion: ");
                entrada = teclado.nextLine();

                if (entrada.equals("1")) {
                    System.out.println("Introduzca el titulo del libro que desea almacenar:");
                    System.out.print("Titulo:");
                    entrada = teclado.nextLine();
                    System.out.println(s.ejecutar_servicio("introducir_libro", entrada));
                } else if (entrada.equals("2")) {
                    System.out.println(s.ejecutar_servicio("lista_libros",""));
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

