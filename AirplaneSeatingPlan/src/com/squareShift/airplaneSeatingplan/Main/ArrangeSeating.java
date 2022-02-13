package com.squareShift.airplaneSeatingplan.Main;

import java.util.HashMap;

import com.squareShift.airplaneSeatingplan.Abstractions.StringConstants;
import com.squareShift.airplaneSeatingplan.Exceptions.PassengersCannotBeAccomodatedException;
import com.squareShift.airplaneSeatingplan.UtilitiesAndHelper.SeatingHelper;


/*
 * This class  provides and abstract layer over the detailed and the complex   implementation .
 * Its acts as an facade for the  detailed implementation .
 * Performs the delegation of the following tasks. 
 *  1. Seating plan Initialization.
 *  2. Seating Assignment.
 *  3. Assign passenger to the  respective  seat .
 *  4. Display the seating arrangement as well as seating with passenger number 
 */

public class ArrangeSeating {

	Object[] blocks = null;
	SeatingHelper seatingHelper = null;

	ArrangeSeating() {

	}

	ArrangeSeating(Object[] seatingArray) {

		this.blocks = seatingArray;

		if (seatingHelper == null) {
			seatingHelper = new SeatingHelper(blocks);
		}
	}
    /* This method is for initializing the Seating and  mark
     * it with A (Aisle), M (Middle), W (Window)
     */
	public void initializeSeating(Object[] masterPlan) {

		try {
			seatingHelper.iterateOverMasterSeatingPlanAndInitialize();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	/* This method is for arranging the seat assignment plan , like   creating queue for the
	 *  Aisles , Windows , Middle and  assign the seating plan according the order of preference
	 */
	public void arrangeSeatingPlan(int noOfpassengers, HashMap<Integer, String> configList) {

		try {
			seatingHelper.arrangeSeatingPlan(noOfpassengers, configList);
		} catch (PassengersCannotBeAccomodatedException e) {
			System.out.println(StringConstants.EXCEPTION_OCCURRED + e);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/* This method is for assigning the seat to the passenger generically like ,
	 * if the order  of preference is A, W,M the first  all aisles seat first 
	 * then Windows , then Middle
	 */
	public void arrangePassengersToSeats(HashMap<Integer, String> configList) {

		try {
			seatingHelper.arrangePassengersToSeats(configList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/* This method just display the seating arrangement for he dynamic 2D array
	 * 
	 */
	public void displaySeatingArrangement() {
		System.out.println(StringConstants.SEATING_ARRANGEMENT);
		seatingHelper.displaySeatingArrangement();
	}
	
	/* This method just display the seating arrangement  with properly with passenger number.
	 * If the passenger is assigned then it displays type of seat along with passenger number,
	 * If no passenger is assigned then it will show  seat type along with 0 to show it is empty.
	 */
	public void displayArrangementWithPassengers() {
		System.out.println(StringConstants.PASSESNEGRS_ARRANAGEMENT);
		seatingHelper.displayArrangementWithPassengers();

	}

}
