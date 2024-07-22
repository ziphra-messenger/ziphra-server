package com.privacity.commonback.components;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.common.util.Validations;
import com.privacity.commonback.common.enumeration.HealthCheckerServerType;
import com.privacity.commonback.common.enumeration.HealthCheckerState;
import com.privacity.commonback.common.interfaces.HealthCheckerInterface;
import com.privacity.commonback.constants.HealthCheckerConstants;
import com.privacity.commonback.pojo.HealthCheckerPojo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class HealthCheckerAbstract implements Runnable, HealthCheckerInterface{

	Map<HealthCheckerServerType, HealthCheckerPojo> servers = new HashMap<HealthCheckerServerType, HealthCheckerPojo>();

	private boolean running=false;

	@Override
	public void alertOffLine(HealthCheckerServerType e) {
		if (!thereIsA(e))return;
		log.debug("alertOffLine: " + e);
		if (!checkStatus(e)) {
			if (!running) {
				log.trace("Start healthChecker thread");
				new Thread(this).start();
			}
		}
	}

	@Override
	public boolean isOnline(HealthCheckerServerType e) {
		if (!thereIsA(e))return true;
		log.trace("isOnline: "+ e + " " + servers.get(e).getState().isOnline());
		return servers.get(e).getState().isOnline();
	}

	@Override
	public boolean thereIsA(HealthCheckerServerType e) {
		return servers.containsKey(e);
	}
	
	@Override
	public HealthCheckerPojo getServer(HealthCheckerServerType e) {
		return servers.get(e);
	}
	@PostConstruct
	public void pc() {

		HealthCheckerServerType.stream()
        .forEach(type ->
        addServer(type,getUrlServerForType(type))
        		);
        
		new Thread(this).start();
	}

	protected abstract String getUrlServerForType(HealthCheckerServerType t);
	
	public String getServerValidate(HealthCheckerServerType t) throws PrivacityException {
		
		if (HealthCheckerServerType.MESSAGING.equals(t)) {
			HealthCheckerPojo s = getServer(HealthCheckerServerType.MESSAGING);
			if (!s.getState().isOnline()) {
				throw new PrivacityException(ExceptionReturnCode.MESSAGING_OFFLINE);
			}
			return s.getUrl();
		}
		if (HealthCheckerServerType.IDS_PROVIDER.equals(t)) {
			HealthCheckerPojo s = getServer(HealthCheckerServerType.IDS_PROVIDER);
			if (!s.getState().isOnline()) {
				throw new PrivacityException(ExceptionReturnCode.IDS_PROVIDER_OFFLINE);
			}
			return s.getUrl();
		}
		if (HealthCheckerServerType.SESSION_MANAGER.equals(t)) {
			HealthCheckerPojo s = getServer(HealthCheckerServerType.SESSION_MANAGER);
			if (!s.getState().isOnline()) {
				throw new PrivacityException(ExceptionReturnCode.SESSION_MANAGER_OFFLINE);
			}
			return s.getUrl();
		}
		if (HealthCheckerServerType.REQUEST_ID.equals(t)) {
			HealthCheckerPojo s = getServer(HealthCheckerServerType.REQUEST_ID);
			if (!s.getState().isOnline()) {
				throw new PrivacityException(ExceptionReturnCode.REQUEST_ID_OFFLINE);
			}
			return s.getUrl();
		}

		throw new PrivacityException(ExceptionReturnCode.GENERAL_INVALID_ACCESS);
	}
	
	private void addServer(HealthCheckerServerType type, String url) {
		if (url== null || url.trim().equals("")) {
			log.info("No se ha configurado al server tipo: " + type.name() + " - URL: " +  url);
			return;
		}
		
		if (Validations.isValidURL(url)) {
			log.info("Agregando al server tipo: " + type.name() + " - URL: " +  url);
			servers.put(type, new HealthCheckerPojo(url, HealthCheckerState.OFFLINE));	
		}else {
			log.error("URL invalida al server tipo: " + type.name() + " - URL: " +  url);
		}
	}
	//    public void pcTest() {
	//    	servers.put(HealthCheckerServerType.WEBSOCKET, new HealthCheckerPojo("http://localhost:8090", HealthCheckerState.OFFLINE));
	//    	servers.put(HealthCheckerServerType.MESSAGE_ID, new HealthCheckerPojo("http://localhost:3333", HealthCheckerState.OFFLINE));
	//    	servers.put(HealthCheckerServerType.SESSION_MANAGER, new HealthCheckerPojo("http://localhost:8085", HealthCheckerState.OFFLINE));
	//    	servers.put(HealthCheckerServerType.REQUEST_ID, new HealthCheckerPojo("http://localhost:8085", HealthCheckerState.OFFLINE));
	//    }

	public boolean areServerOffLine() {
		boolean r= false;
		for (Map.Entry<HealthCheckerServerType,HealthCheckerPojo> entry : servers.entrySet()) {
			if ( HealthCheckerState.OFFLINE.equals(entry.getValue().getState())){
				r=true;
				break;
			}
		}
		return r;
	}

	@Override
	public void run() {
		log.debug("Start Thread");
		running=true;

		while (areServerOffLine()) {
			for (Map.Entry<HealthCheckerServerType,HealthCheckerPojo> entry : servers.entrySet()) {

				if ( HealthCheckerState.OFFLINE.equals(entry.getValue().getState())){

					HealthCheckerServerType server = entry.getKey();
					checkStatus(server);

				}
			}
		}
		running=false;
	}

	private boolean checkStatus(HealthCheckerServerType server) {
		log.trace("cheking: " + server.name() + " - " +server );
		boolean state = call(servers.get(server).getUrl());

		if (state) {
			servers.get(server).setState(HealthCheckerState.ONLINE);;
		}else {
			servers.get(server).setState(HealthCheckerState.OFFLINE);;
		}
		log.trace("health " +server.name() + ": " + servers.get(server).getUrl() + " " + servers.get(server).getState());

		return state;
	}

	private boolean call(String url)  {
		boolean r=false;
		try {
			RestTemplate rest = new RestTemplate();


			MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
			map.add(HealthCheckerConstants.HEALTH_CHECKER_REQUEST_PARAMETER,HealthCheckerConstants.HEALTH_CHECKER_REQUEST);


			String response = rest.postForObject(url + HealthCheckerConstants.HEALTH_CHECKER_REST
					+ HealthCheckerConstants.HEALTH_CHECKER_REST_PING
					, map,String.class);

			if (HealthCheckerConstants.HEALTH_CHECKER_RESPONSE.equals(response)) {
				r=true;
			};
		} catch (org.springframework.web.client.ResourceAccessException e) {
			log.error(e.getMessage());
		} catch (org.springframework.web.client.HttpClientErrorException e) {
			log.error(e.getMessage());
		}
		return r;

	}


	//public static void main(String[] args) throws Exception {
	//	
	//	HealthChecker c = new HealthChecker();
	//	c.pcTest();
	//	new Thread(c).start();
	//	
	//	Thread.sleep(5000);
	//	
	//	c.alertOffLine(HealthCheckerServerType.REQUEST_ID);
	//	c.isOnline(HealthCheckerServerType.REQUEST_ID);
	//	
	//	Thread.sleep(5000);
	//	c.isOnline(HealthCheckerServerType.REQUEST_ID);
	//	c.alertOffLine(HealthCheckerServerType.REQUEST_ID);
	//}    
}
