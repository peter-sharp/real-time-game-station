/**
Connect4.java

Main class for Connect4 Applet
Screen layout class

Paul Chudley, 22/4/2002
University of Canterbury
*/

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class Connect4 extends Applet
{
	//declare board for playing Connect 4
	private Connect4PlayingFieldPanel board;

	//declare panel to be displayed at bottom of applet
	private Panel footer;
	
	//declare panel to hold user controls
	private Panel userControls;

	private Button helpButton;
	private HelpWindow helpWindow = null;
	
	public void init()
	{
		setLayout(new BorderLayout());

		//Create the game board.
		board = new Connect4PlayingFieldPanel();

		//Create the "Help" Button
		helpButton = new Button("Help");
		//Allow the help button to respond to a mouse click
		helpButton.addActionListener(new ActionListener()
		{
			//Displays help window if help button is clicked
			public void actionPerformed(ActionEvent event)
			{
				//ensure only one copy of helpWindow is made
				if(helpWindow != null)
					helpWindow.dispose();

				//display help window
				helpWindow = new HelpWindow();
			}
		});

		//fill the user controls panel with player setup choice box and help button
		userControls = new Panel();
		userControls.setBackground(Color.white);
		userControls.add(board.playerConfiguration);
		userControls.add(helpButton);

		//Fill the footer panel with a status label and user controls
		footer = new Panel();
		footer.setLayout(new GridLayout(2,1));
		footer.add(board.gameStatusText);
		footer.add(userControls);
		footer.setBackground(Color.white);
		add(footer,"South");

		//Displays game board on applet panel
		add(board);
	}
}