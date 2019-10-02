package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

import java.util.Iterator;

public class Ordenador {
    
    private Lista <Adaptador> lista;

    public Ordenador(Diccionario<String,Integer> dic){
	lista = new Lista<Adaptador>();
	Iterator<String> llaves = dic.iteradorLlaves();
	while(llaves.hasNext()){
            String palabra = llaves.next();
	    lista.agrega(new Adaptador(palabra,dic.get(palabra)));
	}	
    }
    
    public Lista<Adaptador> ordena(){
	return lista.mergeSort(lista).reversa();
    }
}