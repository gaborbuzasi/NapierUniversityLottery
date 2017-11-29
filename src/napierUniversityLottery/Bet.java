package napierUniversityLottery;

import java.util.ArrayList;
import java.util.List;

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
	
	public Boolean addChosenNumber(int number) {
		if (ChosenNumbers.size() < Lottery.MAX_NUMBERS_PER_BET) {
			ChosenNumbers.add(number);
		}
		else {
			return false;
		}
		
		return true;
	}
	
	public int findNumberInBet(int number) {
		return ChosenNumbers.indexOf(number);
	}

	/**
	 * @return The cost for the bet
	 */
	public int getCost() {
		return Cost;
	}
	
	public List<Integer> getChosenNumbers() {
		return ChosenNumbers;
	}

	/**
	 * @return the isWinning
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
