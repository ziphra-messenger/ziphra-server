package com.privacity.server.component.serverconf;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.servergralconf.SystemGralConf;
import com.privacity.common.dto.servergralconf.MinMaxLenghtDTO;
import com.privacity.common.dto.servergralconf.SGCAESDTO;
import com.privacity.common.dto.servergralconf.SGCAsymEncrypt;
import com.privacity.common.dto.servergralconf.SGCAuth;
import com.privacity.common.dto.servergralconf.SGCInvitationCode;
import com.privacity.common.dto.servergralconf.SGCMyAccountConfDTO;
import com.privacity.common.dto.servergralconf.SGCServerInfo;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.RandomGeneratorType;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.exceptions.ValidationException;

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
