package Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

	/**
	 * Initializes Game from main. Uses an instance of Game called driver to start it all off!
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int boardSize = 0;
		boolean validInput = false;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

			while (!validInput) {
				System.out.println("Enter board size (square size)");
				String text = in.readLine();
				boardSize = Integer.parseInt(text);
				if (boardSize == 4 || boardSize == 6 || boardSize == 8) {
					validInput = true;
				} else {
					System.out.println("Wrong Input");
					validInput = false;
				}
			}
		} catch (Exception e) {

			System.out.println("Wrong Input, enter an Integer");
			System.exit(0);
		}

		int p1Wins = 0;
		int p2Wins = 0;
		
		long p1AvgTime = 0;
		long p2AvgTime = 0;
		long p1MinTime = Integer.MAX_VALUE;
		long p1MaxTime = Integer.MIN_VALUE;
		long p2MinTime = Integer.MAX_VALUE;
		long p2MaxTime = Integer.MIN_VALUE;

		for (int i = 0; i < 10000; i++) {
			Game driver = new Game(boardSize, 2, 2, 2, 2);			// initialize game
			System.out.println("finished with " + i);
			if (driver.getWinner()) {
				p1Wins++;
			} else {
				p2Wins++;
			}
			if (driver.getMinp1TimePerMove() < p1MinTime){
				p1MinTime = driver.getMinp1TimePerMove();
				System.out.println(p1MinTime);
			}
			if (driver.getMaxp1TimePerMove() > p1MaxTime){
				p1MaxTime = driver.getMaxp1TimePerMove();
			}
			if (driver.getMinp2TimePerMove() < p2MinTime){
				p2MinTime = driver.getMinp2TimePerMove();
			}
			if (driver.getMinp1TimePerMove() > p1MinTime){
				p1MinTime = driver.getMinp1TimePerMove();
			}
			p1AvgTime += driver.getP1TimePerMove();
			p2AvgTime += driver.getP2TimePerMove();
			System.out.println("Player 1's shortest move was: " + p1MinTime);
			System.out.println("Player 1's longest move was: " + p1MaxTime);
		}
		System.out.println("Player 1 had: " + p1Wins);
		System.out.println("Player 2 had: " + p2Wins);
		System.out.println("Player 1's shortest move was: " + p1MinTime);
		System.out.println("Player 1's longest move was: " + p1MaxTime);
		System.out.println("Player 1's average move time was: " + p1AvgTime/10000);
		System.out.println("Player 2's shortest move was: " + p2MinTime);
		System.out.println("Player 2's longest move was: " + p2MaxTime);
		System.out.println("Player 2's average move time was: " + p2AvgTime/10000);

		// Game driver = new Game(boardSize); // initialize game

	}

}
