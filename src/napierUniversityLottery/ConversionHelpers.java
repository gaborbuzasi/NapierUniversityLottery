package napierUniversityLottery;

import java.util.List;


public class ConversionHelpers {
	/**
	 * Checks whether the passed in parameter can be parsed into an Integer
	 * @param value String to be parsed to Integer
	 * @return True or False whether parameter contains a integer value
	 */
	public static boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	/**
	 * Checks whether the passed in parameter can be parsed into a float
	 * @param value String to be parsed to Float
	 * @return True or False whether parameter contains a float value
	 */
	public static boolean tryParseFloat(String value) {
		try {
			Float.parseFloat(value);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	/**
	 * Converts a list of integers into a comma delimited string 
	 * @param numbers list of numbers
	 * @return A string containing all numbers delimited by commas
	 */
	public static String arrayToCommaDelimited (List<Integer> numbers) {
		StringBuilder result = new StringBuilder();
		
		for (Integer integer : numbers) {
			result.append(integer);
			result.append(", ");
		}
		
		return result.length() > 0 ? result.substring(0, result.length() - 2) : "";
	}
}
