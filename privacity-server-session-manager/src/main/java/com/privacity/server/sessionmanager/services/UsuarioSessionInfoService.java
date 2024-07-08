package com.privacity.server.sessionmanager.services;
import java.security.PublicKey;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import javax.xml.bind.ValidationException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import com.privacity.server.sessionmanager.model.AESToUse;
import com.privacity.server.sessionmanager.model.UsuarioSessionInfo;
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
    private static final Logger log = Logger.getLogger(UsuarioSessionInfoService.class.getCanonicalName());
    
	public UsuarioSessionInfoService() {
		super();
		log.info("Encrypt Ids Enabled: " + encryptIds );
	}
    public ConcurrentMap<String, UsuarioSessionInfo> getAll(){
		return this.userSessionIds;
    }
    
    public void remove(String username){
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
    		t.setSessionAESWS(ProducersGenerator.dataQueue.poll());
    		//t.setPublicKey(publicKey);
			t.setSessionAESServerIn(ProducersGenerator.dataQueue.poll());
			t.setSessionAESServerOut(ProducersGenerator.dataQueue.poll());
			t.setPrivacityIdServices(new PrivacityIdServices(encryptIds,
					ProducersGenerator.dataQueue.poll().getAESDTO()));
			
    		
    		t.setUsername(username);
    		

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