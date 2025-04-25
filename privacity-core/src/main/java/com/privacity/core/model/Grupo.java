package com.privacity.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Accessors(chain = true)
public class Grupo implements Serializable{

	private static final long serialVersionUID = -7302453059053458970L;
	
	public static final Long  CONSTANT_ID_STARTS_AT=20000L;

	
	@Id

    private Long idGrupo;
    private String name;
    
    @OneToOne(mappedBy = "grupo", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private GrupoGralConf gralConf;
    
    
    @OneToOne(mappedBy = "grupo", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private GrupoGralConfPassword password;	
    
    @OneToOne(mappedBy = "grupo", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private GrupoGralConfLock lock;      

    private boolean deleted;
    
	public Grupo() {
		super();
		//this.gralConf = new GrupoGralConf(this);;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deleted, idGrupo, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		return deleted == other.deleted && Objects.equals(idGrupo, other.idGrupo) && Objects.equals(name, other.name)
			;
	}
    
    
    /*

    @OneToOne(mappedBy = "grupo", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private AESGrupo aesGrupo;*/
}