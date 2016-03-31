/**
Connect4PlayingFieldPanel.java

Graphical & mouse interface for Connect4PlayingField in Connect4 applet

Paul Chudley, 22/4/2002
University of Canterbury
*/

import java.awt.*;
import java.awt.event.*;


/**Visual interface for game. Ignorant of game rules, and board geometry.
   Indicates game status (via interface functions from Connect4PlayingField class).
   Handles player configuration (human / computer player numbers) 
   via a public choice box. This choice box, and the game status string
   should be displayed via the client class.*/
public class Connect4PlayingFieldPanel extends PlayingFieldPanel
{
	public Choice playerConfiguration;
	private int playerConfigIndex = 0;

	/** Sets up game user interface and begins the game */
	public Connect4PlayingFieldPanel()
	{
		//Create the game board.
		board = new Connect4PlayingField();

		//board remains hidden within this class, but allow
		// other classes to see the game status label, so it can be displayed.
		gameStatusText = board.gameStatusText;

		//Allows game board to respond to mouse
		addMouseListener(this);

		//Create the Choice box to configure human / computer players
		playerConfiguration = new Choice ();
		playerConfiguration.addItem("Player 1: Human, Player 2: Computer");
		playerConfiguration.addItem("Player 1: Computer, Player 2: Human");
		playerConfiguration.addItem("Player 1: Human, Player 2: Human");
		//Allow Choice box to respond to mouse
		playerConfiguration.addItemListener(new ItemListener()
		{
			//Update player configuration if user selects new configuration in choicebox.
			public void itemStateChanged(ItemEvent event)
			{
				//If player configuration has not changed, no need to reset game.
				//If it has changed, game should restart with new player config.
				if(playerConfigIndex != playerConfiguration.getSelectedIndex())
				{
					//remember previous state of player configuration choice box
					playerConfigIndex = playerConfiguration.getSelectedIndex();
				
					//Respond to user selection of player configuration
					updatePlayerConfig();
					startNewGame();
				}
			}
		});

		//Find which player has been selected as COMPUTER
		updatePlayerConfig();

		startNewGame();
	}
	
	/** Starts a new game*/
	private void startNewGame()
	{
		//remove all pieces from the board
		board.initialise();

		//Allow computer to make first move when selected
		while(board.thePlayers.isComputersTurn() && !board.gameIsOver())
			board.makeBestMove();

		//update the graphical display
		repaint();
	}

	/**Graphical User Interface Input function.
	  Attempts to make the move specified by the mouse screen position "mouseX"
	  by calling the interface function from the game board.*/
	private void makeMove(int mouseX)
	{
		int dontCare = 0;

		//call the function which knows the board geometry
		//and can determine which part of the board was clicked.
		board.makeMove(mouseDownX,dontCare,dontCare,dontCare,0,dontCare,getSize().width,dontCare);
	}

	protected void mousePressedAction()
	{
		//If game is over, a mousepress restarts game
		if(board.gameIsOver())
		{
			startNewGame();
			return; //because this click resets the game
			// and no moves should be accepted until next click
		}

		//Otherwise, if game not over, clicks on the board are used to play game
		// Find which column was clicked and make a move there if possible.
		makeMove(mouseDownX);

		//Allow computer to play if selected
		while(board.thePlayers.isComputersTurn() && !board.gameIsOver())
			board.makeBestMove();

		// Update the graphical display
		repaint();
	}

	/** Used to implement drag and drop events*/
	protected void mouseReleasedAction()
	{
		//Make a move described by the mouse movement between press and release
		//Not implemented in Connect 4, since only mouseDownX is required to play this.
		/*
		board.makeMove(mouseDownX,mouseDownY,mouseUpX,mouseUpY,0,0,getSize().width,getSize().height);
		*/
	}

	//Sets up the computer player (as player 1, 2, or not in game)
	//depending on the state of the playerConfiguration choice box.
	private void updatePlayerConfig()
	{
		switch(playerConfiguration.getSelectedIndex())
		{
			default:
			case 0:
				board.thePlayers.setComputerPlayer(1,false);// Player one is not the computer
				board.thePlayers.setComputerPlayer(2,true);// Player two is the computer
				break;
			case 1:
				board.thePlayers.setComputerPlayer(1,true);// Player one is the computer
				board.thePlayers.setComputerPlayer(2,false);// Player two is not the computer
				break;
			case 2:
				board.thePlayers.setComputerPlayer(1,false);// Player one is not the computer
				board.thePlayers.setComputerPlayer(2,false);// Player two is not the computer
				break;
		}
	}

}
