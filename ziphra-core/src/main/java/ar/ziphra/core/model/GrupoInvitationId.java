package ar.ziphra.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;


@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class GrupoInvitationId  implements Serializable {

	private static final long serialVersionUID = -1657143766873819117L;

	@ManyToOne
	@JoinColumn(name = "idUserInvitado")
    private Usuario usuarioInvitado;

	@ManyToOne
	@JoinColumn(name = "idUserInvitante")
    private Usuario usuarioInvitante;

	@ManyToOne
	@JoinColumn(name = "idGrupo")
	@ToString.Exclude	
    private Grupo grupo;

	//private Long idGrupoInvitation;
	
	





    
 }