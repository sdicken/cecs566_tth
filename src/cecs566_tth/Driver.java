package cecs566_tth;


public class Driver 
{
	public static void main(String [] args)
	{
		String matchingHash = Utils.findMatchingHash("BFQG");
		System.out.println(matchingHash);
		System.out.println(Utils.convertMessageTo4x4Matrices(matchingHash));
		System.out.println(Utils.translateHashValueToLetters(Utils.compressionFunction(Utils.convertMessageTo4x4Matrices(matchingHash))));
	}
}
