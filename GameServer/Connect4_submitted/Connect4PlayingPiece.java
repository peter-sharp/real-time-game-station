/**
Connect4PlayingPiece.java

Provides graphical representation of Connect 4 playing piece

Paul Chudley, 22/4/2002
University of Canterbury
*/

import java.awt.*;

public class Connect4PlayingPiece extends PlayingPiece
{
	public Connect4PlayingPiece(Player pieceOwner, int col, int row)
	{
		owner = pieceOwner;
		setPosition(col, row);
	}

	/** Paints the playing piece in the specified rectangle.*/
	public void paint(Graphics g, int left, int right, int width, int height) 
	{
		if(owner != null)
		{
			//Use playing piece owner's color.
			g.setColor(owner.playerColor);
			//Draw the Connect 4 playing piece.
			g.fillOval(left, right, width, height);
		}
	}

	/** Determines whether the move "shape" of this particular 
		playing piece allows it to make a move from its current location
	    to the specifed location, ignoring board status considerations.
		
		In Connect 4, a piece may only be placed, not moved. Thus if
		the piece is off the field, it is allowed to move anywhere.
		Otherwise if it is on the field, it is allowed to move nowhere.*/
	public boolean canMoveTo(int column, int row)
	{
		//If piece not on board,
		if(getColumn() == OFF_FIELD && getRow() == OFF_FIELD)
			return true; //allowed to move anywhere. Don't care where to.
						//Therefore destination arguments are ignored.

		//Otherwise piece already on board. Can move nowhere.
		return false;
	}
}
