package com.privacity.server.sessionmanager.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.privacity.common.util.RandomGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
@Log
public class MutateDigitUtil {

	//private static final String CONSTANT_DIGITS = "!#%-`abcdefghijklmnopqrstuvwxyz~´¿ÉÑÓÚáéíñýŒšƒ";
	private static final String CONSTANT_DIGITS = "!#%-_}abcdefghijklmnopqrstuvwxyz~¿@<>:;ÉÑÓÚáéíñýŒšƒ]";
	@Getter
	@Setter
	private int base;
	
	@Getter
	@Setter
	private Map<String, String> porNro = new HashMap<String, String>();
	@Getter
	@Setter
	private Map<String, String> porLetra = new HashMap<String, String>();

	public MutateDigitUtil(){
		this.base=MathBaseConverter.CONSTANT_BASE_MAX;
		buildMap();
	}
	public MutateDigitUtil(boolean ramdomBase ){
		if ( ramdomBase) {
			this.base= RandomGenerator.betweenTwoNumber(20,36);
		}else {
			this.base=MathBaseConverter.CONSTANT_BASE_MAX;
		}
		buildMap();
	}
	
	public MutateDigitUtil(int base ){
		this.base=base;
		buildMap();

	}

	private void buildMap() {
		ArrayList<String> lista = new ArrayList<String>();
		String bufer = CONSTANT_DIGITS;
		
		Random random = new Random();
		for ( int i = 0 ; i < base ; i++) {
			String letter = String.valueOf(bufer.toCharArray()[random.nextInt(bufer.length())]);
			//String letter = String.valueOf(i);
			lista.add(letter);
			bufer = bufer.replaceFirst( letter ,"");
		}
		
		Collections.sort(lista);
		

		for ( int i = 0 ; i < base ; i++) {
			String baseValue = new MathBaseConverter().convertirBaseN(base,i);

			
			
			porNro.put(baseValue, lista.get(i));
			porLetra.put( lista.get(i), baseValue);
			
//			System.out.println( lista.get(i) + " "  + baseValue  + " count " + porLetra.size());
			
//			System.out.println(porNro);
//			System.out.println(porLetra);

		}
		
		System.out.println(porNro);
		System.out.println(porLetra);
	}
	
	public String mutate(String cadena ) {

		for ( int i = 0 ; i < cadena.length() ; i++) {
			try {
				cadena = cadena.replaceAll(cadena.substring(i,i+1), porNro.get(cadena.substring(i, i+1)));
			} catch (Exception e) {
				//System.out.println("ERROR 2 " + cadena);
			}
		}
		return cadena;
	}

	public String unmutate(String cadena )  {

		for ( int i = 0 ; i < cadena.length() ;++ i) {
			try {
				cadena = cadena.replaceAll(cadena.substring(i,i+1), porLetra.get(cadena.substring(i, i+1)));
			} catch (Exception e) {
				//System.out.println("ERROR 1 " + cadena);
			}
		}
		return cadena;
	}
	
//	public static void main(String[] a ) {
//		MutateDigitUtil m = new MutateDigitUtil();
//		String mut = "12300ZR";
//		String mutado = m.mutate(mut);
//		
//		System.out.println("mutado --> " + mutado);
//
//		String un = m.mutate(mutado);
//		System.out.println("unmutado --> " + un);
//		
//
//	}
}