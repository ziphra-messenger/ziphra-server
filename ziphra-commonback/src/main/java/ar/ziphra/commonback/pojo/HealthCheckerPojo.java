package ar.ziphra.commonback.pojo;
import ar.ziphra.commonback.common.enumeration.HealthCheckerState;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HealthCheckerPojo {
	
	
	private String url;
	private HealthCheckerState state;

}
