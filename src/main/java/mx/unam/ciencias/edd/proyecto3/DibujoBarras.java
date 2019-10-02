package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

public class DibujoBarras extends Dibujo{

    private int total;
    
    public DibujoBarras(int total){
	svg = svg.replace("alto","1050");
        svg = svg.replace("largo","1000");
	svg += "<rect x=\"0\" y=\"0\" width=\"3\" height=\"620\" style=\"fill:black\"/>" +"\n" + "<rect x=\"0\" y=\"620\" width=\"500\" height=\"3\" style=\"fill:black\"/>" + "\n";
	this.total = total;
    }
    
    @Override public  String dibuja(Coleccion<Adaptador> coleccion){
	Colores color = new Colores();
	int i = 0;
	for(Adaptador a:coleccion){
	    int ancho = sacaAncho(a.getApariciones());
	    String porcentaje = sacaPorcentaje(a.getApariciones());
	    String col = color.colores(i);
	    String barra = agregaRectangulo(i,col,ancho);
	    String cuadrado = agregaCuadrado(i,col);
	    String texto = "";
	    if(i != coleccion.getElementos()-1)
		texto = agregaTexto(i++,"Palabra: \""+a.getPalabra()+"\" porcentaje del total de palabras: " + porcentaje);
	    else
		texto = agregaTexto(i++,"El resto de las palabras: " + porcentaje);
	    svg += barra + "\n" + cuadrado + "\n" + texto + "\n";
	}
	svg += "</g>" + "\n" + "</svg>";
	return svg;
    }
    
    private int sacaAncho(int apariciones){
	return (int)((apariciones*500)/total);
    }
    
    private String sacaPorcentaje(double apariciones){
	String porcentaje = Double.toString((apariciones*100)/total);
	if(porcentaje.length()>6){
	    return porcentaje.substring(0,5);
	}
	return porcentaje;
    }

    private String agregaCuadrado(int numero,String color){
	String cuadrado = "<rect x=\"0\" y=\"posicion\" width=\"30\" height=\"30\" style=\"fill:color\"/>";
	int y = 630 + 40*numero;
	cuadrado = cuadrado.replace("posicion",Integer.toString(y));
	cuadrado = cuadrado.replace("color",color);
	return cuadrado;
    }
    
    private String agregaRectangulo(int numero, String color,int porcentaje){
	String rectangulo = "<rect x=\"3\" y=\"posicion\" width=\"tamaño\" height=\"40\" style=\"fill:color\"/>";
	rectangulo = rectangulo.replace("color",color);
	int y = 560-60*numero;
	rectangulo = rectangulo.replace("posicion",Integer.toString(y));
	rectangulo = rectangulo.replace("tamaño",Integer.toString(porcentaje));
	return rectangulo;
    }
    
    private String agregaTexto(int numero, String contenido){
	String texto = agregaElemento(contenido);
	texto = texto.replace("posicionx","40");
	int y = 650 + 40*numero; 
	texto = texto.replace("posiciony",Integer.toString(y));
	return texto;
    }
}