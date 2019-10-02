package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

import java.io.BufferedWriter;

import java.io.File;

import java.io.FileWriter;

import java.io.IOException;

public class GeneradorArboles{
    
    private Lista<Adaptador> lista; 
    
    private Dibujo dibujo;

    public GeneradorArboles(Lista<Adaptador> ordenado){
	lista = new Lista<Adaptador>();
	int i = 65;
	int j = 0;
	for(Adaptador a : ordenado){
	    if(j++==15)
		break;
	    a.setLetra((char)i++);
	    lista.agrega(a);
	}
    }
    
    public void genera(String nombre,String directorio,Errores error){
	generaRojinegro(nombre,directorio,error);
	generaAVL(nombre,directorio,error);
    }
	
    public Lista<Adaptador> getLista(){
	return lista;
    }
 
    private void generaRojinegro(String nombre,String directorio,Errores error){
	dibujo = new DibujoArbolRojinegro();
	File rojinegro = new File(directorio +"/"+nombre+"Rojinegro.svg");
	String contenido = dibujo.dibuja(lista);
	try{
            rojinegro.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(rojinegro));
            bw.write(contenido);
            bw.close();
        }catch(IOException io){
	    error.errores(5);
        }
    }

    private void generaAVL(String nombre,String directorio,Errores error){
	dibujo = new DibujoArbolAVL();
        File avl = new File(directorio +"/"+nombre+"AVL.svg");
        String contenido = dibujo.dibuja(lista);
        try{
            avl.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(avl));
            bw.write(contenido);
            bw.close();
        }catch(IOException io){
	    error.errores(5);
        }

    }
}    
	
   