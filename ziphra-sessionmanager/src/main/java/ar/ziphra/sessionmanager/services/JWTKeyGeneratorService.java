package ar.ziphra.sessionmanager.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.JWTDTO;

@Service
public class JWTKeyGeneratorService {

	@Value("${ar.ziphra.sessionmanager.services.ZiphraIdServices.encryptId.jwt}")
	private String jwt;
	private final int jwtExpirationMs=86400000;
	

	public JWTKeyGeneratorService() {
		super();

//		jwt="KjE/9phHEvWv+iwTwbietJba0gbHLizYjpuqgXAuovrXUqm71j0CEY+QEhDkuywENDOhVG3fbs8A0NKq+mOhGK/7zjsreH3IJx/2fv5dI5BtL3zBc2HMz2BReZ81jqVzO";

	}



	public JWTDTO get() {
		return new JWTDTO(jwt, jwtExpirationMs);
	}


	
	
}
