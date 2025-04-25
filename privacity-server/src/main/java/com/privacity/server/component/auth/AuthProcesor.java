package com.privacity.server.component.auth;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.adapters.LocalDateAdapter;
import com.privacity.common.dto.AESAllDTO;
import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.LoginDataDTO;
import com.privacity.common.dto.response.LoginDTOResponse;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.enumeration.RolesSecurityAccessToServerEnum;
import com.privacity.core.model.EncryptKeys;
import com.privacity.core.model.MyAccountConf;
import com.privacity.core.model.Role;
import com.privacity.core.model.Usuario;
import com.privacity.security.util.UserDetailsImpl;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.encryptkeys.RSAComponent;
import com.privacity.server.security.JwtUtils;

import lombok.extern.slf4j.Slf4j;


@CrossOrigin(origins = "*", maxAge = 3600)
@Service
//@RequestMapping("/api/auth")
@Slf4j
public class AuthProcesor {

	@Value("${serverconf.privacityIdAESOn}")
	private boolean encryptIds;
	
	@Autowired @Lazy
	private AuthenticationManager authenticationManager;


	

	@Autowired @Lazy
	private JwtUtils jwtUtils;
	
	@Autowired @Lazy
	private FacadeComponent comps;
	
	public LoginDTOResponse login( Usuario loginRequest) throws Exception {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getUsuarioPassword().getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(loginRequest.getUsername());
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		userDetails.setJwt(jwt);
//		List<String> roles = userDetails.getAuthorities().stream()
//				.map(item -> item.getAuthority())
//				.collect(Collectors.toList());

		//List<UsuarioSessionIdInfo> info = SocketSessionRegistry.getSessionIds(userDetails.getUsername());
		
	
		Usuario usuarioDB = comps.util().usuario().getUsuarioForUsername(loginRequest.getUsername());
		
		 //
		// Encriptar publica
		
		RSAComponent t = comps.common().RSA();
		
//		{
		EncryptKeys ek = usuarioDB.getEncryptKeys();
		Security.addProvider(new BouncyCastleProvider());
        KeyFactory kf = KeyFactory.getInstance("RSA","BC");

        X509EncodedKeySpec spec2 = new X509EncodedKeySpec(
                new Gson().fromJson(
                		ek.getPublicKeyNoEncrypt()

                        , byte[].class));
          PublicKey publicKey = kf.generatePublic(spec2);
//        ////System.out.println(publicKey.toString());
//        
//
//	}
		////System.out.println("Session AES Key = " + info.getSessionAES().getSecretKeyAES());
		////System.out.println("Session AES Salt = " + info.getSessionAES().getSaltAES());
		////System.out.println("Session AES iterator = " + info.getSessionAES().getIteration());
		
		AESAllDTO aesAll = comps.service().usuarioSessionInfo().getAesDtoAll(loginRequest.getUsername());
		
		byte[] aesEncriptadoKey = t.encryptFilePublic(aesAll.getSessionAESDTOServerIn().getSecretKeyAES().getBytes(StandardCharsets.UTF_8), publicKey);
		byte[] aesEncriptadoSalt = t.encryptFilePublic(aesAll.getSessionAESDTOServerIn().getSaltAES().getBytes(StandardCharsets.UTF_8), publicKey);
		byte[] aesEncriptadoIterator = t.encryptFilePublic((aesAll.getSessionAESDTOServerIn().getIteration()+"").getBytes(StandardCharsets.UTF_8), publicKey);
		
		AESDTO aesEncrypt = new AESDTO(); 
		aesEncrypt.setSecretKeyAES(Base64.getEncoder().withoutPadding().encodeToString(aesEncriptadoKey));
		aesEncrypt.setSaltAES(Base64.getEncoder().withoutPadding().encodeToString(aesEncriptadoSalt));
		aesEncrypt.setIteration(Base64.getEncoder().withoutPadding().encodeToString(aesEncriptadoIterator));
		
		LoginDTOResponse response = new LoginDTOResponse();
		response.setSessionAESDTO(aesEncrypt);
		response.setPrivateKey(usuarioDB.getEncryptKeys().getPrivateKey());
		

		//String userIdEnc = privacityIdServices.getAES(usuarioDB.getIdUser().toString());
		
		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
				.create();
		
		LoginDataDTO data = new LoginDataDTO();
		
		data.setId(usuarioDB.getIdUser().toString());
		data= gson.fromJson( comps.service().usuarioSessionInfo().privacityIdServiceEncrypt(usuarioDB.getUsername()
				, data
				,data.getClass().getName()),LoginDataDTO.class);
		
		data.setToken(jwt);
		data.setNickname(usuarioDB.getNickname());
		data.setInvitationCode(usuarioDB.getUserInvitationCode().getInvitationCode());

		data.setMyAccountGralConfDTO(comps.common().mapper().doit(comps.repo().user().findById(usuarioDB.getIdUser()).get().getMyAccountConf()));
		data.setSessionAESDTOWS(aesAll.getSessionAESDTOWS());
		data.setSessionAESDTOServerEncrypt(aesAll.getSessionAESDTOServerOut());


		data.setPublicKey(usuarioDB.getEncryptKeys().getPublicKey());

	
//		
		
		
		response.setLoginDataDTO(data);
		
		log.trace("LoginDTOResponse salida-> " + comps.util().string().gsonToSend(response));
		return response;
	}

	public Boolean validateUsername(String username) {
		if (comps.repo().user().existsByUsername(username)) {
			return true;
		}
		return false;
	}
	
	public void registerUser(Usuario usuario, RolesSecurityAccessToServerEnum erole) throws PrivacityException {
		

		// Create new user's account
		Usuario user = new Usuario(usuario.getUsername(),
							 comps.util().passwordEncoder().encode(usuario.getUsuarioPassword().getPassword()));

		user.setNickname(usuario.getNickname());
		Set<Role> roles = new HashSet<>();

		Role userRole = comps.repo().role().findByName(erole).get();
		roles.add(userRole);
		user.setRoles(roles);
		usuario.getUserInvitationCode().setUsuario(user);
		user.setEncryptKeys(usuario.getEncryptKeys());
		user.getEncryptKeys().setIdEncryptKeys(comps.factory().idsGenerator().getNextEncryptKeysId());
		
		comps.repo().encryptKeys().save(user.getEncryptKeys());
		
		user.setUserInvitationCode(usuario.getUserInvitationCode());
		user.getUserInvitationCode().getEncryptKeys().setIdEncryptKeys(comps.factory().idsGenerator().getNextEncryptKeysId());
		user.setMyAccountConf(new MyAccountConf(user));
		comps.util().myAccountConf().getDefaultConf(user);
		
		user.setIdUser(comps.factory().idsGenerator().getNextUsuarioId());
		comps.repo().user().save(user);

	
	}
}
