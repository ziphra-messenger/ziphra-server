package com.privacity.server.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@SequenceGenerator(name = "grupo_secuencia", initialValue = 27011, allocationSize = 1)
public class Grupo implements Serializable{

	private static final long serialVersionUID = -7302453059053458970L;
	
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "grupo_secuencia")
    private Long idGrupo;
    private String name;
    
    @OneToOne(mappedBy = "grupo", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private GrupoGralConf gralConf;
    
    @OneToOne(mappedBy = "grupo", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private GrupoGralConfPassword password;	
    
    @OneToOne(mappedBy = "grupo", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private GrupoGralConfLock lock;      

    private boolean deleted;
    
	public Grupo() {
		super();
		this.gralConf = new GrupoGralConf(this);;
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