/**
PlayingPiece.java


Paul Chudley, 22/4/2002
University of Canterbury
*/

import java.awt.*;

public abstract class PlayingPiece
{
	public Player owner;

	//Error checking constants
	public final static int OFF_FIELD = -1;

	//Location variables - default value is OFF_FIELD
	private int currentColumn;
	private int currentRow;


	/** Paints the playing piece in the specified rectangle.*/
	public abstract void paint(Graphics g, int left, int right, int width, int height);

	/** Determines whether the move "shape" of this particular 
		playing piece allows it to make a move from its current location
	    to the specifed location, ignoring board status considerations.*/
	public abstract boolean canMoveTo(int col, int row);

	/** Change the internally stored location of the playing piece*/
	public void moveTo(int col, int row)
	{
		if(!canMoveTo(col,row))
			return;
		
		setPosition(col, row);
	}

	public int getColumn()
	{
		return currentColumn;
	}

	public int getRow()
	{
		return currentRow;
	}

	protected void setPosition(int col, int row)
	{
		currentColumn = col;
		currentRow = row;
	}
}
