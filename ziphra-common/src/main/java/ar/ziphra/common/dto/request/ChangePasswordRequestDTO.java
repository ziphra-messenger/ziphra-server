package ar.ziphra.common.dto.request;

import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ZiphraIdExclude	
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDTO {
	@ZiphraIdExclude	
	private String username;
	@ZiphraIdExclude	
	private String newPassword;
	@ZiphraIdExclude	
	private String newPasswordConfirm;
	@ZiphraIdExclude	
	private String oldPassword;
}
