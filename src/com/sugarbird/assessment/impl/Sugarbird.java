package com.sugarbird.assessment.impl;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import org.hamcrest.core.IsInstanceOf;

import com.sugarbird.assessment.ApplicationMain;
import com.sugarbird.assessment.service.Bird;
import com.sugarbird.assessment.service.SunObserver;

/**
 * Houses the functionality associated with the Sugarbird which feeds on
 * flowers. It extends the {@code SubObserver} class to so it be informed of
 * changes of the different states of the <code>Sun</code>.
 * 
 * @author molise
 * 
 */
public class Sugarbird extends SunObserver implements Bird {

	// state of the bird ()
	private State state;

	/**
	 * Creates an instance of {@code Sugarbird}
	 * 
	 * @param name
	 *            The name of the bird
	 * @param sun
	 *            An instance of {@code Sun}.
	 */
	public Sugarbird(String name, Observable sun) {
		super(name, sun);
	}

	@Override
	public void onDayStartAction(Object action) {
		state = State.AWAKE;
		acceptAction(action);
	}

	@Override
	public void onDayEndAction(Object action) {
		state = State.SLEEPING;
		acceptAction(action);
	}

	@Override
	public void onHourChangeAction(Object action) {
		if (state.equals(State.SLEEPING)) {
			ApplicationMain.AppProperties.printObject(state.getStateName());
			acceptAction(action);
			return;
		}
		List<Observer> observers = ((Sun) sun).getObservers();

		int maxSize = observers.size();
		if (maxSize <= 1) {
			return;
		}
		final Random random = new Random();
		int randomNumber;
		Observer observer;
		while (true) {
			randomNumber = random.nextInt(maxSize);
			observer = observers.get(randomNumber);

			if (observer instanceof Flower) {
				Flower flower = (Flower) observer;

				//visit the flower
				if (flower.isBirdFeedingAllowed()) {
					Sun.totalCounts++;
					flower.printFlowerDetails();
					flower.feeding();
					acceptAction(action);
					return;
				} else {
					flower.printFlowerDetails();
					flower.deregister();
					maxSize--;
					if (maxSize == 1) {
						return;
					}
				}
			}
		}
	}

	/**
	 * Gets the current state of the bird.
	 * @return
	 * 
	 */
	public State getState() {
		return state;
	}


	/**
	 * Housekeepr for different states that {@code Sugarbird} can occupy.
	 * 
	 * @author molise
	 * 
	 */
	protected enum State {
		AWAKE, SLEEPING("SLEEP");
		private String stateName;

		/**
		 * Creates an instance of {@code State}
		 */
		private State() {
			stateName = "";
		}

		/**
		 * Creates an instance of {@code State}
		 * 
		 * @param stateName
		 *            The name of the state.
		 */
		private State(String stateName) {
			this.stateName = stateName;
		}

		/**
		 * Gets the name of the state.
		 * 
		 * @return A {@code String} representing the name of the state
		 */
		public String getStateName() {
			return stateName;
		}
	}

}
