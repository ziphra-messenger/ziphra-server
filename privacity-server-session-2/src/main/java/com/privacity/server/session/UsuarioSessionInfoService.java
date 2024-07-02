package com.privacity.server.session;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.ValidationException;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.privacity.common.dto.AESDTO;
import com.privacity.server.common.exceptions.PrivacityException;
import com.privacity.server.common.model.EncryptKeys;
import com.privacity.server.common.model.Usuario;
import com.privacity.server.common.repositories.UsuarioRepository;
import com.privacity.server.common.util.AESToUse;

/**
 * Created by baiguantao on 2017/8/4.
 * User session record class
 */
@Component
@Qualifier("uuu")
public class UsuarioSessionInfoService{
    //this map save every session
    //This collection stores session
    private final ConcurrentMap<String, UsuarioSessionInfo> userSessionIds = new ConcurrentHashMap<String, UsuarioSessionInfo>();
    
	@Value("${serverconf.sessionAes.bits}")
	private int bitsEncrypt;

	@Value("${serverconf.privacityIdAESOn}")
	private boolean encryptIds;
	
	@Qualifier("beanB")
	@Autowired
	UsuarioRepository usuarioRepository;
    /**
     *
     * Get session Id
     * @param user
     * @return 
     * @return
     * @throws ValidationException 
     * @throws PrivacityException 
     */
 
    public ConcurrentMap<String, UsuarioSessionInfo> getAll(){
		return this.userSessionIds;
    }
    
    public void remove(String username){
    		this.userSessionIds.remove(username);
    }
    
    public UsuarioSessionInfo get(String username) throws ValidationException {
    	return get(username,false);
    }
    public UsuarioSessionInfo get(String username, boolean create) throws ValidationException {
    	try {
    		if (userSessionIds.containsKey(username)) {
    			return get(usuarioRepository.findByUsername(username).get(),create);
    		}else {
    			return get(usuarioRepository.findByUsername(username).get(),create);	
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
    	
    }


//    public UsuarioSessionInfo get(Usuario user) throws Exception {
//    	return get(user,false);
//    }

    public boolean isOnline(Usuario user) throws Exception {
    	return this.isOnline(user.getUsername());
    }

    public boolean isOnline(String username) throws Exception {
    	return this.userSessionIds.containsKey(username);
    }
    
    public Object encryptIds(String username, Object object, Class clazz) throws Exception {
    	this.get(username).getPrivacityIdServices().encryptIds(object);
    	return object;
    }
    
    public Object decryptIds(String username, Object object, Class clazz) throws Exception {
    	this.get(username).getPrivacityIdServices().decryptIds(object);
    	return object;
    }

    private UsuarioSessionInfo get(Usuario user, boolean create) throws Exception {
    	
    	if ((this.userSessionIds.get(user.getUsername()) == null) 
    			|| create) {
    		
    		AESToUse aesToUse = ProducerConsumerDemonstrator.dataQueue.poll();
    	        	
    		String AES =aesToUse.getSecretKeyAES();
    		String SaltAES = aesToUse.getSaltAES();
    		int AESIteration = aesToUse.getInterationCount();

    		PublicKey publicKey=null;
    		EncryptKeys ek = user.getEncryptKeys();
    		
   		
    		{
    			Security.addProvider(new BouncyCastleProvider());
                KeyFactory kf = KeyFactory.getInstance("RSA","BC");

                X509EncodedKeySpec spec2 = new X509EncodedKeySpec(
                        new Gson().fromJson(
                        		ek.getPublicKeyNoEncrypt()

                                , byte[].class));
                  publicKey = kf.generatePublic(spec2);
                ////System.out.println(publicKey.toString());
                

    		}
    		
    		//AESDTO aes = new AESDTO();
    		{
    		
    		//aes.setSecretKeyAES(AES);
    		}
    		UsuarioSessionInfo t = new UsuarioSessionInfo();
    		t.setSessionAES(new AESDTO(AES, SaltAES,AESIteration,128));
    		t.setPublicKey(publicKey);
			t.setSessionAESToUseWS(ProducerConsumerDemonstrator.dataQueue.poll());
			t.setSessionAESToUseServerEncrypt(ProducerConsumerDemonstrator.dataQueue.poll());
			t.setPrivacityIdServices(new PrivacityIdServices(	
			 encryptIds,
					ProducerConsumerDemonstrator.dataQueue.poll().getAESDTO()));
			
    		
    		t.setUsuarioId(user.getIdUser());
    		t.setUsername(user.getUsername());
    		

    		//t.getUsuarioDB().setMyAccountConf(user.getMyAccountConf());
    		
    		this.userSessionIds.put(user.getUsername(),t);
    		
    		try {
				t.setSessionAESToUse(aesToUse);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ValidationException("error de encriptacion");
			}
    	}
        UsuarioSessionInfo set = (UsuarioSessionInfo)this.userSessionIds.get(user.getUsername());
        
        return set;
        

    }
    


}