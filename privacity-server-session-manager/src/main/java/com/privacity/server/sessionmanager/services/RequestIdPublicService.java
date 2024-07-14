package com.privacity.server.sessionmanager.services;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

import com.privacity.common.dto.RequestIdDTO;

@Service
public class RequestIdPublicService {

	
	private ConcurrentMap<String,RequestIdDTO> requestIdsPublic = new ConcurrentHashMap<String,RequestIdDTO>();

	
	public RequestIdDTO get(String h) {
		return requestIdsPublic.get(h);
	}
	public void remove(String h) {
		requestIdsPublic.remove(h);
	}
	public void put(String h,RequestIdDTO r) {
		 requestIdsPublic.put(h, r);
	}
}