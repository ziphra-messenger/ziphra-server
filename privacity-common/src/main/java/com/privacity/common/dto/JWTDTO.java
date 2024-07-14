package com.privacity.common.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class JWTDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7827611401422319369L;
	public String jwtSecret;
	public int jwtExpirationMs;
	public JWTDTO(String jwtSecret, int jwtExpirationMs) {
		super();
		this.jwtSecret = jwtSecret;
		this.jwtExpirationMs = jwtExpirationMs;
	}
}
