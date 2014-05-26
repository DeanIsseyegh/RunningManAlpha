package com.mygdx.leftman.android;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class fasfa {

	public static void main(String[] args){
		fasfa f = new fasfa();
		f.go();
	}
	
	private void go(){
		int[] arrayToReverse = {1, 2, 3, 4, 5};
		int[] reversedArray = reverseArray(arrayToReverse);
		System.out.println(reversedArray);
	}
	
	private int[] reverseArray(int[] arrayToReverse){
		int[] reversedArray = new int[arrayToReverse.length];
		for (int i = arrayToReverse.length -1, j = 0; i > -1; i--, j++){
			reversedArray[j] = arrayToReverse[i];
		}
		
		System.out.println(arrayToReverse.length + " ==* " + reversedArray.length);
		return reversedArray;
	}
	
}
