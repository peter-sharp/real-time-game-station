/**
PlayerSet.java

Keeps track of who's turn it is.

Paul Chudley, 22/4/2002
University of Canterbury
*/ 

import java.awt.*;

class PlayerSet 
{
	public static final int NOT_PLAYING = 0;

	private int numOfPlayers = 0;
	private Player[] players;
	private int currentPlayer = -1;

	public PlayerSet(int howMany)
	{
		//set array bounds and create player array
		numOfPlayers = howMany;
		if(numOfPlayers < 0)
			numOfPlayers = 0;  //don't accept negative player count.
		players = new Player[numOfPlayers];

		//create each player.
		for(int i = 0; i < numOfPlayers; i++)
		{
			players[i] = new Player(i+1);
		}

		//set current player to initial state
		reset();
	}

	public int getNumOfPlayers()
	{
		return numOfPlayers;
	}

	public void setComputerPlayer(int playerNumber, boolean computerPlayer)
	{
		if (playerNumber > 0 && playerNumber <= numOfPlayers)
		{
			players[playerNumber - 1].isComputer = computerPlayer;
		}
	}

	public void setPlayerName(int playerNumber, String name)
	{
		if (playerNumber > 0 && playerNumber <= numOfPlayers)
		{
			players[playerNumber-1].playerName = name;
		}
	}

	public void setPlayerColor(int playerNumber, Color color)
	{
		if (playerNumber > 0 && playerNumber <= numOfPlayers)
		{
			players[playerNumber-1].playerColor = color;
		}
	}

	public boolean computerIsPlaying()
	{
		for(int i = 0; i < numOfPlayers; i++)
		{
			if(players[i].isComputer)
				return true; //At least one of the players is the computer
		}
		return false; //computer is not one of the players
	}
	
	public boolean isComputersTurn()
	{
		if(currentPlayer < 0 || currentPlayer >= numOfPlayers)
			return false; // it's no-one's turn. error.
		if(players[currentPlayer].isComputer)
			return true; //it's computer's turn
		return false; //it's not computer's turn
	}

	public Player getWhosTurn()
	{	
		if(currentPlayer < 0 || currentPlayer >= numOfPlayers)
			return null;
		return players[currentPlayer];
	}

	public void nextPlayer()
	{
		currentPlayer++; //increment to next player
		if(currentPlayer >= numOfPlayers) // in a circular manner
			currentPlayer -= numOfPlayers; //(ie. mod numOfPlayers) 
	}

	public void reset()
	{
		//If there are any players
		if(numOfPlayers > 0)
			currentPlayer = 0; //start with first player
		else
			currentPlayer = -1; //not valid array index
	}
}
