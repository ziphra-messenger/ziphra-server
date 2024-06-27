package com.privacity.server.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.util.UtilService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


	private String jwtSecret;
	
	@Value("${privacity.security.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	@Value("${privacity.security.ramdom.jwtSecret}")
	private boolean createRamdomJwtSecret;
	
	@Autowired @Lazy
	private FacadeComponent comps;

	
	public JwtUtils(@Autowired UtilService utilService, @Value("${privacity.security.ramdom.jwtSecret}")
	
	boolean createRamdomJwtSecret , @Value("${privacity.security.jwtSecret}") String jwtSecretDefault,
	@Value("#{T(Boolean).parseBoolean('${privacity.security.jwtSecret.mix}')}")Boolean mix,
	@Value("#{T(Integer).parseInt('${privacity.security.jwtSecret.mix.iteration}')}") int mixIteration,
				
				@Value("#{T(Boolean).parseBoolean('${privacity.security.ramdom.jwtSecret.mix.iteration}')}")Boolean mixIterationRandom
			) {
		//@Value("#{T(Boolean).jwtSecretDefault('${privacity.security.jwtSecret.mix}')}")Boolean mix,
		super();
		//Boolean.parseBoolean(jwtSecretDefault)
		jwtSecret=jwtSecretDefault;
		if (createRamdomJwtSecret) {
			jwtSecret= comps.common().randomGenerator().jwtSecret();
		}
		if ( mix == true) {

			jwtSecret = utilService.mix(jwtSecret,mixIterationRandom, mixIteration,10, 100);
			//jwtSecret = utilService.shuffleString(jwtSecret);
			
		
		}
	}

	public String generateJwtToken(Authentication authentication) {

		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		return Jwts.builder()
				.setSubject((userPrincipal.getUsername()))
				.setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
