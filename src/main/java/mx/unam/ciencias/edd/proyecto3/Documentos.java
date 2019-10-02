package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.FileReader;

import java.text.Normalizer;

/**
 *Clase Documentos que se encargará de la lectura de archivos y el conteo de sus palabras
 */
public class Documentos{
    /**
     *Lista de diccionarios en donde se guardarán las palabras de cada archivo
     */
    private Lista<Diccionario<String,Integer>> archivos;
    /**
     *Diccionario de los nombres de los archivos que recivió el programa
     */
    private Diccionario<Integer,String> nombres;
    /**
     *Constructor vacio que únicamente inicializara los atributos de la clase
     */
    public Documentos(){
	archivos = new Lista<Diccionario<String,Integer>>();
	nombres = new Diccionario<Integer,String>();
    }
    /**
     *Método que se encargará de recibir los nombres de los archivos para leerlos
     *@param nombres una lista que contiene los nombres de los archivos
     *@param error para notificar de cualquier error que pueda surgir en el programa 
     *@param directorio la ubicación en donde se deben construir los archivos
     */
    public void archivo(Lista<String> nombres, Errores error,String directorio){
	for(int i = 0; i<nombres.getElementos();i++){
	    String nombre = nombres.get(i);
	    archivo(nombre,error);
	    if(nombre.contains("/"))
		nombre=sinDireccion(nombre);
	    this.nombres.agrega(i,nombre);
	}
	GeneradorArchivos generador = new GeneradorArchivos(archivos,this.nombres,directorio);
	generador.genera(error);
    }
    /**
     *Método que regresa únicamente el nombre del archivo que nos den, sin dirección
     *@param nombre el string a limpiar
     *@return el nombre solo
     */
    private String sinDireccion(String nombre){
	String [] nombres = nombre.split("/");
	return nombres[nombres.length-1];
    }

    /**                                                                                                                                                      
     *Método que se encargará de leer el archivo que reciba                                                                      
     *@param archivo el archivo que debe leerse
     *@param error para notificar de cualquier error que pueda surgir en el programa                
     */
    private void archivo(String archivo,Errores error){
	Diccionario<String,Integer>dic = new Diccionario<String,Integer>(); 
	String linea = "";
	try{
	    FileReader fr = new FileReader(archivo);
	    BufferedReader br = new BufferedReader(fr);
	    while((linea = br.readLine())!=null)
		corta(linea,dic);
	    archivos.agrega(dic);
	}catch(IOException e){
	    error.errores(3);
	}
    }
    /**
     *Método recursivo que cortará las lineas del archivo en palabras
     *@param linea la linea que se dividirá en palabras
     *@param dic diccionario donde se guardarán las palabras y sus repeticiones
     */
    private void corta(String linea,Diccionario<String,Integer> dic){
	if(linea.length()<=0)
	    return;
	linea = linea.trim();
	String[] palabras = linea.split("\\s");
	agrega(limpia(palabras[0]),dic);
	linea = linea.substring(palabras[0].length(),linea.length());
	corta(linea.trim(),dic);
    }
    /**
     *Método limpia que se encargará de quitar todos los caracteres no alfa a la palabra y dejarla en minusculas
     *@param palabra la palabra a limpiar
     *@return la palabra limpia
     */
    private String limpia(String palabra){
	palabra = palabra.toLowerCase();
	palabra = Normalizer.normalize(palabra, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""); 
	palabra = palabra.replaceAll("\\p{Punct}","");
	return palabra;
    }
    /**
     *Método que se encargará de agregar y llevar el conteo de cada palabra en el archivo
     *@param palabra la palabra que se agregará
     *@param dic el diccionario donde se guardará
     */
    private void agrega(String palabra,Diccionario<String,Integer> dic){
	if(palabra.length()<=0)
	    return;
	if(dic.contiene(palabra)){
	    dic.agrega(palabra,dic.get(palabra)+1);
	    return;
	}
	dic.agrega(palabra,1);
    }
}
