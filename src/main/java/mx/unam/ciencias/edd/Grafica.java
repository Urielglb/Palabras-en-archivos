package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            // Aquí va su código.
	    iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
	    return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
	 @Override public T next() {
            // Aquí va su código.
	     return iterador.next().elemento;
	 }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* El diccionario de vecinos del vértice. */
        public Diccionario<T, Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
	    this.elemento = elemento;
	    this.color = Color.NINGUNO;
	    vecinos = new Diccionario<T,Vecino>();
        }
        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
	    return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
	    return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
	    if(this == null)
		return Color.NINGUNO;
	    return color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
	    return vecinos;
	    
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
	    this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            // Aquí va su código.
	    return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.
	    if(esInfinito(this) && !esInfinito(vertice)||distancia-vertice.distancia>0)
		return 1;
	    if(!esInfinito(this)&& esInfinito(vertice)||distancia-vertice.distancia<0)
		return -1;
	    if(esInfinito(this)&& esInfinito(vertice))
		return 0;
	    return 0;
        }
	
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            // Aquí va su código.
	    this.vecino = vecino;
	    this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            // Aquí va su código.
	    return vecino.get();
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            // Aquí va su código.
	    return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            // Aquí va su código.
	    return vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
	    return vecino.vecinos();
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
	vertices = new Diccionario<T,Vertice>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
	return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
	return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
	if (elemento == null)
	    throw new IllegalArgumentException("el elemento es nulo");
	if(contiene(elemento))
	    throw new IllegalArgumentException("Ya esta el elemento");
	Vertice ver = new Vertice(elemento);
	vertices.agrega(elemento,ver);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.
	if(a.equals(b) || sonVecinos(a,b))
	    throw  new IllegalArgumentException ("Los vertices son iguales o ya son vecinos");
	if(!contiene(a) || !contiene(b))
	    throw new NoSuchElementException ("No hay un elemento en la gráfica o ninguno");
	Vertice uno = getVertice(a);
	Vertice dos = getVertice(b);
	uno.vecinos.agrega(b,new Vecino(dos,1));
	dos.vecinos.agrega(a,new Vecino(uno,1));
	aristas++;
	
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
	if(a.equals(b) || sonVecinos(a,b) || peso<0)
            throw  new IllegalArgumentException ("Los vertices son iguales o ya son vecinos");
        if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException ("No hay un elemento en la gráfica o ninguno");
	Vertice uno = getVertice(a);
        Vertice dos = getVertice(b);
        uno.vecinos.agrega(b,new Vecino(dos,peso));
        dos.vecinos.agrega(a,new Vecino(uno,peso));
        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.
	if(a.equals(b)||!sonVecinos(a,b))
	    throw  new IllegalArgumentException ("Los vertices son iguales o ya son vecinos");
	if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException ("No hay un elemento en la gráfica o ninguno");
	Vertice uno = getVertice(a);
	Vertice dos = getVertice(b);
	for(Vecino vec : uno.vecinos)
	    if(vec.get().equals(dos.get()))
		uno.vecinos.elimina(vec.vecino.elemento);
	for(Vecino vec : dos.vecinos)
            if(vec.get().equals(uno.get()))
		dos.vecinos.elimina(vec.vecino.elemento);
	aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <tt>true</tt> si el elemento está contenido en la gráfica,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
	if(elemento == null)
	    return false;
	Vertice ver = getVertice(elemento);
	return ver != null;
    }
    private Vertice getVertice(T elemento){
	for(Vertice i : vertices){
	    if(i.elemento.equals(elemento))
		return i;
	}
	return null;
    }
    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
	if(!contiene(elemento))
	    throw new NoSuchElementException ("No hay un elemento en la gráfica o ninguno");
	Vertice eliminar = getVertice(elemento);
	aristas -= eliminar.vecinos.getElementos();
	vertices.elimina(elemento);
	for(Vecino vec:eliminar.vecinos)
	    for(Vecino v : vec.vecino.vecinos)
		if(v.get().equals(eliminar.get()))
		    vec.vecino.vecinos.elimina(v.get());
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <tt>true</tt> si a y b son vecinos, <tt>false</tt> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
	if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException ("No hay un elemento en la gráfica o ninguno");
	Vertice ver = getVertice(a);
	Vertice vecino = getVertice(b);
	for(Vecino v : ver.vecinos)
	    if(v.vecino.get().equals(vecino.get()))
		return true;
	return false;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException ("No hay un elemento en la gráfica o ninguno");
	if(a.equals(b)||!sonVecinos(a,b))
            throw  new IllegalArgumentException ("Los vertices son iguales o ya son vecinos");
	double peso = 0;
	Vertice ver = getVertice(a);
	Vertice buscar = getVertice(b);
	for(Vecino vec :ver.vecinos)
	    if(vec.vecino.equals(buscar))
		return vec.peso;
	return 0;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        // Aquí va su código.
	if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException ("No hay un elemento en la gráfica o ninguno");
	if(a.equals(b)||!sonVecinos(a,b))
            throw  new IllegalArgumentException ("Los vertices son iguales o ya son vecinos");
	Vertice uno = getVertice(a);
	Vertice dos = getVertice(b);
	for(Vecino vec : uno.vecinos)
	    if(vec.vecino.equals(dos))
		vec.peso = peso;
	for(Vecino vec : dos.vecinos)
            if(vec.vecino.equals(uno))
                vec.peso = peso;

	
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.
	Vertice ver = getVertice(elemento);
	if(ver == null)
	    throw new NoSuchElementException ("Ese elemento no está en la gráfica");
	return ver;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        // Aquí va su código.
	if(vertice == null || (vertice.getClass()!=Vertice.class && vertice.getClass()!=Vecino.class) )
	    throw new IllegalArgumentException();
	if(vertice.getClass() == Vertice.class){
	    Vertice v = (Vertice)vertice;
	    v.color = color;
	}
	if(vertice.getClass() == Vecino.class){
	    Vecino v = (Vecino)vertice;
	    v.vecino.color = color;
	}
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código.
	pintaRojo();
	Cola<Vertice> cola = new Cola<Vertice>();
	Vertice ver = vertices.iterator().next();
	ver.color = Color.NEGRO;
	cola.mete(ver);
	while(!cola.esVacia()){
	    Vertice v = cola.saca();
	    for(Vecino vec : v.vecinos)
		if(vec.vecino.color == Color.ROJO){
		    vec.vecino.color = Color.NEGRO;
		    cola.mete(vec.vecino);
		}
	}
	boolean conexa = negros();
	despinta();
	return conexa;
    }
    private boolean negros(){
	for(Vertice v : vertices)
	    if(v.color == Color.ROJO)
		return false;
	return true;
    }
    private void pintaRojo(){
	for(Vertice v : vertices)
	    v.color = Color.ROJO;
    }
    private void despinta(){
	for(Vertice v :vertices)
            v.color = Color.NINGUNO;
    }
    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
	for(Vertice v : vertices)
	    accion.actua(v);
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
	if(!contiene(elemento))
	    throw new NoSuchElementException ("Ese elemento no está en la gráfica");
	pintaRojo();
	Vertice ver = getVertice(elemento);
	ver.color = Color.NEGRO;
	Cola<Vertice> cola = new Cola<Vertice>();
	cola.mete(ver);
	while(!cola.esVacia()){
	    Vertice v = cola.saca();
	    accion.actua(v);
	    for(Vecino vec: v.vecinos)
		if(vec.vecino.color == Color.ROJO){
                    vec.vecino.color = Color.NEGRO;
                    cola.mete(vec.vecino);
		}
	}
	despinta();
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
	if(!contiene(elemento))
            throw new NoSuchElementException ("Ese elemento no está en la gráfica");
	pintaRojo();
        Vertice ver = getVertice(elemento);
        ver.color = Color.NEGRO;
        Pila<Vertice> pila = new Pila<Vertice>();
	pila.mete(ver);
        while(!pila.esVacia()){
            Vertice v = pila.saca();
            accion.actua(v);
            for(Vecino vec: v.vecinos)
                if(vec.vecino.color == Color.ROJO){
                    vec.vecino.color = Color.NEGRO;
                    pila.mete(vec.vecino);
                }
        }
	despinta();
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
	return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
	vertices.limpia();
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        // Aquí va su código.
	String vertices = "{";
	String aristas = "{";
	for(Vertice v : this.vertices)
	    vertices += v.elemento.toString()+", ";
	vertices += "}";
	pintaRojo();
	for(Vertice ver : this.vertices){
	    for(Vecino vec : ver.vecinos)
		if(vec.vecino.color == Color.ROJO)
		    aristas += "("+ver.elemento.toString()+", "+vec.vecino.elemento.toString()+"), ";
	    ver.color = Color.NEGRO;
	}
	aristas += "}";
	return vertices + ", " + aristas;    
	
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la gráfica es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        // Aquí va su código.
	if(vertices.getElementos()!=grafica.vertices.getElementos())
	    return false;
	if(aristas!=grafica.getAristas())
	    return false;
	for(Vertice v : vertices){
	    v.color = Color.ROJO;
	    if(!grafica.contiene(v.elemento))
		return false;
	}
	for(Vertice v : vertices)
	    for(Vecino vec :v.vecinos){
		if(vec.vecino.color == Color.ROJO)	
		    if(!grafica.sonVecinos(v.elemento,vec.vecino.elemento))
			return false;
		v.color = Color.NEGRO;
	    }
	return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <tt>a</tt> y
     *         <tt>b</tt>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
	if(!contiene(origen) || !contiene(destino))
	    throw new NoSuchElementException();
	Lista<VerticeGrafica<T>> lista = new Lista<VerticeGrafica<T>>();
	Vertice a = getVertice(origen);
	if(origen.equals(destino)){
            lista.agrega(getVertice(origen));
            return lista;
        }
	setInfinito(a,-1);
	Cola<Vertice> cola = new Cola<Vertice>();
	cola.mete(a);
	while(!cola.esVacia()){
	    Vertice u = cola.saca();
	    for(Vecino v : u.vecinos){
		if(esInfinito(v.vecino)){
		    v.vecino.distancia = u.distancia+1;
		    cola.mete(v.vecino);
		}
	    }
	}
	Vertice b = getVertice(destino);
	if(esInfinito(b))
	    return lista;
	cola.mete(b);
	while(!cola.esVacia()){
	    Vertice u = cola.saca();
	    lista.agrega(u);
	    for(Vecino v : u.vecinos)
		if(v.vecino.distancia == u.distancia-1){
		    cola.mete(v.vecino);
		    break;
		}
	}
	return lista.reversa();
    }
    private boolean esInfinito(Vertice ver){
	return ver.distancia<0 || ver.distancia == Double.MAX_VALUE;
    }
    private void setInfinito(Vertice origen,double infinito){
	for(Vertice v : vertices)
	    v.distancia = infinito;
	origen.distancia = 0;
    }
    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <tt>origen</tt> y
     *         el vértice <tt>destino</tt>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
		if(!contiene(origen) || !contiene(destino))
            throw new NoSuchElementException();
        Lista<VerticeGrafica<T>> lista = new Lista<VerticeGrafica<T>>();
        Vertice a = getVertice(origen);
        Vertice b = getVertice(destino);
	if(a.equals(b)){
            lista.agrega(a);
            return lista;
        }
	MonticuloMinimo<Vertice> dij = new MonticuloMinimo<Vertice>();
	setInfinito(a,Double.MAX_VALUE);
	for(Vertice ver : vertices)
	    dij.agrega(ver);
	while(!dij.esVacia()){
	    Vertice ver = dij.elimina();
	    for(Vecino v : ver.vecinos)
		if(v.vecino.distancia > (ver.distancia + v.peso)){
		    v.vecino.distancia = ver.distancia + v.peso;
		    dij.reordena(v.vecino);
		}
	}
	if(esInfinito(b))
	    return lista;
	Cola<Vertice> col = new Cola<Vertice>();
	col.mete(b);
        while(!col.esVacia()){
	    Vertice vertice = col.saca();
	    lista.agrega(vertice);
            for(Vecino v : vertice.vecinos)
                if(v.vecino.distancia==(vertice.distancia-v.peso)){
		    col.mete(v.vecino);
		    break;
		}
        }
	return lista.reversa();
    }
}
