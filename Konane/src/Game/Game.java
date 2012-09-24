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
		setUpPlayers();
		initializeGame();
		secondMove(firstMove());
		printBoard();
		loopManager();
	}

	/**
	 * Main Loop Manager of the Game.
	 * 
	 * P1 is offered a turn, and after that turn, the board is checked to see if it is a win. Then,
	 * if no win happened, P2 is offered a turn. Then, the board is checked again.
	 */
	public void loopManager() {
		boolean isGameFinished = false;
		while (!isGameFinished) {
			Move p1ValueToUpdate = new Move(null, null);
			Move p2ValueToUpdate = new Move(null, null);
			printMove("p1");
			p1ValueToUpdate = p1.makeAMove();
			while (!isValidMove(p1ValueToUpdate, black)) {
				System.out.println("Wrong input, p1. Try Again.");
				printMove("p1");
				p1ValueToUpdate = p1.makeAMove();
			}
			applyUpdate(p1ValueToUpdate, black);
			if (!checkIfMovesLeft(white)) {
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
			if (!checkIfMovesLeft(black)) {
				// p2 wins
				winner = p2;
				isGameFinished = true;
			}
		}
	}

	/**
	 * Checks if there are any moves left. Uses checks on above, below, right, or left of a piece to
	 * see if any more moves exist.
	 * 
	 * @param color
	 *            color of piece in danger of losing.
	 * 
	 * @return true if moves exist, false if none exist.
	 */
	public boolean checkIfMovesLeft(String color) {
		boolean moveRight = false, moveLeft = false, moveUp = false, moveDown = false;
		// check if any black moves left
		if (color.equals(black)) { // p2 made a move, now p1 is at risk of losing

			for (int i = 0; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {
					if (gameBoard[i][j].getColorValue().equals(black)) {
						moveLeft = isLeftMove(i, j);
						moveRight = isRightMove(i, j);
						moveUp = isUpMove(i, j);
						moveDown = isDownMove(i, j);
						if (moveLeft || moveRight || moveUp || moveDown) {
							return true;
						}
					}
				}
			}
			return false;

			// check if any white moves left
		} else { // p1 made a move, now p2 is at risk of losing
			for (int i = 0; i < boardSize; i++) {
				for (int j = 0; j < boardSize; j++) {
					if (gameBoard[i][j].getColorValue().equals(white)) {
						moveLeft = isLeftMove(i, j);
						moveRight = isRightMove(i, j);
						moveUp = isUpMove(i, j);
						moveDown = isDownMove(i, j);
						if (moveLeft || moveRight || moveUp || moveDown) {
							return true;
						}
					}
				}
			}
			return false;
		}
	}

	/**
	 * Checks if a move exists left of the input row/col pair
	 * 
	 * @param row
	 *            Row to check if moves exist
	 * @param column
	 *            Column to check if moves exist
	 * 
	 * @return false if no moves, true if moves.
	 */
	public boolean isLeftMove(int row, int column) {
		if (column == 0 || column == 1) {	// can't check left with that few spots
			return false;
		} else {
			if (gameBoard[row][column - 1].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameBoard[row][column - 2].getColorValue().equals(empty)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * Checks if a move exists right of the input row/col pair
	 * 
	 * @param row
	 *            Row to check if moves exist
	 * @param column
	 *            Column to check if moves exist
	 * 
	 * @return false if no moves, true if moves.
	 */
	public boolean isRightMove(int row, int column) {
		if (column == (boardSize - 1) || column == (boardSize - 2)) {	// can't check right with that
																		// few spots
			return false;
		} else {
			if (gameBoard[row][column + 1].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameBoard[row][column + 2].getColorValue().equals(empty)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * Checks if a move exists above the input row/col pair
	 * 
	 * @param row
	 *            Row to check if moves exist
	 * @param column
	 *            Column to check if moves exist
	 * 
	 * @return false if no moves, true if moves.
	 */
	public boolean isUpMove(int row, int column) {
		if (row == 0 || row == 1) {	// can't check up with that few spots
			return false;
		} else {
			if (gameBoard[row - 1][column].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameBoard[row - 2][column].getColorValue().equals(empty)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	/**
	 * Checks if a move exists below the input row/col pair
	 * 
	 * @param row
	 *            Row to check if moves exist
	 * @param column
	 *            Column to check if moves exist
	 * 
	 * @return false if no moves, true if moves.
	 */
	public boolean isDownMove(int row, int column) {
		if (row == (boardSize - 1) || row == (boardSize - 2)) {	// can't check up with that few
																// spots
			return false;
		} else {
			if (gameBoard[row + 1][column].getColorValue().equals(empty)) {
				return false;
			} else {
				if (gameBoard[row + 2][column].getColorValue().equals(empty)) {
					return true;
				} else {
					return false;
				}
			}
		}
	}


	/**
	 * Recursive method for checking moves until legal. If illegal, will immediately break
	 * 
	 * 
	 * @param move
	 *            a copy of the last move, used for grabbing unassociated values
	 * @param direction
	 *            direction of moves to check in (horizontal or vertical)
	 * @param movement
	 *            movement of last move (left, right, up, down)
	 * @param currentPlacement
	 *            current spot of piece as it jumps
	 * @param finalSpot
	 *            ending spot. Needed for generalized base case (it covers rows and columns)
	 * @return //recursive, but true if all jumps were legal
	 */
	public boolean isJumpingLegal(Move move, String direction, String movement,
			int currentPlacement, int finalSpot) {

		if (currentPlacement == finalSpot) {
			return true;
		} else {
			if (direction.equals("horizontal")) {
				if (movement.equals("left")) {
					if (gameBoard[move.getRowFrom()][currentPlacement - 1]
							.getColorValue().equals(empty)) {
						return false;
					} else {
						return isJumpingLegal(move, direction, movement,
								currentPlacement - 2, finalSpot);
					}
				} else {
					if (gameBoard[move.getRowFrom()][currentPlacement + 1]
							.getColorValue().equals(empty)) {
						return false;
					} else {
						return isJumpingLegal(move, direction, movement,
								currentPlacement + 2, finalSpot);
					}
				}
			} else {
				if (movement.equals("up")) {
					if (gameBoard[currentPlacement - 1][move.getColFrom()]
							.getColorValue().equals(empty)) {
						return false;
					} else {
						return isJumpingLegal(move, direction, movement,
								currentPlacement - 2, finalSpot);
					}
				} else {
					if (gameBoard[currentPlacement + 1][move.getColFrom()]
							.getColorValue().equals(empty)) {
						return false;
					} else {
						return isJumpingLegal(move, direction, movement,
								currentPlacement + 2, finalSpot);
					}
				}
			}

		}
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
		// I need to check multiple jumps,
		try {
			if (!gameBoard[move.getRowFrom()][move.getColFrom()].getColorValue().equals(color)){
				return false;
			}
			if (!gameBoard[move.getRowTo()][move.getColTo()].getColorValue()
					.equals(empty)) {	// a non-empty space we are jumping to
				return false;
			}
			if ((((int) Math.abs(move.getColFrom() - move.getColTo())) % 2 != 0)
					|| ((int) Math.abs(move.getRowFrom() - move.getRowTo())) % 2 != 0) {
				// check if is an illegal square we land on
				return false;
			}

			if (isMoveHorizontalOrVertical(move).equals("horizontal")) {

				if (move.getColFrom() > move.getColTo()) {	// move is left
					if (move.getColFrom() < 2 || move.getColTo() < 0) {	// boundary check
						return false;
					}
					return isJumpingLegal(move, "horizontal", "left",
							move.getColFrom(), move.getColTo());

				} else if (move.getColFrom() < move.getColTo()) {					// move is right
					if (move.getColFrom() > (boardSize - 2)
							|| move.getColTo() > boardSize) {	// boundary check
						return false;
					}
					return isJumpingLegal(move, "horizontal", "right",
							move.getColFrom(), move.getColTo());

				} else {	// same move spot
					return false;
				}

			} else if (isMoveHorizontalOrVertical(move).equals("vertical")) {// vertical

				if (move.getRowFrom() > move.getRowTo()) {	// move is up
					if (move.getRowFrom() < 2 || move.getRowTo() < 0) {	// boundary check
						return false;
					}
					return isJumpingLegal(move, "vertical", "up",
							move.getRowFrom(), move.getRowTo());

				} else if (move.getRowFrom() < move.getRowTo()) {					// move is right
					if (move.getRowFrom() > (boardSize - 2)
							|| move.getRowTo() > boardSize) {	// boundary check
						return false;
					}
					return isJumpingLegal(move, "vertical", "down",
							move.getRowFrom(), move.getRowTo());

				} else {	// same move spot
					return false;
				}

			} else {// error
				return false;
			}
		} catch (NullPointerException e) {
			return false;
		}
	}

	/**
	 * 
	 * 
	 * ALERT ALERT ALERT --- Not sure if a piece is removed or not after it is done jumping. I know
	 * the pieces any piece jumps over are removed. But is the piece that does the jumping removed?
	 * The only change would be the last variable. [move.getRowTo()][move.getColTo()]
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

		if (isMoveHorizontalOrVertical(move).equals("horizontal")) { // move made across rows
			iterator = move.getColFrom();

			while (iterator > move.getColTo()) {
				gameBoard[move.getRowFrom()][iterator].setColorValue(empty);
				iterator--;
			}

			while (iterator < move.getColTo()) {
				gameBoard[move.getRowFrom()][iterator].setColorValue(empty);
				iterator++;
			}

		} else {
			iterator = move.getRowFrom();

			while (iterator > move.getRowTo()) {
				gameBoard[iterator][move.getColFrom()].setColorValue(empty);
				iterator--;
			}

			while (iterator < move.getRowTo()) {
				gameBoard[iterator][move.getColFrom()].setColorValue(empty);
				iterator++;

			}
		}
		gameBoard[move.getRowTo()][move.getColTo()].setColorValue(color);
		p1.updateStoredGameBoard(gameBoard);
		p2.updateStoredGameBoard(gameBoard);
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
	 * Checks if second move is valid -- that is, it is adjacent to the first move.
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
	public boolean isValidSecondMove(Move initialMove, Move secondMove) {
		int initialRow = initialMove.getSelectionMoveRow();
		int initialCol = initialMove.getSelectionMoveCol();
		int secondRow = secondMove.getSelectionMoveRow();
		int secondCol = secondMove.getSelectionMoveCol();

		if (initialRow == 0) { // upper left
			if (secondRow == 1 && secondCol == 0) {
				return true;
			} else if (secondRow == 0 && secondCol == 1) {
				return true;
			} else {
				return false;
			}
		} else if (initialRow == (boardSize - 1)) { // lower right
			if (secondRow == (boardSize - 1) && secondCol == (boardSize - 2)) {
				return true;
			} else if (secondRow == (boardSize - 2)
					&& secondCol == (boardSize - 1)) {
				return true;
			} else {
				return false;
			}
		} else { // middle
			if (secondRow == initialRow) { // same row
				if (secondCol == (initialCol + 1)) { // right
					return true;
				} else if (secondCol == (initialCol - 1)) { // left
					return true;
				} else {
					return false;
				}
			} else if (secondCol == initialCol) { // same col
				if (secondRow == (initialRow + 1)) { // below
					return true;
				} else if (secondRow == (initialRow - 1)) { // above
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
	public void secondMove(Move initialMove) {
		p2.updateStoredGameBoard(gameBoard);
		Move secondMove = p2.secondMove();
		while (!isValidSecondMove(initialMove, secondMove)) {
			System.out.println("Wrong second move! Try again p2!");
			secondMove = p2.secondMove();
		}
		gameBoard[secondMove.getSelectionMoveRow()][secondMove
				.getSelectionMoveCol()].setColorValue(empty);
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
	public Move firstMove() {
		p1.updateStoredGameBoard(gameBoard);
		Move firstMove = p1.firstMove();
		while (!isValidStartingMove(firstMove)) {
			System.out.println("Bad starting move. Try again P1.");
			firstMove = p1.firstMove();
		}

		gameBoard[firstMove.getSelectionMoveRow()][firstMove
				.getSelectionMoveCol()].setColorValue(empty);
		return (firstMove);
	}

	/**
	 * Checks if a starting move is in the 4 corners of the board, or the 4 middle corners.
	 * 
	 * The assignment asks for only even squares.. If time persists, will put in logic to deal with
	 * odd squares.
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
	public boolean isValidStartingMove(Move move) {
		int rowChoice = move.getSelectionMoveRow();
		int colChoice = move.getSelectionMoveCol();

		int middleOfPuzzle = boardSize / 2;
		if (rowChoice == colChoice) {
			if (rowChoice == middleOfPuzzle
					|| rowChoice == (middleOfPuzzle - 1)) { // rowChoice = colChoice already
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
	 * Prints menu for new players to choose from.
	 */
	public void printPlayerMenu() {
		System.out.println("1: Human Player");
		System.out.println("2: MiniMax with alpha/beta pruning");
		System.out.println("3: MiniMax without alpah/beta pruning");
	}

	/**
	 * Builds a player at runtime, based on user choice. Returns a boolean representing the validity
	 * of the choices.
	 * 
	 * Bad choices mean go back to chooser again.
	 * 
	 * color means black or white -- first player is black. second player is white.
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
	 * Sets up 2 players, querying them for info until 2 players of the 3 player choices are met
	 */
	public void setUpPlayers() {
		p1 = null;
		p2 = null;
		int choice;
		Scanner scan = new Scanner(System.in);
		while (p1 == null) {
			System.out
					.println("Select player 1 -- PLAYER 1 WILL BE BLACK AND GO FIRST");
			printPlayerMenu();
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
			choice = scan.nextInt();

			p2 = playerBuilder(choice, white);
			if (p2 == null) {
				System.out.println("Invalid selection. Please try again.");
			}
		}
	}

	/**
	 * Initialize Game -- Black has a piece in the Upper left hand corner, and every other spot
	 * thereafter.
	 * 
	 * Logic below based on the fact that in a square array, every other spot is either an even
	 * number, or odd.
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
		String line = " ";
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

	/**
	 * gets a copy of the current gameboard. Players use this as a copy for them to work on.
	 * 
	 * @return current gameboard.
	 */
	public Tile[][] getGameBoard() {
		return gameBoard;
	}

}
