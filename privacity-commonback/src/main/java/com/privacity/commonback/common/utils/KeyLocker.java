package com.privacity.commonback.common.utils;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.interfaces.KeyLockerCallback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeyLocker {

	private KeyLockerCallback cb;
	
	public KeyLocker(KeyLockerCallback cb) {
		super();
		log.info("Instanciando: KeyLocker");
		this.cb=cb;

	}

    private static final Set<Object> lockedKeys = new HashSet<>();

    private static final Map<Object,Object> sequence = new HashMap<Object,Object>();
    
    private void lock(Object key) throws InterruptedException {
        synchronized (lockedKeys) {
            while (!lockedKeys.add(key)) {
                lockedKeys.wait();
            }
        }
    }
    private void lockAll() throws InterruptedException {
    	log.warn("lockAll");
        synchronized (lockedKeys) {
            while (!lockedKeys.isEmpty()) {
            	log.warn("lockAll wait");
                lockedKeys.wait();
            }
        }
    }
    private void unlock(Object key) {
        synchronized (lockedKeys) {
            lockedKeys.remove(key);
            lockedKeys.notifyAll();
        }
    }
    
    private void unlockAll() {
        synchronized (lockedKeys) {
        	log.warn("unlockAll notifyAll");
            lockedKeys.notifyAll();
        }
    }   
    public void doSynchronouslyOnlyForAllKeys() throws PrivacityException  {
    	log.warn("doSynchronouslyOnlyForAllKeys");
        try {
        	lockAll();
        	log.warn("doSynchronouslyOnlyForAllKeys sequence.clear()");
        	sequence.clear();
        	log.warn("doSynchronouslyOnlyForAllKeys lockedKeys.clear()");
        	lockedKeys.clear();
        	
        } catch (InterruptedException e) {
        	e.printStackTrace();
        	
        	log.error(ExceptionReturnCode.KEYLOCK_REFRESH_FAIL.getToShow());
			throw new PrivacityException(ExceptionReturnCode.KEYLOCK_REFRESH_FAIL);
			
		} finally {
			log.warn("finally unlockAll()");
            unlockAll();
            
        }
        

    }
    public Object doSynchronouslyOnlyForEqualKeys(Object key) throws PrivacityException  {
    	Object r;
        try {
            lock(key);

            if ( !sequence.containsKey(key)) {
            	log.debug(cb.getInfoForLog() + " - Nueva Key: " + key);
            	sequence.put(key, cb.actionInitValue(key));
            }else {
            	sequence.put(key, cb.actionNewValue(sequence.get(key)));
            }

            r=sequence.get(key);
            log.debug(cb.getInfoForLog() + " - Key: " + key + " Nuevo Valor: " + r);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        	String logadd;
        	if ( key== null) {
        		logadd ="key: null";
        	}else {
        		logadd= "key: " + key.toString();
        	}
        	
        	log.error(ExceptionReturnCode.KEYLOCK_FAIL.getToShow(cb.getInfoForLog() + " " + logadd, e));
			throw new PrivacityException(ExceptionReturnCode.KEYLOCK_FAIL);
			
		} finally {
            unlock(key);
            
        }
        
        return r;
    }
}
