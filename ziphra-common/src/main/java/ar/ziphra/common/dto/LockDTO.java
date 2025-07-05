package ar.ziphra.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.CUSTOM)
@ZiphraIdExclude	
public class LockDTO {
	@JsonInclude(Include.NON_DEFAULT)
	@ZiphraIdExclude	
	private boolean enabled;
	
	@JsonInclude(Include.NON_NULL)
	@ZiphraIdExclude	
	private int seconds;
	
	@JsonInclude(Include.NON_NULL)
	@ZiphraIdExclude	
	private int minSecondsValidation;


	
}
