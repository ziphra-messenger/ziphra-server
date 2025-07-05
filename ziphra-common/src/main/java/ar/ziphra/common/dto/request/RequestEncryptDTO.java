package ar.ziphra.common.dto.request;

import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestEncryptDTO {
	@ZiphraIdExclude	
	private String request;
}
