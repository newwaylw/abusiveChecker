package com.newway.abusivechecker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *Check whether a sentence contains abusive word
 *using a bloom filter
 * @author wei
 * 13 Feb 2011
 */
public class BloomAbuseChecker extends AbstractAbuseChecker {
	
	//a good bloom filter is 20 times the element size
	private static final int DEFAULT_BLOOMFILTER_SIZE = 200 * 4 * 1024 ;
	private BloomFilter bFilter;
	private boolean ignoreCase;

	public BloomAbuseChecker(String abusiveListFile, boolean ignoreCase){
		this(abusiveListFile, DEFAULT_BLOOMFILTER_SIZE, ignoreCase);
	}

	/**
	 * Constructor
	 * 
	 * @param abusiveListFile - input list of abusive words, one word per line
	 * @param bloomFilterSize - set the bloom filter size
	 * @param ignoreCase - ignore case between the abusive list and the input
	 */
	public BloomAbuseChecker(String abusiveListFile, int bloomFilterSize, boolean ignoreCase){
		super();
		this.setBloomFilter(bloomFilterSize);
		this.ignoreCase = ignoreCase;
		try{
			Scanner scanner = new Scanner(new FileInputStream(abusiveListFile));
			while(scanner.hasNextLine()){
				if(this.ignoreCase)
					bFilter.add(scanner.nextLine().toLowerCase());
				else
					bFilter.add(scanner.nextLine());
				}
			}catch(FileNotFoundException e){
				//e.printStackTrace();
				System.err.println("Dude, I can't find the file: "+abusiveListFile);
			}
	}
	
	/**
	 * set the size of the bloom filter, each call will create a new bloom filter
	 * @param bloomFilterSize - bloom filter size (bits)
	 **/
	public void setBloomFilter(int bloomFilterSize){
		bFilter = new BloomFilter(bloomFilterSize);
	}
	
	/**
	 * use a bloom filter to perform membership test.
	 * @param str
	 * @return true if the input string contains any abusive words, false otherwise.
	 */
	public boolean containsAbusive(String str){
		String[] words = str.split("\\s+");
		for (String w : words){
			if ((!this.ignoreCase && this.bFilter.contains(w) ) || (this.ignoreCase && this.bFilter.contains(w.toLowerCase())) ) 
				return true;
		}
		return false;
	}
	
	/**
	 * number of words found in the filter
	 * @param str - input text, words separated by space
	 * @return int number of words found
	 */
	public int numAbusive(String str){
		String[] words = str.split("\\s+");
		int c=0;
		for (String w : words){
			if ((!this.ignoreCase && this.bFilter.contains(w) ) || this.ignoreCase && this.bFilter.contains(w.toLowerCase()) )
				c++;
		}
		return c;
	}

	public static void main(String[] args){
		if(args.length != 2){
			System.err.println("usage:"+BloomAbuseChecker.class.getName()+" <word list file> <STDIN>" );
			System.exit(0);
		}

		//case insensitive
		AbstractAbuseChecker checker = new BloomAbuseChecker(args[0],true);
		int num = checker.numAbusive(args[1]);
		System.out.println("input sentence contains "+num+ " abusive word(s).");
		
	}
}
