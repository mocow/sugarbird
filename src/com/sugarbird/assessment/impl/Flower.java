package com.sugarbird.assessment.impl;

import java.util.Observable;

import com.sugarbird.assessment.ApplicationMain;
import com.sugarbird.assessment.service.SunObserver;
import com.sugarbird.assessment.service.Liquid;
import com.sugarbird.assessment.service.Plant;

/**
 * Houses the functionality between different types of flowers which contains
 * liquid substance. It extends the {@code SubObserver} class to so it be
 * informed of changes of the different states of the <code>Sun</code>.
 * 
 * @author molise
 * 
 */
public class Flower extends SunObserver implements Plant {

	// liquid substance contain by the flower
	private Liquid liquid;
	// the state of the flower
	private State state;

	/**
	 * Creates an instance of {@code Flower}
	 * 
	 * @param name
	 *            The name of the flower
	 * @param sun
	 *            An instance of {@code Sun}.
	 */
	public Flower(String name, Observable sun) {
		super(name, sun);
	}

	/**
	 * Creates an instance of {@code Flower}
	 * 
	 * @param liquid
	 *            Liquid substance contain by the flower
	 * @param name
	 *            The name of the flower
	 * @param sun
	 *            An instance of {@code Sun}.
	 */
	public Flower(Liquid liquid, String name, Observable sun) {
		this(name, sun);
		this.liquid = liquid;
		this.name = name;
	}

	/**
	 * Gets Liquid substance contain by the flower
	 * 
	 * @return An instance of {@link Liquid}
	 * 
	 */
	public Liquid getLiquid() {
		return liquid;
	}

	/**
	 * Gets the state of the flower
	 * 
	 * @return An instance of {@link State}
	 */
	public State getState() {
		return state;
	}

	@Override
	public void onDayStartAction(Object action) {
		if (liquid.isAvailable()) {
			state = State.OPEN;
			acceptAction(action);
		}
	}

	@Override
	public void onDayEndAction(Object action) {
		
		state = State.CLOSED;
		acceptAction(action);
		
	}

	@Override
	public void onHourChangeAction(Object action) {

	}

	/**
	 * Prints out the details of the flower into a file or console.
	 */
	public void printFlowerDetails() {
		ApplicationMain.AppProperties.printObject(this.name + " ("
				+ liquid.getQuantity() + ")");
	}

	/**
	 * Checks whether the flower contains enough liquid for the birds to feed
	 * on.
	 * 
	 * @return
	 */
	public boolean hasNectarAvaiable() {
		if (!liquid.isAvailable()) {
			return false;
		}
		return true;
	}

	/**
	 * Reduces the quantity of the flower's liquid. It simulates the process of
	 * bird feeding on the flower.
	 */
	public void birdFeeding() {
		liquid.reduceQuantity();
		if (!liquid.isAvailable()) {
			state = State.CLOSED;
		}
	}

	/**
	 * Contains different states of the flower.
	 * 
	 * @author molise
	 * 
	 */
	protected enum State {
		OPEN, CLOSED, NOTIFY_BIRD;
	}
}
