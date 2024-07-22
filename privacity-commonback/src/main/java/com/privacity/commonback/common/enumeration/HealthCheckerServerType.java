package com.privacity.commonback.common.enumeration;

import java.util.stream.Stream;

public enum HealthCheckerServerType {
	MESSAGING,
	SESSION_MANAGER,
	IDS_PROVIDER,
	REQUEST_ID;
	
    public static Stream<HealthCheckerServerType> stream() {
        return Stream.of(HealthCheckerServerType.values()); 
    }
}
