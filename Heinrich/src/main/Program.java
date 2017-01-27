package main;

import logic.LogicHandler;
import test.TestDataHandler;
import view.ViewHandler;

public class Program
{

	public static void main(String args[])
	{

		ViewHandler view = new ViewHandler();

		
		//######################TEST###################
		
		
		for(int i=0; i<TestDataHandler.getAllClasses().size();i++){
		
			float absoluteOccurence;
			float relativeOccurence;
			float classMiddle;	
			System.out.println("Daten f�r Klasse "+ (i+1) + ": ");
			
			System.out.println("--- absolute H�ufigkeit: " + absoluteOccurence);
			System.out.println("--- relative H�ufigkeit: " + absoluteOccurence);
			System.out.println("--- absolute H�ufigkeit: " + absoluteOccurence);
		}
		
		
		float median = LogicHandler.median(TestDataHandler.getAllClasses());
	
	}

}
