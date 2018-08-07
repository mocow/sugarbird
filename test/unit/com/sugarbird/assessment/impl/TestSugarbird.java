package com.sugarbird.assessment.impl;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sugarbird.assessment.service.Liquid;
import com.sugarbird.assessment.service.Plant;

public class TestSugarbird {

	private Sugarbird sugarbird;
	private Sun sun;
	@Before
	public void setUp()
	{
		sun = getObservableSun();
		sugarbird = new Sugarbird("Sugarbird",sun);
	}
	
	@Test
	public void testGetName()
	{
		//set up
		//run
		String name = sugarbird.getName();
		//assert
		Assert.assertEquals("Sugarbird", name);
	}
	
	@Test
	public void testUpdate_OnDayEndAction()
	{
		//set up
		//run
		sugarbird.update(null,Sun.TimeEvent.ON_DAY_END);
		//assert
		Assert.assertEquals(Sugarbird.State.SLEEPING, sugarbird.getState());	
		
	}
	
	@Test
	public void testUpdate_OnDayStartAction()
	{
		//set up
		//run
		sugarbird.update(null,Sun.TimeEvent.ON_DAY_START);
		//assert
		Assert.assertEquals(Sugarbird.State.AWAKE, sugarbird.getState());	
		
	}
	
	@Test
	public void testUpdate_OnHourChangeAction_BirdSleeping()
	{
		//set up
		sugarbird.update(null,Sun.TimeEvent.ON_DAY_END);
		//run
		sugarbird.update(null,Sun.TimeEvent.ON_HOUR_CHANGE);
		//assert
		Assert.assertEquals(Sugarbird.State.SLEEPING, sugarbird.getState());	
		
	}
	
	@Test
	public void testUpdate_OnHourChangeAction_BirdAwake()
	{
		//set up
		sugarbird.update(null,Sun.TimeEvent.ON_DAY_START);
		sun.addObserver(sugarbird);
		Liquid nectar = new Nectar();
		nectar.reFill(10);
		Plant flower = new Flower(nectar,"FLOWER-"+0,sun);
		sun.addObserver((Flower)flower);
		Sun.totalCounts = 0;
		
		//run
		sugarbird.update(null,Sun.TimeEvent.ON_HOUR_CHANGE);
		//assert
		Assert.assertEquals(Sugarbird.State.AWAKE, sugarbird.getState());
		Assert.assertEquals(1, Sun.totalCounts);
		
	}
	
	
	@Test
	public void testUpdate_OnHourChangeAction_NoFlowersRegistered()
	{
		//set up
		sugarbird.update(null,Sun.TimeEvent.ON_DAY_START);
		sun.addObserver(sugarbird);
		Sun.totalCounts = 0;
		
		//run
		sugarbird.update(null,Sun.TimeEvent.ON_HOUR_CHANGE);
		//assert
		Assert.assertEquals(Sugarbird.State.AWAKE, sugarbird.getState());
		Assert.assertEquals(0, Sun.totalCounts);
		
	}
	
	private Sun getObservableSun()
	{
		Sun observableSun = new Sun(Sun.TimeEvent.ON_DAY_START);
		return observableSun;
	}
}
