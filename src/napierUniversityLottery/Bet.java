package napierUniversityLottery;

import java.util.ArrayList;
import java.util.List;

/**
 * Bet class provides the data structure for a bet that will be placed in the lottery
 * @author Gabor Buzasi
 *
 */
public class Bet {
	 
	private String Name;
	private List<Integer> ChosenNumbers = new ArrayList<Integer>();
	private boolean IsWinning = false;
	private static final int Cost = 1;
	
	/**
	 * @return The name of the person who placed the bet
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * Set the name of the person placing the bet
	 * @param name Name of the person placing the bet
	 */
	public void setName(String name) {
		Name = name;
	}
	
	/**
	 * Adds the number to your bet
	 * @param number Number to place in your bet
	 * @return Boolean whether adding your number to the bet was successful
	 */
	public Boolean addChosenNumber(int number) {
		if (ChosenNumbers.size() < Lottery.MAX_NUMBERS_PER_BET) {
			ChosenNumbers.add(number);
		}
		else {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 
	 * @param number The number to find in the Bet
	 * @return The index of the number if found in your bet otherwise -1
	 */
	public int findNumberInBet(int number) {
		return ChosenNumbers.indexOf(number);
	}

	/**
	 * @return The cost for the bet
	 */
	public int getCost() {
		return Cost;
	}
	
	/**
	 * Retrieves all numbers placed in the bet
	 * @return A list of integers
	 */
	public List<Integer> getChosenNumbers() {
		return ChosenNumbers;
	}

	/**
	 * @return a boolean whether your Bet is winning or not
	 */
	public boolean isWinning() {
		return IsWinning;
	}

	/**
	 * @param isWinning the isWinning to set
	 */
	public void setIsWinning(boolean isWinning) {
		IsWinning = isWinning;
	}

}
