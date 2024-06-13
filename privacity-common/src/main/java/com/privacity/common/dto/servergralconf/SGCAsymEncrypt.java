package com.privacity.common.dto.servergralconf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SGCAsymEncrypt {
	private String type;
	private int bits; 
}
