package ar.ziphra.common.dto;

import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(fluent = false, chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ZiphraIdExclude	
public class AESAllDTO {
	@ZiphraIdExclude	
	private AESDTO sessionAESDTOServerIn;
	@ZiphraIdExclude	
	private AESDTO sessionAESDTOWS;
	@ZiphraIdExclude	
	private AESDTO sessionAESDTOServerOut;
}
