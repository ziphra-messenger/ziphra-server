package com.privacity.server.sessionmanager.services;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.server.common.adapters.LocalDateAdapter;
import com.privacity.server.sessionmanager.model.AESToUse;
import com.privacity.server.sessionmanager.model.Session;
import com.privacity.server.sessionmanager.model.UsuarioSessionInfo;
import com.privacity.server.sessionmanager.repositories.SessionRepository;
import com.privacity.server.sessionmanager.util.pool.ProducersGenerator;

/**
 * Created by baiguantao on 2017/8/4.
 * User session record class
 */
@Service
public class UsuarioSessionInfoService{
    //this map save every session
    //This collection stores session
    private final ConcurrentMap<String, UsuarioSessionInfo> userSessionIds = new ConcurrentHashMap<String, UsuarioSessionInfo>();
    @Value("${com.privacity.server.sessionmanager.services.UsuarioSessionInfoService.encryptIds}")
    private boolean encryptIds;
    
    @Value("${com.privacity.server.sessionmanager.services.UsuarioSessionInfoService.saveSession.database}")
    private boolean databaseSave;
    
    
    private static final Logger log = Logger.getLogger(UsuarioSessionInfoService.class.getCanonicalName());
    
    @Autowired
    SessionRepository sessionRepository;
	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
			.create();
	
	public UsuarioSessionInfoService() {
		super();
		log.info("Encrypt Ids Enabled: " + encryptIds );
	}
    public ConcurrentMap<String, UsuarioSessionInfo> getAll(){
		return this.userSessionIds;
    }
    
    public void remove(String username){
    		if (databaseSave) sessionRepository.deleteById(username);
    		this.userSessionIds.remove(username);
    }
    

    public UsuarioSessionInfo get(String username) throws Exception {

    		if (userSessionIds.containsKey(username)) {
    			return get(username,false);
    		}else {
    			return get(username,true);	
    		}
    }
    
    public UsuarioSessionInfo get(String username, boolean create) throws Exception {
    	
    	if ((this.userSessionIds.get(username) == null) 
    			|| create) {
    		
    		AESToUse aesToUse = ProducersGenerator.dataQueue.poll();
    	        	
    		String AES =aesToUse.getSecretKeyAES();
    		String SaltAES = aesToUse.getSaltAES();
    		int AESIteration = aesToUse.getInterationCount();

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
    		
    		if ( databaseSave) sessionDBO = sessionRepository.findById(username);
    		
    		if (databaseSave && sessionDBO.isPresent()) {
    			
    			System.out.println("lo recupera de la base");
    			Session sDB= sessionDBO.get();
    			
        		
        		t.setSessionAESWS(new AESToUse(sDB.getAESDTOWS() ));
           		t.setSessionAESServerIn(new AESToUse(sDB.getAESDTOServerIn() ));
           		t.setSessionAESServerOut(new AESToUse(sDB.getAESDTOServerOut() ));
          		
           		
           		t.setPrivacityIdServices(new PrivacityIdServices
           				(encryptIds, sDB.getAESDTOPrivacityId(), 
           						Long.parseLong( sDB.privacityIdOrderSeed), 
           			Long.parseLong(	sDB.orderRamdomNumber),
           			Integer.parseInt(	sDB.base),
           				gson.fromJson(sDB.mutateDigitPorLetra, (new HashMap<String, String>()).getClass()),
           				gson.fromJson(sDB.mutateDigitPorNro, (new HashMap<String, String>()).getClass())));
           				
         		
    		}else {
    			System.out.println("lo crea desde cero");
    		t.setSessionAESWS(ProducersGenerator.dataQueue.poll());
    		//t.setPublicKey(publicKey);
			t.setSessionAESServerIn(ProducersGenerator.dataQueue.poll());
			t.setSessionAESServerOut(ProducersGenerator.dataQueue.poll());
			t.setPrivacityIdServices(new PrivacityIdServices(encryptIds,
					ProducersGenerator.dataQueue.poll().getAESDTO()));
			
			Session s = new Session(username, t.getSessionAESServerIn().getAESDTO(), 
					t.getSessionAESServerOut().getAESDTO(),
					t.getSessionAESWS().getAESDTO(),
					t.getPrivacityIdServices().getAESDTO(),
					t.getPrivacityIdServices().getPrivacityIdOrderSeed()+"",
					t.getPrivacityIdServices().getOrderRamdomNumber()+"",
					t.getPrivacityIdServices().getMutateDigitUtil().getBase()+"",
					gson.toJson( t.getPrivacityIdServices().getMutateDigitUtil().getPorLetra()),
					gson.toJson( t.getPrivacityIdServices().getMutateDigitUtil().getPorNro())
					);
			sessionRepository.save(s);
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