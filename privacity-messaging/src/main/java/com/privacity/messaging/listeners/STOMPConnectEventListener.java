package com.privacity.messaging.listeners;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.core.model.Grupo;
import com.privacity.messaging.configuration.SocketSessionRegistry;
import com.privacity.messaging.util.UtilFacade;

/**
 * Created by baiguantao on 2017/8/4.
 * STOMP Monitoring category
 * Used for session registration and key value acquisition
 */

public class STOMPConnectEventListener  implements ApplicationListener<SessionConnectEvent>{

    @Autowired
    SocketSessionRegistry webAgentSessionRegistry;

	@Autowired
	@Lazy
	private UtilFacade uf;
	
    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        //login get from browser
        
        String agentId = event.getUser().getName();
        String sessionId = sha.getSessionId();
        
        System.out.println("CONECTADO A WS");
        System.out.println("NOMBRE AGENT ID = " + agentId);
        System.out.println("SESSION ID = " + sessionId);
        
        for ( String sessionsss : uf.socketSessionRegistry().getSessionIds(agentId) ) {
        	uf.socketSessionRegistry().unregisterSessionId(agentId,sessionsss);	
        }
        
        webAgentSessionRegistry.registerSessionId(agentId,sessionId);
        
        Thread t = new Thread() {
        	
        public void run() {
        online(agentId,true);
        

        //comps.service().usuarioSessionInfo().remove(username);

    }};

        
        t.start();
} 
    
	private void senderToGrupoMinusCreator(String username, long idGrupo, GrupoDTO g, boolean minuscreator) throws Exception {

			ProtocoloDTO p = uf.webSocketSender().buildProtocoloDTO(
					ProtocoloComponentsEnum.GRUPO,
			        ProtocoloActionsEnum.GRUPO_HOW_MANY_MEMBERS_ONLINE,
			        g);
			uf.webSocketSender().senderToGrupo(p, idGrupo, username, minuscreator);
			
		}
		

	public void online(String agentId, boolean minuscreator) {
		List<Grupo> grupos = uf.userForGrupoRepository().findByGrupoByUsername(agentId);
		
		for (Grupo g : grupos) {
			GrupoDTO r = new GrupoDTO();
			r.setIdGrupo(g.getIdGrupo()+"");
			
			try {
				r.setMembersQuantityDTO(
						uf.webSocketSender().getMembersOnlineByGrupo(r.getIdGrupo())
				);
				
				if (r.getMembersQuantityDTO().getQuantityOnline() != 0) {
					ProtocoloDTO p;
					
					senderToGrupoMinusCreator(agentId,
							 g.getIdGrupo(), r,minuscreator);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		};
	}
    
	}
