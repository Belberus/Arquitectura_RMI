// Alberto Martinez y Pablo Piedrafita

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

/*
* Clase cliente que interactuara con el Broker para llamar a los servicios que ofrece
*/
public class Client {

    public static String IPBroker; // Direccion donde se encuentra el Broker
    public static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        try {
            IPBroker = args[0];

            // Obtenemos el Broker
            Registry registry = LocateRegistry.getRegistry(IPBroker);
            ServicesBrokerInterface services = (ServicesBrokerInterface) registry.lookup("//"+IPBroker+":ServicesBroker");
            String entrada = "";
            boolean finalizar = false;

            // Mostramos el menu e interactuamos con el usuario
            while (!finalizar) {
                System.out.println("\n---------------------------");
                System.out.println("MENÚ DE OPCIONES: \n" +
                        "   1 - Listar servicios disponibles. \n" +
                        "   2 - Ejecutar servicio... \n" +
                        "   3 - Salir.");

                System.out.print("Introduzca orden: \n");
                entrada = teclado.nextLine();

                if (entrada.equals("1")) {
                    String servicios = services.listar_servicios();
                    System.out.println(servicios);
                } else if (entrada.equals("2")) {
                    System.out.print("\n- Introduzca el nombre del servicio: ");
                    String servicio = teclado.nextLine();
                    System.out.print("\n- Introduzca los parametros del servicio (cadena vacia si no tiene): ");
                    String parametros = teclado.nextLine();

                    String respuesta = services.ejecutar_servicio(servicio, parametros);

                    System.out.println(respuesta);
                } else if (entrada.equals("3")) {
                    finalizar = true;
                } else System.out.println("Comando no existente.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e ) {
            System.out.println("Es necesario especificar la IP donde se encuentra el Broker con el registro RMI.");
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
