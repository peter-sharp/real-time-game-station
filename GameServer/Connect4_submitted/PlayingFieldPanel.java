/**
PlayingFieldPanel.java

Abstract class that defines the interface to the graphical i/o class

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
public abstract class PlayingFieldPanel extends Panel implements MouseListener
{
	protected int mouseDownX;
	protected int mouseDownY;
	protected int mouseUpX;
	protected int mouseUpY;

	protected PlayingField board;

	//Must pass this through from board to client class, since board is 
	//not visible from client class.
	//Should set this reference to board.gameStatusText in constructor.
	public Label gameStatusText;

	/**Graphical User Interface Output function.
	 Draws the PlayingField on the panel.*/
	public void paint(Graphics g)
	{	
		//draw game board of specified size
		board.paint(g,getSize().width,getSize().height);
	}

	//Used for implementing single click events
	protected abstract void mousePressedAction();

	//Used for implementing "drag and drop" events (with two mouse locations)
	protected abstract void mouseReleasedAction();

	//Allows panel to utilise mouse presses.
	public void mousePressed(MouseEvent event)
	{
		//Save where the mouse was pressed down.
		mouseDownX = event.getX();
		mouseDownY = event.getY();

		mousePressedAction();
	}


	public void mouseReleased(MouseEvent event)
	{
		//Save where the mouse was released.
		mouseDownX = event.getX();
		mouseDownY = event.getY();

		mouseReleasedAction();
	}

	public void mouseClicked(MouseEvent event){}
	public void mouseEntered(MouseEvent event){}
	public void mouseExited(MouseEvent event){}
}
