package ar.ziphra.appserver.main;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.constants.HealthCheckerConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RestController
//@RequestMapping(path = HealthCheckerConstants.HEALTH_CHECKER_REST)
public class PingController {
	//@PostMapping(HealthCheckerConstants.HEALTH_CHECKER_REST_PING)
	public String ping() throws ZiphraException{
		return HealthCheckerConstants.HEALTH_CHECKER_RESPONSE;
	}
}



