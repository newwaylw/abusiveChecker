package com.newway.abusivechecker;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for
 */
public class AbuseCheckerTest 
    extends TestCase{
	AbstractAbuseChecker simpleChecker;
	AbstractAbuseChecker bloomChecker;
	int size;
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AbuseCheckerTest( String testName ){
        super( testName );
    }

    /**
     * setUp() method that initialises common objects
     */
    protected void setUp() throws Exception {
        super.setUp();
        this.size = 2000 * 1024;
        simpleChecker = new SimpleAbuseChecker("abusive.txt", false);
        bloomChecker = new BloomAbuseChecker("abusive.txt", size, false);
    }

    /**
     * tearDown() method that cleanup the common objects
     */
    protected void tearDown() throws Exception {
        super.tearDown();
        simpleChecker = null;
        bloomChecker = null;
    }
    
    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){
         return new TestSuite( AbuseCheckerTest.class );
     }
    
    /**
     * Test simple checker
     */
    public void testSimpleAbuseChecker(){
    	
    String sentence1 = "this is a test sentence";
    String sentence2 = "this is a fuckingabusive sentence you can't tell";
    String sentence3 = "this is a fucking test sentence";
    String sentence4 = "this is another fucking ASShole test sentence";
    String sentence5 = "fuck fuck fucker fUCK";
    
	assertEquals(0, simpleChecker.numAbusive(sentence1) );
	assertEquals(0, simpleChecker.numAbusive(sentence2) );
	assertEquals(1, simpleChecker.numAbusive(sentence3) );
	assertEquals(1, simpleChecker.numAbusive(sentence4) );
	assertEquals(3, simpleChecker.numAbusive(sentence5) );
	
	simpleChecker = new SimpleAbuseChecker("abusive.txt", true);
	assertEquals(0, simpleChecker.numAbusive(sentence1) );
	assertEquals(0, simpleChecker.numAbusive(sentence2) );
	assertEquals(1, simpleChecker.numAbusive(sentence3) );
	assertEquals(2, simpleChecker.numAbusive(sentence4) );
	assertEquals(4, simpleChecker.numAbusive(sentence5) );
    }
    
    /**
     * test bloom filter checker
     */
    public void testBloomAbuseChecker(){
	//use bloom filter	 
        String sentence1 = "this is a test sentence";
        String sentence2 = "this is a fuckingabusive sentence you can't tell";
        String sentence3 = "this is a fucking test sentence";
        String sentence4 = "this is another fucking ASShole test sentence";
        String sentence5 = "fuck fuck fucker fuCK";
        
    	assertEquals(0, bloomChecker.numAbusive(sentence1) );
    	assertEquals(0, bloomChecker.numAbusive(sentence2) );
    	assertEquals(1, bloomChecker.numAbusive(sentence3) );
    	assertEquals(1, bloomChecker.numAbusive(sentence4) );
    	assertEquals(3, bloomChecker.numAbusive(sentence5) );
    	
    	bloomChecker = new BloomAbuseChecker("abusive.txt", size, true);
    	assertEquals(0, bloomChecker.numAbusive(sentence1) );
    	assertEquals(0, bloomChecker.numAbusive(sentence2) );
    	assertEquals(1, bloomChecker.numAbusive(sentence3) );
    	assertEquals(2, bloomChecker.numAbusive(sentence4) );
    	assertEquals(4, bloomChecker.numAbusive(sentence5) );
    }
    
    /*
    public static void main(String[] args) {
    	junit.textui.TestRunner.run(
    			AbuseCheckerTest.class);
    }
    */
}
