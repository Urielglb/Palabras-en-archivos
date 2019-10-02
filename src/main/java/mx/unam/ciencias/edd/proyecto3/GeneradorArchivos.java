package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileWriter;

import java.io.IOException;
/**
 *Clase Generador Archivos que se encargará de generar los archivos html de cada archivo recibido con sus respectivos svg
 */
public class GeneradorArchivos{
    /**
     *String directorio que tendrá la dirección de donde deben crearse los archivos
     */
    private String directorio;
    /**
     *Lista archivos que tendrá las palbras con sus repeticiones de cada archivo
     */
    private Lista<Diccionario<String,Integer>> archivos;
    /**
     *Diccionario de nombres que tiene los nombres de los archivos recibidos
     */
    private Diccionario<Integer,String> nombres;
    /**
     *Varieable datos que creará todo el contenido dentro del html de cada archivo
     */
    private Datos datos; 
    /**
     *Constructor que igualará sus atributos con los recibidos
     *@param archivos una lista de diccionarios con las palabras de cada archivo y sus repeticiones
     *@param nombres un diccionario con los nombres de los archivos recibidos
     *@param directorio la dirección en donde se crearán los archivos generados por el programa
     */
    public GeneradorArchivos(Lista<Diccionario<String,Integer>> archivos,Diccionario<Integer,String> nombres,String directorio){
	this.archivos = archivos;
	this.nombres = nombres;
	this.directorio = directorio;
    }
    /**
     *Método genera que se encargará de generar todos los archivos 
     *@param error para notificar de cualquier error que pueda surgir en el programa
     */
    public void genera(Errores error){
	int i = 0;
	for(Diccionario<String,Integer> palabras : archivos){
	    creaArchivo(palabras,nombres.get(i++),error);
	}
	creaIndice(error);
    }
    /**
     *Método genera archivo que genera el html de cada archivo
     *@param palabras las palabras del archivo
     *@param documento el nombre del archivo que se recibio
     *@param error para notificar de cualquier error que pueda surgir en el programa
     */
    private void creaArchivo(Diccionario<String,Integer> palabras, String documento,Errores error){
	datos = new Datos();
	File nombre = new File(directorio +"/" +limpia(documento)+".html");
	String contenido = datos.html(palabras,documento,directorio, error);
	try{
	    nombre.createNewFile();
	    BufferedWriter bw = new BufferedWriter(new FileWriter(nombre));
	    bw.write(contenido);
	    bw.close();
	}catch(IOException io){
	    error.errores(5);
	}
	
    }
    /**
     *Método que genéra el índice que tendrá una liga a cada documento html generado
     *@param error para notificar de cualquier error que pueda surgir en el programa      
     */
    private void creaIndice(Errores error){
	Indice indi = new Indice(nombres);
	File indice = new File (directorio+"/"+"index.html");
	String contenido = indi.html(archivos);
	try{
            indice.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(indice));
            bw.write(contenido);
            bw.close();
        }catch(IOException io){
	    error.errores(5);
        }
    }
    /**
     *Método limpia que regresará los nombres del archivo sin extensión 
     *@param nombre el nombre a limpiar
     *@return limpio el nombre del archivo si  extensión
     */
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