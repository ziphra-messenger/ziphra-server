package com.privacity.server.component.encryptkeys;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class EncryptIdOrder {


	private long l3;
	public EncryptIdOrder() {
		


		 l3 = 
				Long.parseLong(
				RandomStringUtils.randomNumeric(new Random().nextInt(13-10) + 10)
				);
		

		
		
	}

	public String encrypt(long value) {
		long r = (value) + l3;
		return convertir(r);
		
	}

	public long desencrypt(String value) {
		long r2 = Long.parseLong(value) -l3; 
		return r2;
		
	}	
	public static String convertir(long val){
	    Locale.setDefault(Locale.US);
	    DecimalFormat num = new DecimalFormat("####");
	    return num.format(val);
	}
}
