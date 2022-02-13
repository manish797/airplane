package com.squareShift.airplaneSeatingplan.Entities;

import java.util.Objects;

import com.squareShift.airplaneSeatingplan.Abstractions.SEAT_TYPE;

public class Seat {

	private SEAT_TYPE seatType;
	private boolean seatOccupied;
	private int assignedValue;

	public Seat() {
	}

	public Seat(SEAT_TYPE seatType, boolean seatOccupied, int assignedValue) {
		super();
		this.seatType = seatType;
		this.seatOccupied = seatOccupied;
		this.assignedValue = assignedValue;
	}

	@Override
	public String toString() {
		return seatType + " " + assignedValue;
	}

	@Override
	public int hashCode() {
		return Objects.hash(seatOccupied, seatType);
	}

	public Seat(SEAT_TYPE seatType, boolean seatOccupied) {
		super();
		this.seatType = seatType;
		this.seatOccupied = seatOccupied;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Seat)) {
			return false;
		}
		Seat other = (Seat) obj;
		return seatOccupied == other.seatOccupied && seatType == other.seatType;
	}

	/**
	 * @return the seatOccupied
	 */
	public boolean isSeatOccupied() {
		return seatOccupied;
	}

	/**
	 * @param seatOccupied the seatOccupied to set
	 */
	public void setSeatOccupied(boolean seatOccupied) {
		this.seatOccupied = seatOccupied;
	}

	/**
	 * @return the assignedValue
	 */
	public int getAssignedValue() {
		return assignedValue;
	}

	/**
	 * @param assignedValue the assignedValue to set
	 */
	public void setAssignedValue(int assignedValue) {
		this.assignedValue = assignedValue;
	}

	/**
	 * @return the seatType
	 */
	public SEAT_TYPE getSeatType() {
		return seatType;
	}

	/**
	 * @param seatType the seatType to set
	 */
	public void setSeatType(SEAT_TYPE seatType) {
		this.seatType = seatType;
	}

}