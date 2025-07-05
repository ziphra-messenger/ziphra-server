package ar.ziphra.core.model;
import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Entity
@Table
@Accessors(chain = true)
public class MessageIdSequence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long idGrupo;

    private Long idMessage;

}
