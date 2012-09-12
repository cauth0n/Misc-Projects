package Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Human extends Player {

	public Human(String colorValue) {
		super(colorValue);
		playerType = "Human";
	}

	@Override
	public String getPlayerType() {
		// TODO Auto-generated method stub
		return playerType;
	}

	@Override
	public String getColorValue() {
		// TODO Auto-generated method stub
		return colorValue;
	}

	@Override
	public void setColorValue(String colorValue) {
		// TODO Auto-generated method stub
		this.colorValue = colorValue;
	}

	@Override
	public Move makeAMove() {
		// TODO Auto-generated method stub
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			int[] originals = new int[2];
			int[] finals = new int[2];
			String line;
			System.out.println("Enter the row of the piece you want to move.");
			line = in.readLine();
			originals[0] = Integer.parseInt(line);
			System.out
					.println("Enter the column of the piece you want to move.");
			line = in.readLine();
			originals[1] = Integer.parseInt(line);

			System.out.println("Enter the row you want to move to.");
			line = in.readLine();
			finals[0] = Integer.parseInt(line);
			System.out.println("Enter the column to move.");
			line = in.readLine();
			finals[1] = Integer.parseInt(line);

			Move move = new Move(originals, finals);
			return move;
		} catch (Exception e) {
			System.out.println("Wrong input");
		}
		return null;
	}

	@Override
	public Move firstMove() {
		int[] rawFirstMove = new int[2];
		Scanner scan = new Scanner(System.in);

		System.out
				.println("Player 1, you need to remove a piece to start it off!");
		printBoard();

		System.out.println("Player 1, enter the row of your choice.");
		rawFirstMove[0] = scan.nextInt();
		System.out.println("Player 1, enter the column of your choice");
		rawFirstMove[1] = scan.nextInt();
		Move firstMove = new Move(rawFirstMove);
		return firstMove;
	}

	@Override
	public Move secondMove() {
		int[] rawSecondMove = new int[2];
		Scanner scan = new Scanner(System.in);

		System.out
				.println("Player 2, you need to remove a piece adjacent to Player 1's move!");
		printBoard();

		System.out.println("Player 2, enter the row of your choice.");
		rawSecondMove[0] = scan.nextInt();
		System.out.println("Player 2, enter the column of your choice");
		rawSecondMove[1] = scan.nextInt();
		Move firstMove = new Move(rawSecondMove);
		return firstMove;
	}

	@Override
	public void updateStoredGameBoard(Tile[][] gameBoardCopy) {
		copyOfGameBoard = gameBoardCopy;
	}

	/**
	 * Returns a dashed line the same size as the game board.
	 * 
	 * Used for printing lines after every row.
	 * 
	 * 
	 * @return formatted line representing length of board as dashes "--"
	 */
	public String printBoardLine() {
		int boardSize = copyOfGameBoard.length;
		String line = "";
		for (int i = 0; i < ((boardSize * 4) + 1); i++) {
			line += "-";
		}
		return line;
	}

	/**
	 * Print the game board. Prints by iterating through entire array, checking
	 * if black or white, and printing
	 */
	public void printBoard() {
		int boardSize = copyOfGameBoard.length;
		System.out.println(printBoardLine());
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				System.out.print("|");
				if (copyOfGameBoard[i][j].getColorValue().equals(black)) {
					System.out.print(" b ");
				} else if (copyOfGameBoard[i][j].getColorValue().equals(white)) {
					System.out.print(" w ");
				} else {
					System.out.print("   ");
				}
			}
			System.out.println("|");
			System.out.println(printBoardLine());
		}
	}
}
