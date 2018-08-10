package com.sugarbird.assessment.impl;


import java.util.Observable;
import java.util.Observer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sugarbird.assessment.impl.Sun.TimeEvent;
import com.sugarbird.assessment.service.Liquid;
import com.sugarbird.assessment.service.Plant;

public class TestSun {

	private Sun sun;
	@Before
	public void setUp()
	{
		sun = new Sun(Sun.TimeEvent.ON_DAY_START);
	}
	
	@Test
	public void testConstructor_NullPointer()
	{
		//run
		try
		{
			sun = new Sun(null);
			Assert.fail("The test should fail. NullPointer exception not throw");
		}
		catch(NullPointerException ex)
		{
			
		}
	}
	
	@Test
	public void testConstructor()
	{
		//run
		sun = new Sun(Sun.TimeEvent.ON_DAY_START);
		//assert
		Assert.assertEquals(Sun.TimeEvent.ON_DAY_START, sun.getTimeEvent());
		Assert.assertEquals(0, sun.getObservers().size());
	}
	
	@Test
	public void testAddObserver_NullPointer()
	{
		//run
		try
		{
			sun.addObserver(null);
			Assert.fail("The test should fail. NullPointer exception not throw");
		}
		catch(NullPointerException ex)
		{
			
		}
	}
	
	@Test
	public void testAddObserver()
	{
		//
		Liquid nectar = new Nectar();
		nectar.reFill(10);
		Plant flower = new Flower(nectar,"FLOWER-"+0,sun);
		
		//run
		sun.addObserver((Flower)flower);
		//assert
		Assert.assertEquals(1, sun.getObservers().size());
	}
	
	@Test
	public void testDeleteObserver()
	{
		// set up
		setObservers();
		Observer flowerToRemove = sun.getObservers().get(1);
		
		//run
		sun.deleteObserver(flowerToRemove);
		//assert
		Assert.assertEquals(2, sun.getObservers().size());
		Assert.assertEquals("FLOWER-0", ((Flower)sun.getObservers().get(0)).getName());
		Assert.assertEquals("FLOWER-2", ((Flower)sun.getObservers().get(1)).getName());
	}
	
	@Test
	public void testNotifyObservers()
	{
		// set up
		setObservers();
		//run
		sun.notifyObservers(Sun.TimeEvent.ON_DAY_START);
		//assert
		Assert.assertEquals(true, ((FlowerForTesting)sun.getObservers().get(0)).isUpdated());
		Assert.assertEquals(true, ((FlowerForTesting)sun.getObservers().get(1)).isUpdated());
		Assert.assertEquals(true, ((FlowerForTesting)sun.getObservers().get(2)).isUpdated());
	}
	
	@Test
	public void testProcessTimeEvent_OnDayStart_OnHourChange_0()
	{
		// set up
		setObservers();
		Sun.eventHour = 0;
		TimeEvent event = sun.generateTimeEvent();
		//run
		sun.processTimeEvent(event);
		//assert
		Assert.assertEquals(1, ((FlowerForTesting)sun.getObservers().get(0)).getNotificationCount());
		Assert.assertEquals(1, ((FlowerForTesting)sun.getObservers().get(1)).getNotificationCount());
		Assert.assertEquals(1, ((FlowerForTesting)sun.getObservers().get(2)).getNotificationCount());
	}
	
	@Test
	public void testProcessTimeEvent_OnDayEnd_OnHourChange_12()
	{
		// set up
		setObservers();
		Sun.eventHour = 12;
		TimeEvent event = sun.generateTimeEvent();
		//run
		sun.processTimeEvent(event);
		//assert
		Assert.assertEquals(1, ((FlowerForTesting)sun.getObservers().get(0)).getNotificationCount());
		Assert.assertEquals(1, ((FlowerForTesting)sun.getObservers().get(1)).getNotificationCount());
		Assert.assertEquals(1, ((FlowerForTesting)sun.getObservers().get(2)).getNotificationCount());
	}
	
