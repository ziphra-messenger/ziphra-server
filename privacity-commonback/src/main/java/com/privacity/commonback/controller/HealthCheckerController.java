package com.privacity.commonback.controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.commonback.common.utils.HealthCheckerUtil;
import com.privacity.commonback.constants.HealthCheckerConstants;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = HealthCheckerConstants.HEALTH_CHECKER_REST)
@Slf4j
public class HealthCheckerController {

	@PostMapping(HealthCheckerConstants.HEALTH_CHECKER_REST_PING)
	public String ping(@RequestParam String ping){
		log.trace(HealthCheckerUtil.log(ping));
		return HealthCheckerConstants.HEALTH_CHECKER_RESPONSE;
	}



}
