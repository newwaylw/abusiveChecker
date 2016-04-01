package com.newway.abusivechecker;

/**
 * Check whether a sentence contains abusive word
 * using Java Set structure 
 * @author wei
 * 13 Feb 2011
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

public class SimpleAbuseChecker extends AbstractAbuseChecker {
	private boolean ignoreCase;
	private Set<String>abuseWordSet;
	
	/**
	 * use a set to store all abuse words
	 * @param abusiveListFile
	 * @param ignoreCase - whether to treat words case sensitive
	 */
	public SimpleAbuseChecker(String abusiveListFile, boolean ignoreCase){
		super();
		this.abuseWordSet = new HashSet<String>();
		this.ignoreCase = ignoreCase;
		
		try{
		Scanner scanner = new Scanner(new FileInputStream(abusiveListFile));
		while(scanner.hasNextLine()){
			if(this.ignoreCase)
				abuseWordSet.add(scanner.nextLine().toLowerCase());
			else
				abuseWordSet.add(scanner.nextLine());
		}
		
		}catch(FileNotFoundException e){
			//e.printStackTrace();
			System.err.println("Dude, I can't find the file: "+abusiveListFile);
		}
	}
	
	/**
	 * 
	 * @param str
	 * @return true if the text contains any abusive words 
	 */
	public boolean containsAbusive(String str){
		String[] words = str.split("\\s+");
		for (String w : words){
			if ((!this.ignoreCase && this.abuseWordSet.contains(w) ) || this.ignoreCase && this.abuseWordSet.contains(w.toLowerCase()) )
				return true;
		}
		return false;
	}

	public int numAbusive(String str){
		String[] words = str.split("\\s+");
		int c=0;
		for (String w : words){
			if ((!this.ignoreCase && this.abuseWordSet.contains(w) ) || this.ignoreCase && this.abuseWordSet.contains(w.toLowerCase()) )
				c++;
		}
		return c;
	}
	
	public static void main(String[] args){
		if(args.length != 2){
			System.err.println("usage:"+SimpleAbuseChecker.class.getName()+" <word list file> <STDIN>" );
			System.exit(0);
		}

		//case insensitive
		AbstractAbuseChecker checker = new SimpleAbuseChecker(args[0],true);
		int num = checker.numAbusive(args[1]);
		System.out.println("input sentence contains "+num+ " abusive word(s).");
		
	}
}
