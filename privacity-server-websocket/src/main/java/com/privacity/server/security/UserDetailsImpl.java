package com.privacity.server.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long idUser;

	private String username;

	@JsonIgnore
	private String password;

	@JsonIgnore
	private String jwt;

	
	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long idUser, String username, String password,String jwt,
			Collection<? extends GrantedAuthority> authorities 
			) {
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
		this.jwt=jwt;
		

	}
	public UserDetailsImpl(Long idUser, String username, String password,
			Collection<? extends GrantedAuthority> authorities 
			) {
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	
		

	}
	public static UserDetailsImpl build(Usuario user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		try {
//		EncryptKeys ek =  new EncryptKeys();
//		ek.setPrivateKey(CryptSessionRegistry.getInstance().getSessionIds(user.getUsername()).getPrivateKeyToSend());
//		ek.setPublicKey(CryptSessionRegistry.getInstance().getSessionIds(user.getUsername()).getPublicKeyToSend());
//		ek.setPublicKeyNoEncrypt(CryptSessionRegistry.getInstance().getSessionIds(user.getUsername()).getEncryptKeys().getPublicKeyNoEncrypt());
//	
			return new UserDetailsImpl(
					user.getIdUser(), 
					user.getUsername(), 
					user.getUsuarioPassword().getPassword(), 

					authorities
//					user.getNickname(),
//					CryptSessionRegistry.getInstance().getSessionIds(user).getSessionAESDTOToSend(),
//					ek
					
					);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return idUser;
	}


	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(idUser, user.idUser);
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}


}
