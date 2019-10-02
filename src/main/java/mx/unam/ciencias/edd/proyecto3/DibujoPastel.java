package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

public class DibujoPastel extends Dibujo{

    private double total;

    private double radio = 150;
    
    private double cx = 200;

    private double cy = 200;

    private int gradoFinal = 0;

    public DibujoPastel(double total){
	this.total = total;
	svg = svg.replace("alto","900");
        svg = svg.replace("largo","1000");
    }
    
    @Override public String dibuja(Coleccion<Adaptador> coleccion){
	Colores color = new Colores();
	creaCirculo();
	int i =0;
	String gajos = "";
	String cuadros = "";
	String texto = "";
	Adaptador ultimo = new Adaptador();
	for(Adaptador a:coleccion){
	    if(i == coleccion.getElementos()-1){
		ultimo = a;
		break;
	    }
	    String col = color.colores(i);
	    int grado = sacaGrado(a.getApariciones());
	    String porcentaje = sacaPorcentaje(a.getApariciones());
	    gajos += creaGajo(gradoFinal,grado+gradoFinal,col) + "\n";
	    gradoFinal += grado;
	    cuadros += agregaCuadrado(i,col) +"\n";
	    texto += agregaTexto(i++,"Palabra: \""+a.getPalabra()+"\" porcentaje del total de palabras: " + porcentaje) + "\n";
	}
	creaFondo(i,color,"El resto de las palabras: " +sacaPorcentaje(ultimo.getApariciones()));
	svg += gajos + "\n" + cuadros + "\n" + texto;
	svg += "</g>" + "\n" + "</svg>";
        return svg;
    }

    private void creaFondo(int i,Colores color,String contenido){
	String circulo = "<circle cx=\"posx\" cy=\"posy\" r=\"rad\" stroke=\"black\" stroke-width=\"1\" fill=\"white\"></circle>";
	circulo = circulo.replace("posx",Double.toString(cx));
        circulo= circulo.replace("posy",Double.toString(cy));
        circulo= circulo.replace("rad",Double.toString(radio));
	circulo= circulo.replace("white",color.colores(i));
	String cuadro = agregaCuadrado(i,color.colores(i));
	String texto = agregaTexto(i,contenido);
	svg += circulo + "\n"+ cuadro +  "\n" + texto +  "\n";
    }
    
    private String sacaPorcentaje(int apariciones){
	String porcentaje = Double.toString((apariciones*100)/total);
	if(porcentaje.length()>6){
	    return porcentaje.substring(0,5);
	}
	return porcentaje;
    }
    private void creaCirculo(){
	String circulo = "<circle cx=\"posx\" cy=\"posy\" r=\"rad\" stroke=\"black\" stroke-width=\"1\" fill=\"white\"></circle>";
	circulo = circulo.replace("posx",Double.toString(cx));
	circulo= circulo.replace("posy",Double.toString(cy));
	circulo= circulo.replace("rad",Double.toString(radio));
	svg += circulo + "\n";
    }

    private int sacaGrado(int apariciones){
	return (int)((apariciones*360)/total);
    }
    
    private String  creaGajo(int gradoi,int gradof,String color){
	String gajo = "<path d=\"contenido\" fill=\"color\"/>";
	double xi = cx + radio * Math.cos((Math.PI/180)*gradoi);
	double yi = cy + radio * Math.sin((Math.PI/180)*gradoi);
	double xf = cx + radio * Math.cos((Math.PI/180)*gradof);
	double yf = cy + radio * Math.sin((Math.PI/180)*gradof);
	String d = "M" + cx +","+cy+" L" + xi +","+yi+" A"+radio+","+radio+" 0 0, 1 "+" "+xf+","+yf+ " z";
	gajo = gajo.replace("contenido",d);
	gajo = gajo.replace("color",color);
	return gajo;
    }
    
    private String agregaCuadrado(int numero,String color){
        String cuadrado = "<rect x=\"0\" y=\"posicion\" width=\"30\" height=\"30\" style=\"fill:color\"/>";
        int y = 430 + 40*numero;
        cuadrado = cuadrado.replace("posicion",Integer.toString(y));
        cuadrado = cuadrado.replace("color",color);
        return cuadrado;
    }

    private String agregaTexto(int numero, String contenido){
        String texto = agregaElemento(contenido);
        texto = texto.replace("posicionx","40");
        int y = 450 + 40*numero;
        texto = texto.replace("posiciony",Integer.toString(y));
        return texto;
    }

}