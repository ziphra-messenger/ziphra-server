package com.privacity.server.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.security.Usuario;

@Service
public class UtilService {

	@Autowired
	@Lazy
	private FacadeComponent comps;

	public Usuario getUser() {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
	    UserDetails userDetail = (UserDetails) auth.getPrincipal();
	    
		Usuario u = comps.repo().user().findByUsername(userDetail.getUsername()).get();
		return u;
	}    
 

}
