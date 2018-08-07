package com.sugarbird.assessment.service;

/**
 * Encapsulates the logic associated with the liquid substance contained by flower's,
 * 
 * @author molise
 * 
 */
public interface Liquid {
	/**
	 * Checks the presence of the liquid
	 * 
	 * @return <code>True</code> if the quantity of the liquid is greater than
	 *         0, <code>False</code> otherwise.
	 */
	public boolean isAvailable();

	/**
	 * Gets the amount of the liquid substance.
	 * 
	 * @return An integer representing the quantity of the liquid.
	 */
	public int getQuantity();

	/**
	 * Responsible for reducing the amount of the flower's liquid substance.
	 */
	public void reduceQuantity();

	/**
	 * Responsible for increasing the quantity of the liquid
	 * 
	 * @param quantity
	 *            The new quantity.
	 */
	public void reFill(int quantity);

	/**
	 * Gets the name of the liquid
	 * 
	 * @return The name of the flower
	 */
	public String getName();
}
