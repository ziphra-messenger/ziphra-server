package com.privacity.server.component.auth;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.LoginDataDTO;
import com.privacity.common.dto.response.LoginDTOResponse;
import com.privacity.server.common.enumeration.ERole;
import com.privacity.server.common.exceptions.ValidationException;
import com.privacity.server.common.model.MyAccountConf;
import com.privacity.server.common.model.Role;
import com.privacity.server.common.model.Usuario;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.encrypt.RSA;
import com.privacity.server.security.JwtUtils;
import com.privacity.server.security.UserDetailsImpl;
import com.privacity.server.util.PrivacityLogguer;


@CrossOrigin(origins = "*", maxAge = 3600)
@Service
//@RequestMapping("/api/auth")
public class AuthProcesor {

	@Value("${serverconf.privacityIdAESOn}")
	private boolean encryptIds;
	
	@Autowired @Lazy
	private AuthenticationManager authenticationManager;

	
	@Autowired @Lazy
	private PrivacityLogguer privacityLogguer;
	

	@Autowired @Lazy
	private JwtUtils jwtUtils;
	
	@Autowired @Lazy
	private FacadeComponent comps;
	
	public LoginDTOResponse login( Usuario loginRequest) throws Exception {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getUsuarioPassword().getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		//List<UsuarioSessionIdInfo> info = SocketSessionRegistry.getSessionIds(userDetails.getUsername());
		
		AESDTO sessionAes = comps.service().usuarioSessionInfo().getSessionAesDTO(loginRequest.getUsername(),true);
		 PublicKey publicKey = comps.service().usuarioSessionInfo().getPublicKey(loginRequest.getUsername());
		 
		
		Usuario usuarioDB = comps.util().usuario().getUsuarioById(loginRequest.getUsername());
		
		 //
		// Encriptar publica
		
		RSA t = comps.common().RSA();
		////System.out.println("Session AES Key = " + info.getSessionAES().getSecretKeyAES());
		////System.out.println("Session AES Salt = " + info.getSessionAES().getSaltAES());
		////System.out.println("Session AES iterator = " + info.getSessionAES().getIteration());
		
		byte[] aesEncriptadoKey = t.encryptFilePublic(sessionAes.getSecretKeyAES().getBytes(StandardCharsets.UTF_8), publicKey);
		byte[] aesEncriptadoSalt = t.encryptFilePublic(sessionAes.getSaltAES().getBytes(StandardCharsets.UTF_8), publicKey);		
		byte[] aesEncriptadoIterator = t.encryptFilePublic((sessionAes.getIteration()+"").getBytes(StandardCharsets.UTF_8), publicKey);
		
		AESDTO aesEncrypt = new AESDTO(); 
		aesEncrypt.setSecretKeyAES(Base64.getEncoder().withoutPadding().encodeToString(aesEncriptadoKey));
		aesEncrypt.setSaltAES(Base64.getEncoder().withoutPadding().encodeToString(aesEncriptadoSalt));
		aesEncrypt.setIteration(Base64.getEncoder().withoutPadding().encodeToString(aesEncriptadoIterator));
		
		LoginDTOResponse response = new LoginDTOResponse();
		response.setSessionAESDTO(aesEncrypt);
		response.setPrivateKey(usuarioDB.getEncryptKeys().getPrivateKey());
		

		//String userIdEnc = privacityIdServices.getAES(usuarioDB.getIdUser().toString());
		
		
		LoginDataDTO data = new LoginDataDTO();
		data.setToken(jwt);
		data.setId(usuarioDB.getIdUser().toString());
		data.setNickname(usuarioDB.getNickname());
		data.setInvitationCode(usuarioDB.getUserInvitationCode().getInvitationCode());
		data.setPublicKey(usuarioDB.getEncryptKeys().getPublicKey());
		data.setMyAccountGralConfDTO(comps.common().mapper().doit(comps.repo().user().findById(usuarioDB.getIdUser()).get().getMyAccountConf()));
		data.setSessionAESDTOWS(comps.service().usuarioSessionInfo().getSessionAESToUseWS(loginRequest.getUsername()));
		data.setSessionAESDTOServerEncrypt(comps.service().usuarioSessionInfo().getSessionAESToUseServerEncrypt(loginRequest.getUsername()));
		privacityLogguer.write(data);

		comps.service().usuarioSessionInfo().encryptIds(loginRequest.getUsername(),data);
			privacityLogguer.write(data);
	
//		
		
		
		response.setLoginDataDTO(data);
		
		return response;
	}

	public Boolean validateUsername(String username) {
		if (comps.repo().user().existsByUsername(username)) {
			return true;
		}
		return false;
	}
	
	public void registerUser(Usuario usuario) throws ValidationException {
		

		// Create new user's account
		Usuario user = new Usuario(usuario.getUsername(),
							 comps.util().passwordEncoder().encode(usuario.getUsuarioPassword().getPassword()));

		user.setNickname(usuario.getNickname());
		Set<Role> roles = new HashSet<>();

		Role userRole = comps.repo().role().findByName(ERole.ROLE_USER).get();
		roles.add(userRole);
		user.setRoles(roles);
		usuario.getUserInvitationCode().setUsuario(user);
		user.setEncryptKeys(usuario.getEncryptKeys());
		user.setUserInvitationCode(usuario.getUserInvitationCode());
		
		user.setMyAccountConf(new MyAccountConf(user));
		comps.util().myAccountConf().getDefaultConf(user);

		
		comps.repo().user().save(user);

	
	}
}
