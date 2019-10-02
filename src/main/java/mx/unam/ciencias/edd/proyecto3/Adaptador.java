package mx.unam.ciencias.edd.proyecto3;

public class Adaptador implements Comparable<Adaptador>{
    private String palabra;

    private int apariciones;

    private char letra;
    
    public Adaptador(){
	palabra = "";
    }

    public Adaptador(String palabra,int apariciones){
        this.palabra = palabra;
        this.apariciones = apariciones;
    }
    
    public Adaptador(String palabra,int apariciones, char letra){
	this.palabra = palabra;
	this.apariciones = apariciones;
	this.letra = letra;
    }
    
    public void setLetra(char letra){
	this.letra = letra;
    }
    
    public int compareTo(Adaptador n){
	return this.apariciones-n.apariciones;
    }
    public String getPalabra(){
	return palabra;
    }
    public String getLetra(){
	return Character.toString(letra);
    }
    
    public int getApariciones(){
        return apariciones;
    }
    
    @Override public String toString(){
	return Character.toString(letra);
    }

}