package cecs566_tth;

import java.util.Arrays;

public class Driver 
{
	public static void main(String [] args)
	{
		System.out.println(Utils.translateTotalToLetters(Utils.compressionFunction(Utils.convertMessageTo4x4Matrices("I leave twenty million dollars to my friendly cousin Bill."))));
		Integer [] targetNumbers = { 6,25,17,22 };
		System.out.println(Utils.attack(Arrays.asList(targetNumbers)));
	}
}
