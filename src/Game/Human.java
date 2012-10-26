package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class Human extends Player {

	public Human(String colorValue) {
		super(colorValue);
		playerType = "Human";
	}

	@Override
	public String getPlayerType() {
		return playerType;
	}

	@Override
	public String getColorValue() {
		return colorValue;
	}

	@Override
	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}

	@Override
	public Move makeAMove() {
		Scanner input = new Scanner(System.in);
		int[] originals = new int[2];
		int[] finals = new int[2];
		System.out.println("Enter the row of the piece you want to move.");
		originals[0] = input.nextInt();
		System.out.println("Enter the column of the piece you want to move.");
		originals[1] = input.nextInt();
		System.out.println("Enter the row you want to move to.");
		finals[0] = input.nextInt();
		System.out.println("Enter the column to move.");
		finals[1] = input.nextInt();
		Move move = new Move(copyOfGameBoard[originals[0]][originals[1]], copyOfGameBoard[finals[0]][finals[1]]);
		return move;
	}

	@Override
	public Move firstMove(ArrayList<Move> setOfAllStartingMoves) {
		int[] rawFirstMove = new int[2];
		Scanner scan = new Scanner(System.in);
		System.out.println("Player 1, you need to remove a piece to start it off!");
		printBoard();
		System.out.println("Player 1, enter the row of your choice.");
		rawFirstMove[0] = scan.nextInt();
		System.out.println("Player 1, enter the column of your choice");
		rawFirstMove[1] = scan.nextInt();
		Move firstMove = new Move(copyOfGameBoard[rawFirstMove[0]][rawFirstMove[1]]);
		return firstMove;
	}

	@Override
	public Move secondMove(ArrayList<Move> setOfAllStartingMoves) {
		int[] rawSecondMove = new int[2];
		Scanner scan = new Scanner(System.in);
		System.out.println("Player 2, you need to remove a piece adjacent to Player 1's move!");
		printBoard();
		System.out.println("Player 2, enter the row of your choice.");
		rawSecondMove[0] = scan.nextInt();
		System.out.println("Player 2, enter the column of your choice");
		rawSecondMove[1] = scan.nextInt();
		Move firstMove = new Move(copyOfGameBoard[rawSecondMove[0]][rawSecondMove[1]]);
		return firstMove;
	}

	@Override
	public void updateStoredGameBoard(Tile[][] gameBoardCopy, ArrayList<Move> copyOfMoves, String color) {
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
	 * Print the game board. Prints by iterating through entire array, checking if black or white,
	 * and printing
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
