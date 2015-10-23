package jp.co.xing.utaehon03.basegame;

/**
 * Function RanDomNoRepeat
 * create: 01/03/2012
 * create by: haipq
 */

import java.util.ArrayList;
import java.util.Random;

public class RanDomNoRepeat {
	private Random random;
	private int lastRandom;
	private ArrayList<Integer> arrTmp;
	private int mMin;
	private int mMax;
	
	public RanDomNoRepeat() {
		random = new Random();
		arrTmp = new ArrayList<Integer>();
		lastRandom = -1;
	}
	
	//public 
	
	/**
	 * setLenghNoRepeatLast: only for getNextIntNoRepeatLast();
	 * @param pMax
	 */
	public void setLenghNoRepeat(int pMax){
		this.mMax = pMax;
		this.mMin = 0;
	}
	
	/**
	 *  setLenghNoRepeatLast: only for getNextIntNoRepeatLast();
	 * @param pMin
	 * @param pMax
	 */
	public void setLenghNoRepeat(int pMin, int pMax){
		
		this.mMin = pMin;
		this.mMax = pMax;
		
	}
	
	/**
	 * getNextInt: normal random
	 * @param max
	 * @return
	 */
	public int getNextInt(int max){
		return random.nextInt(max);
	}
	
	/**
	 * getNextInt: normal random form minimum to maximum
	 * @param min
	 * @param max
	 * @return
	 */
	public int getNextInt(int min, int max){
		return random.nextInt(max - min) + min;
	}
	
	/**
	 * getNextIntNoRepeatLast: No repeat last value
	 * @param max
	 * @return
	 */
	public int getNextIntNoRepeatLast(){
		int value = random.nextInt(this.mMax - this.mMin) + this.mMin;
			while ( value == lastRandom ) {
				value = random.nextInt(this.mMax - this.mMin) + this.mMin;
			}
			lastRandom = value;
		return value;
	}
	
	/**
	 * getNextIntNoRepeat 
	 * @param autoReset->  true: when max then clear temp
	 * @return 
	 */
	
	public int getNextIntNoRepeat(boolean autoReset){
		if(autoReset){
			if( this.mMax - this.mMin == arrTmp.size() -1 ){
				arrTmp.clear();
			}
		}
		
		int value = random.nextInt(this.mMax - this.mMin) + this.mMin;
		
	     if (arrTmp.isEmpty())
			 arrTmp.add(value);
	     else
	     {
	            while (!isCheck(value))
	            	value = random.nextInt(this.mMax - this.mMin) + this.mMin;
	     }
	    arrTmp.add(value);
	    
		return value;
	}
	
	/**
	 * isCheck in array
	 * @param check
	 * @return
	 */
	protected boolean isCheck(int check){ 
		
		for (int x = 0; x < arrTmp.size(); x++)
		    if (check == arrTmp.get(x).intValue())
                return false;
		
		return true;
	}
	
	/**
	 * clearTmp: clear all temp;
	 */
	public void clearTmp(){
		  if (!arrTmp.isEmpty()){
			  arrTmp.clear();
		  }
		  lastRandom = -1;
	}
	
	
}