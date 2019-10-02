package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
/**
 *Clase Indice que se encargará de generar el contendio del indice
 */
public class Indice extends HTML{
    /**
     *Diccionario de nombres donde se guardaran los nombres que recibe el programa
     */
    private Diccionario<Integer,String> nombres;
    /**
     *Construcor que recibira un diccionario de nombres
     *@param nombres diccionario de nombres que usaremos para igualar nuestro diccionario
     */
    public Indice(Diccionario<Integer,String> nombres){
	this.nombres = nombres;
    }
    /**
     *Método html que se encargará de generar el contenido dentro del índice
     *@param archivos archivos las palabras dentro de cada archivo
     *@return el contenido dentro del índice
     */
    public String html(Lista<Diccionario<String,Integer>> archivos){
	agregaNombre("index.html");
	agregaTitulo("Índice de documentos");
	HTML += "<body>" + "\n";
	for(int i = 0; i<nombres.getElementos();i++){
	    String nombre = nombres.get(i);
	    String total = " número de palabras: "+sacaTotal(archivos.get(i));
	    agregaLiga(limpia(nombre)+".html",nombre,total);
	}
	HTML += "</body>" + "\n" + "</html>";
	return HTML;
    }
    /**
     *Método para agregar las referencías a los archivos que se recibieron de entrada
     *@param liga el archivo al que se hace referencia
     *@param el archivo al que se hace referencia
     *@param el número total de palabras dentro de cada archivo
     */
    private void agregaLiga(String liga,String nombre,String total){
	agregaParrafo("<a href=\""+liga+"\">"+nombre + "</a>"+total);
    }
   /**                                                                                                                                                       
    *Método saca total que se encarga de contar el número total de palabras en cada archivo                                                                  
    *@param palabras las palabras dentro de cada archivo                                                                                                     
    *@return total el total de palabras dentro del archivo                                                                                                   
    */
    private String sacaTotal(Diccionario<String,Integer> palabras){
	int total = 0;
        for(Integer i : palabras)
            total += i;
        return Integer.toString(total);
    }

    private String limpia(String nombre){
        String limpio = "";
        for(int i = 0;i<nombre.length();i++){
            if(nombre.charAt(i)=='.')
                break;
            limpio += String.valueOf(nombre.charAt(i));
        }
        return limpio;
    }


}