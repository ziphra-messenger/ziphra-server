package com.privacity.server.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.commonback.common.interfaces.HealthCheckerInterface;
import com.privacity.commonback.security.JwtUtilsAbstract;
import com.privacity.server.tasks.HealthChecker;


@Component
public class JwtUtils extends JwtUtilsAbstract{

	@Autowired
	@Lazy
	private HealthChecker healthChecker;
	protected HealthCheckerInterface getHealthChecker() {
		return healthChecker;
	}
	

}
