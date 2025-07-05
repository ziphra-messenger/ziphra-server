package ar.ziphra.commonback.common.interfaces;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.enumeration.HealthCheckerServerType;
import ar.ziphra.commonback.pojo.HealthCheckerPojo;

public interface HealthCheckerInterface {

	void alertOffLine(HealthCheckerServerType e);

	boolean isOnline(HealthCheckerServerType e);

	boolean thereIsA(HealthCheckerServerType e);

	HealthCheckerPojo getServer(HealthCheckerServerType e);
	
	public String getServerValidate(HealthCheckerServerType t) throws ZiphraException;

}