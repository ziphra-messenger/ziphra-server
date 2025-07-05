package ar.ziphra.commonback.common.utils;

public class MixBytesUtil {


	private static final int CONSTANT_ITERATION_MIN = 10;
	private static final int CONSTANT_ITERATION_MAX = 30;

	public  String  mix(String c) {
		return new String( mix(c.getBytes()));
	}

	public  byte[]  mix(byte[] c){

		int lenght = calcularLenght(c);

		long iteraciones = calcularIteraciones(c);
		//		System.out.println("largo " + c.length + "-- c " + " --");

		for (int j = 0 ; j< iteraciones ; j++) {

			byte[] a= new byte[c.length];

			byte[] arr = c;

			int lastIndexA=0;
			int lastIndexB=lenght/2;
			for (int i = 0 ; i <  lenght ; i++) {

				if (i%2 == 0) {
					a[lastIndexA++]=arr[i];
				}else {
					try {
						a[lastIndexB++]=arr[i];
					} catch (Exception e) {
					}
				}
			}

			c = a;
		}

		return c;
	}
	public  String  unmix(String c) {
		return new String( unmix(c.getBytes()));
	}
	public  byte[]  unmix(byte[] c){

		int lenght = calcularLenght(c);
		long iteraciones = calcularIteraciones(c);
		int j;
		for (j = 0 ; j <  iteraciones; j++) {
			byte[] arr = c;
			byte[] a= new byte[c.length];
			int lastIndex=0;
			//String b="";

			for (int i = 0 ; i <  (lenght/2) ; i++) {

				a[lastIndex] = arr[i];
				lastIndex++;
				try {
					a[lastIndex]= arr[i +lenght/2];
					lastIndex++;
				} catch (Exception e) {
				}

				c = a;
			}
			//			System.out.println( j +  " - " + new String (c) + "--");
		}
		//		System.out.println( j +  " - " + new String (c) + "--");

		return c;
	}

	private static int calcularLenght(byte[] c) {

		int lenght = c.length;
		if( c.length % 2 != 0){
			lenght = c.length+1;
		}
		return lenght;
	}

	private static long calcularIteraciones(byte[] c) {

		long suma=0;
		for ( int k = 0 ; k < c.length ; k++) {
			suma += c[k];
		}

		long iteraciones = suma;

		while(true) {
			iteraciones = suma;
			if (iteraciones > CONSTANT_ITERATION_MAX) {
			}else if (iteraciones < CONSTANT_ITERATION_MIN) {
				iteraciones = CONSTANT_ITERATION_MIN;
				break;
			}			
			else if (iteraciones >= CONSTANT_ITERATION_MIN &&  (iteraciones <= CONSTANT_ITERATION_MAX)) {
				break;
			}	
			//System.out.println("suma = " + suma);
			suma= suma/6;
		}
		//		System.out.println("iteraciones = " + iteraciones);
		return iteraciones;
	} 
}