package com.privacity.commonback.common.interfaces;

import com.privacity.common.exceptions.PrivacityException;

public interface HealthCheckerPingActionInterface {

	void actionBeforePing() throws PrivacityException;
}
