package com.privacity.server.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@Entity
@AllArgsConstructor

@SequenceGenerator(name = "grupo_secuencia", initialValue = 27011, allocationSize = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

@DiscriminatorValue("0")

public class Grupo2
extends Grupobase
implements Serializable{

	private static final long serialVersionUID = -7302453059053458970L;
	

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "grupo_secuencia")
    private Long idGrupo;
    private String name;
  

    private boolean deleted;
    
	public Grupo2() {
		super();
		
	}

}