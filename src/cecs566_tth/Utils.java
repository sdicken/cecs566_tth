package cecs566_tth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Utils 
{
	public static String findMatchingHash(String targetHash)
	{
		List<List<List<Integer>>> result = new ArrayList<List<List<Integer>>>();
		String computedHash = new String();
		while(!computedHash.equals(targetHash))
		{
			result = new ArrayList<List<List<Integer>>>();
			for(int i = 0; i < 3; i++)
			{
				List<List<Integer>> matrix = new ArrayList<List<Integer>>();
				for(int j = 0; j < 4; j++)
				{
					List<Integer> row = new ArrayList<Integer>();
					for(int k = 0; k < 4; k++)
					{
						row.add(new Random().nextInt(26));
					}
					matrix.add(row);
				}
				result.add(matrix);
			}
			computedHash = translateHashValueToLetters(compressionFunction(result));
		}
		return convert4x4MatricesToMessage(result);
	}
	
	public static String preprocessMessage(String message)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < message.length(); i++)
		{
			char c = message.charAt(i);
			Integer asciiValue = (int) c;
			if(asciiValue >= 97 && asciiValue <= 122)	// lowercase letter a-z
			{
				sb.append(c);
			}
			else if(asciiValue >= 65 && asciiValue <= 90) // uppercase letter A-Z
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static List<List<List<Integer>>> convertMessageTo4x4Matrices(String message)
	{
		List<List<List<Integer>>> result = new ArrayList<List<List<Integer>>>();
		message = preprocessMessage(message);
		int originalMessageLength = message.length();
		for(int i = 0; i < (int) originalMessageLength/16; i++)
		{
			List<List<Integer>> block = new ArrayList<List<Integer>>();
			for(int j = 0; j < 4; j++)
			{
				List<Integer> row = new ArrayList<Integer>();
				for(int k = 0; k < 4; k++)
				{
					char c = message.charAt(16*i + 4*j + k);
					Integer asciiValue = (int) c;
					if(asciiValue >= 97 && asciiValue <= 122)	// lowercase letter a-z
					{
						row.add(asciiValue - 97);
					}
					else if(asciiValue >= 65 && asciiValue <= 90) // uppercase letter A-Z
					{
						row.add(asciiValue - 65);
					}
				}
				block.add(row);
			}
			result.add(block);
		}
		return result;
	}
	
	public static String convert4x4MatricesToMessage(List<List<List<Integer>>> matrices)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < matrices.size(); i++)
		{
			List<List<Integer>> matrix = matrices.get(i);
			for(int j = 0; j < matrix.size(); j++)
			{
				List<Integer> row = matrix.get(j);
				for(int k = 0; k < row.size(); k++)
				{
					Integer val = row.get(k);
					val += 97;	// make lowercase letter a-z
					sb.append((char) val.byteValue());
				}
			}
		}
		return sb.toString();
	}
	
	public static List<Integer> compressionFunction(List<List<List<Integer>>> matrices4x4)
	{
		Integer [] initializationVector = { 0,0,0,0 };
		List<Integer> iv = Arrays.asList(initializationVector);
		List<Integer> result = new ArrayList<Integer>();
		for(int i = 0; i < matrices4x4.size(); i++)
		{
			List<List<Integer>> matrix = matrices4x4.get(i);
			if(i == 0)
				result = compressionRound1(matrix, iv);
			else
				result = compressionRound1(matrix, result);
			result = compressionRound2(matrix, result);
			
		}
		return result;
	}
	
	public static String translateHashValueToLetters(List<Integer> runningTotal)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < runningTotal.size(); i++)
		{
			Integer number = runningTotal.get(i);
			number += 65;
			char c = (char) number.byteValue();
			sb.append(c);
		}
		return sb.toString();
	}
	
	/**
	 * Method sums columns mod 26
	 * @param matrix
	 * @param blockNumber
	 * @param runningTotal
	 * @return Four-number running total.
	 */
	public static List<Integer> compressionRound1(List<List<Integer>> matrix, List<Integer> runningTotal)
	{
		List<Integer> result = new ArrayList<Integer>();
		List<Integer> columnsTotal = sumAllColumnsOfMatrix(matrix);
		for(int i = 0; i < columnsTotal.size(); i++)
		{
			Integer sum = (runningTotal.get(i) + columnsTotal.get(i)) % 26;
			result.add(sum);
		}

		return result;
	}
	
	/**
	 * Rotates rows:
	 * Row 1: left 1
	 * Row 2: left 2
	 * Row 3: left 3
	 * Row 4: reverse order
	 * @param matrices4x4
	 * @param blockNumber
	 * @param runningTotal
	 * @return Four-number running total.
	 */
	public static List<Integer> compressionRound2(List<List<Integer>> matrix, List<Integer> runningTotal)
	{
		List<Integer> result = new ArrayList<Integer>();
		List<List<Integer>> rotatedMatrix = rotateRowsOfMatrix(matrix);
		List<Integer> rotatedColumnTotal = sumAllColumnsOfMatrix(rotatedMatrix);
		for(int i = 0; i < rotatedColumnTotal.size(); i++)
		{
			Integer sum = (runningTotal.get(i) + rotatedColumnTotal.get(i)) % 26;
			result.add(sum);
		}
		return result;
	}
	
	public static List<Integer> sumAllColumnsOfMatrix(List<List<Integer>> matrix)
	{
		List<Integer> result = new ArrayList<Integer>();
		for(int i = 0; i < matrix.size(); i++)
		{
			Integer columnTotal = 0;
			for(int j = 0; j < matrix.size(); j++)	// loop over all columns of matrix
			{
				List<Integer> row = matrix.get(j);
				for(int k = 0; k < row.size(); k++)	// loop over all rows of matrix
				{
					if(k == i)
					{
						columnTotal = (columnTotal + row.get(k)) % 26;
						break;
					}
				}
			}
			result.add(columnTotal);
		}
		return result;
	}
	
	public static List<List<Integer>> rotateRowsOfMatrix(List<List<Integer>> matrix)
	{
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		for(int i = 0; i < matrix.size(); i++)
		{
			List<Integer> row = matrix.get(i);
			List<Integer> newRow = new ArrayList<Integer>();
			Integer temp0 = row.get(0);
			Integer temp1 = row.get(1);
			Integer temp2 = row.get(2);
			Integer temp3 = row.get(3);
			switch(i)
			{
				case 0:
				{
					newRow.add(temp1);
					newRow.add(temp2);
					newRow.add(temp3);
					newRow.add(temp0);
					break;
				}
				case 1:
				{
					newRow.add(temp2);
					newRow.add(temp3);
					newRow.add(temp0);
					newRow.add(temp1);
					break;
				}
				case 2:
				{
					newRow.add(temp3);
					newRow.add(temp0);
					newRow.add(temp1);
					newRow.add(temp2);
					break;
				}
				case 3:
				{
					newRow.add(temp3);
					newRow.add(temp2);
					newRow.add(temp1);
					newRow.add(temp0);
					break;
				}
			}
			result.add(newRow);
		}
		return result;
	}
}
