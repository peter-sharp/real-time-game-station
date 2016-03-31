/**
Connect4PlayingField.java

Game logic unit for Connect4 applet

Paul Chudley, 22/4/2002
University of Canterbury
*/

import java.awt.*;

public class Connect4PlayingField extends PlayingField
{
	//Game size instance constants
	//Values should be set in constructor.
	private final int FIELD_WIDTH;
	private final int FIELD_HEIGHT;
	private final int WINNING_LENGTH;

	//Player piece default colors
	public static final Color PLAYER_ONE_COLOR = Color.red;
	public static final Color PLAYER_TWO_COLOR = Color.yellow;

	//Default instance game board display colors.
	public static final Color HOLE_COLOR = Color.white;
	public static final Color BORDER_COLOR = Color.black;
	public static final Color FIELD_COLOR = Color.blue;
	
//PUBLIC CONSTRUCTORS:

	public Connect4PlayingField(int width, int height, int winningLength)
	{
		//Set instance game size constants
		FIELD_WIDTH = width;
		FIELD_HEIGHT = height;
		WINNING_LENGTH = winningLength;

		//Set up the players
		thePlayers = new PlayerSet(2); //Two player game
		thePlayers.setPlayerName(1,"Player One");
		thePlayers.setPlayerName(2,"Player Two");
		thePlayers.setPlayerColor(1,PLAYER_ONE_COLOR);
		thePlayers.setPlayerColor(2,PLAYER_TWO_COLOR);

		//create array of required size
		fieldArray = new PlayingPiece[FIELD_WIDTH][FIELD_HEIGHT];

		//Create game status label
		gameStatusText = new Label("");

		//initialise array and other game variables
		initialise();
	}

	public Connect4PlayingField()
	{
		//Use defaults: width 7, height 6, winningLength 4.
		this(7,6,4);
	}

//PUBLIC INITIALISATION FUNCTIONS:

	public void initialise()
	{
		// Ensure every cell on the board is empty.
		for(int col = 0; col < FIELD_WIDTH; col++)
		{
			for(int row = 0; row < FIELD_HEIGHT; row++)
			{
				fieldArray[col][row] = null; //no PlayingPiece
			}
		}
		
		thePlayers.reset(); //Reset who's turn it is.
		winner = null; //No-one has won yet.

		//Update the game status
		updateStatusText();
	}

//PUBLIC FUNCTIONS TO DEAL WITH THE GRAPHICAL INTERFACE:

	/**Graphical User Interface Input function.
	  Attempts to make the move specified by the relative mouse screen position "mouseX"
	  (relative to board position "left" and size "width").*/
	public void makeMove(int mouseDownX, int mouseDownY, int MouseUpX, int mouseUpY, int left, int top, int width, int height)
	{
		//Discard most of this info because not needed for connect 4.
		makeMove(mouseDownX,left,width);
	}
	

	/**Graphical User Interface Output function.
	 Draws the PlayingField in the specified rectangle.*/
	public void paint(Graphics g, int width, int height)
	{
		// sets thickness of buffer region around each cell on the board.
		// The value is a percentage of the cell dimensions.
		int cellBufferPercentage = 10;

		//Draw Board
		g.setColor(FIELD_COLOR);
		g.fillRect(0, 0, width - 1, height - 1);

		//Draw the pieces located in each cell
		for (int col = 0; col < FIELD_WIDTH ; col++)
		{
			// gap around each cell
			int xBuffer = cellBufferPercentage * width / FIELD_WIDTH / 100;

			// position and width of each cell
			int xOffset = width * col / FIELD_WIDTH + xBuffer;
			int cellwidth = width / FIELD_WIDTH - 2 * xBuffer;

			for (int row = 0; row < FIELD_HEIGHT ; row++)
			{
				// gap around each cell
				int yBuffer = cellBufferPercentage * height / FIELD_HEIGHT / 100;

				// position and height of each cell
				int yOffset = height * (FIELD_HEIGHT - row - 1) / FIELD_HEIGHT + yBuffer;
				int cellheight = height / FIELD_HEIGHT - 2 * yBuffer;

				//Set the cell color
				g.setColor(HOLE_COLOR);
				//Draw the cell.
				g.fillOval(xOffset, yOffset, cellwidth, cellheight);

				//If there is a PlayingPiece there
				PlayingPiece currentPiece = fieldArray[col][row];
				if(currentPiece != null)
				{
					//Draw the playing piece using its own paint routine
					currentPiece.paint(g,xOffset, yOffset, cellwidth, cellheight);
				}

			}
		}

		//draw border around game board
		g.setColor(BORDER_COLOR);
		g.drawRect(0, 0, width - 1, height - 1);
	}

//Private game status functions

	/**Returns true if every cell is occupied (ie no valid moves remain)*/
	private boolean fieldIsFull()
	{
		for(int i = 0; i < FIELD_WIDTH; i++)
		{
			if(!columnIsFull(i))
				return false;
		}
		return true;
	}


// PUBLIC FUNCTIONS TO GET GAME STATUS:


