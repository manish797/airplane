package com.squareShift.airplaneSeatingplan.UtilitiesAndHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import com.squareShift.airplaneSeatingplan.Abstractions.SEAT_TYPE;
import com.squareShift.airplaneSeatingplan.Entities.Seat;
import com.squareShift.airplaneSeatingplan.Exceptions.PassengersCannotBeAccomodatedException;
import com.squareShift.airplaneSeatingplan.Exceptions.SeatingArrangementException;
import com.squareShift.airplaneSeatingplan.Exceptions.WrongSeatingConfigurationException;

/*
 * This class provides  the detailed implementation and assignment
 * of the  seats  to display 
 */
public class SeatingHelper {

	private Queue<Integer> aislesSet = null;
	private Queue<Integer> windowsSet = null;
	private Queue<Integer> middlesSet = null;
	private int totalAisles;
	private int totalWindows;
	private int totalMiddles;
	private int noOfPassengers;
	private int seatCounter = 1;
	private Object blocks[] = null;

	public SeatingHelper(Object[] masterBlock) {

		if (masterBlock != null) {
			this.blocks = masterBlock;
		}
		aislesSet = new LinkedList<>();
		windowsSet = new LinkedList<>();
		middlesSet = new LinkedList<>();
	}

	public void initializeTheSeatingBlock(int row, int col, Object[] obj, int counter, int lengthOfBlocks)
			throws SeatingArrangementException {
		try {
			Seat[][] seatBlock = null;
			this.blocks = obj;
			if (row <= 0 || col <= 0) {

				throw new SeatingArrangementException("Rows and Columns size should greater than 0  for seating group");
			}

			seatBlock = new Seat[row][col];
			initializeArrangeSeats(seatBlock, counter, lengthOfBlocks, obj);
		} catch (Exception e) {
			System.out.println("EXCETION MESSAGE in SeatingHelper.initializeTheSeatingBlock==> " + e.getCause());
		}

	}

	public void iterateOverMasterSeatingPlanAndInitialize() {
		try {
			for (int blockNum = 0; blockNum <= blocks.length - 1; blockNum++) {

				Seat[][] seatBlock = (Seat[][]) blocks[blockNum];
				initializeArrangeSeats(seatBlock, blockNum + 1, blocks.length, blocks);

			}
		} catch (Exception e) {
			System.out.println(
					"EXCETION MESSAGE in SeatingHelper.iterateOverMasterSeatingPlanAndInitialize==> " + e.getCause());
		}

	}

	private void initializeArrangeSeats(Seat[][] seatBlock, int counter, int lengthOfBlocks, Object[] obj) {
		try {

			for (int i = 0; i <= seatBlock.length - 1; i++) {

				for (int j = 0; j <= seatBlock[i].length - 1; j++) {
					seatBlock[i][j] = new Seat();
					if (j == 0 && counter == 1) {
						seatBlock[i][j].setSeatType(SEAT_TYPE.W);
						totalWindows++;
					} else if (j == seatBlock[i].length - 1 && counter == lengthOfBlocks) {
						seatBlock[i][j].setSeatType(SEAT_TYPE.W);
						totalWindows++;
					} else if (j == 0 || j == seatBlock[i].length - 1) {
						seatBlock[i][j].setSeatType(SEAT_TYPE.A);
						totalAisles++;
					} else {
						seatBlock[i][j].setSeatType(SEAT_TYPE.M);
						totalMiddles++;
					}

				}

			}
			obj[counter - 1] = seatBlock;
		} catch (Exception e) {
			System.out.println("EXCETION MESSAGE in SeatingHelper.initializeArrangeSeats ==> " + e.getCause());
		}
	}

	public void arrangePassengersToSeats(HashMap<Integer, String> configList) {
		try {
			if (configList != null) {

				for (int i = 1; i < 4; i++) {
					try {
						arrangePassenegrsByPatternType(configList.get(i));
					} catch (WrongSeatingConfigurationException e) {

						System.out.println(" EXCEPTION OCCURRED ===>" + e);
					}

				}

			}
		} catch (Exception e) {
			System.out.println("EXCETION MESSAGE in SeatingHelper.arrangePassengersToSeats ==> " + e.getCause());
		}

	}

	private void arrangePassenegrsByPatternType(String preference) throws WrongSeatingConfigurationException {

		if (preference != null) {
			switch (preference) {

			case "A":
				assignAislesSeatPasssengers();
				break;
			case "W":
				assignWindowsSeatPasssengers();
				break;
			case "M":
				assignMiddleSeatPasssengers();
				break;
			default:
				throw new WrongSeatingConfigurationException(
						"Seating preference should of one of  the type among A (Aisle) , W (Window) , M (Middle)");
			}
		}

	}

