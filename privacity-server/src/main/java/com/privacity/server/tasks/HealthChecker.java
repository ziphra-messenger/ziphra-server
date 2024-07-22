package com.privacity.server.tasks;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.commonback.components.HealthCheckerAbstract;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HealthChecker extends HealthCheckerAbstract{

	@Value("${com.privacity.server.tasks.healthChecker.serverMessaging}")
	private String serverMessaging;

	@Value("${com.privacity.server.tasks.healthChecker.serverSessionnanager}")
	private String serverSessionmanager;

	@Value("${com.privacity.server.tasks.healthChecker.serverRequestId}")
	private String serverRequestId;

	@Value("${com.privacity.server.tasks.healthChecker.serverIdsProvider}")
	private String serverIdsProvider;

	@Override
	protected String getUrlServerForType(HealthCheckerServerType t) {
		if (HealthCheckerServerType.MESSAGING.equals(t)) {
			log.info("Enviando a HealthCheckerAbstract type: " + t.name() + " url: " + serverMessaging);
			return serverMessaging;
		}
		if (HealthCheckerServerType.SESSION_MANAGER.equals(t)) {
			log.info("Enviando a HealthCheckerAbstract type: " + t.name() + " url: " + serverSessionmanager);
			return serverSessionmanager;
		}
		if (HealthCheckerServerType.REQUEST_ID.equals(t)) {
			log.info("Enviando a HealthCheckerAbstract type: " + t.name() + " url: " + serverRequestId);
			return serverRequestId;
		}
		if (HealthCheckerServerType.REQUEST_ID.equals(t)) {
			log.info("Enviando a HealthCheckerAbstract type: " + t.name() + " url: " + serverIdsProvider);
			return serverIdsProvider;
		}
		return null;
	}
}
