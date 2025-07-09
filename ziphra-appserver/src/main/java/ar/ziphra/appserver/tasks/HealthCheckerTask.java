package ar.ziphra.appserver.tasks;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ar.ziphra.commonback.common.enumeration.HealthCheckerServerType;
import ar.ziphra.commonback.components.HealthCheckerTaskAbstract;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HealthCheckerTask extends HealthCheckerTaskAbstract{

	@Value("${ar.ziphra.appserver.tasks.healthChecker.serverMessaging}")
	private String serverMessaging;

	@Value("${ar.ziphra.appserver.tasks.healthChecker.serverSessionnanager}")
	private String serverSessionmanager;

	@Value("${ar.ziphra.appserver.tasks.healthChecker.serverRequestId}")
	private String serverRequestId;

	@Value("${ar.ziphra.appserver.tasks.healthChecker.serverIdsProvider}")
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
		if (HealthCheckerServerType.IDS_PROVIDER.equals(t)) {
			log.info("Enviando a HealthCheckerAbstract type: " + t.name() + " url: " + serverIdsProvider);
			return serverIdsProvider;
		}
		return null;
	}
}
