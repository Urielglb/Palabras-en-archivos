package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

import java.io.File;
/**
 *Clase Lector que se encargará de guardar los archivos que reciba el programa
 */
public class Lector{
    /**
     *String donde se guardará la dirección  y/o nombre del directorio donde se guardarán todos los archivos creados
     */
    private String directorio = "";
    /**
     *Método que recibe los argumentos de entrada y acomoda los archivos en un diccionario y el nombre lo guarda en la variable directorio
     *@param args los argumentos de entrada
     */
    public void lee(String args[]){
	Errores error = new Errores();
	Documentos doc = new Documentos();
	Lista<String> lista = new Lista<String>();
	int j = -1;
	for(int i = 0; i<args.length;i++){
	    if(args[i].equals("-o")){
		j = i + 1;
		nombre(args,i,error);
		continue;
	    }
	    if(j==i)
		continue;
	    lista.agrega(args[i]);
	}
	creaCarpeta(error);
	if(directorio.equals(""))
	    error.errores(1);
	doc.archivo(lista,error,directorio);
    }
    /**
     *Método que guarda la dirección y/o nombre donde se desean guardar los documentos creados
     *@param la dirección y/o nombre de la carpeta
     */
    private void nombre(String args[],int i,Errores error){
	try{
	    directorio = args[i+1];
	}catch(IndexOutOfBoundsException ioee){
	    error.errores(2);
	}
    }
    /**
     *Método que se encarga de crear el directorio donde se guardarán todas las clases generadas por el programa
     *@param error que se encargará de lanzar una excepción de ser necesario
     */
    private void creaCarpeta(Errores error){
	File carpeta = new File(directorio);
	if(carpeta.exists()&&carpeta.isFile())
	    error.errores(4);
	if(carpeta.exists() && carpeta.isDirectory()){
	    eliminaCarpeta(carpeta);
	    carpeta.mkdir();
	    return;
	}
	if(!carpeta.exists())
	    carpeta.mkdirs();
	
    }
    /**
     *Método recursivo que borrará todos los archivos dentro de una carpeta y la misma carpeta
     *@param archivo el archivo a borrar
     */
    private void eliminaCarpeta(File archivo){
	if(!archivo.exists())
	    return;
	if(archivo.isDirectory())
	    for(File f : archivo.listFiles())
		eliminaCarpeta(f);
	archivo.delete();
    }
}