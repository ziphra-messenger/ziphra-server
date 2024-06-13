package com.privacity.common.dto.servergralconf;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SGCServerInfo {
	private String apiRestUrl;
	private String wsUrl;
	
	private String name; 
	private String version; 
	
}
