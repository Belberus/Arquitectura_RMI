// Alberto Martinez y Pablo Piedrafita

import java.util.ArrayList;

/*
* Objeto servicio para almacenar servicios en servidores
*/
public class Servicio {
    private String nombreServicio;
    private ArrayList<String> parametros;
    private String tipoRetorno;

    /*
    * Constructor del objeto servicio
    */
    public Servicio(String nombreServicio, ArrayList<String> parametros, String tipoRetorno) {
        this.nombreServicio = nombreServicio;
        this.parametros = parametros;
        this.tipoRetorno = tipoRetorno;
    }

    /*
    * Devuelve el nombre del servicio
    */
    public String getNombreServicio() {
        return nombreServicio;
    }

    /*
    * Devuelve un string con los parametros de entrada del servicio
    */
    public String getParametros() {
        if (parametros.isEmpty()) {
            return "";
        } else {
            String respuesta = "";
            for (String s : parametros) {
                respuesta+= s+", ";
            }
            return respuesta;
        }
    }

    /*
    * Devuelve un string con el parametro de retorno del servicio
    */
    public String getTipoRetorno() {
        return tipoRetorno;
    }
}
