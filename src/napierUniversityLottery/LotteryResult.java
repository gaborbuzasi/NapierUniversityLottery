package napierUniversityLottery;

import java.util.ArrayList;
import java.util.List;

public class LotteryResult {
	public String Name;
	public List<Bet> Bets = new ArrayList<Bet>();
	public int TotalAmount;
	
	/**
	 * Checks whether a collection of bets contains a specific person
	 * @param lr Collection of bets to draw the lottery results on
	 * @param name A specific person's name who's placed a bet
	 * @return The index of the person's location within the collection
	 */
	public static int containsName(List<LotteryResult> lr, String name) {
	    for (int i = 0; i < lr.size(); i++) {
	    	if(((List<LotteryResult>) lr).get(i) != null && ((List<LotteryResult>) lr).get(i).Name.equals(name)) {
	            return i;
	        }
		}
		
	    return -1;
	}
}
