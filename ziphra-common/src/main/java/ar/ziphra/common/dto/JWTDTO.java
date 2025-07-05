package ar.ziphra.common.dto;

import java.io.Serializable;

import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@ZiphraIdExclude	
public class JWTDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7827611401422319369L;
	@ZiphraIdExclude	
	private String jwtSecret;
	@ZiphraIdExclude	
	private int jwtExpirationMs;
	public JWTDTO(String jwtSecret, int jwtExpirationMs) {
		super();
		this.jwtSecret = jwtSecret;
		this.jwtExpirationMs = jwtExpirationMs;
	}
}
