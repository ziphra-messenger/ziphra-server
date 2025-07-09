package ar.ziphra.core.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import ar.ziphra.common.annotations.ExcludeInterceptorLog;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Embeddable
@Accessors(chain = true)
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
