package com.privacity.commonback.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.interfaces.HealthCheckerPingActionInterface;
import com.privacity.commonback.common.utils.HealthCheckerUtil;
import com.privacity.commonback.constants.HealthCheckerConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(path = HealthCheckerConstants.HEALTH_CHECKER_REST)
public class HealthCheckerController {
	
	private HealthCheckerPingActionInterface healthCheckerPingActionInterface;

	public HealthCheckerController(@Autowired
			HealthCheckerPingActionInterface healthCheckerPingActionInterface) {
		super();
		this.healthCheckerPingActionInterface = healthCheckerPingActionInterface;
	}

	@PostMapping(HealthCheckerConstants.HEALTH_CHECKER_REST_PING)
	public String ping(@RequestParam String ping) throws PrivacityException{
		healthCheckerPingActionInterface.actionBeforePing();
		log.trace(HealthCheckerUtil.log(ping));
		return HealthCheckerConstants.HEALTH_CHECKER_RESPONSE;
	}



}
