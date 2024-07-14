package com.privacity.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EncryptKeysDTO {
	
	public String publicKey; 
	public String privateKey;

	public String publicKeyNoEncrypt;
	
	public String borrar;


		
	private String check (String s) {
		if ( s != null ) {
			return s.length() + "";
		}else {
			return "null";
		}
	}



	@Override
	public String toString() {
		return "EncryptKeysDTO [publicKey=" + check(publicKey)
		+ ", privateKey=" + check(privateKey) + ", publicKeyNoEncrypt="
				+ check(publicKeyNoEncrypt) + ", borrar=" + check(borrar) + "]";
	}
}
