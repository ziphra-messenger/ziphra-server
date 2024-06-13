package com.privacity.server.websocket;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.privacity.common.config.ConstantProtocolo;
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
					p = comps.webSocket().sender().buildProtocoloDTO(
							ConstantProtocolo.PROTOCOLO_COMPONENT_GRUPO,
					        ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_HOW_MANY_MEMBERS_ONLINE,
					        r);
					
					comps.webSocket().sender().senderToGrupoMinusCreator(
							comps.service().usuarioSessionInfo().get(agentId).getUsuarioDB().getIdUser(),
							g.getIdGrupo(), p);
				}
			} catch (PrivacityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        };
        

        //comps.service().usuarioSessionInfo().remove(username);

    }
        };
        
        t.start();
} 
};