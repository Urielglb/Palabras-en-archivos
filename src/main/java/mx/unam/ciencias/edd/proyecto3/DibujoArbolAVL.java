package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

public class DibujoArbolAVL extends DibujoArbol{
    private ArbolAVL<Adaptador> arbol;

    @Override public  String dibuja(Coleccion<Adaptador> coleccion){
        pasarColeccion(coleccion);
        int altura = arbol.altura()*100 + 100;
        if(altura<= 40)
            return "";
        int largo = ((arbol.getElementos()+1)/2)*100+40;
        svg = svg.replace("alto",Integer.toString(altura));
        svg = svg.replace("largo",Integer.toString(largo));
        String circulos = raiz(arbol.raiz(),largo/2,largo);
        svg += circulos +"\n" + "</g>" + "\n"+"</svg>";
        return svg;
    }
    @Override protected String raiz(VerticeArbolBinario<Adaptador> raiz,int posicion,int largo){
        String circulo = "<circle cx=\"posx\" cy=\"32\" r=\"16\" stroke=\"black\" stroke-width=\"3\" fill=\"white\"></circle>";
        circulo = circulo.replace("posx",Integer.toString(posicion));
        circulo += "\n" + texto(raiz.get().toString(),posicion,32);
        if(raiz.hayIzquierdo())
            circulo += circulos(1,largo,raiz.izquierdo(),posicion,32,false);
        if(raiz.hayDerecho())
            circulo += circulos(1,largo,raiz.derecho(),posicion,32,true);
	circulo += "\n" + balance(raiz,posicion,32);
        return circulo;
    }
    @Override protected String circulos(int altura,int largo,VerticeArbolBinario<Adaptador> ver,int xPadre,int yPadre,boolean derecho){
        if(ver.get() == null)
            return "";
        String circulo = "\n"+"<circle cx=\"posx\" cy=\"posy\" r=\"16\" stroke=\"black\" stroke-width=\"3\" fill=\"white\"></circle>";
        int y = 32+(100*altura);
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
	circulo+= "\n"+ balance(ver,x,y);
        return circulo;
    }
    @Override protected void pasarColeccion(Coleccion<Adaptador> coleccion){
        arbol = new ArbolAVL<Adaptador>();
        for(Adaptador elem : coleccion)
            arbol.agrega(elem);
    }
    
    private String balance(VerticeArbolBinario<Adaptador> ver,int x, int y){
	String balance = "<text x=\"posicionx\" y = \"posiciony\" font-family =\"Times New Roman\" font-size = \"11\" >alt/bal</text>";
	balance = balance.replace("posicionx",Integer.toString(x+20));
	balance= balance.replace("posiciony",Integer.toString(y-10));
	balance= balance.replace("alt",Integer.toString(ver.altura()));
	balance= balance.replace("bal",Integer.toString(balance(ver)));
	return balance;
    }
    private int balance(VerticeArbolBinario<Adaptador> ver){
        if(!ver.hayIzquierdo() && !ver.hayDerecho())
            return 0;
        if(!ver.hayIzquierdo())
            return ((-1)-ver.derecho().altura());
        if(!ver.hayDerecho())
            return (ver.izquierdo().altura()+1);
        return (ver.izquierdo().altura()-ver.derecho().altura());
    }

}