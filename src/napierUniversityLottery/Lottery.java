package napierUniversityLottery;

import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * The main class starting the UI class and handling the bets
 * @author Gabor Buzasi
 *
 */
public class Lottery {

	private static List<Bet> betsPlaced = new ArrayList<Bet>();
	
	public static final int MAXIMUM_BETS = 20;
	public static final int MAX_NUMBERS_PER_BET = 6;
	private static final int MAX_BETS_BY_PERSON = 3;
	public static final int MIN_NUMBER = 1;
	public static final int MAX_NUMBER = 50;
	
	
	public static void main(String[] args) {
		LotteryUI.start();
	}
	
	public static Boolean addBet(Bet b) {
		if (b != null && betsPlaced.size() < MAXIMUM_BETS) {
			betsPlaced.add(b);
			//checkNumberOfBetsForDraw();
			return true;
		}
		else {
			return false;
		}
	}
	
	public static boolean isBetOverLimit() {
		return betsPlaced.size() > MAXIMUM_BETS;
	}
	
	public static boolean checkNumberOfBetsForDraw() {
		if (betsPlaced.size() >= MAXIMUM_BETS) {
			LotteryUI.drawGameUI();
			return true;
		}
		return false;
	}

	public static Integer getBetIndexer() {
		return betsPlaced.size() + 1;
	}
	
	public static Bet luckyBet(String name) {
		if (betsPlaced.size() >= MAXIMUM_BETS) {
			return null;
		}
		
		Bet b = new Bet();
		b.setName(name);
		
		for (int i = 0; i < MAX_NUMBERS_PER_BET; i++) {
			int ran = 0;
			
			do {
				ran = ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER + 1);
			} while (b.getChosenNumbers().contains(ran));
			
			b.addChosenNumber(ran);
		}
		
		if (addBet(b))
			return b;
		
		return null;
	}
	
	public static String[] drawGame() {
		List<Integer> numbersDrawn = new ArrayList<Integer>();
		List<String> output = new ArrayList<String>();
		List<LotteryResult> results = new ArrayList<LotteryResult>();
		
		int sumOfPayout = 0;
		int sumOfPayin = 0;
		
		for (int i = 0; i < MAX_NUMBERS_PER_BET; i++) {
			int random = 0;
			
			do {
				random = ThreadLocalRandom.current().nextInt(MIN_NUMBER, MAX_NUMBER + 1);
			} while (numbersDrawn.contains(random));
			
			numbersDrawn.add(random);
		}
		
		output.add("The winning numbers are: " + ConversionHelpers.arrayToCommaDelimited(numbersDrawn));
		
		for (Bet b : betsPlaced) {
			List<Integer> matches = new ArrayList<Integer>(b.getChosenNumbers());
			matches.retainAll(numbersDrawn);
			
			if (matches.size() > 0) {
				b.setIsWinning(true);
			}
			
			int amountWon = calculateWinnings(matches.size());
			
			int indexOfName = LotteryResult.containsName(results, b.getName());
			
			if (indexOfName > -1) {
				results.get(indexOfName).Bets.add(b);
				results.get(indexOfName).TotalAmount += amountWon;
			}
			else {
				LotteryResult lr = new LotteryResult();
				lr.Name = b.getName();
				lr.Bets.add(b);
				lr.TotalAmount += amountWon;
				results.add(lr);
			}
			
			sumOfPayin += b.getCost();
		}
		
		for (LotteryResult lotteryResult : results) {
			int winningBets = 0;
			
			for (Bet b : lotteryResult.Bets) {
				System.out.println(lotteryResult.Name + " placed the following bets: " + ConversionHelpers.arrayToCommaDelimited(b.getChosenNumbers()));
				if (b.isWinning()) {
					winningBets++;
				}
			}
			
			if (lotteryResult.TotalAmount > 0) {
				output.add(lotteryResult.Name + " has won £" + lotteryResult.TotalAmount + " from " + winningBets + " winning ticket(s) of a total of " + lotteryResult.Bets.size());
			}
			
			sumOfPayout += lotteryResult.TotalAmount;
		}
		
		output.add("The lottery has made a " + ((sumOfPayin > sumOfPayout) ? ("profit of £" + (sumOfPayin - sumOfPayout)) : 
											    (sumOfPayout - sumOfPayin == 0 ? "hasn't made any loss or profit" : 
																			 ("loss of £" + (sumOfPayout - sumOfPayin)))));
		return output.toArray(new String[output.size()]);
	}
	
	private static int calculateWinnings(int numberOfMatchingNumbers) {
		switch (numberOfMatchingNumbers) {
			case 1:
				return 3;
				
			case 2:
				return 6;
		
			case 3: 
				return 25;
				
			case 4: 
				return 750;
				
			case 5:
				return 5000;
				
			case 6:
				return 5000000;

			default:
				return 0;
		}
	}

	public static boolean isMaxNameCountReached(String betterName) {
		int count = 0;
		
		for (Bet b : betsPlaced) {
			if (b.getName().equals(betterName))
				count++;
		}
		return count >= MAX_BETS_BY_PERSON;
	}

	public static void resetLottery() {
		betsPlaced.clear();
	}
	
}
