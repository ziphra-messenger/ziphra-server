package ar.ziphra.messaging.task;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ar.ziphra.commonback.common.enumeration.HealthCheckerServerType;
import ar.ziphra.commonback.components.HealthCheckerTaskAbstract;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HealthCheckerTask extends HealthCheckerTaskAbstract{

	@Value("${ar.ziphra.server.tasks.healthChecker.serverSessionnanager}")
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
