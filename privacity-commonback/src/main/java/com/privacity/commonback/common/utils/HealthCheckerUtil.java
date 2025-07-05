package com.privacity.commonback.common.utils;

import com.privacity.commonback.constants.HealthCheckerConstants;

public class HealthCheckerUtil {
	
	public static String log(String ping) {
		return "I am alive. Request: " + ping + " Response: " + HealthCheckerConstants.HEALTH_CHECKER_RESPONSE;
	}
}
