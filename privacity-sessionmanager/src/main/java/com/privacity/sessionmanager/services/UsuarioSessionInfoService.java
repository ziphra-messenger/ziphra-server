package com.privacity.sessionmanager.services;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.utils.PrivacityIdEncoder;
import com.privacity.sessionmanager.model.AESToUse;
import com.privacity.sessionmanager.model.Session;
import com.privacity.sessionmanager.model.UsuarioSessionInfo;
import com.privacity.sessionmanager.util.pool.ProducersGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsuarioSessionInfoService{
	//this map save every session
	//This collection stores session
	private final ConcurrentMap<String, UsuarioSessionInfo> userSessionIds = new ConcurrentHashMap<String, UsuarioSessionInfo>();
	@Value("${com.privacity.server.sessionmanager.services.UsuarioSessionInfoService.encryptIds}")
	private boolean encryptIds;

	@Value("${com.privacity.server.sessionmanager.services.UsuarioSessionInfoService.saveSession.database}")
	private boolean databaseSave;


	@Autowired
	@Lazy
	private UtilFacade uf;

	public UsuarioSessionInfoService() {
		super();
		log.info("Encrypt Ids Enabled: " + encryptIds );
		log.info("Database Save Enabled: " + databaseSave );
	}
	public ConcurrentMap<String, UsuarioSessionInfo> getAll(){
		return this.userSessionIds;
	}

	public void remove(String username){
		if (databaseSave) uf.sessionRepository().deleteById(username);
		this.userSessionIds.remove(username);
	}


	public UsuarioSessionInfo get(String username) throws PrivacityException {

		if (userSessionIds.containsKey(username)) {
			return get(username,false);
		}else {
			return get(username,true);	
		}
	}

	public UsuarioSessionInfo get(String username, boolean create) throws PrivacityException {

		if ((this.userSessionIds.get(username) == null) 
				|| create) {

			AESToUse aesToUse = ProducersGenerator.dataQueue.poll();

			PublicKey publicKey=null;
			//EncryptKeys ek = user.getEncryptKeys();


			//    		{
			//    			Security.addProvider(new BouncyCastleProvider());
			//                KeyFactory kf = KeyFactory.getInstance("RSA","BC");
			//
			//                X509EncodedKeySpec spec2 = new X509EncodedKeySpec(
			//                        new Gson().fromJson(
			//                        		ek.getPublicKeyNoEncrypt()
			//
			//                                , byte[].class));
			//                  publicKey = kf.generatePublic(spec2);
			//                ////System.out.println(publicKey.toString());
			//                
			//
			//    		}

			//AESDTO aes = new AESDTO();
			{

				//aes.setSecretKeyAES(AES);
			}
			UsuarioSessionInfo t = new UsuarioSessionInfo();


			Optional<Session> sessionDBO=null;

			if ( databaseSave) sessionDBO = uf.sessionRepository().findById(username);

			if (databaseSave && sessionDBO.isPresent()) {

				log.debug("lo recupera de la base: " + username);
				Session sDB= sessionDBO.get();


				t.setSessionAESWS(new AESToUse(sDB.getAESDTOWS() ));
				t.setSessionAESServerIn(new AESToUse(sDB.getAESDTOServerIn() ));
				t.setSessionAESServerOut(new AESToUse(sDB.getAESDTOServerOut() ));


				t.setPrivacityIdEncoder(new PrivacityIdEncoder
						(encryptIds, sDB.getAESDTOPrivacityId(), 
								Long.parseLong( sDB.privacityIdOrderSeed), 
								Long.parseLong(	sDB.orderRamdomNumber),
								Integer.parseInt(	sDB.base),
								uf.gson().fromJson(sDB.mutateDigitPorLetra, (new HashMap<String, String>()).getClass()),
								uf.gson().fromJson(sDB.mutateDigitPorNro, (new HashMap<String, String>()).getClass())));


			}else {
				log.debug("lo crea desde cero: " + username);

				t.setSessionAESWS(ProducersGenerator.dataQueue.poll());
				//t.setPublicKey(publicKey);
				t.setSessionAESServerIn(ProducersGenerator.dataQueue.poll());
				t.setSessionAESServerOut(ProducersGenerator.dataQueue.poll());
				t.setPrivacityIdEncoder(new PrivacityIdEncoder(encryptIds,
						ProducersGenerator.dataQueue.poll().getAESDTO()));

				Session s = new Session(username, t.getSessionAESServerIn().getAESDTO(), 
						t.getSessionAESServerOut().getAESDTO(),
						t.getSessionAESWS().getAESDTO(),
						t.getPrivacityIdEncoder().getAESDTO(),
						t.getPrivacityIdEncoder().getPrivacityIdOrderSeed()+"",
						t.getPrivacityIdEncoder().getOrderRamdomNumber()+"",
						t.getPrivacityIdEncoder().getMutateDigitUtil().getBase()+"",
						uf.gson().toJson( t.getPrivacityIdEncoder().getMutateDigitUtil().getPorLetra()),
						uf.gson().toJson( t.getPrivacityIdEncoder().getMutateDigitUtil().getPorNro())
						);
				uf.sessionRepository().save(s);
				t.setUsername(username);

			}
			//t.getUsuarioDB().setMyAccountConf(user.getMyAccountConf());

			this.userSessionIds.put(username,t);

			//    		try {
			//				t.setSessionAESToUse(aesToUse);
			//			} catch (Exception e) {
			//				e.printStackTrace();
			//				throw new ValidationException("error de encriptacion");
			//			}
		}
		UsuarioSessionInfo set = (UsuarioSessionInfo)this.userSessionIds.get(username);

		return set;


	}





}