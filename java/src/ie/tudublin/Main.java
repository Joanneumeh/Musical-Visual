package ie.tudublin;

import example.CubeVisual;
import example.MyVisual;
import example.RotatingAudioBands;
import Assignment.*;

public class Main
{	

	public void startUI()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new MyVisual());		
	}

	public void visualiser()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Visualiser());		
	}


	public static void main(String[] args)
	{
		Main main = new Main();
		// main.startUI();
		main.visualiser();
		// main.testSketch();			
	}
}