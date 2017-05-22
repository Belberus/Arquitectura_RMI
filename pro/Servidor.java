// Alberto Martínez y Pablo Piedrafita

import java.util.ArrayList;

/*
* Objeto servidor para almacenar servidores registrados en el Broker
*/
public class Servidor {

    private String nombreServidor = "";
    private String ipRemota = "";
    private ArrayList<Servicio> servicios = new ArrayList<Servicio>();
    /*
    * Constructor del objeto servidor
    */
    public Servidor(String nombreServidor, String ipRemota) {
        this.nombreServidor = nombreServidor;
        this.ipRemota = ipRemota;
    }

    /*
    * Añade un servicio al servidor
    */
    public void addService(Servicio servicio) {
        servicios.add(servicio);
    }

    /*
    * Devuelve un string con todos los servicios registrados en el servidor
    */
    public String getServices() {
        String respuesta = "";

        for (Servicio s : servicios) {
            respuesta += "      Servicio: " + s.getNombreServicio()+"; Parametros: " + s.getParametros()+"; Tipo retorno: "+s.getTipoRetorno()+"\n";
        }
        return respuesta;
    }

    /*
    * Devuelve el nombre del servidor
    */
    public String getServerName() {
        return nombreServidor;
    }

    /*
    * Devuelve la IP asociada al servidor
    */
    public String getServerIP() {
        return ipRemota;
    }

    /*
    * Devuelve true si el servicio especificado esta registrado en el servidor. False en cualquier otro caso
    */
    public boolean serviceRegistered(String servicio) {
        for (Servicio s: servicios) {
            if (s.getNombreServicio().equals(servicio)) {
                return true;
            }
        }
        return false;
    }

    /*
    * Devuelve un string con el tipo de parametro de respuesta del servicio registrado en el servidor
    */
    public String getReturnTypeFromService(String nombreServicio) {

        for (Servicio s: servicios) {
            if (s.getNombreServicio().equals(nombreServicio)) {
                return s.getTipoRetorno();
            }
        }
        return "Servicio no existente.";
    }
}
