package com.newway.abusivechecker;

/**
 * The Bloom filter is a space-efficient probabilistic data structure that is used 
 * to test whether an element is a member of a set. False positives are possible, 
 * but false negatives are not. Elements can be added to the set. 
 * The more elements that are added to the set, the larger the probability of false positives.
 *
 * @author wei
 * 13 Feb 2011
 */

import java.io.Serializable;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;

public class BloomFilter implements Serializable{

	private static final long serialVersionUID = 8735005959747455735L;
	// SEEDs are used to generate random hash functions for bloom filter
	public static final int SEED1 = 0x5;
	public static final int SEED2 = 0x74;
	public static final int SEED3 = 0xb8;
	private int size;
	private BitSet bitSet;
	
	public BloomFilter(){
		this.bitSet = new BitSet();
	}

	/**
	 * n governs the probability of false positive
	 * The required number of bits m, given n (the number of inserted elements) 
	 * and a desired false positive probability p (and assuming the optimal value of 
	 * k hash function is used) is
	 * m = - n * ln(p) / ln(2)^2
	 */
	public BloomFilter(int size){
		this.size = size;
		this.bitSet = new BitSet(size); 
	}

	/**
	 * sets the size of the new bloom filter
	 *@param size - bit size of the bloom filter
	 */
	public void setBloomFilterSize(int size){
		this.size = size;
		this.bitSet = new BitSet(size); 
	}

	/**
	 * add a string into the bloom filter
	 */
	public void add(String word){
		int p1 = MurmurHash.hash(word.getBytes(), SEED1) % this.size;
		int p2 = MurmurHash.hash(word.getBytes(), SEED2) % this.size;
		int p3 = MurmurHash.hash(word.getBytes(), SEED3) % this.size;
		
		this.bitSet.set(Math.abs(p1));
		this.bitSet.set(Math.abs(p2));
		this.bitSet.set(Math.abs(p3));
	}
	
	/**
	 * add all strings into the bloom filter
	 */
	public void addAll(Collection<String> coll){
		Iterator<String> it = coll.iterator();
		while(it.hasNext()){
			this.add(it.next());
		}
	}
	
	/**
	 * test string membership in the bloom filter
	 * @param str - input string
	 * @return true if this bloom filter contains the given string
	 */
	public boolean contains(String str){
		int p1 = MurmurHash.hash(str.getBytes(), SEED1) % this.size;
		int p2 = MurmurHash.hash(str.getBytes(), SEED2) % this.size;
		int p3 = MurmurHash.hash(str.getBytes(), SEED3) % this.size;
		
		if(this.bitSet.get(Math.abs(p1)) && this.bitSet.get(Math.abs(p2)) && this.bitSet.get(Math.abs(p3)))
			return true;
		else return false;
	}
	
	public static void main (String[] args){
		BloomFilter filter = new BloomFilter(100000);
		filter.add("fuck");
		filter.add("fish");
		
		System.out.println(filter.contains("fisf"));
		System.out.println(filter.contains("fuck"));
	}
}
