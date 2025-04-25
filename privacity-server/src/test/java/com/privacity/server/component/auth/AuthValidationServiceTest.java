package com.privacity.server.component.auth;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.privacity.common.dto.EncryptKeysDTO;
import com.privacity.common.dto.request.RegisterUserRequestDTO;
import com.privacity.commonback.common.enumeration.RolesSecurityAccessToServerEnum;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class AuthValidationServiceTest {

	@Autowired
	private AuthValidationService controller;

	@Test
	void registerUser() throws Exception {
		try {
			String data="server6";
		log.info("dentro");
		RegisterUserRequestDTO r = new RegisterUserRequestDTO();
		r.setNickname(data);
		r.setPassword(data);
		r.setUsername(data);
		
		EncryptKeysDTO e = new EncryptKeysDTO();
		e.setPrivateKey(data);
		e.setPublicKey(data);
		e.setPublicKeyNoEncrypt(data);
		
		EncryptKeysDTO e1 = new EncryptKeysDTO();
		e1.setPrivateKey(data);
		e1.setPublicKeyNoEncrypt(data);
		
		r.setEncryptKeysDTO(e);
		r.setInvitationCodeEncryptKeysDTO(e1);
		
		//controller.registerUser(r);

		//r.setInvitationCodeEncryptKeysDTO(e);

			controller.registerUser(r,RolesSecurityAccessToServerEnum.ROLE_SERVER);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void login() throws Exception {
		try {
			
		//	controller.test();
//			String data="server6";
//		log.info("dentro");
//		LoginRequestDTO r = new LoginRequestDTO();
//		r.setPassword(data);
//		r.setUsername(data);
//
//		LoginDTOResponse res = controller.login(r);
//		UtilsStringComponent us = new UtilsStringComponent();
//		
////		Gson g = new GsonBuilder()
////		.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
////		.setPrettyPrinting()
////		.addSerializationExclusionStrategy(new ExclusionStrategy() {
////
////			@Override
////			public boolean shouldSkipField(FieldAttributes f) {
////				f.getClass().
////				return false;
////			}
////
////			@Override
////			public boolean shouldSkipClass(Class<?> clazz) {
////				// TODO Auto-generated method stub
////				clazz.
////				return false;
////			}})
////				
////		.excludeFieldsWithoutExposeAnnotation().create();
//		
//		String js = us.gson().toJson(res);
//		
//		log.info(js);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}
}

 class TestExclStrat implements ExclusionStrategy {

    private Class<?> c;
    private String fieldName;
    public TestExclStrat(String fqfn) throws SecurityException, NoSuchFieldException, ClassNotFoundException
    {
        this.c = Class.forName(fqfn.substring(0, fqfn.lastIndexOf(".")));
        this.fieldName = fqfn.substring(fqfn.lastIndexOf(".")+1);
    }
    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes f) {

        return (f.getDeclaringClass() == c && f.getName().equals(fieldName));
    }

}