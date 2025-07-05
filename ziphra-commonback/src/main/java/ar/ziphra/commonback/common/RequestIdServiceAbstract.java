package ar.ziphra.commonback.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ar.ziphra.common.dto.RequestIdDTO;

abstract public class RequestIdServiceAbstract {

	
	protected ConcurrentMap<String,RequestIdDTO> requestIds = new ConcurrentHashMap<String,RequestIdDTO>();

	
	public RequestIdDTO get(String h) {
		return requestIds.get(h);
	}
	public void remove(String h) {
		requestIds.remove(h);
	}
	public void put(String h,RequestIdDTO r) {
		requestIds.put(h, r);
	}
	
	public ConcurrentMap<String, RequestIdDTO> getAll() {
		return requestIds;
	}
}