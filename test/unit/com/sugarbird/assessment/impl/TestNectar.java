package com.sugarbird.assessment.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestNectar {

	private Nectar nectar;
	@Before
	public void setUp()
	{
		nectar = new Nectar();
	}
	
	@Test
	public void testIsAvailable_False()
	{
		//set up
		nectar.reFill(0);
		//run
		boolean status = nectar.isAvailable();
		
		//assert
		Assert.assertEquals(false, status);
	}
	
	@Test
	public void testIsAvailable_True()
	{
		//set up
		nectar.reFill(10);
		//run
		boolean status = nectar.isAvailable();
		
		//assert
		Assert.assertEquals(true, status);
	}
	
	@Test
	public void testGetName()
	{
		//set up
		
		//run
		String name = nectar.getName();
		
		//assert
		Assert.assertEquals("Nectar", name);
	}
	
	@Test
	public void testGetQuantity()
	{
		//set up
		nectar.reFill(2);
		//run
		int quantity = nectar.getQuantity();
		
		//assert
		Assert.assertEquals(2, quantity);
	}
	
	@Test
	public void testReFill_NegativeValue()
	{
		//set up
		nectar.reFill(10);
		//run
		nectar.reFill(-3);
		//assert
		Assert.assertEquals(10, nectar.getQuantity());
	}
	
	@Test
	public void testReduceQuantity()
	{
		//set up
		nectar.reFill(10);
		//run
		nectar.reduceQuantity();
		//assert
		Assert.assertEquals(9, nectar.getQuantity());
	}
}
