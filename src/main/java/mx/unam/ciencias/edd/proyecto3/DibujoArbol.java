package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

public abstract  class DibujoArbol extends Dibujo{
    
    protected ArbolBinario<Integer> arbol;
    
    protected abstract void pasarColeccion(Coleccion<Adaptador> coleccion);
    
    protected String raiz(VerticeArbolBinario<Adaptador> raiz,int posicion,int largo){
	String circulo = "<circle cx=\"posx\" cy=\"22\" r=\"16\" stroke=\"black\" stroke-width=\"3\" fill=\"white\"></circle>";
	circulo = circulo.replace("posx",Integer.toString(posicion));
	circulo += "\n" + texto(raiz.get().toString(),posicion,22);
	if(raiz.hayIzquierdo())
	    circulo += circulos(1,largo,raiz.izquierdo(),posicion,22,false);
	if(raiz.hayDerecho())
	    circulo += circulos(1,largo,raiz.derecho(),posicion,22,true);
	return circulo;
    }
    protected String circulos(int altura,int largo,VerticeArbolBinario<Adaptador> ver,int xPadre,int yPadre,boolean derecho){
	if(ver.get() == null)
	    return "";
	String circulo = "\n"+"<circle cx=\"posx\" cy=\"posy\" r=\"16\" stroke=\"black\" stroke-width=\"3\" fill=\"white\"></circle>";
	int y = 22+(100*altura);
	int x = 0;
	if(derecho)
	    x = xPadre+(int)(largo/Math.pow(2,ver.profundidad()+1));
	else
	    x = xPadre-(int)(largo/Math.pow(2,ver.profundidad()+1));
	circulo = circulo.replace("posx",Integer.toString(x));
	circulo= circulo.replace("posy",Integer.toString(y));
	circulo += "\n"+texto(ver.get().toString(),x,y);
	circulo += "\n" +linea(xPadre,yPadre,x,y);
	if(ver.hayIzquierdo())
	circulo += circulos(altura+1,largo,ver.izquierdo(),x,y,false);
	if(ver.hayDerecho())
	circulo+= circulos(altura+1,largo,ver.derecho(),x,y,true);
	return circulo;
    }

    protected String texto(String elemento,int x,int y){
	String texto = super.agregaElemento(elemento);
	texto = texto.replace("20","11");
	if(elemento.length()<3)
	    texto = texto.replace("posicionx",Integer.toString(x-4));
	else
	    texto = texto.replace("posicionx",Integer.toString(x-6));
	    texto =texto.replace("posiciony",Integer.toString(y+3));
	return texto;
    }
    protected String linea(int xPadre,int yPadre,int x,int y){
	String linea = "<line x1=\"xp\" y1=\"yp\" x2=\"xh\" y2=\"yh\" style=\"stroke:#000; stroke-width:3\"></line>";
	linea = linea.replace("xp",Integer.toString(xPadre));
	linea =linea.replace("xh",Integer.toString(x));
	linea =linea.replace("yp",Integer.toString(yPadre+16));
	linea =linea.replace("yh",Integer.toString(y-16));
	return linea;
    }
    
}