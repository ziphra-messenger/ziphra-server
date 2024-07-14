package com.privacity.server.loadbalance.messageid.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Getter;
@Service
public class ForwardManagerService {

	@Getter
	@Value("${com.privacity.server.loadbalance.messageid.services.par}")
	private String par;

	@Getter
	@Value("${com.privacity.server.loadbalance.messageid.services.impar}")
	private String impar;
	
}
