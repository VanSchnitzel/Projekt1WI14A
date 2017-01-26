package logic;

import java.util.ArrayList;

import data.StatisticClass;

//Robert, Mathias
public class LogicHandler
{
	
	/**
	 * Methode zur Ermittlung der Klassenmitten eines Datensatzes
	 * @param classes Datensatz, f�r den die Klassenmitten ermittelt werden sollen.
	 * @return Array der Klassenmitten
	 * @author Mathias Engmann
	 */
	public float[] classMiddle(ArrayList<StatisticClass> classes){
		float[]results = new float[classes.size()];
		
		for(int i=0; i<classes.size(); i++){
			float a = classes.get(i).getLowerValue();
			float b  = classes.get(i).getUpperValue();
			float currentClassMiddle = (a+b)/2;
		
			results[i] = currentClassMiddle;
		}
	
		return results;
	}
	
	/**
	 * Methode zur Berechnung der relativen H�ufigkeiten der Klassen eines Datensatzes
	 * @param classes Datensatz, f�r den die relativen H�ufigkeiten ermittelt werden sollen.
	 * @return Array der relativen H�ufigkeiten
	 * @author Mathias Engmann
	 */
	public float[] relativeOccurence(ArrayList<StatisticClass> classes){
		float[]results = new float[classes.size()];
		
		for(int i=0; i<classes.size();i++){
			//result[i] = ;
			
		}
		
		return results;
	}

	public static float median(){
		//Braucht: Klassenmitten, relative H�ufigkeiten
	}
	
	public static float arithmeticMiddle(){
		//Braucht: Klassenmitten
	}
	
	public static float absoluteDeviation(){
		//Braucht: Median, Mittelwert, Randwerte ??
	}
	
	public static float standardDeviation(){
		
	}
	
	public static float variance(){
		
	}
	
	public static float giniCoefficient(){
		
	}
	
	public static float[] quantiles(){
		
	}
	
	public static float histogramElementHeight(){
		
	}
	
	public static float histogramElementWidth(){
		
	}
}
