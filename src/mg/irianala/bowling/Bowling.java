package mg.irianala.bowling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bowling {

	public static void main(String[] args) {
		//"5,8,2 3,/ 9,/ 3,4,1 X,X,X,X"
		final String scoreSampleInput = "1,-,3 X X X -,/,1,2";
		
		System.out.println(computeScore(scoreSampleInput));
	}
	
	public static String computeScore(String input) {
		String[] frameScore = input.split(" ");
		List<String[]> scorePerTry = new ArrayList<String[]>();
		String finalScore = "";
		for(String s:frameScore) {
			scorePerTry.add(s.split(","));
		}
		
		for(int i = 0; i < scorePerTry.size(); i++) {
			finalScore  = finalScore + computeNormaly(i, scorePerTry, false, false) + " ";
		}
		
		return finalScore;
	}
	
	public static String computeNormaly(int index, List<String[]> completeScore, boolean isSpare, boolean isStrike) {
		System.out.println("computing normaly");
		
		for(int i = 0 ; i < (isSpare ? 2 : isStrike ? 3 : completeScore.get(index).length); i++) {
			if (completeScore.get(index)[i].equals("X")) {
				return computeWithStrike(index, completeScore);
			} else if (completeScore.get(index)[i].equals("/")) {
				return isSpare ? "15" : computeWithSpare(index, completeScore);
			} 
		}
		return String.valueOf(
				(completeScore.get(index)[0].equals("-") ? 0 : Integer.valueOf(completeScore.get(index)[0])) + 
				(completeScore.get(index)[1].equals("-") ? 0 :Integer.valueOf(completeScore.get(index)[1])) + 
				(isSpare ? 0 : completeScore.get(index)[2].equals("-") ? 0 :Integer.valueOf(completeScore.get(index)[2])));
	}
	
	public static String computeWithSpare(int index, List<String[]> completeScore) {
		System.out.println("computing with spare");
		if (index == 4) {
			return String.valueOf(
					15 +
					(completeScore.get(index)[completeScore.get(index).length - 2].equals("X") ? 15 : completeScore.get(index)[completeScore.get(index).length - 2].equals("-") ? 0 : Integer.valueOf(completeScore.get(index)[completeScore.get(index).length - 2])) +
					(completeScore.get(index)[completeScore.get(index).length - 1].equals("X") ? 15 : completeScore.get(index)[completeScore.get(index).length - 1].equals("-") ? 0 : Integer.valueOf(completeScore.get(index)[completeScore.get(index).length - 1]))
					);
		} else {
			return String.valueOf(15 + Integer.valueOf(computeNormaly(index + 1, completeScore, true, false)));
		}
	}
	
	public static String computeWithStrike(int index, List<String[]> completeScore) {
		System.out.println("computing strike");
		if (index == 4) {
			return String.valueOf(
					15 +
					(completeScore.get(index)[1].equals("X") ? 15 : completeScore.get(index)[1].equals("-") ? 0 : Integer.valueOf(completeScore.get(index)[1])) +
					(completeScore.get(index)[2].equals("X") ? 15 : completeScore.get(index)[2].equals("-") ? 0 : Integer.valueOf(completeScore.get(index)[2])) +
					(completeScore.get(index)[3].equals("X") ? 15 : completeScore.get(index)[3].equals("-") ? 0 : Integer.valueOf(completeScore.get(index)[3]))
					);
		} else {
			return String.valueOf(15 + Integer.valueOf(computeNormaly(index + 1, completeScore, false, true)));
		}
	}
	
}
