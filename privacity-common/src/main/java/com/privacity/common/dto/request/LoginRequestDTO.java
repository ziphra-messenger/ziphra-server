package com.privacity.common.dto.request;

import com.privacity.common.annotations.PrivacityIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@PrivacityIdExclude	
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
	@PrivacityIdExclude	
	private String username;
	@PrivacityIdExclude	
	private String password;
}
