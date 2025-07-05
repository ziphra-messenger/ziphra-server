package ar.ziphra.messaging.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsMessage {

	private String username;
	private String protocolo;

}