	public boolean gameIsOver()
	{
		if(gameIsWon() || fieldIsFull())
			return true;
		return false;
	}

	public boolean gameIsWon()
	{
		if(winner != null)
			return true;
		return false;
	}

	public Player getWinner()
	{
		return winner;
	}

//PUBLIC FUNCTIONS TO PLACE PIECES ON THE BOARD:

	/**Attempts to place the current player's piece in a column of the computer's choice.*/
	public void makeBestMove()
	{
		int col = bestMove(); //find best move
		placePiece(col); //Place piece there.
	}

//PRIVATE SUB-FUNCTIONS TO HELP PLACE PIECES ON THE BOARD:

	/**Graphical User Interface Input function.
	  Attempts to make the move specified by the relative mouse screen position "mouseX"
	  (relative to board position "left" and size "width").*/
	private void makeMove(int mouseX, int left, int width)
	{
		//"left" and "width" give PlayingField location and size within applet window.
		//Convert mouse co-ordinates from pixel measuring scale to column number.
		int col = (mouseX - left) * FIELD_WIDTH / width;

		placePiece(col);
	}

	private int bestMove()
	{
		// Algorithm for finding the "best" move.

		int col;
		for(col = 0; col < FIELD_WIDTH; col++)
		{
			//Find first available column.
			if(!columnIsFull(col))
				break;
		}
		return col;
	}

	/**Attempts to place the current player's piece in the specified column.
	  Updates PlayingField array, winner and whosTurn variables.*/
	private void placePiece(int col)
	{
		if(columnIsFull(col))
			return; //Cannot move here. Do nothing.

		//If control reaches this point, there is at least 1
		// unoccupied cell in the column.

		//find first unoccupied cell in column
		int row = findUnoccupiedCell(col);

		//attempt to place the current player's piece in the specified location.
		placePiece(col, row);
	}

	/**Attempts to place the current player's piece in the specified location.
	  Updates PlayingField array, winner and whosTurn variables.*/
	private void placePiece(int col, int row)
	{		
		//Create a new PlayingPiece object belonging to the current player
		//and residing off the board (for now)
		PlayingPiece newPiece = new Connect4PlayingPiece(thePlayers.getWhosTurn(), OFF_FIELD, OFF_FIELD);

		//Check that newPiece is allowed to move from current location to 
		// specified new location
		if(!newPiece.canMoveTo(col,row))
			return; //Piece cannot move from current location to specified
		             // new location. Piece does not move in this way.

		//Check that the specified location is unoccupied
		if(fieldArray[col][row] != null)
			return; //location full. cannot place piece here

		//Place a new piece here.
		fieldArray[col][row] = newPiece;
		newPiece.moveTo(col,row); //update internally stored position.
		
		//it is now the next player's turn
		thePlayers.nextPlayer();

		//Check to see if anyone has won. Update winner variable.
		checkForWinner();
		
		//Update the game status string
		updateStatusText();
	}


	private int findUnoccupiedCell(int columnNumber)
	{
		//Ensure array bounds are never exceeded
		if(columnNumber < 0 || columnNumber >= FIELD_WIDTH)
			return OFF_FIELD;
		
		//Check rows from bottom up to find unoccupied cell
		for(int row = 0; row < FIELD_HEIGHT; row++)
		{
			if(fieldArray[columnNumber][row] == null)
				return row; //lowest unoccupied cell
		}
		//No unoccupied cells found.
		return OFF_FIELD;
	}

	private boolean columnIsFull(int columnNumber)
	{
		//if cannot find unoccupied cell in that column
		if(findUnoccupiedCell(columnNumber) == OFF_FIELD)
			return true;
		return false;
	}


//Private function for setting game status label

	//Determine game status and print status on status label component
	protected void updateStatusText()
	{
		if(thePlayers.computerIsPlaying())
		{
			if(gameIsOver())
			{
				if(getWinner().isComputer)
					gameStatusText.setText("Computer won. Click board to play again.");
				else if(getWinner() != null)
					gameStatusText.setText("You won. Click board to play again.");
				else
					gameStatusText.setText("It's a draw. Click to play again.");
			}
			else
				gameStatusText.setText("Your turn...");
		}
		else
		{
			if(gameIsOver())
			{
				if(getWinner() == null)
					gameStatusText.setText("It's a draw. Click to play again.");
				else
					gameStatusText.setText(getWinner().playerName + " won. Click board to play again.");
			}
			else
				gameStatusText.setText(thePlayers.getWhosTurn().playerName + "'s Turn...");
		}
	}


//PRIVATE FUNCTIONS FOR FINDING THE WINNER OF THE GAME (IF THERE IS ONE)
	
	/** Checks the entire board for lines of WINNING_LENGTH
	 comprised of identical pieces. 
	 Checks performed in horizontal, vertical and both diagonal directions.
	 If such a line is found, the winner variable is set 
	 to the player constant owning the pieces comprising the line.*/
	private void checkForWinner()
	{
		if(foundWinningLineRunningNorth())
			return;
		if(foundWinningLineRunningEast())
			return;
		if(foundWinningLineRunningNorthEast())
			return;
		if(foundWinningLineRunningSouthEast())
			return;
	}

