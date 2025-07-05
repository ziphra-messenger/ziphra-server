package ar.ziphra.commonback.common.interfaces;

import ar.ziphra.common.exceptions.ZiphraException;

public interface HealthCheckerPingActionInterface {

	void actionBeforePing() throws ZiphraException;
}
