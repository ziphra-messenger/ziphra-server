package com.privacity.server.component.serverconf;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.servergralconf.SystemGralConf;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.PrivacityException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ServerConfValidationService {
	
	@Autowired @Lazy
	private FacadeComponent comps;
	
	public LocalDateTime getTime() throws PrivacityException {
		//if (1 == 1 ) throw new ValidationException(ExceptionReturnCode.MYACCOUNT_LOCK_MIN_SECONDS_VALIDATION);
		return LocalDateTime.now();
	}
	public SystemGralConf getSystemGralConf(){
		
		return comps.common().serverConf().getSystemGralConf();
	}
}
