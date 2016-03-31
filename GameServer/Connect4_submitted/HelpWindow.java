/**
HelpWindow.java

Frame which displays user help for Connect 4 applet

Paul Chudley, 22/4/2002
University of Canterbury
*/

import java.awt.*;
import java.awt.event.*;

public class HelpWindow extends Frame implements WindowListener
{
	private static final int WIDTH = 300;
	private static final int HEIGHT = 400;

	private Label helpText;

	public HelpWindow()
	{
		//Sets titlebar text of help window
		setTitle("Connect 4 Help");

		setLayout(new GridLayout(1,1));

		//Add the help text to the window.
		helpText = new Label("");
		helpText.setText("This is where the Help is to be provided to the user.");
		add(helpText);

		//allow the user to control the window with the mouse.
		addWindowListener(this);

		//Create the window
		setSize(WIDTH,HEIGHT);
		setVisible(false);
		show();
	}

	//Invoked when a window is in the process of being closed. 
	public void windowClosing(WindowEvent event)
	{
		//destroy the window
		dispose();
	}

	public void windowActivated(WindowEvent event) {}
	public void windowClosed(WindowEvent event) {}
	public void windowDeactivated(WindowEvent event) {}
	public void windowDeiconified(WindowEvent event) {}
	public void windowIconified(WindowEvent event) {}
	public void windowOpened(WindowEvent event) {}
}