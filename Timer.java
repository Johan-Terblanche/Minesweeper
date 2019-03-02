package main.game;

public class Timer extends Thread
{
	private long startTime;
	private boolean running;
	private int [] time;
	public Timer()
	{
		running = false;
		startTime = 0;
		time = new int [4];
	}
	
	public void startThread()
	{
		this.startTime = System.currentTimeMillis();
		this.running = true;
		this.start();
	}
	public void run()
	{
		while(running)
		{}
	}
	public int[] getTime()
	{
		if(running)
		{
			long milliTime = System.currentTimeMillis() - this.startTime;
			time[0] = (int)(milliTime / 3600000);
			time[1] = (int)(milliTime / 60000) % 60;
			time[2] = (int)(milliTime / 1000) % 60;
			time[3] = (int)(milliTime) % 1000;
		}
		return time;
	}
	public void stopThread()
	{
		this.running = false;
	}
	public boolean running()
	{
		return running;
	}
}
