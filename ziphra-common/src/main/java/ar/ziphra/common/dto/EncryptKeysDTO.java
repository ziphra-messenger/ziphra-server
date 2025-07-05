package ar.ziphra.common.dto;

import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@ZiphraIdExclude	
@AllArgsConstructor
@NoArgsConstructor
public class EncryptKeysDTO {
	@ZiphraIdExclude	
	private String publicKey;
	@ZiphraIdExclude	
	private String privateKey;
	@ZiphraIdExclude	
	private String publicKeyNoEncrypt;
	@ZiphraIdExclude	
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
