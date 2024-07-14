package com.privacity.server.sessionmanager.util;

public class MathBaseConverter {


	private static final String CONSTANT_BASE_ALPHANUM = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final int CONSTANT_BASE_MAX = CONSTANT_BASE_ALPHANUM.length();

	public static final int CONSTANT_BASE_2 = 2;
	public static final int CONSTANT_BASE_8 = 8;
	public static final int CONSTANT_BASE_10 = 10;
	public static final int CONSTANT_BASE_16 = 16;

	public  long convertirBase10(String to10){

		return convertirBase10(CONSTANT_BASE_MAX, to10);
	}

	public  long convertirBase10(int base, String to10){

		int cont=0;
		long suma=0;
		char[] charArray = to10.toCharArray();
		for ( int i=charArray.length -1; i>=0 ; i--) {
			//System.out.println ("entrada: " + charAt2( to10.toCharArray()[i]+"" ));
			suma +=  (long) (  charAt2( charArray[i]+"" ) * Math.pow(base,cont)) ;
			cont++;
			//System.out.println ("paso: " +    charAt2( to10.toCharArray()[i]+"" ) + " suma " + suma);
		}
		//System.out.println ("inverso: " + suma);
		return suma; 
	}

	public  String convertirBaseN(long num){
		return convertirBaseN(CONSTANT_BASE_MAX, num );
	}
	public  String convertirBaseN(int base,long num){
		if (num == 0 ) return "0";
		String salida = "";
		long div=num, mod=0;
		while(num>=base){
			div = num / base;
			mod = num % base;  
			salida = charAt((int)mod) + salida;
			num=div;
		}
		if(div > 0)
			salida = charAt((int)div) + salida;
		return salida;
	}

	private  String charAt(int pos){

		return String.valueOf(CONSTANT_BASE_ALPHANUM.charAt(pos));
	}

	private  int charAt2(String pos){

		return CONSTANT_BASE_ALPHANUM.indexOf(pos);
	}

//	public static void main(String[] as ) {
//		ArrayList<String> a = new ArrayList();
//		/*
//		a.add("4");
//		
//		a.add("7");
//		a.add("C");
//		a.add("I");
//		
//		a.add("0Z");
//		a.add("19");
//		a.add("1B");
//		a.add("1C");
//		a.add("1J");
//		a.add("1L");
//		a.add("1M");		
//		a.add("2A");
//		a.add("06Y");
//		a.add("1MM");
//		a.add("1A4H");
//		*/
//
//		
//		//for (int i = 0 ; i < 10 ; i++) {
//			System.out.println (
//					convertirBaseN( 21472));
//		System.out.println (
//				convertirBaseN( 21204));
//		System.out.println (
//				convertirBaseN( 21120));
//
//		
//
//		//}
//
//		for (int i = 0 ; i < a.size() ; i++) {
//			System.out.println (
//					convertirBase10(a.get(i)));
//		}
//
//		MutateDigitUtil m = new MutateDigitUtil();
//		
//		System.out.println (
//				convertirBase10("GKG"));
//		
//		System.out.println (
//				convertirBase10("GD0"));
//		
//		
//		
//
//	}
}
