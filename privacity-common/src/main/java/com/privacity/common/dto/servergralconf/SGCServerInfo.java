package com.privacity.common.dto.servergralconf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SGCServerInfo {
	private String apiRestUrl;
	private String wsUrl;
	
	private String name; 
	private String version; 
	
}
