package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        // Aquí va su código.
	int r = 0;
	int residuo = llave.length%4;
	byte[] nuevo;
	for(int i = 3; i<(llave.length-residuo);i+=4)
		r = r^corta(llave,i);
	int n = 0;
	if(residuo != 0){
	    n = llena(llave,residuo);
	    r = r^n;   
	}
	return r;
	    
    }
    private static int llena (byte[]llave,int res){
	int r = 0;
	if(res == 1)
	    r = combina(llave[llave.length-1],(byte)0,(byte)0,(byte)0);
	else if(res == 2)
	    r = combina(llave[llave.length-2],llave[llave.length-1],(byte)0,(byte)0);
	else if(res == 3)
	    r = combina(llave[llave.length-3],llave[llave.length-2],llave[llave.length-1],(byte)0);
	return r;
    }
    private static int corta(byte[]llave,int i){
	return combina(llave[i-3],llave[i-2],llave[i-1],llave[i]);
    }
    private static int combina(byte a, byte b, byte c, byte d){
	return ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | ((d & 0xFF));
    }
    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
        // Aquí va su código.
	int a = 0x9E3779B9;
	int b = 0x9E3779B9;
	int c = 0xFFFFFFFF;
	int l = llave.length;
	for(int i =0; l>=12;i+=12){
	    a += combina(llave[i+3],llave[i+2],llave[i+1],llave[i]); 
	    b += combina(llave[i+7],llave[i+6],llave[i+5],llave[i+4]);
	    c += combina(llave[i+11],llave[i+10],llave[i+9],llave[i+8]);
	    l -= 12;
	    int[] mezcla = mezcla(a,b,c);
	    a = mezcla[0];
	    b = mezcla[1];
	    c = mezcla[2];
	}
	if(l>0){
	    if(l<=4)
		a += bobJ(llave,l);
	    else if(l<=8){
		a += bobJ(llave,l);
		l -= 4;
		b += bobJ(llave,l);
		l -= 4;
	    }
	    else if(l<12){
		a += bobJ(llave,l);
		l -= 4;
                b += bobJ(llave,l);
		l -= 4;
		c += bobJ(llave,l)<<8;
	    }
	}
	c += llave.length;
	int[] mezcla = mezcla(a,b,c);
	a =mezcla[0];
	b =mezcla[1];
	c =mezcla[2];
	return c;
    }
    private static int bobJ(byte[] llave, int l){
	//System.out.println("VALOR DE L:" +l);
	byte a = (byte)0;
	byte b = (byte)0;
	byte c = (byte)0;
	byte d = (byte)0;
	a = (l>=4)? llave[llave.length-(l-3)]:a;
	b = (l>=3)? llave[llave.length-(l-2)]:b;
	c = (l>=2)? llave[llave.length-(l-1)]:c;
	d = llave[llave.length-l];
	return combina(a,b,c,d);
    }
	     
    private static int[] mezcla(int a,int b, int c){
	int []aux = new int[3]; 
	a -= b;
	a -= c;
	a ^= (c>>>13);
	b -= c;
	b -= a;
	b ^= (a<<8);
	c -= a;
	c -= b;
	c ^= (b>>>13);
	a -= b;
	a -=c;
	a ^= (c>>>12);
	b -= c;
	b -= a;
	b ^= (a<<16);
	c -= a;
	c -= b;
	c ^= (b>>>5);
	a -= b;
	a -= c;
	a ^= (c>>>3);
	b -= c;
	b -= a;
	b ^= (a<<10);
	c -= a;
	c -= b;
	c ^= (b>>>15);
	aux [0] = a;
	aux[1] = b;
	aux[2] = c;
	return aux;
    }
    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        // Aquí va su código.
	int h = 5381;
	for(int i = 0; i<llave.length ;i++)
	    h = h*33 + (llave[i] & 0xFF);
	return h;
    }
}
