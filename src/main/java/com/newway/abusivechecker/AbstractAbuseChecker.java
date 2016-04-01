package com.newway.abusivechecker;
/**
 * checks whether a text file contains any abusive words supplied.
 * 
 * @author wei
 *
 */

public abstract class AbstractAbuseChecker {

	public abstract boolean containsAbusive(String str);
	
	public abstract int numAbusive(String str);

}

