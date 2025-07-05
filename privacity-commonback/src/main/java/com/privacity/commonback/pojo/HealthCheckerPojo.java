package com.privacity.commonback.pojo;
import com.privacity.commonback.common.enumeration.HealthCheckerState;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthCheckerPojo {
	
	
	private String url;
	private HealthCheckerState state;

}
