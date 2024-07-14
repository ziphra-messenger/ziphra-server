package com.privacity.common.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestIdDTO {
	public String requestIdClientSide;
	public String requestIdServerSide;
	public LocalDateTime date;

}
