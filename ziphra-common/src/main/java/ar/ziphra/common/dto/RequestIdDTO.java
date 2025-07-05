package ar.ziphra.common.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import ar.ziphra.common.annotations.ZiphraIdExclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
public class RequestIdDTO {
	@ZiphraIdExclude	
	private String requestIdClientSide;
	@ZiphraIdExclude	
	private String requestIdServerSide;
	@ZiphraIdExclude	
	private LocalDateTime date;

}
