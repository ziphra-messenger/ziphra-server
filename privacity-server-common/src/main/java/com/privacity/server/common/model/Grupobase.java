package com.privacity.server.common.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DiscriminatorFormula;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@Entity
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorFormula(
        "CASE WHEN deleted = true THEN '1' " +
        " WHEN deleted = false then '0'   end"
)

public class Grupobase implements Serializable{

	private static final long serialVersionUID = -7302453059053458970L;
	
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "grupo_secuencia")
    protected Long idGrupo;
    protected String name;
    

    protected boolean deleted;
    
	public Grupobase() {

	}
    
    
    /*

    @OneToOne(mappedBy = "grupo", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    	 AESGrupo aesGrupo;*/
}