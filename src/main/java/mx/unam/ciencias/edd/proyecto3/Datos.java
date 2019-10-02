package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

import java.util.Iterator;
/**
 *Clase Datos que se encargará de generar la información para cada archivo
 */
public class Datos extends HTML{
    /**
     *Método html que generará un string con todo el contenido de cada archivo
     *@param palabras las palabras dentro de cada archivo
     *@param el nombre del archivo
     *@param directorio donde deben generarse los documentos extra de cada archivo
     *@param error para notificar de cualquier error que pueda surgir en el programa
     */
    public String html(Diccionario<String,Integer> palabras,String nombre,String directorio,Errores error){
	if(palabras.getElementos()<=0)
	    return "";
	Ordenador ordenador = new Ordenador(palabras);
	Lista<Adaptador> ordenado = ordenador.ordena();
	int total = sacaTotal(palabras);
	GeneradorArboles generador = new GeneradorArboles(ordenado);
	GeneradorGraficas generadorG = new GeneradorGraficas(ordenado,total);
	generador.genera(limpia(nombre),directorio,error);
	generadorG.genera(limpia(nombre),directorio,error);
	agregaNombre(limpia(nombre)+".html");
	agregaTitulo("Palabras en " +nombre +" y sus apariciones");
	HTML += "\n" + "<body>" + "\n";
	Iterator<String> llaves = palabras.iteradorLlaves();
	while(llaves.hasNext()){
	    String palabra = llaves.next();
	    String a =(palabras.get(palabra)==1)? "vez":" veces";
	    agregaParrafo("La palabra \""+ palabra +"\""+ " aparece: " + palabras.get(palabra) + a);
	}
	agregaTitulo("Gráfica de Barras");
	agregaObjeto("data=\""+limpia(nombre)+"Barras.svg\" "+ "type=\"image/svg+xml\"");
	agregaTitulo("Gráfica de Pastel");
	agregaObjeto("data=\""+limpia(nombre)+"Pastel.svg\" "+ "type=\"image/svg+xml\"");
	Lista<Adaptador> letras = generador.getLista();
	agregaTitulo("Árboles Rojinegros y AVL");
	Lista<Adaptador> arboles = generador.getLista();
	for(Adaptador a : arboles){
	    agregaParrafo("La letra " + a.getLetra() + " es la palabra: " + a.getPalabra());
	}
	agregaTitulo("Árbol Rojinegro");
	agregaObjeto("data=\""+limpia(nombre)+"Rojinegro.svg\" "+ "type=\"image/svg+xml\"");
	agregaTitulo("Árbol AVL");
        agregaObjeto("data=\""+limpia(nombre)+"AVL.svg\" "+ "type=\"image/svg+xml\"");
	HTML += "</body>" + "\n" + "</html>";
	return HTML;
    }
    /**
     *Método saca total que se encarga de contar el número total de palabras en cada archivo
     *@param palabras las palabras dentro de cada archivo
     *@return total el total de palabras dentro del archivo
     */
    private int sacaTotal(Diccionario<String,Integer> palabras){
	int total = 0;
	for(Integer i : palabras)
	    total += i;
	return total;
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