package ar.ziphra.appserver.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.commonback.common.interfaces.HealthCheckerInterface;
import ar.ziphra.commonback.security.JwtUtilsAbstract;
import ar.ziphra.appserver.tasks.HealthCheckerTask;


@Component
public class JwtUtils extends JwtUtilsAbstract{

	@Autowired
	@Lazy
	private HealthCheckerTask healthChecker;
	protected HealthCheckerInterface getHealthChecker() {
		return healthChecker;
	}
	

}
