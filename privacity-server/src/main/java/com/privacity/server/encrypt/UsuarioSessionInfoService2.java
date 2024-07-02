package com.privacity.server.encrypt;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.privacity.server.security.UsuarioSessionInfo2;
import com.privacity.server.session.UsuarioSessionInfo;

/**
 * Created by baiguantao on 2017/8/4.
 * User session record class
 */
@Service
public class UsuarioSessionInfoService2{
	public UsuarioSessionInfo get(String username, boolean create) throws Exception {
		return null;
	}
      public UsuarioSessionInfo2 get2(String username, boolean create) throws Exception {
//    	
//    	    final String uri = "http://localhost:8081/entry/gett";
//
//    	    RestTemplate restTemplate = new RestTemplate();
//    	    UsuarioSessionInfo result = restTemplate.getForObject(uri, UsuarioSessionInfo.class);
//
//    	    System.out.println(result);

    	  RestTemplate rest = new RestTemplate();

    	  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    	  map.add("username", "bVcK/lqjhMW/GeLyG/MGGg==");


    	  UsuarioSessionInfo2 result = rest.postForObject("http://localhost:8081/entry/gett", map, UsuarioSessionInfo2.class);
    	  
        return result;
        

    }
      
//  	public static void main(String[] args) throws Exception {
//
//		MapCompActMethService m = new MapCompActMethService();
//		m.build();
//
//	}
    
    public UsuarioSessionInfo2 get3(String username, boolean create) throws Exception {
//    	
//    	    final String uri = "http://localhost:8081/entry/gett";
//
//    	    RestTemplate restTemplate = new RestTemplate();
//    	    UsuarioSessionInfo result = restTemplate.getForObject(uri, UsuarioSessionInfo.class);
//
//    	    System.out.println(result);

    	  RestTemplate rest = new RestTemplate();

    	  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    	  map.add("username", "bVcK/lqjhMW/GeLyG/MGGg==");


    	  UsuarioSessionInfo2 result = rest.postForObject("http://localhost:8081/entry/gett", map, UsuarioSessionInfo2.class);
    	  
        return result;
        

    }
      
public static void main(String[] args) throws Exception {

	System.out.println(
	new UsuarioSessionInfoService2().get2("ddwq", true).toString());
}

public void remove(String username) {
	// TODO Auto-generated method stub
	
}

public UsuarioSessionInfo get(String username) {
	// TODO Auto-generated method stub
	return null;
}

public UsuarioSessionInfo get() {
	// TODO Auto-generated method stub
	return null;
}

public boolean isOnline(String username) {
	// TODO Auto-generated method stub
	return false;
}

public Map<String, UsuarioSessionInfo> getAll() {
	// TODO Auto-generated method stub
	return null;
}

}