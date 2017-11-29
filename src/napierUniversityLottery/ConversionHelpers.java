package napierUniversityLottery;

import java.util.List;


public class ConversionHelpers {
	public static boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	public static boolean tryParseFloat(String value) {
		try {
			Float.parseFloat(value);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	
	public static String arrayToCommaDelimited (List<Integer> numbers) {
		StringBuilder result = new StringBuilder();
		
		for (Integer integer : numbers) {
			result.append(integer);
			result.append(", ");
		}
		
		return result.length() > 0 ? result.substring(0, result.length() - 2) : "";
	}
}
