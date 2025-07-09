package ar.ziphra.core.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import ar.ziphra.common.enumeration.GrupoRolesEnum;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Data
@Accessors(chain = true)
public class GrupoInvitation implements Serializable{
	
	private static final long serialVersionUID = -2737865014106237360L;

	@EmbeddedId
	private GrupoInvitationId grupoInvitationId;
	
    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="id_aes")
	private AES aes; 
    @Column(length=14000)
    private String invitationMessage;
	private GrupoRolesEnum role;
	
	@Lob
	private String privateKey;
	
 

}
