package com.privacity.server.common.model;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.common.enumeration.PasswordGrupoTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForGrupo {

	@EmbeddedId
	private UserForGrupoId userForGrupoId; 
	
	
	private GrupoRolesEnum role;


    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name="id_aes")
    
    private AES aes;

    //para el grupo
     private String alias;
     //para el usuario
     private String nickname;
     private boolean deleted;
   
    private PasswordGrupoTypeEnum passwordGrupoType;
    private String passwordGrupo; // lo pide al ingresar...
    

}
