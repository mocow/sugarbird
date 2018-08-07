package com.sugarbird.assessment.impl;

import com.sugarbird.assessment.service.Liquid;

/**
 * Encapsulates the logic associated with the flower's liquid substance, Nectar,
 * 
 * @author molise
 * 
 */
public class Nectar implements Liquid {

	private int quantity = 0;

	@Override
	public boolean isAvailable() {
		return quantity > 0;
	}

	@Override
	public String getName() {
		return "Nectar";
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public void reFill(int quantity) {
		if (quantity > -1) {
			this.quantity = quantity;
		}
	}

	@Override
	public void reduceQuantity() {
		if (quantity > 0) {
			quantity--;
		}
	}
}
