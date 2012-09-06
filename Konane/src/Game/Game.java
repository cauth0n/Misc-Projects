package Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Game {

	/**
	 * Strings declared as final because they will be assigned to Tile spots.
	 */
	protected final String black = "Black";
	protected final String white = "White";
	protected final String empty = "Empty";

	private Player p1;
	private Player p2;
	private Player winner;

	private int boardSize;
	private Tile[][] gameBoard;

	/**
	 * Constructor
	 * 
	 * 
	 * @param boardSize
	 *            Corresponds to size of the board playing on
	 */
	public Game(int boardSize) {
		this.boardSize = boardSize;
		gameBoard = new Tile[boardSize][boardSize];
		init();
	}

	/**
	 * Initializer, builds all game information needed
	 */
	public void init() {
		int[] initialMove;
		setUpPlayers();
		initializeGame();
		initialMove = firstMove();
		secondMove(initialMove);
		printBoard();
		loopManager();
	}

	/**
	 * Main Loop Manager of the Game.
	 * 
	 * P1 is offered a turn, and after that turn, the board is checked to see if
	 * it is a win. Then, if no win happened, P2 is offered a turn. Then, the
	 * board is checked again.
	 */
	public void loopManager() {
		boolean isGameFinished = false;
		while (!isGameFinished) {
			Move p1ValueToUpdate = new Move(null, null);
			Move p2ValueToUpdate = new Move(null, null);
			printMove("p1");
			System.out.println("p1: " + p1);
			p1ValueToUpdate = p1.makeAMove();
			while (!isValidMove(p1ValueToUpdate, black)) {
				System.out.println("Wrong input, p1. Try Again.");
				printMove("p1");
				p1ValueToUpdate = p1.makeAMove();
			}
			applyUpdate(p1ValueToUpdate, black);
			if (!checkIfMovesLeft()) {
				// p1 wins
				winner = p1;
				isGameFinished = true;
			}

			// p2 now
			printMove("p2");
			p2ValueToUpdate = p2.makeAMove();
			while (!isValidMove(p2ValueToUpdate, white)) {
				System.out.println("Wrong input, p2. Try Again.");
				printMove("p2");
				p1ValueToUpdate = p2.makeAMove();
			}
			applyUpdate(p2ValueToUpdate, white);
			if (!checkIfMovesLeft()) {
				// p2 wins
				winner = p2;
				isGameFinished = true;
			}
		}
	}

	/**
	 * Checks if any moves are left.
	 * 
	 * DO NOT localize it. Make sure it checks all the possible game board
	 * moves.
	 * 
	 * 
	 * @return true if more moves exist false if no more moves exist
	 */
	public boolean checkIfMovesLeft() {
		return true;
	}

	/**
	 * Checks if the input parameters constitute a valid move.
	 * 
	 * @param move
	 *            Last move made
	 * @param color
	 *            Color making the last move
	 * 
	 * 
	 * @return Boolean, depending on whether that move is allowed.
	 */
	public boolean isValidMove(Move move, String color) {
		return true;
	}

	/**
	 * 
	 * 
	 * ALERT ALERT ALERT --- Not sure if a piece is removed or not after it is
	 * done jumping. I know the pieces any piece jumps over are removed. But is
	 * the piece that does the jumping removed? The only change would be the
	 * last variable. [move.getRowTo()][move.getColTo()]
	 * 
	 * Assuming here that it is NOT removed. That it stays. ---
	 * 
	 * Applies update to global gameBoard. Move is already established as legal.
	 * 
	 * @param move
	 *            Last move made
	 * @param color
	 *            Color making the last move
	 */
	public void applyUpdate(Move move, String color) {
		int iterator;
		gameBoard[move.getRowFrom()][move.getColFrom()].setColorValue(empty);

		if (isMoveHorizontalOrVertical(move).equals("horizontal")) { // move
																		// made
																		// across
																		// rows
			iterator = move.getColFrom();
			if (iterator > move.getColTo()) {
				while (iterator > move.getColTo()) {
					gameBoard[move.getRowFrom()][iterator].setColorValue(empty);
					iterator--;
				}
			} else {
				while (iterator < move.getColTo()) {
					gameBoard[move.getRowFrom()][iterator].setColorValue(empty);
					iterator++;
				}
			}
		} else {
			iterator = move.getRowFrom();
			if (iterator > move.getRowTo()) {
				while (iterator > move.getRowTo()) {
					gameBoard[iterator][move.getColFrom()].setColorValue(empty);
					iterator--;
				}
			} else {
				while (iterator < move.getRowTo()) {
					gameBoard[iterator][move.getColFrom()].setColorValue(empty);
					iterator++;
				}
			}
		}
		gameBoard[move.getRowTo()][move.getRowTo()].setColorValue(color);
	}

	/**
	 * Checks to see if move made was horizontal or vertical (Can only be one).
	 * 
	 * 
	 * @param move
	 *            move we are checking for.
	 * 
	 * 
	 * @return horizontal if rows match vertical if columns match
	 */
	public String isMoveHorizontalOrVertical(Move move) {
		if (move.getRowFrom() == move.getRowTo()) {
			return "horizontal";
		} else if (move.getColFrom() == move.getColTo()) {
			return "vertical";
		} else {
			System.out
					.println("Error in isMoveHorizontalOrVertical method. Should be vert or horiz, nothing else.");
			return null;
		}
	}

	/**
	 * Checks if second move is valid -- that is, it is adjacent to the first
	 * move.
	 * 
	 * Assuming board is even and square.\
	 * 
	 * initialMove has format: [0] = row, [1] = column
	 * 
	 * 
	 * @param rowChoice
	 *            choice of row for second move
	 * @param colChoice
	 *            choice of column for second move
	 * @param initialMove
	 *            first move made by other player
	 * 
	 * @return true if valid move false if invalid move
	 */
	public boolean checkIfValidSecondMove(int rowChoice, int colChoice,
			int[] initialMove) {
		if (initialMove[0] == 0) { // upper left
			if (rowChoice == 1 && colChoice == 0) {
				return true;
			} else if (rowChoice == 0 && colChoice == 1) {
				return true;
			} else {
				return false;
			}
		} else if (initialMove[0] == (boardSize - 1)) { // lower right
			if (rowChoice == (boardSize - 1) && colChoice == (boardSize - 2)) {
				return true;
			} else if (rowChoice == (boardSize - 2)
					&& colChoice == (boardSize - 1)) {
				return true;
			} else {
				return false;
			}
		} else { // middle
			if (rowChoice == initialMove[0]) { // same row
				if (colChoice == (initialMove[1] + 1)) { // right
					return true;
				} else if (colChoice == (initialMove[1] - 1)) { // left
					return true;
				} else {
					return false;
				}
			} else if (colChoice == initialMove[1]) { // same col
				if (rowChoice == (initialMove[0] + 1)) { // below
					return true;
				} else if (rowChoice == (initialMove[0] - 1)) { // above
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	/**
	 * Second move of game. Must be white, and adjacent to the first move.
	 * 
	 * 
	 * @param initialMove
	 *            First move made by other player
	 */
	public void secondMove(int[] initialMove) {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		boolean isSecondMoveValid = false;
		String input;
		int rowChoice;
		int colChoice;
		try {
			while (!isSecondMoveValid) {
				System.out
						.println("Player 2, your turn. You will enter a row and column adjacent to Player 1's move, that will remove your piece.");
				System.out.println("Here is the board:");
				printBoard();
				System.out.println("Enter a row adjacent to the empty space.");
				input = in.readLine();
				rowChoice = Integer.parseInt(input);
				System.out
						.println("Enter a column adjacent to the empty space.");
				input = in.readLine();
				colChoice = Integer.parseInt(input);
				if (checkIfColorOfPieceIsPresent(rowChoice, colChoice, white)) {
					if (checkIfValidSecondMove(rowChoice, colChoice,
							initialMove)) {
						gameBoard[rowChoice][colChoice].setColorValue(empty);
						isSecondMoveValid = true;
					} else {
						System.out
								.println("Please select a correct spot. Must be adjacent to the empty spot.");
					}
				} else {
					System.out.println("Select a spot with your color");
					continue;
				}
			}
		} catch (Exception e) {
			System.out
					.println("Error. You entered a bad spot. Start over on this move.");
			secondMove(initialMove);
		}
	}

	/**
	 * Simply show player the board.
	 * 
	 * 
	 * @param player
	 *            Player that sees the board next
	 */
	public void printMove(String player) {
		System.out.format("Your move, %s.\nThe board looks like this:\n\n",
				player);
		printBoard();
	}

	/**
	 * First Move. This has a unique moveset.
	 * 
	 * 
	 * @return first move choice. Needed for validity of second move.
	 */
	public int[] firstMove() {
		boolean validChoice = false;
		String input;
		int rowChoice = -1;
		int colChoice = -1;
		int[] firstMove = new int[2]; // array that represents row (element 0)
										// and col (element 1)
		Scanner scan = new Scanner(System.in);
//		try (BufferedReader in = new BufferedReader(new InputStreamReader(
//				System.in))) {
			System.out
					.println("Player 1, you need to remove a piece to start it off!");
			printBoard();
			while (!validChoice) {
				System.out.println("Player 1, enter the row of your choice.");
//				input = in.readLine();
//				rowChoice = Integer.parseInt(input);
				rowChoice = scan.nextInt();
				System.out.println("Player 1, enter the column of your choice");
//				input = in.readLine();
//				colChoice = Integer.parseInt(input);
				colChoice = scan.nextInt();
				validChoice = checkIfColorOfPieceIsPresent(rowChoice,
						colChoice, black);
				if (!validChoice) {
					System.out
							.println("Your piece does not exist there. Try Again");
					continue;
				} else {
					validChoice = checkIfValidStartingMove(rowChoice, colChoice);
					if (!validChoice) {
						System.out
								.println("Your piece must be in the corners, or the middle. Try Again");
					} else {
						validChoice = true;
					}
				}
			}
//		} catch (Exception e) {
//			System.out
//					.println("Error. You entered a bad spot. Start over on this move.");
//			// firstMove();
//			e.printStackTrace();
//			System.exit(1);
//		}
		gameBoard[rowChoice][colChoice].setColorValue(empty);
		firstMove[0] = rowChoice;
		firstMove[1] = colChoice;
		return (firstMove);
	}

	/**
	 * Checks if a starting move is in the 4 corners of the board, or the 4
	 * middle corners.
	 * 
	 * The assignment asks for only even squares.. If time persists, will put in
	 * logic to deal with odd squares.
	 * 
	 * 
	 * @param rowChoice
	 *            choice of first row
	 * @param colChoice
	 *            choice of first column
	 * 
	 * 
	 * @return true if valid starting move false if invalid starting move
	 */
	public boolean checkIfValidStartingMove(int rowChoice, int colChoice) {
		int middleOfPuzzle = boardSize / 2;
		if (rowChoice == colChoice) {
			if (rowChoice == middleOfPuzzle
					|| rowChoice == (middleOfPuzzle - 1)) { // rowChoice =
															// colChoice
															// already.
				return true;
			} else if ((rowChoice == 0) || (rowChoice == boardSize - 1)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * Checks if the color specified is present at that location on the
	 * gameboard.
	 * 
	 * 
	 * @param rowChoice
	 *            row of piece to check the color of
	 * @param colChoice
	 *            column of piece to check the color of
	 * @param color
	 *            color of piece
	 * 
	 * 
	 * @return true if piece exists there false if piece is not there
	 */
	public boolean checkIfColorOfPieceIsPresent(int rowChoice, int colChoice,
			String color) {
		if (gameBoard[rowChoice][colChoice].getColorValue().equals(color)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Prints menu for new players to choose from.
	 */
	public void printPlayerMenu() {
		System.out.println("1: Human Player");
		System.out.println("2: MiniMax with alpha/beta pruning");
		System.out.println("3: MiniMax without alpah/beta pruning");
	}

	/**
	 * Builds a player at runtime, based on user choice. Returns a boolean
	 * representing the validity of the choices.
	 * 
	 * Bad choices mean go back to chooser again.
	 * 
	 * color means black or white -- first player is black. second player is
	 * white.
	 * 
	 * 
	 * @param player
	 *            Player object (p1 or p2) to chose from
	 * @param playerChoice
	 *            Human, minimax w/o ab pruning, or minimax with ab pruning.
	 * @param color
	 *            which color player will be
	 * 
	 * 
	 * @return true if valid choice is made false if invalid choice is made
	 */
	public Player playerBuilder(int playerChoice, String color) {
		Player retVal = null;

		switch (playerChoice) {
		case 1:
			retVal = new Human(color);
			break;
		case 2:
			retVal = new MiniMaxWithAB(color);
			break;
		case 3:
			retVal = new MiniMaxWithoutAB(color);
			break;
		}

		return retVal;
	}

	/**
	 * Sets up 2 players, querying them for info until 2 players of the 3 player
	 * choices are met
	 */
	public void setUpPlayers() {
		String input;
		p1 = null;
		p2 = null;
		// boolean player1SetUp = false;
		// boolean player2SetUp = false;
		int choice;
		Scanner scan = new Scanner(System.in);
//		try (BufferedReader in = new BufferedReader(new InputStreamReader(
//				System.in))) {
			while (p1 == null) {
				System.out
						.println("Select player 1 -- PLAYER 1 WILL BE BLACK AND GO FIRST");
				printPlayerMenu();
//				input = in.readLine();
//				choice = Integer.parseInt(input);
				choice = scan.nextInt();

				p1 = playerBuilder(choice, black);
				if (p1 == null) {
					System.out.println("Invalid selection. Please try again.");
				}
			}
			while (p2 == null) {
				System.out
						.println("Select player 2 -- PLAYER 2 WILL BE WHITE AND GO SECOND");
				printPlayerMenu();
//				input = in.readLine();
				choice = scan.nextInt();
//				choice = Integer.parseInt(input);

				p2 = playerBuilder(choice, white);
				if (p2 == null) {
					System.out.println("Invalid selection. Please try again.");
				}
			}
//		} catch (Exception e) {
//			System.out.println("Input Error setting up Players.");
//			System.exit(0);
//		}
	}

	/**
	 * Initialize Game -- Black has a piece in the Upper left hand corner, and
	 * every other spot thereafter.
	 * 
	 * Logic below based on the fact that in a square array, every other spot is
	 * either an even number, or odd.
	 */
	public void initializeGame() {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if ((i + j) % 2 == 0) {
					gameBoard[i][j] = new Tile(black);
				} else {
					gameBoard[i][j] = new Tile(white);
				}
			}
		}
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
		System.out.println(printBoardLine());
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				System.out.print("|");
				if (gameBoard[i][j].getColorValue().equals(black)) {
					System.out.print(" b ");
				} else if (gameBoard[i][j].getColorValue().equals(white)) {
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
