package ar.ziphra.commonback.common.utils;

import ar.ziphra.commonback.constants.HealthCheckerConstants;

public class HealthCheckerUtil {
	
	public static String log(String ping) {
		return "I am alive. Request: " + ping + " Response: " + HealthCheckerConstants.HEALTH_CHECKER_RESPONSE;
	}
}
