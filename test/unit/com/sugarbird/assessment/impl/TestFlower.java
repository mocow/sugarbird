package com.sugarbird.assessment.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sugarbird.assessment.service.Liquid;

public class TestFlower {
	Flower flower;
	
	@Before
	public void setUp()
	{
		Liquid nectar = new Nectar();
		flower = new Flower(nectar, "flower", null);
	}
	
	@Test
	public void testGetName()
	{
		//set up
		
		//run
		String name = flower.getName();
		//Assert
		Assert.assertEquals("flower", name);
		
	}
	
	@Test
	public void testGetLiquid()
	{
		//set up
		
		//run
		Liquid nectar = flower.getLiquid();
		//Assert
		Assert.assertNotNull(nectar);
		Assert.assertEquals("Nectar", nectar.getName());
		
	}
	
	@Test
	public void testUpdate_OnDayStartAction()
	{
		//set up
		flower.getLiquid().reFill(10);
		//run
		flower.update(null,Sun.TimeEvent.ON_DAY_START);
		//Assert
		Assert.assertEquals(Flower.State.OPEN, flower.getState());
	}
	

	@Test
	public void testUpdate_OnDayEndAction()
	{
		//set up
		flower.getLiquid().reFill(10);
		//run
		flower.update(null,Sun.TimeEvent.ON_DAY_END);
		//Assert
		Assert.assertEquals(Flower.State.CLOSED, flower.getState());
		
	}
	
	@Test
	public void testUpdate_OnHourChangeAction()
	{
		//set up
		flower.getLiquid().reFill(10);
		flower.update(null,Sun.TimeEvent.ON_DAY_START);
		
		//run
		flower.update(null,Sun.TimeEvent.ON_HOUR_CHANGE);
		//Assert
		Assert.assertEquals(Flower.State.OPEN, flower.getState());	
	}
	
	@Test
	public void testIsBirdFeedingAllowed_True()
	{
		//set up
		flower.getLiquid().reFill(10);
		
		//run
		boolean status = flower.isBirdFeedingAllowed();
		//Assert
		Assert.assertEquals(true, status);		
	}
	
	@Test
	public void testIsBirdFeedingAllowed_False()
	{
		//set up
		flower.getLiquid().reFill(0);
		
		//run
		boolean status = flower.isBirdFeedingAllowed();
		//Assert
		Assert.assertEquals(false, status);		
	}
	
	@Test
	public void testFeeding_NectarFinished()
	{
		//set up
		flower.getLiquid().reFill(1);
		flower.update(null,Sun.TimeEvent.ON_DAY_START);
		
		//run
		flower.feeding();
		//Assert
		Assert.assertEquals(Flower.State.CLOSED, flower.getState());	
	}
	
	@Test
	public void testFeeding_NectarRemaining()
	{
		//set up
		flower.getLiquid().reFill(3);
		flower.update(null,Sun.TimeEvent.ON_DAY_START);
		
		//run
		flower.feeding();
		//Assert
		Assert.assertEquals(2, flower.getLiquid().getQuantity());
		Assert.assertEquals(Flower.State.OPEN, flower.getState());	
	}
}
