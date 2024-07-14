package com.privacity.server.loadbalance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class ForwardManagerService {

	List<String> servers = new ArrayList<String>();

	int i=1;
	
	public ForwardManagerService() {
		super();
		
		servers.add("http://localhost:8080");
		servers.add("http://localhost:8080");
	}
	
	public String get() {
		if ( i == 0 ) {
			i=1;
		}else {
			i=0;
		}
		
		return servers.get(i);
	}
	
}
