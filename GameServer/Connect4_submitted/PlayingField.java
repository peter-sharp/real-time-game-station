/**
PlayingField.java

Abstract class that defines the interface to the game-specific game play class

Paul Chudley, 22/4/2002
University of Canterbury
*/

import java.awt.*;

public abstract class PlayingField
{
	public PlayerSet thePlayers;
	protected Player winner;

	//Error checking constants
	protected final static int OFF_FIELD = PlayingPiece.OFF_FIELD;

	//the game board (an array of cells which can hold a playing piece)
	protected PlayingPiece[][] fieldArray;

	//Label to hold game status text. Can be displayed by a client class.
	public Label gameStatusText;

	/**Sets up the game board ready for a new game*/
	public abstract void initialise();

//PUBLIC FUNCTIONS TO DEAL WITH THE GRAPHICAL INTERFACE:

	/**Graphical User Interface Input function.
	  Attempts to make the move specified by the drag and drop event
	  with specified start and end mouse co-ordinates within
	  the specified game size rectangle.*/
	public abstract void makeMove(int mouseDownX, int mouseDownY, int MouseUpX, int mouseUpY, int left, int top, int width, int height);

	/**Graphical User Interface Output function.
	 Draws the PlayingField in the specified rectangle.*/
	public abstract void paint(Graphics g, int width, int height);

// PUBLIC FUNCTIONS TO GET GAME STATUS:
	public abstract boolean gameIsOver();
	public abstract boolean gameIsWon();
	public abstract Player getWinner();

//PUBLIC FUNCTIONS TO PLACE PIECES ON THE BOARD:
	public abstract void makeBestMove();
//REQUIRED NON-PUBLIC FUNCTION
	protected abstract void updateStatusText();
}

