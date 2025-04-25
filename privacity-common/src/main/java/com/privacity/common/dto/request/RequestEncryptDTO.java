package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEncryptDTO {
	@PrivacityIdExclude	
	private String request;
}