	private void assignMiddleSeatPasssengers() {
		try {

			if (middlesSet.isEmpty())
				return;

			int maxRows = 0;
			for (int i = 0; i <= blocks.length - 1; i++) {

				Seat[][] seat = (Seat[][]) blocks[i];

				int h = seat.length;
				if (h > maxRows) {
					maxRows = h;
				}
			}

			int rowCount = 0;

			while (!middlesSet.isEmpty() && rowCount < maxRows) {

				for (int i = 0; i <= blocks.length - 1; i++) {
					Seat[][] seat = (Seat[][]) blocks[i];

					for (int k = 0; k < seat[0].length - 1; k++) {

						if (rowCount <= seat.length - 1 && seat[rowCount][k].getSeatType().equals(SEAT_TYPE.M)
								&& middlesSet.size() > 0) {

							if (!seat[rowCount][k].isSeatOccupied()) {
								seat[rowCount][k].setAssignedValue(middlesSet.poll());
								seat[rowCount][k].setSeatOccupied(true);
							}

						}

					}

				}
				rowCount++;
			}
		} catch (Exception e) {
			System.out.println("EXCETION MESSAGE in SeatingHelper.assignMiddleSeatPasssengers ==> " + e.getCause());
		}

	}

	private void assignWindowsSeatPasssengers() {
		try {
			if (windowsSet.isEmpty())
				return;

			int maxRows = 0;
			for (int i = 0; i <= blocks.length - 1; i++) {

				Seat[][] seat = (Seat[][]) blocks[i];

				int h = seat.length;
				if (h > maxRows) {
					maxRows = h;
				}
			}

			int rowCount = 0;

			while (!windowsSet.isEmpty() && rowCount < maxRows) {

				for (int i = 0; i <= blocks.length - 1; i++) {

					Seat[][] seat = (Seat[][]) blocks[i];

					if (i == 0 && rowCount <= seat.length - 1) {

						if (!seat[rowCount][0].isSeatOccupied() && windowsSet.size() > 0
								&& seat[rowCount][0].getSeatType().equals(SEAT_TYPE.W)) {
							seat[rowCount][0].setAssignedValue(windowsSet.poll());
							seat[rowCount][0].setSeatOccupied(true);
						}

					}

					else if (i == blocks.length - 1 && rowCount <= seat.length - 1 && windowsSet.size() > 0
							&& seat[rowCount][seat[rowCount].length - 1].getSeatType().equals(SEAT_TYPE.W)) {

						if (!seat[rowCount][seat[rowCount].length - 1].isSeatOccupied()) {
							seat[rowCount][seat[rowCount].length - 1].setAssignedValue(windowsSet.poll());
							seat[rowCount][seat[rowCount].length - 1].setSeatOccupied(true);
						}
					}
				}
				rowCount++;

			}
		} catch (Exception e) {
			System.out.println("EXCEPTION MESSAGE in SeatingHelper.assignWindowsSeatPasssengers ==> " + e.getCause());
		}

	}

	private void assignAislesSeatPasssengers() {
		try {
			if (aislesSet.isEmpty())
				return;

			int maxRows = 0;
			for (int i = 0; i <= blocks.length - 1; i++) {

				Seat[][] seat = (Seat[][]) blocks[i];

				int h = seat.length;
				if (h > maxRows) {
					maxRows = h;
				}
			}

			int rowCount = 0;

			while (!aislesSet.isEmpty() && rowCount < maxRows) {

				for (int i = 0; i <= blocks.length - 1; i++) {

					Seat[][] seat = (Seat[][]) blocks[i];

					if (i == 0 && rowCount <= seat.length - 1) {

						if (!seat[rowCount][seat[rowCount].length - 1].isSeatOccupied() && aislesSet.size() > 0
								&& seat[rowCount][seat[rowCount].length - 1].getSeatType().equals(SEAT_TYPE.A)) {
							seat[rowCount][seat[rowCount].length - 1].setAssignedValue(aislesSet.poll());
							seat[rowCount][seat[rowCount].length - 1].setSeatOccupied(true);
						}

					}

					else if (i == blocks.length - 1 && rowCount <= seat.length - 1 && aislesSet.size() > 0
							&& seat[rowCount][0].getSeatType().equals(SEAT_TYPE.A)) {

						if (!seat[rowCount][0].isSeatOccupied()) {
							seat[rowCount][0].setAssignedValue(aislesSet.poll());
							seat[rowCount][0].setSeatOccupied(true);
						}
					} else {

						if (rowCount <= seat.length - 1 && aislesSet.size() > 0
								&& seat[rowCount][seat[rowCount].length - 1].getSeatType().equals(SEAT_TYPE.A)) {

							if (!seat[rowCount][0].isSeatOccupied()) {
								seat[rowCount][0].setAssignedValue(aislesSet.poll());
								seat[rowCount][0].setSeatOccupied(true);
							}

							if (!seat[rowCount][seat[rowCount].length - 1].isSeatOccupied() && aislesSet.size() > 0) {
								seat[rowCount][seat[rowCount].length - 1].setAssignedValue(aislesSet.poll());
								seat[rowCount][seat[rowCount].length - 1].setSeatOccupied(true);
							}
						}
					}

				}
				rowCount++;

			}
		} catch (Exception e) {
			System.out.println("EXCETION MESSAGE in SeatingHelper.assignAislesSeatPasssengers ==> " + e.getCause());
		}
	}

