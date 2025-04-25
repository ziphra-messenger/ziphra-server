package com.privacity.server.component.common;

import org.springframework.stereotype.Component;

import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.interfaces.HealthCheckerPingActionInterface;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HealthCheckerPingAction implements HealthCheckerPingActionInterface{


	@Override
	public void actionBeforePing() throws PrivacityException {
		log.trace("No implementa HealthCheckerPingActionInterface");
	}
	

}