package com.privacity.core.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import lombok.experimental.Accessors;

@Accessors(chain = true)
@Embeddable
@SequenceGenerator(name = "message_secuencia", initialValue = 10000, allocationSize = 1)
public class MessageId implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;
    
    public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Long getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(Long idMessage) {
		this.idMessage = idMessage;
	}
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "message_secuencia")
	private Long idMessage;

    public MessageId() {

    }

	public MessageId(Grupo grupo, Long idMessage) {
		super();
		this.grupo = grupo;
		this.idMessage = idMessage;
	}

	@Override
	public int hashCode() {
		return Objects.hash(grupo, idMessage);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageId other = (MessageId) obj;
		return Objects.equals(grupo, other.grupo) && Objects.equals(idMessage, other.idMessage);
	}
	

}
