package com.limscrudu.j2atm.model;

/**
 * @author limcearna
 * 
 *         An amount represented by a note of a particular denomination and a
 *         quantity of the note
 *
 */
public class NoteNQuantity {

	Integer denomination;
	Integer quantity;

	public NoteNQuantity(Integer denomination, Integer quantity) {
		this.denomination = denomination;
		this.quantity = quantity;
	}

	/**
	 * @return the note
	 */
	public Integer getDenomination() {
		return denomination;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setDenomination(Integer note) {
		this.denomination = note;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
