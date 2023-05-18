package mg.irianala.bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Bowling {

	public static void main(String[] args) {
		//"5,8,2 3,/ 9,/ 3,4,1 X,X,X,X"
		final String scoreSampleInput = "1,-,3 X X X -,/,1,2";
		
		System.out.println(computeScore(scoreSampleInput));
	}
	
	public static String computeScore(String input) {
		var frameScore = input.split(" ");
		List<String[]> scorePerTry = new ArrayList<>();
		StringBuilder finalScore = new StringBuilder();

		for(String s : frameScore) {
			scorePerTry.add(s.split(","));
		}
		
		for(int i = 0; i < scorePerTry.size(); i++) {
			finalScore.append(
					computeNormally(i, scorePerTry, false, false)
			);
		}
		
		return finalScore
				.append(" ")
				.toString();
	}
	
	public static String computeNormally(int index, List<String[]> completeScore, boolean isSpare, boolean isStrike) {
		var current = completeScore.get(index);

		int limit;
		if (isSpare) {
			limit = 2;
		} else if (isStrike) {
			limit = 3;
		} else {
			limit = completeScore.get(index).length;
		}
		
		for(int i = 0 ; i < limit; i++) {
			if (current[i].equals("X")) {
				return computeWithStrike(index, completeScore);
			} else if (completeScore.get(index)[i].equals("/")) {
				return isSpare ? "15" : computeWithSpare(index, completeScore);
			} 
		}

		var summedScore = IntStream
				.range(0, 3)
				.map(i -> {
					int score = 0;
					if (i == 2 && isSpare) {
						return score;
					} else {
						return current[i].equals("-") ? 0 : Integer.parseInt(current[i]);
					}
				}).sum();

		return String.valueOf(summedScore);
	}
	
	public static String computeWithSpare(int index, List<String[]> completeScore) {
		if (index == 4) {

			var current = completeScore.get(index);
			var currentLength = current.length;

			var summedScore = IntStream
					.range(1, 3)
					.map(i -> determineScore(current, currentLength - i))
					.sum();

			return String.valueOf(15 + summedScore);
		} else {
			return String.valueOf(15 + Integer.parseInt(computeNormally(index + 1, completeScore, true, false)));
		}
	}
	
	public static String computeWithStrike(int index, List<String[]> completeScore) {
		if (index == 4) {

			var current = completeScore.get(index);

			var summedScore = IntStream
					.range(1, 4)
					.map(i -> determineScore(current, i))
					.sum();


			return String.valueOf(15 + summedScore);
		} else {
			return String.valueOf(15 + Integer.parseInt(computeNormally(index + 1, completeScore, false, true)));
		}
	}

	private static int determineScore(String[] current, int index) {
		int score;
		if (current[index].equals("X")) {
			score = 15;
		} else if (current[index].equals("-")) {
			score = 0;
		} else {
			score = Integer.parseInt(current[index]);
		}
		return score;
	}
	
}
