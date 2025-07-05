package ar.ziphra.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ZiphraIdExclude	
public class GrupoGralConfPasswordDTO {
	@JsonInclude(Include.NON_NULL)
	@ZiphraIdExclude	
	private boolean enabled;
	
	@JsonInclude(Include.NON_NULL)
	@ZiphraIdExclude	
	private boolean extraEncryptDefaultEnabled=true;
	
	@JsonInclude(Include.NON_NULL)
	@ZiphraIdExclude	
	private String password;
	
	@JsonInclude(Include.NON_NULL)
	@ZiphraIdExclude	
	private boolean deleteExtraEncryptEnabled=true;
	
	@JsonInclude(Include.NON_NULL)
	@ZiphraIdExclude	
	private String passwordExtraEncrypt;
	
	
		
}

