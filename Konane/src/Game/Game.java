package Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Game {

	/**
	 * Strings declared as final because they will be assigned to Tile spots.
	 */
	protected final String black = "Black";
	protected final String white = "White";
	protected final String empty = "Empty";
	
	private Player p1;
	private Player p2;
	
	private int boardSize;
	private Tile[][] gameBoard;
	
	
	/*
	 * Constructor
	 */
	public Game(int boardSize){
		this.boardSize = boardSize;
		gameBoard = new Tile[boardSize][boardSize];
		init();
	}
	
	/*
	 * Initializer, builds all game information needed
	 */
	public void init(){
		int[] initialMove;
		setUpPlayers();
		initializeGame();
		initialMove = firstMove();
		secondMove(initialMove);
		printBoard();
		loopManager();
	}
	
	/*
	 * Checks if second move is valid -- that is, it is adjacent to the first move.
	 * 
	 * Assuming board is even and square.\
	 * 
	 * initialMove has format: [0] = row,  [1] = column
	 */
	public boolean checkIfValidSecondMove(int rowChoice, int colChoice, int[] initialMove){
		if (initialMove[0] == 0){	//upper left
			if (rowChoice == 1 && colChoice == 0){
				return true;
			}else if (rowChoice == 0 && colChoice == 1){
				return true;
			}else{
				return false;
			}
		}else if (initialMove[0] == (boardSize - 1)){		//lower right
			if (rowChoice == (boardSize - 1) && colChoice == (boardSize - 2)){
				return true;
			}else if (rowChoice == (boardSize - 2) && colChoice == (boardSize - 1)){
				return true;
			}else{
				return false;
			}
		}else{				//middle
			if (rowChoice == initialMove[0]){		//same row
				if (colChoice == (initialMove[1] + 1)){		// right
					return true;
				}else if (colChoice == (initialMove[1] - 1)){	//left
					return true;
				}else{
					return false;
				}
			}else if (colChoice == initialMove[1]){		//same col	
				if (rowChoice == (initialMove[0] + 1)){		//below
					return true;
				}else if (rowChoice == (initialMove[0] - 1)){		//above
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
	}
	
	/*
	 * Second move of game. Must be white, and adjacent to the first move.
	 */
	public void secondMove(int[] initialMove){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		boolean isSecondMoveValid = false;
		String input;
		int rowChoice;
		int colChoice;
		try{
			while (!isSecondMoveValid){
				System.out.println("Player 2, your turn. You will enter a row and column adjacent to Player 1's move, that will remove your piece.");
				System.out.println("Here is the board:");
				printBoard();
				System.out.println("Enter a row adjacent to the empty space.");
				input = in.readLine();
				rowChoice = Integer.parseInt(input);
				System.out.println("Enter a column adjacent to the empty space.");
				input = in.readLine();
				colChoice = Integer.parseInt(input);
				if (checkIfColorOfPieceIsPresent(rowChoice, colChoice, white)){
					if (checkIfValidSecondMove(rowChoice, colChoice, initialMove)){
						gameBoard[rowChoice][colChoice].setColorValue(empty);
						isSecondMoveValid = true;
					}else{
						System.out.println("Please select a correct spot. Must be adjacent to the empty spot.");
					}
				}else{
					System.out.println("Select a spot with your color");
					continue;
				}
			}
		}catch(Exception e){
			System.out.println("Error. You entered a bad spot. Start over on this move.");
			secondMove(initialMove);
		}
	}
	
	/*
	 * Simply show player the board.
	 */
	public void printMove(String player){
		System.out.format("Your move, %s./n The board looks like this:/n/n", player);
		printBoard();
	}
	
	/*
	 * First Move. This has a unique moveset.
	 */
	public int[] firstMove(){
		boolean validChoice = false;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input;
		int rowChoice = -1;
		int colChoice = -1;
		int[] firstMove = new int[2];		//array that represents row (element 0) and col (element 1)
		try{
			System.out.println("Player 1, you need to remove a piece to start it off!");
			printBoard();
			while (!validChoice){
				System.out.println("Player 1, enter the row of your choice.");
				input = in.readLine();
				rowChoice = Integer.parseInt(input);
				System.out.println("Player 1, enter the column of your choice");
				input = in.readLine();
				colChoice = Integer.parseInt(input);
				validChoice = checkIfColorOfPieceIsPresent(rowChoice, colChoice, black);
				if (!validChoice){
					System.out.println("Your piece does not exist there. Try Again");
					continue;
				}else{
					validChoice = checkIfValidStartingMove(rowChoice, colChoice);
					if (!validChoice){
						System.out.println("Your piece must be in the corners, or the middle. Try Again");
					}else{
						validChoice = true;
					}
				}
			}			
		}catch(Exception e){
			System.out.println("Error. You entered a bad spot. Start over on this move.");
			firstMove();
		}
		gameBoard[rowChoice][colChoice].setColorValue(empty);
		firstMove[0] = rowChoice;
		firstMove[1] = colChoice;
		return (firstMove);
	}
	
	/*
	 * Checks if a starting move is in the 4 corners of the board, or the 4 middle corners.
	 * 
	 * The assignment asks for only even squares.. If time persists, will put in logic to deal with odd squares.
	 */
	public boolean checkIfValidStartingMove(int rowChoice, int colChoice){
		int middleOfPuzzle = boardSize / 2;
		if (rowChoice == colChoice){
			if (rowChoice == middleOfPuzzle || rowChoice == (middleOfPuzzle - 1)){	//rowChoice = colChoice already.
				return true;
			}else if ((rowChoice == 0) || (rowChoice == boardSize - 1)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	/*
	 * Checks if the color specified is present at that location on the gameboard.
	 */
	public boolean checkIfColorOfPieceIsPresent(int rowChoice, int colChoice, String color){
		if (gameBoard[rowChoice][colChoice].getColorValue().equals(color)){
			return true;
		}else{
			return false;
		}
	}
	
	 /* 
	 * Main Loop Manager of the Game.
	 * 
	 * P1 is offered a turn, and after that turn, the board is checked to see if it is a win.
	 * Then, if no win happened, P2 is offered a turn. Then, the board is checked again.
	 */
	public void loopManager(){
		boolean isGameFinished = false;
		while (!isGameFinished){
			//logic.
			break;
		}
	}
	
	/*
	 * Prints menu for new players to choose from.
	 */
	public void printPlayerMenu(){
		System.out.println("1: Human Player");
		System.out.println("2: MiniMax with alpha/beta pruning");
		System.out.println("3: MiniMax without alpah/beta pruning");
	}
	
	/*
	 * Builds a player at runtime, based on user choice.
	 * Returns a boolean representing the validity of the choices.
	 * 
	 * Bad choices mean go back to chooser again.
	 * 
	 * color means black or white -- first player is black. second player is white.
	 */
	public boolean playerBuilder(Player player, int playerChoice, String color){
		switch(playerChoice){
			case 1: player = new Human(color);return true;
			case 2: player = new MiniMaxWithAB(color);return true;
			case 3: player = new MiniMaxWithoutAB(color);return true;
			default: return false;
		}
	}
	
	/*
	 * Sets up 2 players, querying them for info until 2 players of the 3 player choices are met
	 */
	public void setUpPlayers(){
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input;
		boolean player1SetUp = false;
		boolean player2SetUp = false;
		int choice;
		try{
			while (!player1SetUp){
				System.out.println("Select player 1 -- PLAYER 1 WILL BE BLACK AND GO FIRST");
				printPlayerMenu();
				input = in.readLine();
				choice = Integer.parseInt(input);
				if (playerBuilder(p1, choice, black)){
					player1SetUp = true;
				}else{
					System.out.println("Invalid selection. Please try again.");
				}
			}
			while (!player2SetUp){
				System.out.println("Select player 2 -- PLAYER 2 WILL BE WHITE AND GO SECOND");
				printPlayerMenu();
				input = in.readLine();
				choice = Integer.parseInt(input);
				if (playerBuilder(p1, choice, white)){
					player2SetUp = true;
				}else{
					System.out.println("Invalid selection. Please try again.");
				}
			}
		}catch(Exception e){
			System.out.println("Input Error setting up Players.");
			System.exit(0);
		}
	}

	/*
	 * Initialize Game -- Black has a piece in the Upper left hand corner, and every other spot thereafter.
	 * 
	 * Logic below based on the fact that in a square array, every other spot is either an even number, or odd.
	 */
	public void initializeGame(){
		for (int i = 0; i < boardSize; i++){
			for (int j = 0; j < boardSize; j++){
				if ((i + j) % 2 == 0){
					gameBoard[i][j] = new Tile(black);
				}else{
					gameBoard[i][j] = new Tile(white);
				}
			}
		}
	}

	/*
	 * Returns a dashed line the same size as the game board.
	 * 
	 * Used for printing lines after every row.
	 */
	public String printBoardLine(){
		String line = "";
		for (int i = 0; i < ((boardSize * 2) + 1); i++){
			line += "-";
		}
		return line;
	}
	
	/*
	 * Print the game board. Prints by iterating through entire array, checking if black or white, and printing
	 */
	public void printBoard(){
		System.out.println(printBoardLine());
		for (int i = 0; i < boardSize; i++){
			for (int j = 0; j < boardSize; j++){
				System.out.print("|");
				if (gameBoard[i][j].getColorValue().equals(black)){
					System.out.print("b");
				}else if (gameBoard[i][j].getColorValue().equals(white)){
					System.out.print("w");
				}else{
					System.out.print(" ");
				}
			}
			System.out.println("|");
			System.out.println(printBoardLine());
		}
	}
}
