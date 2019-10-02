package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;

public class HTML{

    protected String HTML = "<!DOCTYPE html>" + "\n" +"<html>"+"\n";
	
    protected void agregaParrafo(String contenido){
	HTML += "<p>" + contenido + "</p>" + "\n";
    }

    protected void agregaNombre(String nombre){
	HTML += "<head>" + "\n" + "<title>" + nombre + "</title>" +"\n" + "</head>" + "\n";
    }
    
    protected void agregaTitulo(String titulo){
	HTML += "<h1>" + titulo + "</h1>";
    }
    
    protected void agregaObjeto(String objeto){
	HTML += "<object " + objeto+"></object>";
    }
    
}