	@Test
	public void testProcessTimeEvent_OnHourChange_15()
	{
		// set up
		setObservers();
		Sun.eventHour = 15;
		TimeEvent event = sun.generateTimeEvent();
		//run
		sun.processTimeEvent(event);
		//assert
		Assert.assertEquals(1, ((FlowerForTesting)sun.getObservers().get(0)).getNotificationCount());
		Assert.assertEquals(1, ((FlowerForTesting)sun.getObservers().get(1)).getNotificationCount());
		Assert.assertEquals(1, ((FlowerForTesting)sun.getObservers().get(2)).getNotificationCount());
	}
	
	@Test
	public void testGenerateTimeEvent_Hour_15()
	{
		// set up
		Sun.eventHour = 15;
		//run
		TimeEvent event = sun.generateTimeEvent();
		//assert
		Assert.assertEquals(TimeEvent.ON_HOUR_CHANGE, event);
	}
	
	@Test
	public void testGenerateTimeEvent_Hour_0()
	{
		// set up
		Sun.eventHour = 0;
		//run
		TimeEvent event = sun.generateTimeEvent();
		//assert
		Assert.assertEquals(TimeEvent.ON_DAY_START, event);
	}
	
	@Test
	public void testGenerateTimeEvent_Hour_11_HourChange()
	{
		// set up
		Sun.eventHour = 11;
		//run
		TimeEvent event = sun.generateTimeEvent();
		//assert
		Assert.assertEquals(TimeEvent.ON_HOUR_CHANGE, event);
	}
	
	@Test
	public void testGenerateTimeEvent_Hour_11_DayEnd()
	{
		// set up
		sun = new Sun(TimeEvent.ON_HOUR_CHANGE);
		Sun.eventHour = 11;
		sun.isMidDay = true;
		
		//run
		TimeEvent event = sun.generateTimeEvent();
		//assert
		Assert.assertEquals(TimeEvent.ON_DAY_END, event);
	}
	
	@Test
	public void testGenerateTimeEvent_Hour_11_SLEEP()
	{
		// set up
		sun = new Sun(TimeEvent.ON_DAY_END);
		Sun.eventHour = 11;
		sun.isMidDay = true;
		
		//run
		TimeEvent event = sun.generateTimeEvent();
		//assert
		Assert.assertEquals(TimeEvent.ON_HOUR_CHANGE, event);
	}
	
	@Test
	public void testGenerateTimeEvent_OnDayStart_OnHourChnage()
	{
		// set up
		Sun.eventHour = 0;
		//run
		TimeEvent event1 = sun.generateTimeEvent();
		TimeEvent event2 = sun.generateTimeEvent();
		//assert
		Assert.assertEquals(TimeEvent.ON_DAY_START, event1);
		Assert.assertEquals(TimeEvent.ON_HOUR_CHANGE, event2);
	}
	
	
	private void setObservers()
	{
		Liquid nectar = new Nectar();
		nectar.reFill(10);
		Plant flower = new FlowerForTesting(nectar,"FLOWER-0",sun);
		sun.addObserver((FlowerForTesting)flower);
		nectar = new Nectar();
		nectar.reFill(10);
		flower = new FlowerForTesting(nectar,"FLOWER-1",sun);
		sun.addObserver((FlowerForTesting)flower);
		nectar = new Nectar();
		nectar.reFill(10);
		flower = new FlowerForTesting(nectar,"FLOWER-2",sun);
		sun.addObserver((FlowerForTesting)flower);
	}
	private class FlowerForTesting extends Flower
	{
		private boolean notified;
		private int notificationCount=0;
		public FlowerForTesting(String name, Observable sun) {
			super(name, sun);
		}
		
		private FlowerForTesting(Liquid liquid, String name, Observable sun) {
			super(liquid, name, sun);
		}


		@Override
		public void update(Observable arg0, Object action) {
			notified = true;
			notificationCount++;
		}

		public boolean isUpdated() {
			return notified;
		}

		public int getNotificationCount() {
			return notificationCount;
		}
		
		
	}
}
