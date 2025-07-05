package ar.ziphra.server.main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.interfaces.HealthCheckerPingActionInterface;
import ar.ziphra.commonback.common.utils.HealthCheckerUtil;
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



