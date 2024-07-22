package com.privacity.messaging.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.commonback.components.HealthCheckerAbstract;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HealthChecker extends HealthCheckerAbstract{

	@Value("${com.privacity.server.tasks.healthChecker.serverSessionnanager}")
	private String serverSessionmanager;

	@Override
	protected String getUrlServerForType(HealthCheckerServerType t) {
		if (HealthCheckerServerType.SESSION_MANAGER.equals(t)) {
			log.info("Enviando a HealthCheckerAbstract type: " + t.name() + " url: " + serverSessionmanager);
			return serverSessionmanager;
		}
		return null;
	}




	
}
