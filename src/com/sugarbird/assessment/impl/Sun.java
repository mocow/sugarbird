package com.sugarbird.assessment.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import com.sugarbird.assessment.ApplicationMain;

/**
 * This class represents an observable <code>Sun</code> object which is
 * responsible for generating time events during the day and night. The events
 * are observed by objects from the {@code Flower} and {@code Sugarbird} class.
 * 
 * @author molise
 * 
 */
public class Sun extends Observable {

	// the current time event generated
	private TimeEvent event;
	// the time on which the event is generated
	protected static int eventHour;
	// collection of registered observers
	private List<Observer> observers;
	// total count of observers
	private int observersCount;

	protected boolean isMidNight;
	protected boolean isMidDay;
	public static int totalCounts = 0;
	State state;
	/**
	 * Creates an instance of the {@code Sun}
	 * 
	 * @param event
	 *            The initial event raised.
	 */
	public Sun(TimeEvent event) {
		if (event == null) {
			throw new NullPointerException("Null TimeEvent is not allowed");
		}

		this.event = event;
		Sun.eventHour = 0;
		observers = new ArrayList<Observer>();
		observersCount = 0;
		isMidNight = false;
		isMidDay = false;
		state = State.OFF;
	}

	public void addObserver(Observer observer) {
		if (observer == null) {
			throw new NullPointerException("Null TimeEvent is not allowed");
		}
		observers.add(observer);
		observersCount++;
	}

	public void notifyObservers(Object event) {
		for (int index = 0; index < observersCount; index++) {
			observers.get(index).update(this, event);
		}
	}

	public void deleteObserver(Observer o) {
		observers.remove(o);
		observersCount--;
	}
	
	/**
	 * Responsible for generating the events and notifying the registered
	 * observers.
	 */
	public void processTimeEvent(TimeEvent event) {
		String newLine = "\n";
		if (State.OFF.equals(state)) {
			if (TimeEvent.ON_HOUR_CHANGE.equals(this.event)
					&& TimeEvent.ON_HOUR_CHANGE.equals(event)) {
				newLine = "";
			}
		}
		this.event = event;

		printSunTimeEventDetails(newLine, "");
		notifyObservers(event);
		if (!isMidNight && !isMidDay) {
			Sun.eventHour++;
		}
	}

	/**
	 * Responsible for generating the events.
	 * @return
	 */
	public TimeEvent generateTimeEvent()
	{
		TimeEvent currentEvent  = TimeEvent.ON_HOUR_CHANGE;
		if (Sun.eventHour +1 == Constant._24_HOURS) {
			if(TimeEvent.ON_DAY_END.equals(event))
			{
				Sun.eventHour = 0;
				isMidNight = false;
			}
			else
			{
				currentEvent = TimeEvent.ON_DAY_END;
				isMidNight = true;
				return currentEvent;
			}
		}
		TimeEvent.resetTimeEvents();
		if (Sun.eventHour == 0) {
			isMidDay = false;
			state = State.ON;
			if(!isMidNight)
			{
				currentEvent = TimeEvent.ON_DAY_START;
				isMidNight = true;
			}
			else
			{
				isMidNight = false;
			}
		}
		else if ((Sun.eventHour +1 == Constant._12_HOURS)) {
			isMidNight = false;
			
			if(!isMidDay)
			{
				isMidDay = true;
			}
			else if(TimeEvent.ON_HOUR_CHANGE.equals(event))
			{
				currentEvent = TimeEvent.ON_DAY_END;
				state = State.OFF;
			}
			else
			{
				isMidDay = false;
			}
		}
		return currentEvent;
	}
	
	
	/**
	 * Gets the current event generated.
	 * 
	 * @return
	 */
	public TimeEvent getTimeEvent() {
		return event;
	}

	/**
	 * Gets the collection of registered observers
	 * 
	 * @return A list of of observers.
	 */
	public List<Observer> getObservers() {
		return observers;
	}

	/**
	 * Prints out the details of the current {@link Sun#event} into a file or
	 * console.
	 * 
	 * @param additionalData
	 *            The additional data to be appended when printing event data.
	 */
	public void printSunTimeEventDetails(String additionalData) {
		printSunTimeEventDetails("", additionalData);
	}

	/**
	 * Prints out the details of the current {@link Sun#event} into a file or
	 * console.
	 * 
	 * @param prefixData
	 *            The additional data to be prepended when printing event data.
	 * @param additionalData
	 *            The additional data to be appended when printing event data.
	 */
	public void printSunTimeEventDetails(String prefixData,
			String additionalData) {
		ApplicationMain.AppProperties.printObject(prefixData + event.toString()
				+ additionalData);
	}

	/**
	 * Housekeeper for all the events associated with {@link Sun}.
	 * 
	 * @author molise
	 * 
	 */
	public enum TimeEvent {
		ON_DAY_START("DAY START"), ON_DAY_END("DAY END"), ON_HOUR_CHANGE(
				"HOUR CHANGE");
		private String eventName;
		private boolean accepted;

		/**
		 * Creates an instance of {@link TimeEvent}.
		 * 
		 * @param eventName
		 *            The name of the event.
		 */
		private TimeEvent(String eventName) {
			this.eventName = eventName;
		}

		/**
		 * Gets the name of the timer event.
		 * 
		 * @return The name of the timer event.
		 */
		public String getEventName() {
			return eventName;
		}

		/**
		 * Gets the acceptance state of the event.
		 * 
		 * @return A boolean value indicating whether the eveny was accepated or
		 *         not.
		 */
		public boolean isAccepted() {
			return accepted;
		}

		/**
		 * Sets the acceptance state of the event.
		 * 
		 * @param accepted
		 *            A boolean value indicating whether the eveny was accepated
		 *            or not.
		 */
		public void setAccepted(boolean accepted) {
			this.accepted = accepted;
		}

		/**
		 * Converts the time event to {@link String}.
		 */
		public String toString() {
			return eventName + " (" + eventHour + ")";
		}

		/**
		 * Clears the acceptance state of the events.
		 */
		public static void resetTimeEvents() {
			for (TimeEvent event : TimeEvent.values()) {
				event.setAccepted(false);
			}
		}
	}

	/**
	 * Houses the states of the Sun.
	 * @author molise
	 *
	 */
	private enum State
	{
		ON,OFF;
	}
	/**
	 * Housekeeper for the constants associated with the {@link Sun} class.
	 * 
	 * @author molise
	 * 
	 */
	public static class Constant {
		public static int _12_HOURS = 12;
		public static int _24_HOURS = 24;
	}
}
