package ar.ziphra.security.util;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.ziphra.core.model.Usuario;
import ar.ziphra.core.repository.UsuarioRepository;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UsuarioRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.trace("Entrada: " +  username);
		 Optional<Usuario> userO = userRepository.findByUsername(username);
			
		if (!userO.isPresent()) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}

		return UserDetailsImpl.build(userO.get());
	}

}
