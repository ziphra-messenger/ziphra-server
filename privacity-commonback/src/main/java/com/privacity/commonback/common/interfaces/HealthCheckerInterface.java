package com.privacity.commonback.common.interfaces;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.commonback.pojo.HealthCheckerPojo;

public interface HealthCheckerInterface {

	void alertOffLine(HealthCheckerServerType e);

	boolean isOnline(HealthCheckerServerType e);

	boolean thereIsA(HealthCheckerServerType e);

	HealthCheckerPojo getServer(HealthCheckerServerType e);
	
	public String getServerValidate(HealthCheckerServerType t) throws PrivacityException;

}