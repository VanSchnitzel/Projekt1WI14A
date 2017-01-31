package logic;

import java.util.ArrayList;

import data.StatisticClass;
import data.StatisticClassValue;
import data.ClampType;

//Robert, Mathias
public class LogicHandler 
{
	/*
	 * x Klassenmitten
	 * x Median
	 * x Arithmetisches Mittel
	 * x Absolute H�ufigkeit
	 * x Relative H�ufigkeit
	 * Histogramm
	 * - Breite
	 * - H�he
	 * Empirische Verteilungsfunktion
	 * Quantil
	 * Mittlere absolute Abweichung
	 * Varianz
	 * Standardabweichung
	 * Gini-Koeffizient
	 */

	//TODO ERRORHANDLING, KOMMENTARE
	/**
	 * Methode zur Ermittlung der Klassenmitten eines Datensatzes
	 * @param classes Datensatz, f�r den die Klassenmitten ermittelt werden sollen.
	 * @return Array der Klassenmitten
	 * @author Mathias Engmann
	 */
	public static float[] getClassMiddles(ArrayList<StatisticClass> classes){
		float[]results = new float[classes.size()];
		
		for(int i=0; i<classes.size(); i++){
			float a = classes.get(i).getLowerValue().value;
			float b  = classes.get(i).getUpperValue().value;
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
	 * @param statisticClass 
	 */
	public static float[] getRelativeOccurences(ArrayList<StatisticClass> classes, int sampleSize) throws IllegalArgumentException{
		float[]results = new float[classes.size()];

		if(sampleSize<=0  || classes.size()<=0){
			
			throw new IllegalArgumentException("Die Stichprobengr��e muss gr��er als 0 sein.");
		}
		
		else{
			for(int i=0; i<classes.size();i++){
				results[i] = classes.get(i).getAbsoluteOccurences()/(float)sampleSize;
			}
			
			
			return results;
		}
	}

	//TODO KOMMENTARE; DOKU; ERRORHANDLING
	/**
	 * 
	 * @param classes
	 * @param classMiddles
	 * @param relativeOccurences
	 * @return
	 * @throws Exception
	 * @author Mathias Engmann
	 */
	public static float getMedian(ArrayList<StatisticClass> classes, float[] classMiddles, float[] relativeOccurences) throws Exception{
		//Braucht: Klassenmitten, relative H�ufigkeiten
		
		
		if(classes.size() ==0 || classMiddles.length == 0 || relativeOccurences.length ==0){
			throw new IllegalArgumentException("Die angegebenen Wertearrays sind nicht gef�llt.");
		}
		
		else{
			float currentRelativeShare = 0;
			float relativeShareBeforeHit = 0;
			
			int classIndexWithMedian = -1;
			
			for(int i=0 ; i<classMiddles.length; i++){
				
				currentRelativeShare += relativeOccurences[i];
				
				if(currentRelativeShare>0.5f){
					classIndexWithMedian = i;
					break;	
				}
				
				else{
					relativeShareBeforeHit = currentRelativeShare;
				}
			
			}
			
			if(classIndexWithMedian==-1){
				throw new Exception("Es konnte keine Klasse ermittelt werden, in dem der Median liegt");
			}
			
			float z1 =  classes.get(classIndexWithMedian).getLowerValue().value;
			float z2 =  classes.get(classIndexWithMedian).getUpperValue().value;
			
			float r1 = relativeShareBeforeHit;
			float r2 = currentRelativeShare;
			
			float result = z1 +((0.5f-r1)/(r2-r1))*(z2-z1);
			return result;
		
		}
	}
	

	//TODO Comments
	/**
	 * 
	 * @param classMiddles
	 * @param relativeOccurences
	 * @return
	 * @author Jonathan Klopfer
	 */
	public static float getArithmeticMiddle(float[] classMiddles, float[] relativeOccurences){
		int classCount = classMiddles.length;
		float arithmeticMiddle = 0;
		
		for (int i = 0; i < classCount; i++){
			arithmeticMiddle += (classMiddles[i] * relativeOccurences[i]);
		}
		
		return arithmeticMiddle;
	}
	
	//TODO Formel in FK (und Buch?) stimmt nicht. R2 dort ist R1+R2
	/** 
	 * 
	 * @param classes
	 * @param classMiddles
	 * @param relativeOccurences
	 * @return
	 * @throws Exception
	 * @author Mathias Engmann
	 */
	public static Quantile[] getQuantiles(ArrayList<StatisticClass> classes, float[] classMiddles, float[] relativeOccurences) throws Exception{
		Quantile[] quantiles = new Quantile[6];
		quantiles[0] = new Quantile(-1f, 0.05f);
		quantiles[1] = new Quantile(-1f, 0.1f);
		quantiles[2] = new Quantile(-1f, 0.25f);
		quantiles[3] = new Quantile(-1f, 0.75f);
		quantiles[4] = new Quantile(-1f, 0.9f);
		quantiles[5] = new Quantile(-1f, 0.95f);
		
		//F�r jedes gesuchte Quantil...
		for(int i=0; i<quantiles.length; i++){
			
			//...zun�chst die Klasse finden, in der sich das Quantil befindet (analog wie beim Median)
			float currentAlpha = quantiles[i].getAlpha();
		
			float currentRelativeShare = 0;
			float relativeShareBeforeHit = 0;
			float threshholdRelativeShare = currentAlpha;
			
			int classIndexWithQuantile = -1;
			
			for(int j=0; j<relativeOccurences.length; j++){
				currentRelativeShare += relativeOccurences[j];
				
				if(currentRelativeShare>=threshholdRelativeShare){
					classIndexWithQuantile = j;
					break;
				}
				
				else{
					relativeShareBeforeHit = currentRelativeShare;
				}
			}
			
			//Pr�fung, ob eine Klasse gefunden wurde
			if(classIndexWithQuantile==-1){
				throw new Exception("Es konnte keine Klasse ermittelt werden, in dem das Quantil " + threshholdRelativeShare + " liegt");
			}
			
			//Quantil berechnen
			float z1 = classes.get(classIndexWithQuantile).getLowerValue().value;
			float z2 = classes.get(classIndexWithQuantile).getUpperValue().value;
			float r1 = relativeShareBeforeHit;
			float r2 = currentRelativeShare;
			float result = z1+((currentAlpha-r1)/(r2-r1))*(z2-z1);
			
			quantiles[i].setValue(result);
		}
	
		return quantiles;
	}
	
	//TODO Fehler auf Seite 124 im Buch: 1/7 sollte in der Klasser stehen, vor abs(36-35)
	/**
	 * 
	 * @param classes
	 * @param classMiddles
	 * @param relativeOccurences
	 * @param z
	 * @return
	 * @author Mathias Engmann
	 */
	public static float getMeanAbsoluteDeviation(ArrayList<StatisticClass> classes, float[] classMiddles, float[] relativeOccurences, float z){
		//Braucht: Median, Mittelwert, Randwerte ??
		
		float sigmaResult = 0;
		float currentDeviation;
		
		for(int i=0; i<classes.size();i++){
			currentDeviation = Math.abs(classMiddles[i] - z);
			sigmaResult += relativeOccurences[i]* currentDeviation;
		}
		
		float result = sigmaResult;
		return result;
	}
	
	//TODO DOKU; KOMMENTARE; FEHLERCATCHING
	/**
	 * 
	 * @param classes
	 * @param classMiddles
	 * @param arithmeticMiddle
	 * @param sampleSize
	 * @return
	 * @author Mathias Engmann
	 */
	public static float getVariance(ArrayList<StatisticClass> classes, float[] classMiddles, float arithmeticMiddle, int sampleSize){
		float result;
		float sigmaResult = 0;
		
		for(int i =0; i<classes.size();i++){
			
			float absOccurence = classes.get(i).getAbsoluteOccurences();
			float classMiddle = classMiddles[i];
			
			sigmaResult += absOccurence * Math.pow((classMiddle - arithmeticMiddle), 2);			
			
		}
		
		result = sigmaResult / (sampleSize-1);
		return result;
	}
	
	/**
	 * 
	 * @param variance
	 * @return
	 * @author Mathias Engmann
	 */
	public static float getStandardDeviation(float variance){
		float result = (float)Math.sqrt(variance);
		return result;
	}
	
	
	public static float getGiniCoefficient(){
		return 0;
	}
	

	public static float getHistogramElementHeight(){
		return 0;
	}
	
	public static float getHistogramElementWidth(){
		return  0;
	}
}
