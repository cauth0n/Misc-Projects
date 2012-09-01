package Game;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Main {
		
	/**
	 * Read input from a BufferedReader, and only start a game if boardSize is 4, 6, or 8.
	 * 
	 * Loops until correct input, or exits if input is not int
	 */
	public static void main(String[] args) {
		int boardSize = 0;
		boolean validInput = false;
		try{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			while (!validInput){
				System.out.println("Enter board size (square size)");
				String text = in.readLine();
				boardSize = Integer.parseInt(text);
				if (boardSize == 4 || boardSize == 6 || boardSize == 8)
				{
					validInput = true;	
				}else{
					System.out.println("Wrong Input");
					validInput = false;
				}
			}
		}catch(Exception e){
			
			System.out.println("Wrong Input, enter an Integer");
			System.exit(0);
		}
		
		
		
		Game driver = new Game(boardSize);			//initialize game

	}

}
