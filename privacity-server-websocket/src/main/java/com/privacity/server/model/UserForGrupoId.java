package com.privacity.server.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.privacity.common.annotations.ExcludeInterceptorLog;
import com.privacity.server.security.Usuario;

import lombok.Data;
import lombok.ToString;

@Data
@Embeddable
public class UserForGrupoId implements Serializable {

    private static final long serialVersionUID = 1L;



    @OneToOne( fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_grupo")
    
//    @PrimaryKeyJoinColumn(name="id_grupo")
    @ToString.Exclude	
		private Grupo grupo;
	

	@ManyToOne( fetch=FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "idUser")
	@ExcludeInterceptorLog
	@ToString.Exclude	
	private Usuario user;



    public UserForGrupoId() {

    }

	public UserForGrupoId(Usuario user, Grupo grupo) {
		super();
		this.user = user;
		this.grupo = grupo;

	}

	public UserForGrupoId(Usuario user) {
		super();
		this.user = user;
	}

	
}
