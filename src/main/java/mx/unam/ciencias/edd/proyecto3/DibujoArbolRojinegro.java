package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

public class DibujoArbolRojinegro extends DibujoArbol{
    private ArbolRojinegro<Adaptador> arbol;
   
    @Override public  String dibuja(Coleccion<Adaptador> coleccion){
        pasarColeccion(coleccion);
        int altura = arbol.altura()*100 + 60;
        if(altura == -1)
            return "";
        int largo = ((arbol.getElementos()+1)/2) * 100+1000;
        svg = svg.replace("alto",Integer.toString(altura));
        svg = svg.replace("largo",Integer.toString(largo));
        String circulos = raiz(arbol.raiz(),largo/2,largo);
        svg += circulos +"\n" + "</g>" + "\n"+"</svg>";
        return svg;
    }
    
    @Override protected String raiz(VerticeArbolBinario<Adaptador> raiz,int posicion,int largo){
        String circulo = "<circle cx=\"posx\" cy=\"22\" r=\"16\" stroke=\"black\" stroke-width=\"3\" fill=\"color\"></circle>";
	boolean esNegro = false;
        circulo = circulo.replace("posx",Integer.toString(posicion));
	if(arbol.getColor(raiz) == Color.ROJO)
	    circulo = circulo.replace("color","red");
	else{
	    circulo= circulo.replace("color","black");
	    esNegro = true;
	}
	circulo += "\n" + texto(raiz.get().toString(),posicion,22,esNegro);
        if(raiz.hayIzquierdo())
            circulo += circulos(1,largo,raiz.izquierdo(),posicion,22,false);
        if(raiz.hayDerecho())
            circulo += circulos(1,largo,raiz.derecho(),posicion,22,true);
        return circulo;
    }
    
    @Override protected String circulos(int altura,int largo,VerticeArbolBinario<Adaptador> ver,int xPadre,int yPadre,boolean derecho){
        if(ver.get() == null)
            return "";
	boolean esNegro = false;
        String circulo = "\n"+"<circle cx=\"posx\" cy=\"posy\" r=\"16\" stroke=\"black\" stroke-width=\"3\" fill=\"color\"></circle>";
	if(arbol.getColor(ver) == Color.ROJO)
	    circulo= circulo.replace("color","red");
        else{
	    circulo= circulo.replace("color","black");
	    esNegro=true;
	}
        int y = 22+(100*altura);
        int x = 0;
        if(derecho)
            x = xPadre+(int)(largo/Math.pow(2,ver.profundidad()+1));
        else
            x = xPadre-(int)(largo/Math.pow(2,ver.profundidad()+1));
        circulo = circulo.replace("posx",Integer.toString(x));
        circulo= circulo.replace("posy",Integer.toString(y));
        circulo += "\n"+texto(ver.get().toString(),x,y,esNegro);
        circulo += "\n" +linea(xPadre,yPadre,x,y);
        if(ver.hayIzquierdo())
	    circulo += circulos(altura+1,largo,ver.izquierdo(),x,y,false);
        if(ver.hayDerecho())
	    circulo+= circulos(altura+1,largo,ver.derecho(),x,y,true);
        return circulo;
    }



    private String texto(String elemento,int x,int y,boolean esNegro){
        String texto = "<text x=\"posicionx\" y = \"posiciony\" font-family =\"Times New Roman\" font-size = \"20\" fill=\"color\">elemento</text>";
        texto = texto.replace("20","11");
	texto = texto.replace("elemento",elemento);
        if(elemento.length()<3)
            texto = texto.replace("posicionx",Integer.toString(x-4));
        else
            texto = texto.replace("posicionx",Integer.toString(x-6));
	if(esNegro)
	    texto = texto.replace("color","white");
	else
	    texto = texto.replace("color","black");
	texto =texto.replace("posiciony",Integer.toString(y+3));
        return texto;
    }



    @Override protected void pasarColeccion(Coleccion<Adaptador> coleccion){
        arbol = new ArbolRojinegro<Adaptador>();
        for(Adaptador elem : coleccion)
            arbol.agrega(elem);
    }

}
