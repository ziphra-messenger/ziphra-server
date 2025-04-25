package com.privacity.common.dto;

import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@PrivacityIdExclude	
@AllArgsConstructor
@NoArgsConstructor
public class EncryptKeysDTO {
	@PrivacityIdExclude	
	private String publicKey;
	@PrivacityIdExclude	
	private String privateKey;
	@PrivacityIdExclude	
	private String publicKeyNoEncrypt;
	@PrivacityIdExclude	
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
