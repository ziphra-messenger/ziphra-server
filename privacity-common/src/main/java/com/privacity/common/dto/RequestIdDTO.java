package com.privacity.common.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.privacity.common.annotations.PrivacityIdExclude;

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
	@PrivacityIdExclude	
	private String requestIdClientSide;
	@PrivacityIdExclude	
	private String requestIdServerSide;
	@PrivacityIdExclude	
	private LocalDateTime date;

}
