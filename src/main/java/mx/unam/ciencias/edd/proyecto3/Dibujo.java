package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

public abstract class Dibujo{
    
    protected String svg = "<svg width=\"largo\" height=\"alto\"" +" xmlns=\"http://www.w3.org/2000/svg\">"+"\n" + "<g>" + "\n";

    protected String agregaElemento( String elemento){
        String texto = "<text x=\"posicionx\" y = \"posiciony\" font-family =\"Times New Roman\" font-size = \"20\" >elemento</text>";
        texto = texto.replace("elemento",elemento);
        return texto;
    }
    
    public  abstract String dibuja(Coleccion<Adaptador> coleccion);

}