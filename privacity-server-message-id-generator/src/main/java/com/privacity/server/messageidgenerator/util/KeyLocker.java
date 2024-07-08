package com.privacity.server.messageidgenerator.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.privacity.server.messageidgenerator.services.IdMessageGenerator;

@Service
public class KeyLocker {
	
	private IdMessageGenerator idMessageGenerator;
	private Random rand = new Random();
	
	public KeyLocker(IdMessageGenerator idMessageGenerator) {
		super();
		this.idMessageGenerator = idMessageGenerator;
	}


	
    private static final Logger log = Logger.getLogger(KeyLocker.class.getCanonicalName());

    private static final Set<Long> lockedKeys = new HashSet<>();

    private static final Map<Long,Long> sequence = new HashMap<Long,Long>();
    
    private void lock(Long key) throws InterruptedException {
        synchronized (lockedKeys) {
            while (!lockedKeys.add(key)) {
                lockedKeys.wait();
            }
        }
    }

    private void unlock(Long key) {
        synchronized (lockedKeys) {
            lockedKeys.remove(key);
            lockedKeys.notifyAll();
        }
    }

    public Long doSynchronouslyOnlyForEqualKeys(Long key) throws InterruptedException {
    	Long r;
        try {
            lock(key);

            if ( !sequence.containsKey(key)) {
            	log.fine("Nuevo Grupo: " + key);
            	sequence.put(key, idMessageGenerator.getNextMessageId(key));
            }else {
            	Long idm = sequence.get(key);
            	
        		int n = rand.nextInt(50);
        		idm = idm + n;
        		sequence.put(key, idm);
            }

            r=sequence.get(key);
            log.fine("Grupo: " + key + " Message Id Generado: " + r);
        } finally {
            unlock(key);
            
        }
        
        return r;
    }
}
