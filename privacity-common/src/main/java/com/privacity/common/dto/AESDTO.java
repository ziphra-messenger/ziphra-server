package com.privacity.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AESDTO {
	public String secretKeyAES;
	public String saltAES;
	public String iteration;
	@Override
	public String toString() {
		return "AESDTO [secretKeyAES=" + check(secretKeyAES)
		+ ", saltAES=" + check(saltAES) + ", iteration=" +
		check(iteration) + "]";
	}
	

	private String check (String s) {
		if ( s != null ) {
			return s.length() + "";
		}else {
			return "null";
		}
	}

}
