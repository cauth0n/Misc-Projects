package Game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
}