	public void arrangeSeatingPlan(int noOfpassengers, HashMap<Integer, String> configList)
			throws PassengersCannotBeAccomodatedException {
		int totalSeats = totalAisles + totalMiddles + totalWindows;
		this.noOfPassengers = noOfpassengers;
		if (noOfpassengers > totalSeats)
			throw new PassengersCannotBeAccomodatedException("Passengers size is greater than the seat avialable");

		configureSeatingPlan(configList);

	}

	private void configureSeatingPlan(HashMap<Integer, String> configList) {

		if (configList != null) {

			for (int i = 1; i < 4; i++) {
				try {
					arrangeByPattern(configList.get(i));
				} catch (Exception e) {

					System.out.println("EXCETION MESSAGE in SeatingHelper.configureSeatingPlan ==> " + e.getCause());
				}

			}

		}

	}

	private void arrangeByPattern(String preference) throws WrongSeatingConfigurationException {

		if (preference != null) {
			switch (preference) {

			case "A":
				initializeSeatingType(aislesSet, totalAisles);
				break;
			case "W":
				initializeSeatingType(windowsSet, totalWindows);
				break;
			case "M":
				initializeSeatingType(middlesSet, totalMiddles);
				break;
			default:
				throw new WrongSeatingConfigurationException(
						"Seating preference should of one of  the type among A (Aisle) , W (Window) , M (Middle)");
			}
		}

	}

	private void initializeSeatingType(Queue<Integer> seatQueue, int totalSeatstype) {

		while (seatQueue.size() < totalSeatstype && noOfPassengers > 0) {
			seatQueue.add(seatCounter++);
			noOfPassengers--;

		}

	}

	public void displaySeatingArrangement() {
		try {
			int maxRows = 0;
			for (int i = 0; i <= blocks.length - 1; i++) {

				Seat[][] seat = (Seat[][]) blocks[i];

				int h = seat.length;
				if (h > maxRows) {
					maxRows = h;
				}
			}

			int rowCount = 0;

			while (rowCount < maxRows) {

				for (int i = 0; i <= blocks.length - 1; i++) {

					Seat[][] seat = (Seat[][]) blocks[i];

					if (rowCount <= seat.length - 1) {
						for (int k = 0; k <= seat[0].length - 1; k++) {
							System.out.print(seat[rowCount][k].getSeatType());
							System.out.print(" ");

						}

					} else {
						for (int j = 0; j <= seat[0].length - 1; j++) {
							System.out.print("   ");

						}
					}

					System.out.print("        ");

				}

				System.out.println("");
				rowCount++;

			}
		} catch (Exception e) {
			System.out.println("EXCEPTION MESSAGE in SeatingHelper.displaySeatingArrangement ==> " + e.getCause());
		}

	}

	public void displayArrangementWithPassengers() {
		try {
			int maxRows = 0;
			for (int i = 0; i <= blocks.length - 1; i++) {

				Seat[][] seat = (Seat[][]) blocks[i];

				int h = seat.length;
				if (h > maxRows) {
					maxRows = h;
				}
			}

			int rowCount = 0;

			while (rowCount < maxRows) {

				for (int i = 0; i <= blocks.length - 1; i++) {

					Seat[][] seat = (Seat[][]) blocks[i];

					if (rowCount <= seat.length - 1) {
						for (int k = 0; k <= seat[0].length - 1; k++) {
							StringBuilder myString = new StringBuilder();
							myString.append(seat[rowCount][k].getSeatType())
									.append(seat[rowCount][k].getAssignedValue());
							System.out.print(myString.toString());
							System.out.print(" ");

						}

					} else {
						for (int j = 0; j <= seat[0].length - 1; j++) {
							System.out.print("   ");

						}
					}

					System.out.print("        ");

				}

				System.out.println("");
				rowCount++;

			}

		}

		catch (Exception e) {
			System.out
					.println("EXCEPTION MESSAGE in SeatingHelper.displayArrangementWithPassengers ==> " + e.getCause());
		}
	}

}
