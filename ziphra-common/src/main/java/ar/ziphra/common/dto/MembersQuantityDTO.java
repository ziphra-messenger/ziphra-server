package ar.ziphra.common.dto;

import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ZiphraIdExclude	
@AllArgsConstructor
@NoArgsConstructor
public class MembersQuantityDTO {
	@ZiphraIdExclude	
	private int quantityOnline;
	@ZiphraIdExclude	
	private int totalQuantity;
}
