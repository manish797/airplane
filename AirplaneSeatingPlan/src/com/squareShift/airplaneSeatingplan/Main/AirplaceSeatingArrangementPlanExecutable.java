package com.squareShift.airplaneSeatingplan.Main;

import java.util.HashMap;
import java.util.Scanner;
import com.squareShift.airplaneSeatingplan.Abstractions.StringConstants;
import com.squareShift.airplaneSeatingplan.Entities.Seat;

public class AirplaceSeatingArrangementPlanExecutable {

	static Object[] masterSeatPlan;
	static HashMap<Integer, String> seatingPreference;
	static int numberOfSeatingGroup;
	static int numberOfRows;
	static int numberOfColumns;
	static int noOfPassenegrs;

	private void takingUserInputs() {
		Scanner sc = null;
		try {
			sc = new Scanner(System.in);
			System.out.println("Enter number of seating groups ");
			numberOfSeatingGroup = takingInput(sc, StringConstants.FOR_SEATING_GROUP, numberOfSeatingGroup);
			Object[] blockArray = new Object[numberOfSeatingGroup];

			for (int i = 0; i < numberOfSeatingGroup; i++) {

				System.out.println("Enter the row and column of seating group ");
				numberOfRows = takingInput(sc, StringConstants.FOR_NUMBER_OF_ROWS_IN_A_GROUP, numberOfRows);
				numberOfColumns = takingInput(sc, StringConstants.FOR_NUMBER_OF_COLUMNS_IN_A_GROUP, numberOfColumns);
				Seat[][] testArray = new Seat[numberOfRows][numberOfColumns];
				blockArray[i] = testArray;
			}

			System.out.println("Enter the number of Passengers waiting in  queue ");

			noOfPassenegrs = takingInput(sc, StringConstants.FOR_NUMBER_OF_PASSESNGERS, noOfPassenegrs);

			System.out.println("Below are the seating group sizes and Passenger Size");

			for (int i = 0; i < blockArray.length; i++) {

				Seat[][] seatArray = (Seat[][]) blockArray[i];

				System.out.println(seatArray.length + " " + seatArray[0].length);

			}
			masterSeatPlan = blockArray;
			System.out.println("Passenegers Size :" + noOfPassenegrs);
		} finally {
			sc.close();
		}

	}

	public static int takingInput(Scanner sc, String message, int valueToInitialize) {
		String value = null;
		try {
			value = sc.next();
			valueToInitialize = Integer.parseInt(value);
			if (valueToInitialize <= 0)
				throw new Exception();

		} catch (Exception e) {
			System.out.println(StringConstants.ENTER_ABSOLUTE_INTEGER + message);
			return takingInput(sc, message, valueToInitialize);

		}
		return valueToInitialize;

	}

	public static void main(String[] args) {

		AirplaceSeatingArrangementPlanExecutable executeSeatingSystem = new AirplaceSeatingArrangementPlanExecutable();
		executeSeatingSystem.takingUserInputs();

		seatingPreference = new HashMap<>();
		seatingPreference.put(1, "M");
		seatingPreference.put(2, "W");
		seatingPreference.put(3, "A");

		executeSeatingSystem.executeSeating();

	}

	private void executeSeating() {

		ArrangeSeating arrangeSeating = new ArrangeSeating(masterSeatPlan);
		arrangeSeating.initializeSeating(masterSeatPlan);
		arrangeSeating.arrangeSeatingPlan(noOfPassenegrs, seatingPreference);
		arrangeSeating.displaySeatingArrangement();
		System.out.println(StringConstants.ORDER_PREFERENCE);
		System.out.println(AirplaceSeatingArrangementPlanExecutable.seatingPreference);
		arrangeSeating.arrangePassengersToSeats(seatingPreference);
		arrangeSeating.displayArrangementWithPassengers();
	}

}
