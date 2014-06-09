package cecs566_tth;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class UtilsTest 
{
	@Test
	public void testConvertMessageTo4x4Blocks()
	{
		List<List<List<Integer>>> expected = new ArrayList<List<List<Integer>>>();
		List<List<Integer>> block1 = new ArrayList<List<Integer>>();
		{
			Integer [] row1 = { 8,11,4,0 };
			List<Integer> row1List = Arrays.asList(row1);
			Integer [] row2 = { 21,4,19,22 };
			List<Integer> row2List = Arrays.asList(row2);
			Integer [] row3 = { 4,13,19,24 };
			List<Integer> row3List = Arrays.asList(row3);
			Integer [] row4 = { 12,8,11,11 };
			List<Integer> row4List = Arrays.asList(row4);
			block1.add(row1List);
			block1.add(row2List);
			block1.add(row3List);
			block1.add(row4List);
		}
		List<List<Integer>> block2 = new ArrayList<List<Integer>>();
		{
			Integer [] row1 = { 8,14,13,3 };
			List<Integer> row1List = Arrays.asList(row1);
			Integer [] row2 = { 14,11,11,0 };
			List<Integer> row2List = Arrays.asList(row2);
			Integer [] row3 = { 17,18,19,14 };
			List<Integer> row3List = Arrays.asList(row3);
			Integer [] row4 = { 12,24,5,17 };
			List<Integer> row4List = Arrays.asList(row4);
			block2.add(row1List);
			block2.add(row2List);
			block2.add(row3List);
			block2.add(row4List);
		}
		List<List<Integer>> block3 = new ArrayList<List<Integer>>();
		{
			Integer [] row1 = { 8,4,13,3 };
			List<Integer> row1List = Arrays.asList(row1);
			Integer [] row2 = { 11,24,2,14 };
			List<Integer> row2List = Arrays.asList(row2);
			Integer [] row3 = { 20,18,8,13 };
			List<Integer> row3List = Arrays.asList(row3);
			Integer [] row4 = { 1,8,11,11 };
			List<Integer> row4List = Arrays.asList(row4);
			block3.add(row1List);
			block3.add(row2List);
			block3.add(row3List);
			block3.add(row4List);
		}
		expected.add(block1);
		expected.add(block2);
		expected.add(block3);
		List<List<List<Integer>>> actual = Utils.convertMessageTo4x4Matrices("I leave twenty million dollars to my friendly cousin Bill.");
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testCompressionRound1()
	{
		Integer [] expectedNumbers = { 19,10,1,5 };
		Integer [] iv = { 0,0,0,0 };
		List<Integer> expected = Arrays.asList(expectedNumbers);
		List<List<List<Integer>>> matrices4x4 = Utils.convertMessageTo4x4Matrices("I leave twenty million dollars to my friendly cousin Bill.");
		List<Integer> actual = Utils.compressionRound1(matrices4x4.get(0), Arrays.asList(iv));
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testRotateMatrix()
	{
		List<List<Integer>> expected = new ArrayList<List<Integer>>();
		Integer [] row1Numbers = { 11,4,0,8 };
		List<Integer> row1 = Arrays.asList(row1Numbers);
		Integer [] row2Numbers = { 19,22,21,4 };
		List<Integer> row2 = Arrays.asList(row2Numbers);
		Integer [] row3Numbers = { 24,4,13,19 };
		List<Integer> row3 = Arrays.asList(row3Numbers);
		Integer [] row4Numbers = { 11,11,8,12 };
		List<Integer> row4 = Arrays.asList(row4Numbers);
		expected.add(row1);
		expected.add(row2);
		expected.add(row3);
		expected.add(row4);
		List<List<List<Integer>>> matrices4x4 = Utils.convertMessageTo4x4Matrices("I leave twenty million dollars to my friendly cousin Bill.");
		List<List<Integer>> actual = Utils.rotateRowsOfMatrix(matrices4x4.get(0));
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testCompressionRound2()
	{
		Integer [] expectedNumbers = { 6,25,17,22 };
		Integer [] iv = { 19,10,1,5 };
		List<Integer> expected = Arrays.asList(expectedNumbers);
		List<List<List<Integer>>> matrices4x4 = Utils.convertMessageTo4x4Matrices("I leave twenty million dollars to my friendly cousin Bill.");
		List<Integer> actual = Utils.compressionRound2(matrices4x4.get(0), Arrays.asList(iv));
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testCompressionFunction()
	{
		Integer [] expectedNumbers = { 1,5,16,6 };
		List<Integer> expected = Arrays.asList(expectedNumbers);
		List<List<List<Integer>>> matrices4x4 = Utils.convertMessageTo4x4Matrices("I leave twenty million dollars to my friendly cousin Bill.");
		List<Integer> actual = Utils.compressionFunction(matrices4x4);
		assertTrue(expected.equals(actual));
	}
	
	@Test
	public void testStringOutput()
	{
		String expected = "BFQG";
		Integer [] expectedNumbers = { 1,5,16,6 };
		List<Integer> runningTotal = Arrays.asList(expectedNumbers);
		String actual = Utils.translateHashValueToLetters(runningTotal);
		assertTrue(expected.equals(actual));
	}
}
