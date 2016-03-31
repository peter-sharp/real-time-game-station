/**
Player.java

Groups all player information into a single object

Paul Chudley, 22/4/2002
University of Canterbury
*/

import java.awt.*;

public class Player 
{
	boolean isComputer = false;
	public int playerNumber = 0;
	public String playerName = null;
	public Color playerColor = null;

	public Player(int number)
	{
		isComputer = false;
		playerNumber = number;
		playerName = null;
		playerColor = null;
	}
	
	public Player(int number, String name, Color color, boolean computerPlayer)
	{
		isComputer = computerPlayer;
		playerNumber = number;
		playerName = name;
		playerColor = color;
	}
}