	/**check for lines of WINNING_LENGTH running East.
	 If such a line is found, the winner variable is set 
	 to the player constant owning the pieces comprising the line,
	 and the function returns true. If no such lines found, returns false.*/
	private boolean foundWinningLineRunningEast()
	{
		//cycle through all possible start points for lines
		// of WINNING_LENGTH running East.
		for(int col = 0; col < FIELD_WIDTH - (WINNING_LENGTH - 1); col++)
		{
			for(int row = 0; row < FIELD_HEIGHT; row++)
			{
				//If start point contains a player's piece
				if(fieldArray[col][row] != null)
				{
					//Check in Easterly direction from startpoint for WINNING_LENGTH
					// consecutive identical pieces
					int len = 1;
					while(len < WINNING_LENGTH && fieldArray[col + len][row] != null && fieldArray[col][row].owner == fieldArray[col + len][row].owner)
					{
						len++;
					}
					//If line of WINNING_LENGTH found, update winner variable.
					if(len >= WINNING_LENGTH)
					{
						//found a winner
						winner = fieldArray[col][row].owner;
						return true;
					}
				}
			}
		}
		//did not find a winner
		return false;
	}
	
	/**check for lines of WINNING_LENGTH running North.
	 If such a line is found, the winner variable is set 
	 to the player constant owning the pieces comprising the line,
	 and the function returns true. If no such lines found, returns false.*/
	private boolean foundWinningLineRunningNorth()
	{
		//cycle through all possible start points for lines
		// of WINNING_LENGTH running North.
		for(int col = 0; col < FIELD_WIDTH; col++)
		{
			for(int row = 0; row < FIELD_HEIGHT - (WINNING_LENGTH - 1); row++)
			{
				//If start point contains a player's piece
				if(fieldArray[col][row] != null)
				{
					//Check in Northerly direction from startpoint for WINNING_LENGTH
					// consecutive identical pieces
					int len = 1;
					while(len < WINNING_LENGTH && fieldArray[col][row + len] != null && fieldArray[col][row].owner == fieldArray[col][row + len].owner)
					{
						len++;
					}
					//If line of WINNING_LENGTH found, update winner variable.
					if(len >= WINNING_LENGTH)
					{
						// found a winner
						winner = fieldArray[col][row].owner;
						return true;
					}
				}
			}
		}
		// did not find a winner
		return false;
	}

	/**check for lines of WINNING_LENGTH running NorthEast.
	 If such a line is found, the winner variable is set 
	 to the player constant owning the pieces comprising the line,
	 and the function returns true. If no such lines found, returns false.*/
	private boolean foundWinningLineRunningNorthEast()
	{
		//cycle through all possible start points for lines
		// of WINNING_LENGTH running NorthEast.
		for(int col = 0; col < FIELD_WIDTH - (WINNING_LENGTH - 1); col++)
		{
			for(int row = 0; row < FIELD_HEIGHT - (WINNING_LENGTH - 1); row++)
			{
				//If start point contains a player's piece
				if(fieldArray[col][row] != null)
				{
					//Check in North-Easterly direction from startpoint for WINNING_LENGTH
					// consecutive identical pieces
					int len = 1;
					while(len < WINNING_LENGTH && fieldArray[col + len][row + len] != null &&fieldArray[col][row].owner == fieldArray[col + len][row + len].owner)
					{
						len++;
					}
					//If line of WINNING_LENGTH found, update winner variable.
					if(len >= WINNING_LENGTH)
					{
						// found a winner
						winner = fieldArray[col][row].owner;
						return true;
					}
				}
			}
		}
		// did not find a winner
		return false;
	}

	/**check for lines of WINNING_LENGTH running SouthEast.
	 If such a line is found, the winner variable is set 
	 to the player constant owning the pieces comprising the line,
	 and the function returns true. If no such lines found, returns false.*/
	private boolean foundWinningLineRunningSouthEast()
	{
		//cycle through all possible start points for lines
		// of WINNING_LENGTH running SouthEast.
		for(int col = 0; col < FIELD_WIDTH - (WINNING_LENGTH - 1); col++)
		{
			for(int row = (WINNING_LENGTH - 1); row < FIELD_HEIGHT; row++)
			{
				//If start point contains a player's piece
				if(fieldArray[col][row] != null)
				{
					//Check in South-Easterly direction from startpoint for WINNING_LENGTH
					// consecutive identical pieces
					int len = 1;
					while(len < WINNING_LENGTH && fieldArray[col + len][row - len] != null &&fieldArray[col][row].owner == fieldArray[col + len][row - len].owner)
					{
						len++;
					}
					//If line of WINNING_LENGTH found, update winner variable.
					if(len >= WINNING_LENGTH)
					{
						// found a winner
						winner = fieldArray[col][row].owner;
						return true;
					}
				}
			}
		}
		// did not find a winner
		return false;
	}
}
