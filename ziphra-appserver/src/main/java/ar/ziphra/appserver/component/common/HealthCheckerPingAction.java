package ar.ziphra.appserver.component.common;

import org.springframework.stereotype.Component;

import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.interfaces.HealthCheckerPingActionInterface;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HealthCheckerPingAction implements HealthCheckerPingActionInterface{


	@Override
	public void actionBeforePing() throws ZiphraException {
		log.trace("No implementa HealthCheckerPingActionInterface");
	}
	

}