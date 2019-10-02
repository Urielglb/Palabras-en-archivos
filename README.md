# Palabras-en-archivos
Programa que será capaz de leer varios un archivos y generará un documento HTML por archivo que contendrá las palabras de estos con sus repeticiones, una gráfica de pastel,una gráfica de barras, un árbol rojinegro y un árbol avl con las palabras mas usadas,ademas de un index con una liga al HTML de cada archivo.

Para la ejecución del archivo es necesario tener maven y se recomienda usar la version de Java 8 pues el proyecto fue escrita en esta.

Los comandos para correr el programa son:

-Para que se compile e instale el .jar del proyecto

$ mvn install

-Para ejecutarlo

$ java -jar target/proyecto3.jar /ruta_de_archivo1 /ruta_de_archivo2 -o /ruta_de_archivos_generados

El programa puede leer el número de archivos que desee el usuario, los archivos que genere el programa se guardarán en una carpeta de nombre y ubicaión que brinde el usuario, debera de pasar esta ruta y nombre seguido de la bandera -o, de forma contraria no servirá

No es necesario que el usuario mande los archivos primero y despues el nombre de la ruta, los puede mandar en el orden que este deseé
