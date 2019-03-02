package main.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Board extends JFrame implements MouseListener
{
	private ArrayList<ArrayList<Tile>> gameBoard;
	private ArrayList<BombCoord> bombs;
	private int numFlags, dimensions, numBombs;
	Image virtualMem;
	Graphics gBuffer;
	Boolean alive;
	int cheat;
	Timer clock;
	Tile MTens, MOnes, STens, SOnes;

	public Board(int dimensions, int numBombs)
	{
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent)
			{
				System.exit(0);
			}
		});
		// /////////////////////////////////////////////////////
		clock = new Timer();
		alive = true;
		this.setVisible(true);
		this.setSize(dimensions * 50 + 18, dimensions * 50 + 40 + 60);
		virtualMem = createImage(dimensions * 50 + 18,
				dimensions * 50 + 40 + 60);
		this.dimensions = dimensions;
		this.numBombs = numBombs;
		gBuffer = virtualMem.getGraphics();
		this.addMouseListener(this);
		bombs = new ArrayList<BombCoord>();
		gameBoard = new ArrayList<ArrayList<Tile>>();
		makeBoard(dimensions, numBombs);
		repaint();
		cheat = 0;
	}

	// ///////////////////////
	private void makeBoard(int dimensions, int numBombs)
	{
		for (int h = 0; h < dimensions; h++)
		{
			gameBoard.add(new ArrayList<Tile>());
			for (int v = 0; v < dimensions; v++)
			{
				gameBoard.get(h).add(new Tile(h * 50, v * 50 + 60));
			}
		}
		for (int x = 0; x < numBombs; x++)
		{
			int h = (int) (Math.random() * dimensions);
			int v = (int) (Math.random() * dimensions);
			if (gameBoard.get(h).get(v).isBomb())
				x--;
			else
			{
				gameBoard.get(h).get(v).setBomb(true);
				bombs.add(new BombCoord(h, v));
			}
		}
		MTens = new Tile(((dimensions * 50 + 18) / 2) - 85, 30, false);
		MOnes = new Tile(((dimensions * 50 + 18) / 2) - 45, 30, false);
		STens = new Tile(((dimensions * 50 + 18) / 2) + 15, 30, false);
		SOnes = new Tile(((dimensions * 50 + 18) / 2) + 55, 30, false);

		numFlags = numBombs;
		findBombs();
		repaint();
	}

	@Override
	public void paint(Graphics g)
	{
		if (gameBoard != null)
		{
			// Game Tiles
			for (ArrayList<Tile> a : gameBoard)
			{
				for (Tile t : a)
				{
					gBuffer.drawImage(t.getImg(), t.getX() + 10, t.getY() + 32,
							50, 50, null);
				}
			}
			// Black Grid
			gBuffer.setColor(Color.black);
			for (int v = 0; v <= dimensions; v++)
			{
				gBuffer.drawLine(v * 50 + 9, 31 + 60, v * 50 + 9,
						dimensions * 50 + 30 + 60);
			}
			for (int h = 0; h <= dimensions; h++)
			{
				gBuffer.drawLine(9, h * 50 + 31 + 60, dimensions * 50 + 9,
						h * 50 + 31 + 60);
			}
			// Clock- Colon
			gBuffer.fillRect(((dimensions * 50 + 18) / 2) - 5, 50, 10, 10);
			gBuffer.fillRect(((dimensions * 50 + 18) / 2) - 5, 70, 10, 10);
			// Clock-Time
			int[] time = clock.getTime();
			int m1 = time[1] / 10;
			int m2 = time[1] % 10;
			int s1 = time[2] / 10;
			int s2 = time[2] % 10;
			MTens.setCImage(m1);
			gBuffer.drawImage(MTens.getImg(), MTens.getX(), MTens.getY(), 30,
					60, null);
			MOnes.setCImage(m2);
			gBuffer.drawImage(MOnes.getImg(), MOnes.getX(), MOnes.getY(), 30,
					60, null);
			STens.setCImage(s1);
			gBuffer.drawImage(STens.getImg(), STens.getX(), STens.getY(), 30,
					60, null);
			SOnes.setCImage(s2);
			gBuffer.drawImage(SOnes.getImg(), SOnes.getX(), SOnes.getY(), 30,
					60, null);
			// System.out.println(time[1]+":"+time[2]);
		}
		g.drawImage(virtualMem, 0, 0, null);
		repaint();
	}

	private void popup(char t)
	{
		clock.stopThread();

		int num = 0;
		String temp = "";
		if (t == 'w')
		{
			temp = "                                  WINNER";
			num = JOptionPane.QUESTION_MESSAGE;
		}
		if (t == 'l')
		{
			temp = "                                   YOU DIED";
			num = JOptionPane.ERROR_MESSAGE;
		}
		String[] buttons = { "Yes", "No" };
		int rc = JOptionPane.showOptionDialog(this,
				"Would you like to play again?", temp,
				JOptionPane.YES_NO_OPTION, num, null, buttons, buttons[0]);
		if (rc == 0)
			restart();
		else
		{
			this.setVisible(false);
			System.exit(0);
		}
	}

	// ///////////////////////
	public String toString()
	{
		String rtrn = "";
		for (int h = 0; h < gameBoard.size(); h++)
			rtrn += gameBoard.get(h) + "\n";
		return rtrn;
	}

	// ///////////////////////
	public void mouseClicked(MouseEvent arg0)
	{
	}

	public void mouseEntered(MouseEvent arg0)
	{
	}

	public void mouseExited(MouseEvent arg0)
	{
	}

	public void mouseReleased(MouseEvent arg0)
	{
	}

	public void mousePressed(MouseEvent arg0)
	{
		int x = arg0.getX() - 8;
		int y = arg0.getY() - 30;
		if (!clock.running())
			clock.startThread();

		for (int r = 0; r < gameBoard.size(); r++)
		{
			for (int c = 0; c < gameBoard.get(r).size(); c++)
			{
				Tile t = gameBoard.get(r).get(c);
				if (t.contains(x, y))
				{
					if (arg0.getButton() == MouseEvent.BUTTON1)
					{
						// Left CLick
						if (t.getFlag() == false)
						{
							if (t.isBomb() == true)
							{
								alive = false;
								revealA();
								t.setImage(11);
							}
							reveal(r, c);
							repaint();
							if (alive == false)
								popup('l');
						}
						cheat = 0;
					}
					if (arg0.getButton() == MouseEvent.BUTTON3)
					{
						// Right CLick
						if (numFlags > 0)
						{
							if (t.isRevealed() == false)
							{
								if (t.flag())
									numFlags--;
								else
									numFlags++;
							}
						}
						if (checkBombs())
						{
							t.setImage(Tile.FLAG);
							revealA();
							repaint();
							popup('w');
						}
						cheat = 0;
					}
					if (arg0.getButton() == MouseEvent.BUTTON2)
					{
						cheat++;
						if (cheat > 2)
						{
							revealA();
							for (BombCoord b : bombs)
							{
								gameBoard.get(b.getR()).get(b.getC())
										.setImage(Tile.FLAG);
							}
							repaint();
							popup('w');
						}
					}
				}
			}
		}
		repaint();
	}

	private boolean checkBombs()
	{
		boolean rtrn = false;
		for (BombCoord a : bombs)
		{
			int r = a.getR();
			int c = a.getC();
			if (gameBoard.get(r).get(c).isBomb()
					&& gameBoard.get(r).get(c).getFlag())
				rtrn = true;
			else
				return false;
		}
		return rtrn;

	}

	private void revealA()
	{
		for (int r = 0; r < gameBoard.size(); r++)
			for (int c = 0; c < gameBoard.get(r).size(); c++)
			{
				if (!(gameBoard.get(r).get(c).isBomb() && gameBoard.get(r)
						.get(c).getFlag()))
				{
					if (!(gameBoard.get(r).get(c).isBomb())
							&& gameBoard.get(r).get(c).getFlag())
						gameBoard.get(r).get(c).setImage(12);
					gameBoard.get(r).get(c).revealT();
				}

			}
	}

	private void reveal(int r, int c)
	{
		if (gameBoard.get(r).get(c).isRevealed() == false
				&& !gameBoard.get(r).get(c).getFlag())
		{
			if (gameBoard.get(r).get(c).revealT() == 0)
			{
				try
				{
					reveal(r - 1, c - 1);
				}
				catch (java.lang.IndexOutOfBoundsException e)
				{
				}
				try
				{
					reveal(r - 1, c);
				}
				catch (java.lang.IndexOutOfBoundsException e)
				{
				}
				try
				{
					reveal(r - 1, c + 1);
				}
				catch (java.lang.IndexOutOfBoundsException e)
				{
				}
				try
				{
					reveal(r, c - 1);
				}
				catch (java.lang.IndexOutOfBoundsException e)
				{
				}
				try
				{
					reveal(r, c + 1);
				}
				catch (java.lang.IndexOutOfBoundsException e)
				{
				}
				try
				{
					reveal(r + 1, c - 1);
				}
				catch (java.lang.IndexOutOfBoundsException e)
				{
				}
				try
				{
					reveal(r + 1, c);
				}
				catch (java.lang.IndexOutOfBoundsException e)
				{
				}
				try
				{
					reveal(r + 1, c + 1);
				}
				catch (java.lang.IndexOutOfBoundsException e)
				{
				}
			}
		}
	}

	public void findBombs()
	{
		for (BombCoord tile : bombs)
		{
			int r = tile.getR();
			int c = tile.getC();
			try
			{
				gameBoard.get(r - 1).get(c + 1).addCount();
			}
			catch (java.lang.IndexOutOfBoundsException e)
			{
			}
			try
			{
				gameBoard.get(r - 1).get(c).addCount();
			}
			catch (java.lang.IndexOutOfBoundsException e)
			{
			}
			try
			{
				gameBoard.get(r - 1).get(c - 1).addCount();
			}
			catch (java.lang.IndexOutOfBoundsException e)
			{
			}
			try
			{
				gameBoard.get(r).get(c + 1).addCount();
			}
			catch (java.lang.IndexOutOfBoundsException e)
			{
			}
			try
			{
				gameBoard.get(r).get(c - 1).addCount();
			}
			catch (java.lang.IndexOutOfBoundsException e)
			{
			}
			try
			{
				gameBoard.get(r + 1).get(c + 1).addCount();
			}
			catch (java.lang.IndexOutOfBoundsException e)
			{
			}
			try
			{
				gameBoard.get(r + 1).get(c).addCount();
			}
			catch (java.lang.IndexOutOfBoundsException e)
			{
			}
			try
			{
				gameBoard.get(r + 1).get(c - 1).addCount();
			}
			catch (java.lang.IndexOutOfBoundsException e)
			{
			}

		}
	}

	private void restart()
	{
		alive = true;
		clock.stopThread();
		clock = new Timer();
		gameBoard = new ArrayList<ArrayList<Tile>>();
		bombs = new ArrayList<BombCoord>();
		cheat = 0;
		makeBoard(dimensions, numBombs);

		repaint();
	}
}