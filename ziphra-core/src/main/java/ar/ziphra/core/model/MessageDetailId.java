package ar.ziphra.core.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
public class MessageDetailId  implements Serializable {

	private static final long serialVersionUID = -1657143766873819117L;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "idUser")
    private Usuario userDestino;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(
            name = "idGrupo",
            referencedColumnName = "id_grupo"),
        @JoinColumn(
            name = "idMessage",
            referencedColumnName = "idMessage")
    })
	@ToString.Exclude

    private Message message;

	@Override
	public String toString() {
		return "MessageDetailId [userDestino=" + userDestino.getUsername() + ", message=" + message.messageId.getIdMessage() + ", grupo " + message.messageId.getGrupo().getIdGrupo() + "]";
	}








    
 }
