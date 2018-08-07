package com.sugarbird.assessment;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;

import com.sugarbird.assessment.impl.Flower;
import com.sugarbird.assessment.impl.Nectar;
import com.sugarbird.assessment.impl.Sugarbird;
import com.sugarbird.assessment.impl.Sun;
import com.sugarbird.assessment.impl.Sun.TimeEvent;
import com.sugarbird.assessment.service.Bird;
import com.sugarbird.assessment.service.Liquid;
import com.sugarbird.assessment.service.Plant;

public class ApplicationMain {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationMain appMain = new ApplicationMain();
		appMain.runSugarbirdFeedingDemo();
	}
	
	public void runSugarbirdFeedingDemo()
	{
		//set up
		Observable observableSun = new Sun(Sun.TimeEvent.ON_DAY_START);
		int flowersCount = 10;
		for (int index = 0; index < flowersCount; index++) {
			Liquid nectar = new Nectar();
			nectar.reFill(10);
			Plant flower = new Flower(nectar,"FLOWER-"+index,observableSun);
			observableSun.addObserver((Flower)flower);
		}
		Bird sugarbird = new Sugarbird("Sugarbird",observableSun);
		observableSun.addObserver((Sugarbird)sugarbird);
		boolean notDone = true;
		
		//run
		Sun sun = ((Sun)observableSun);
		TimeEvent event ;
		AppProperties.printObject("---------------------------------------------------------------",true);
		while(notDone)
		{
			event = sun.generateTimeEvent();
			sun.processTimeEvent(event);
			notDone = sun.getTimeEvent().isAccepted();
		}
		// EXIT (1233) 
		AppProperties.printObject("\n\nEXIT ("+Sun.totalCounts+")");
		AppProperties.printObject("---------------------------------------------------------------");
		
	}
	
	public static class AppProperties
	{
		public static String OUTPUT_FORMART= "output";
		
		public static String getOutputFormat()
		{
			return System.getProperty(OUTPUT_FORMART, "file");
		}
		

		public static void printObject(Object object)
		{
			printObject(object, false);
		}
		
		public static void printObject(Object object, boolean clear) {
			if (object == null) {
				return;
			}

			if (printToFileAllowed()) {
				try {
					if (clear) {
						createEmptyFile("sugarbird_assessment.txt",object.toString());
					} else {
						writeToFile("sugarbird_assessment.txt",	object.toString());
					}
				} catch (IOException e) {
					System.out.println("Failed to write to file.");
				}
			} else {
				printOnConsole(object);
			}
		}
		
		static boolean printToFileAllowed()
		{
			return getOutputFormat().equals("file");
		}
		
		static void writeToFile(String fileName,String line) throws IOException
		{
			final FileWriter fstream = new FileWriter(fileName, true);
			final BufferedWriter out = new BufferedWriter(fstream);
			out.write(line + "\n");
			out.close();
		}
		
		static void createEmptyFile(String fileName, String line) throws IOException
		{
			final FileWriter fstream = new FileWriter(fileName, false);
			final BufferedWriter out = new BufferedWriter(fstream);
			out.write(line+"\n");
			out.close();
		}
		
		static void printOnConsole(Object message)
		{
			System.out.println(message);
		}
	}
}
