package de.pta.WebServiceSpendTest.util;

import java.util.HashMap;
import java.util.UUID;

public class SessionManager {
	static SessionManager theInstance = null;
	static final long SessionLength = 60000L; // one Minute in Millis
	HashMap<String, Long> registry = new HashMap<String, Long>();
	
	protected SessionManager() {
		
	}
	
	public static SessionManager getInstance() {
		if( theInstance == null ) { 
			theInstance = new SessionManager();
		}
		return theInstance;
	}
	
	public String registerNewSession() {
		String guid = UUID.randomUUID().toString();
		registry.put(guid, System.currentTimeMillis() );
		return guid;
	}
	
	public boolean refreshSessionIsAlive( String guid) {
		Long l = registry.get(guid);
		Long now = System.currentTimeMillis();
		if(  l == null || now - l > SessionLength ) {
			registry.remove(guid); // we're done
			return false;
		} 
		else {
			registry.remove(guid);
			registry.put(guid, System.currentTimeMillis() ); // refresh the Time
			
			return true;
		}
	}
	
	

}
