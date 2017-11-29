package napierUniversityLottery;

import java.util.ArrayList;
import java.util.List;

public class LotteryResult {
	public String Name;
	public List<Bet> Bets = new ArrayList<Bet>();
	public int TotalAmount;
	
	public static int containsName(List<LotteryResult> lr, String name) {
	    for (int i = 0; i < lr.size(); i++) {
	    	if(((List<LotteryResult>) lr).get(i) != null && ((List<LotteryResult>) lr).get(i).Name.equals(name)) {
	            return i;
	        }
		}
		
	    return -1;
	}
}
