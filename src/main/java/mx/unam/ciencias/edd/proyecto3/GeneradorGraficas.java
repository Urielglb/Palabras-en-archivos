package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileWriter;

import java.io.IOException;

/**
 *Clase Generador Graficas que se encargará de generas las gráficas de cada archivo
 */
public class GeneradorGraficas{
    /**
     *Una lista de adaptadores donde se guardaran las 10 palabras mas repetidas
     */
    private Lista<Adaptador> lista; 
    /**
     *Un objeto dibujo para crear el contenifo de los svg 
     */
    private Dibujo dibujo;
    /**
     *El número total de palabras
     */
    private int total;
    /**
     *Constructor que recibe una lista y un int
     *@param ordenado una lista con las palabras ordenadas de mayor a menor con su valor de repeticiones
     *@param total el número total de palabras en el archivo
     */
    public GeneradorGraficas(Lista<Adaptador> ordenado,int total){
	this.total = total;
	lista = new Lista<Adaptador>();
	int j = 0;
	for(Adaptador a : ordenado){
	    if(j++==9)
		break;
	    total -= a.getApariciones();
	    lista.agrega(a);
	}
	if(ordenado.getElementos()>=10)
	lista.agrega(new Adaptador("Resto",total));
    }
    /**
     *Método genera que creará las gráficas de barras y de pastel de todos los archivos
     *@param nombre el nombre del archivo
     *@param directorio la ubicación de donde se deben poner los archivos
     *@param error para notificar de cualquier error que pueda surgir en el programa 
     */
    public void genera(String nombre,String directorio,Errores error){
	generaBarras(nombre,directorio,total,error);
	generaPastel(nombre,directorio,total,error);
    }
    /**
     *Método que crea el svg de barras de cada archivo
     *@param nombre el nombre del archivo
     *@param directorio donde se deben crear los archivos
     *@param total el número total de palabras en cada documento
     *@param error para notificar de cualquier error que pueda surgir en el programa
     */
    private void generaBarras(String nombre,String directorio,int total,Errores error){
	dibujo = new DibujoBarras(total);
	File barras = new File(directorio +"/"+nombre+"Barras.svg");
	String contenido = dibujo.dibuja(lista);
	try{
            barras.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(barras));
            bw.write(contenido);
            bw.close();
        }catch(IOException io){
	    error.errores(5);
        }
    }

    /**                                                                                                                                                    
     *Método que crea el svg de pastel de cada archivo                                                                                                      
     *@param nombre el nombre del archivo                                                                                                                   
     *@param directorio donde se deben crear los archivos                                                                                   
     *@param total el número total de palabras en cada documento                                                                                             
     *@param error para notificar de cualquier error que pueda surgir en el programa                                                                       
     */
    private void generaPastel(String nombre,String directorio,int total,Errores error){
	dibujo = new DibujoPastel((double)total);
        File pastel = new File(directorio +"/"+nombre+"Pastel.svg");
        String contenido = dibujo.dibuja(lista);
        try{
            pastel.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(pastel));
            bw.write(contenido);
            bw.close();
        }catch(IOException io){
	    error.errores(5);
        }
	
    }
}    
	
   