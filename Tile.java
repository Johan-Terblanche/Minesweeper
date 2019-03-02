package main.game;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile
{
	private boolean flagged;
	private boolean bomb;
	private boolean revealed;
	private Rectangle hitBox;
	private BufferedImage tileImage;
	public static boolean imagesLoaded;
	public static BufferedImage BOMBI, BLANKI, ONEI, TWOI, THREEI, FOURI,
			FIVEI, SIXI, SEVENI, EIGHTI, FLAGI, STARTI, BOMB1I, FLAG1I, 
			CONE,CTWO, CTHREE, CFOUR, CFIVE, CSIX, CSEVEN, CEIGHT, CNINE, CZERO;
	public static final int BOMB = -1, BLANK = 0, ONE = 1, TWO = 2, THREE = 3,
			FOUR = 4, FIVE = 5, SIX = 6, SEVEN = 7, EIGHT = 8, FLAG = 9,
			START = 10;
	private int xStart, yStart, bombCount, imgNum;
	public Tile()
	{
		loadImages();
	}
	public Tile(int x, int y)
	{
		if (!imagesLoaded)
		{
			loadImages();
		}
		xStart = x;
		yStart = y;
		hitBox = new Rectangle(xStart, yStart, 50, 50);
		bomb = false;
		flagged = false;
		setImage(START);

	}
	public Tile(int x, int y, boolean type)
	{
		if (!imagesLoaded)
		{
			loadImages();
		}
		xStart = x;
		yStart = y;
		if (type)
		{
			hitBox = new Rectangle(xStart, yStart, 50, 50);
			setImage(START);
		}
		else
		{	
			hitBox = new Rectangle(0,0,0,0);
			setCImage(0);
		}
		bomb = false;
		flagged = false;
		
	}
	
	public void setBomb(boolean bmb)
	{
		bomb = bmb;
	}

	public String toString()
	{
		String rtrn = String.valueOf(bombCount);
		return rtrn;
	}

	public void loadImages()
	{
		imagesLoaded = true;
		try
		{
			//Game
			BOMBI = ImageIO.read(getClass().getResourceAsStream("/main/images/mineBomb.jpg"));
			BLANKI = ImageIO.read(getClass().getResourceAsStream("/main/images/mineEmpty.jpg"));
			ONEI = ImageIO.read(getClass().getResourceAsStream("/main/images/mine1.jpg"));
			TWOI = ImageIO.read(getClass().getResourceAsStream("/main/images/mine2.jpg"));
			THREEI = ImageIO.read(getClass().getResourceAsStream("/main/images/mine3.jpg"));
			FOURI = ImageIO.read(getClass().getResourceAsStream("/main/images/mine4.jpg"));
			FIVEI = ImageIO.read(getClass().getResourceAsStream("/main/images/mine5.jpg"));
			SIXI = ImageIO.read(getClass().getResourceAsStream("/main/images/mine6.jpg"));
			SEVENI = ImageIO.read(getClass().getResourceAsStream("/main/images/mine7.jpg"));
			EIGHTI = ImageIO.read(getClass().getResourceAsStream("/main/images/mine8.jpg"));
			FLAGI = ImageIO.read(getClass().getResourceAsStream("/main/images/mineFlag.jpg"));
			STARTI = ImageIO.read(getClass().getResourceAsStream("/main/images/mineBlank.jpg"));
			BOMB1I = ImageIO.read(getClass().getResourceAsStream("/main/images/mineBomb1.jpg"));
			FLAG1I = ImageIO.read(getClass().getResourceAsStream("/main/images/mineFlag1.jpg"));
			//Clock
			CZERO = ImageIO.read(getClass().getResourceAsStream("/main/images/digiZero.jpg"));
			CONE = ImageIO.read(getClass().getResourceAsStream("/main/images/digiOne.jpg"));
			CTWO = ImageIO.read(getClass().getResourceAsStream("/main/images/digiTwo.jpg"));
			CTHREE = ImageIO.read(getClass().getResourceAsStream("/main/images/digiThree.jpg"));
			CFOUR = ImageIO.read(getClass().getResourceAsStream("/main/images/digiFour.jpg"));
			CFIVE = ImageIO.read(getClass().getResourceAsStream("/main/images/digiFive.jpg"));
			CSIX = ImageIO.read(getClass().getResourceAsStream("/main/images/digiSix.jpg"));
			CSEVEN = ImageIO.read(getClass().getResourceAsStream("/main/images/digiSeven.jpg"));
			CEIGHT = ImageIO.read(getClass().getResourceAsStream("/main/images/digiEight.jpg"));
			CNINE = ImageIO.read(getClass().getResourceAsStream("/main/images/digiNine.jpg"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void addCount()
	{
		if (bomb == false)
			bombCount++;
		else
			bombCount = -1;
	}

	public int getCount()
	{
		return bombCount;
	}

	public int revealT()
	{
		if(imgNum != 11&&imgNum!=12)
			setImage(bombCount);
		revealed = true;
		return bombCount;
	}

	public boolean isRevealed()
	{
		return revealed;
	}

	public boolean contains(int x, int y)
	{
		return hitBox.contains(x, y);
	}

	public BufferedImage getImg()
	{
		return tileImage;
	}

	public void setImage(int num)
	{
		if (num == BOMB) tileImage = BOMBI;
		if (num == BLANK) tileImage = BLANKI;
		if (num == ONE) tileImage = ONEI;
		if (num == TWO) tileImage = TWOI;
		if (num == THREE) tileImage = THREEI;
		if (num == FOUR) tileImage = FOURI;
		if (num == FIVE) tileImage = FIVEI;
		if (num == SIX) tileImage = SIXI;
		if (num == SEVEN) tileImage = SEVENI;
		if (num == EIGHT) tileImage = EIGHTI;
		if (num == FLAG) tileImage = FLAGI;
		if (num == START) tileImage = STARTI;
		if (num == 11) tileImage = BOMB1I;
		if (num == 12) tileImage = FLAG1I;
		imgNum=num;
	}
	public void setCImage(int num)
	{
		switch (num)
		{
		case 1: tileImage = CONE;
		break;
		case 2: tileImage = CTWO;
		break;
		case 3: tileImage = CTHREE;
		break;
		case 4: tileImage = CFOUR;
		break;
		case 5: tileImage = CFIVE;
		break;
		case 6: tileImage = CSIX;
		break;
		case 7: tileImage = CSEVEN;
		break;
		case 8: tileImage = CEIGHT;
		break;
		case 9: tileImage = CNINE;
		break;
		case 0: tileImage = CZERO;
		}
	}
	
	public boolean flag()
	{
		if (flagged)
		{
			flagged = false;
			setImage(START);
		}
		else
		{
			flagged = true;
			setImage(FLAG);
		}
		return flagged;
	}

	public boolean getFlag()
	{
		return flagged;
	}

	public int getX()
	{
		return xStart;
	}

	public int getY()
	{
		return yStart;
	}

	public boolean isBomb()
	{
		return bomb;
	}
}
