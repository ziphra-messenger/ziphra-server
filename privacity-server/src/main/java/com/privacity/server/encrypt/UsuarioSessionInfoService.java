package com.privacity.server.encrypt;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.privacity.common.dto.AESDTO;
import com.privacity.common.util.RandomGenerator;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.usuario.UserUtilService;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.main.AESToUse;
import com.privacity.server.model.EncryptKeys;
import com.privacity.server.security.Usuario;
import com.privacity.server.security.UsuarioSessionInfo;

/**
 * Created by baiguantao on 2017/8/4.
 * User session record class
 */
@Service
public class UsuarioSessionInfoService{
    //this map save every session
    //This collection stores session
    private final ConcurrentMap<String, UsuarioSessionInfo> userSessionIds = new ConcurrentHashMap<String, UsuarioSessionInfo>();
    
	@Value("${serverconf.sessionAes.bits}")
	private int bitsEncrypt;

	
    @Autowired @Lazy
    private FacadeComponent comps;    

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
    
    public UsuarioSessionInfo get(String username, boolean create) throws ValidationException {
    	try {
    		if (userSessionIds.containsKey(username)) {
    			return get(userSessionIds.get(username).getUsuarioDB(),create);
    		}else {
    			return get(comps.util().usuario().getUsuarioForUsername(username),create);	
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
    	
    }

    public UsuarioSessionInfo get(String username) throws ValidationException {
    	try {
    		if (userSessionIds.containsKey(username)) {
    			return get(userSessionIds.get(username).getUsuarioDB(),false);
    		}else {
    			return get(comps.util().usuario().getUsuarioForUsername(username),false);	
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null; 
		}
    	
    }
    public UsuarioSessionInfo get(Usuario user) throws Exception {
    	return get(user,false);
    }

    public boolean isOnline(Usuario user) throws Exception {
    	return this.isOnline(user.getUsername());
    }

    public boolean isOnline(String username) throws Exception {
    	return this.userSessionIds.containsKey(username);
    }
    
    public UsuarioSessionInfo get(Usuario user, boolean create) throws Exception {
    	
    	if ((this.userSessionIds.get(user.getUsername()) == null) 
    			|| create) {
    		String AES = comps.common().randomGenerator().sessionAesSecretKey();
    		String SaltAES = comps.common().randomGenerator().sessionAesSecretSalt();
    		int AESIteration = comps.common().randomGenerator().sessionAesIteration();

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
    		
    		AESDTO aes = new AESDTO();
    		{
    		
    		aes.setSecretKeyAES(AES);
    		}
    		UsuarioSessionInfo t = new UsuarioSessionInfo();
    		t.setSessionAES(new AESDTO(AES, SaltAES,AESIteration+""));
    		t.setPublicKey(publicKey);

    		
    		t.setUsuarioDB(user);
    		

    		t.getUsuarioDB().setMyAccountConf(user.getMyAccountConf());
    		
    		this.userSessionIds.put(user.getUsername(),t);
    		
    		try {
				t.setSessionAESToUse(new AESToUse(bitsEncrypt, AESIteration ,AES, SaltAES));
			} catch (Exception e) {
				e.printStackTrace();
				throw new ValidationException("error de encriptacion");
			}
    	}
        UsuarioSessionInfo set = (UsuarioSessionInfo)this.userSessionIds.get(user.getUsername());
        
        return set;
        

    }
    


}