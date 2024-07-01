package com.privacity.server.websocket;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.privacity.common.enumeration.ProtocoloComponentsEnum;import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.model.Grupo;
import com.privacity.server.security.SocketSessionRegistry;

/**
 * Created by baiguantao on 2017/8/4.
 * STOMP Monitoring category
 * Used for session registration and key value acquisition
 */
public class STOMPConnectEventListener  implements ApplicationListener<SessionConnectEvent>{

    @Autowired
    SocketSessionRegistry webAgentSessionRegistry;

	@Autowired @Lazy
	private FacadeComponent comps;
	
    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        //login get from browser
        
        

        String agentId = event.getUser().getName();
        String sessionId = sha.getSessionId();
        
        System.out.println("CONECTADO A WS");
        System.out.println("NOMBRE AGENT ID = " + agentId);
        System.out.println("SESSION ID = " + sessionId);
        
        webAgentSessionRegistry.registerSessionId(agentId,sessionId);
        
        Thread t = new Thread() {
        	
        public void run() {
        List<Grupo> grupos = comps.repo().userForGrupo().findByGrupoByUsername(agentId);
        
        for (Grupo g : grupos) {
        	GrupoDTO r = new GrupoDTO();
        	r.setIdGrupo(g.getIdGrupo()+"");
        	
        	try {
				r.setMembersOnLine(
						comps.validation().grupo().getMembersOnline(r)
				);
				
				if (r.getMembersOnLine() != 0) {
					ProtocoloDTO p;
					
					senderToGrupoMinusCreator(agentId,
							 g.getIdGrupo(), r);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        };
        

        //comps.service().usuarioSessionInfo().remove(username);

    }
        };
        
        t.start();
} 
    
	private void senderToGrupoMinusCreator(String username, long idGrupo, GrupoDTO g) throws Exception {
		
		
		
		List<String> lista = comps.repo().userForGrupo().findByForGrupoMinusCreator(idGrupo, comps.service().usuarioSessionInfo().get(username).getUsuarioDB().getIdUser());
		
		Iterator<String> i = lista.iterator();
		
		while (i.hasNext()){
			String destino = i.next();
			ProtocoloDTO p;


			GrupoDTO newR =  (GrupoDTO) comps.util().utilService().clon(GrupoDTO.class, g);

		
					comps.service().usuarioSessionInfo().get(destino).getPrivacityIdServices() 
					.encryptIds(newR);
				
				
				

			p = comps.webSocket().sender().buildProtocoloDTO(
					ProtocoloComponentsEnum.PROTOCOLO_COMPONENT_GRUPO,
			        ProtocoloActionsEnum.PROTOCOLO_ACTION_GRUPO_HOW_MANY_MEMBERS_ONLINE,
			        newR);
			
			comps.webSocket().sender().sender(new WsMessage (destino , p ));
		}
		
		
	}
}