package com.privacity.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@PrivacityIdExclude	
public class AESDTO implements Serializable{

	private static final long serialVersionUID = -5380719055089743303L;

	@PrivacityIdExclude	
	private String bitsEncrypt;
	@PrivacityIdExclude
	private String iteration;
	@PrivacityIdExclude	
	private String secretKeyAES;
	@PrivacityIdExclude	
	private String saltAES;

	private String check (String s) {
		if ( s != null ) {
			return s.length() + "";
		}else {
			return "null";
		}
	}


	public String toStringComplete() {
		return "AESDTO [bitsEncrypt=" + bitsEncrypt + ", iteration=" + iteration + ", secretKeyAES=" + secretKeyAES
				+ ", saltAES=" + saltAES + "]";
	}

	@Override
	public String toString() {
		return "AESDTO [bitsEncrypt=" + bitsEncrypt + ", iteration=" + iteration + ", secretKeyAES=" + check(secretKeyAES)
		+ ", saltAES=" + check(saltAES) + "]";
	}


}
