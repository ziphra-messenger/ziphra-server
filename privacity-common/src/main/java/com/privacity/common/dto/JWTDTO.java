package com.privacity.common.dto;

import java.io.Serializable;

import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@PrivacityIdExclude	
public class JWTDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7827611401422319369L;
	@PrivacityIdExclude	
	private String jwtSecret;
	@PrivacityIdExclude	
	private int jwtExpirationMs;
	public JWTDTO(String jwtSecret, int jwtExpirationMs) {
		super();
		this.jwtSecret = jwtSecret;
		this.jwtExpirationMs = jwtExpirationMs;
	}
}
