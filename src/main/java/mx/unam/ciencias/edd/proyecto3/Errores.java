package mx.unam.ciencias.edd.proyecto3;
/**
 *Clase errores que se encargrá de lanzar todos los posibles errores que pueda tener el problema y de cerrarlo
 */
public class Errores{
    /**
     *Método errores que recibe un int y dependiendo deo valor de este lanzara un error y en todos los casos cierra el programa
     *@param error un numero para acceder a un mensaje
     */
    public void errores(int error){
	if(error == 1){
            System.err.println( "El uso del programa es el siguiente: \"java -jar target/proyecto3.jar -o directorio archivo1, archivo2, archivo 3\" no has usado la bandera \"-o\" antes del nombre de la carpeta en donde se guardarán los archivos" );
	    System.exit(0);
	}
	if(error == 2){
            System.err.println( "El uso del programa es : \"java -jar target/proyecto3.jar  -o directorio\" siendo directorio el nombre deseado de la carpet donde se pondrán los archios ó una dirección en memoria terminando con el nombre deseado para la carpeta que estara en la dirección brindada");
            System.exit(0);
	}
	if(error == 3){
	    System.err.println( "No encuentro el archuvo que me brinadste, verifica si lo has creado o si me diste bien la ruta de acceso");
            System.exit(0);
	}
	if(error == 4){
            System.err.println("El nombre que me diste despues de la bandera \"-o\" es un archivo");
            System.exit(0);
        }
	if(error == 5){
	    System.err.println("Algo salío mal, revisa que tenga los permisos necesaríos para crear y leer archivos");
            System.exit(0);
	}
	
    }
}