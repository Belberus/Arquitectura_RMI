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
 * Broker que se encargara de gestionar los servicios ofrecidos por los servidores A y B, así como las interacciones del cliente con los mismos
 */
public class ServicesBroker implements ServicesBrokerInterface{

    private ArrayList<Servidor> servidores = new ArrayList<Servidor>();
    private static String IP; // Direccion donde se encuentra el servidor RMI

    /*
    * Constructor del Servidor Broker
    */
    public ServicesBroker(){}

    /*
    * Ejecuta el servicio especificado con los parametros especificados, siempre y cuando se haya registrado en el Broker
    */
    public String ejecutar_servicio(String servicio, String parametros) {
        String nombreServidor = "";
        String IPServidor = "";
        String retorno = "";

        // Buscamos si el servicio esta registrado en algun servidor registrado
        for (Servidor server: servidores) {
            if (server.serviceRegistered(servicio)) {
                nombreServidor = server.getServerName();
                IPServidor = server.getServerIP();
                retorno = server.getReturnTypeFromService(servicio);
            }
        }

        // Invocamos el servicio remotamente y devolvemos la respuesta al cliente
        try {
            Registry registry = LocateRegistry.getRegistry(IPServidor);
            if (!nombreServidor.equals("")) {
                Object sa = registry.lookup("//"+IPServidor+":"+nombreServidor);

                Method method;

                if (parametros.equals("")) {
                    method = sa.getClass().getMethod(servicio);
                    if (retorno.equals("Void"))  {
                        method.invoke(sa);
                    } else if (retorno.equals("ArrayList<String>")) {
                        ArrayList<String> r = (ArrayList<String>) method.invoke(sa);
                        return r.toString();
                    } else if (retorno.equals("String")) {
                        return (String) method.invoke(sa);
                    }
                } else {
                    method = sa.getClass().getMethod(servicio, String.class);
                    if (retorno.equals("Void"))  {
                        method.invoke(sa, parametros);
                    } else if (retorno.equals("ArrayList<String>")) {
                        ArrayList<String> r = (ArrayList<String>) method.invoke(sa,parametros);
                        return r.toString();
                    } else if (retorno.equals("String")) {
                        return (String) method.invoke(sa,parametros);
                    }
                }

            } else return "Servicio no encontrado.";

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
    * Registra un servidor de servicios en el Broker
    */
    public void registrar_servidor(String ipRemota, String nombreServidor) {
        Servidor s = new Servidor(nombreServidor, ipRemota);
        servidores.add(s);
        System.out.println("Servidor " + nombreServidor + " registrado.");
    }

    /*
    * Registra un servicio en un servidor registrado previamente en el Broker
    */
    public void registrar_servicio(String nombreServidor, String nombreServicio, ArrayList<String> parametros, String tipoRetorno) {
        Servicio servicio = new Servicio(nombreServicio, parametros, tipoRetorno);
         for(Servidor s: servidores) {
             if (s.getServerName().equals(nombreServidor)) {
                 s.addService(servicio);
                 System.out.println("Servicio " + nombreServicio + " registrado.");
             }
         }
    }

    /*
    * Devuelve una lista con los servidores registrados en el Broker, así como los servicios registrados en dichos servidores
    */
    public String listar_servicios() {
        String respuesta = "";
        for(Servidor s : servidores) {
            respuesta += "Servidor " + s.getServerName() + " ofrece:\n";
            respuesta += s.getServices();
        }
        return respuesta;
    }

    /*
    * Metodo Main del Broker, lo registra en el registro RMI
    */
    public static void main(String[] args) {
        try {
            IP = args[0];

            System.setProperty("java.rmi.server.hostname",IP);

            Registry registry = LocateRegistry.getRegistry(IP);

            ServicesBrokerInterface s = new ServicesBroker();

            ServicesBrokerInterface stub = (ServicesBrokerInterface) UnicastRemoteObject.exportObject(s,0);

            registry.rebind("//"+IP+":ServicesBroker",stub);

            System.out.println("Broker registrado correctamente.");
        }catch (RemoteException e){
            System.out.println("Error al registrar el broker." + e);
        } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Es necesario pasar como parametro la IP del servidor donde se hostea el registro RMI.");
        }
    }
}
