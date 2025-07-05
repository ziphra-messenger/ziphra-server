package ar.ziphra.common.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@NoArgsConstructor
@ZiphraIdExclude	
public class AESDTO implements Serializable{

	private static final long serialVersionUID = -5380719055089743303L;

	@ZiphraIdExclude	
	private String bitsEncrypt;
	@ZiphraIdExclude
	private String iteration;
	@ZiphraIdExclude	
	private String secretKeyAES;
	@ZiphraIdExclude	
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
