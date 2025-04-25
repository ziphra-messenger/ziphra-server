package com.privacity.commonback.security;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.privacity.common.adapters.LocalDateAdapter;
import com.privacity.common.dto.JWTDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.commonback.common.interfaces.HealthCheckerInterface;
import com.privacity.commonback.constants.SessionManagerRestConstants;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class JwtUtilsAbstract {

	protected abstract  HealthCheckerInterface getHealthChecker();
	
	private String jwtSecret=null;
	
	private int jwtExpirationMs=0;
	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
			.create();

	
	public JwtUtilsAbstract() {
	}

	//@PostConstruct
	public void pc() throws PrivacityException {
	  	  try {
	  		  
	  		
			RestTemplate rest = new RestTemplate();
			  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			  //map.add("idGrupo", "idGrupo=");

			  
			  JWTDTO dto =rest.postForObject(getHealthChecker().getServerValidate(HealthCheckerServerType.SESSION_MANAGER)+
					SessionManagerRestConstants.TOKEN+SessionManagerRestConstants.TOKEN_GET	, map, JWTDTO.class);
			 //log.trace("dtoS TOKEN obtenido: " + dtoS);
			//JWTDTO dto = gson.fromJson(dtoS, JWTDTO.class);
			log.trace("dto TOKEN obtenido: " + dto.toString());
			  jwtSecret = dto.getJwtSecret();
			  jwtExpirationMs= dto.getJwtExpirationMs();
			  log.debug("TOKEN obtenido: " + jwtSecret);
		} catch (RestClientException e) {
			getHealthChecker().alertOffLine(HealthCheckerServerType.SESSION_MANAGER);
			throw new PrivacityException(ExceptionReturnCode.AUTH_GETTING_TOKEN_FAIL.getToShow(e.getMessage()));		
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw new PrivacityException(ExceptionReturnCode.AUTH_GETTING_TOKEN_FAIL.getToShow(e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new PrivacityException(ExceptionReturnCode.AUTH_GETTING_TOKEN_FAIL.getToShow(e.getMessage()));
		}
	}
	public String generateJwtToken(String username) throws PrivacityException {
		if (jwtSecret==null) pc();

		log.debug("Generate token con jwtSecret " + jwtSecret);

		return Jwts.builder()
				.setSubject((username))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUserNameFromJwtToken(String token) throws PrivacityException {
		if (jwtSecret==null) pc();
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) throws PrivacityException {
		if (jwtSecret==null) pc();
	
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
	
}
