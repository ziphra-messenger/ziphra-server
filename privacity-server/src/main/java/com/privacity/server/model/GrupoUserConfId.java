package com.privacity.server.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.privacity.server.security.Usuario;

import lombok.Data;
import lombok.ToString;

@Data
@Embeddable
public class GrupoUserConfId implements Serializable {

    private static final long serialVersionUID = 1L;



    @OneToOne
    @JoinColumn(name = "id_grupo")
    @ToString.Exclude	
//    @PrimaryKeyJoinColumn(name="id_grupo")
		private Grupo grupo;
	

	@ManyToOne
	@JoinColumn(name = "idUser")
	@ToString.Exclude	
	private Usuario user;



    public GrupoUserConfId() {

    }

	public GrupoUserConfId(Usuario user, Grupo grupo) {
		super();
		this.user = user;
		this.grupo = grupo;

	}

	public GrupoUserConfId(Usuario user) {
		super();
		this.user = user;
	}

	
}
