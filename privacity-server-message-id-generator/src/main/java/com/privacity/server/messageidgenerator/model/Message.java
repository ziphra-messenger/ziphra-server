package com.privacity.server.messageidgenerator.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long idGrupo;

    private Long idMessage;

}
