package main.game;

import javax.swing.JOptionPane;

public class MineSweeper
{
	public static void main(String args[])
	{
		String[] buttons = { "Easy", "Medium", "Hard" };
		
		int rc = JOptionPane.showOptionDialog(null,
				"Please Select A Difficulty", "MineSweeper: Johan Terblanche",
				-1, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[1]);
		@SuppressWarnings("unused")
		Tile loader = new Tile();
		if (rc == -1)
			System.exit(0);
		if (rc == 0)
			new Board(10, 15);
		if (rc == 1)
			new Board(15, 40);
		if (rc == 2)
			new Board(17, 55);
	}
}