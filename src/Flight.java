public class Flight implements IFlight {

	private String destination, departureTime;
	private int rowCount, colCount, cClassRows, eClassRows, cClassCol = 4,
			eClassCol = 6;
	private Seat clubClass[][];
	private Seat ecoClass[][];
	private static final int INITIAL = 0;
	Flight(int club, int eco, String dest, String depTime) {
		destination = dest;
		departureTime = depTime;
		cClassRows = club;
		eClassRows = eco;
		clubClass = new Seat[cClassRows][cClassCol];
		ecoClass = new Seat[eClassRows][eClassCol];
	}

	public String getDestination() {

		return destination;
	}

	public String getTime() {

		return departureTime;
	}

	public int numRows(boolean rowClass) {
		return (rowClass) ? cClassRows : eClassRows;
		
	}

	public int numCols(boolean rowClass) {
		return (rowClass) ? cClassCol : eClassCol;
	}

	public void addSeatBooking(Location loc, Person person) {
		rowCount = loc.getRow();
		colCount = loc.getCol();

		if (loc.isClub()) {												//SEAT CLASS
			if (colCount == INITIAL || colCount == (cClassCol - 1)) {	//BOTH
				clubClass[rowCount][colCount] = new Seat(true, true);
				clubClass[rowCount][colCount].setPerson(person);
			} else {													//AILE
				clubClass[rowCount][colCount] = new Seat(false, true);
				clubClass[rowCount][colCount].setPerson(person);
			}
			
		} else {
			if (colCount == INITIAL || colCount == (eClassCol - 1)) {	//WINDOW
				ecoClass[rowCount][colCount] = new Seat(true, false);
				ecoClass[rowCount][colCount].setPerson(person);
			} else if (colCount == 2 || colCount == 3) {				//AILE
				ecoClass[rowCount][colCount] = new Seat(false, true);
				ecoClass[rowCount][colCount].setPerson(person);
			} else {													//NEITHER
				ecoClass[rowCount][colCount] = new Seat(false, false);
				ecoClass[rowCount][colCount].setPerson(person);
			}
		}
	}

	public boolean isSeatEmpty(Location loc) {
		int row, col;
		boolean isClub;

		isClub = loc.isClub();
		row = loc.getRow();
		col = loc.getCol();

		if (isClub) {
			return (clubClass[row][col] == null) ? true : false;
		} else {
			return (ecoClass[row][col] == null) ? true : false;
		}	
	}

	public Location getNextAvailableAisleSeat(boolean rowClass) {
		
		if (rowClass) {
			return getNextAvailableSeat(rowClass);
			
		} else if (!rowClass) {	
			for (int row = 0; row < numRows(rowClass); row++) {
				for (int col = 2; col < 4; col++) {
					Location seatLoc = new Location(rowClass, row, col);

					if (isSeatEmpty(seatLoc)) {
							return seatLoc;
					}
				}
			}
		}
		return null;
	}

	public Location getNextAvailableWindowSeat(boolean rowClass) {
		for (int row = 0; row < numRows(rowClass); row++) {
			for (int col = 0; col < numCols(rowClass); col++) {
				Location seatLoc = new Location(rowClass, row, col);
				
				if (rowClass) {
					if (col == 0 || col == 3) {
						if (isSeatEmpty(seatLoc)) {
							return seatLoc;
						}
					}
				} else if (!rowClass) {
					if (col == 0 || col == 5) {
						if (isSeatEmpty(seatLoc)) {
							return seatLoc;
						}
					}
				}
			}
		}
		return null;
	}

	public Location getNextAvailableSeat(boolean rowClass) {
		for (int row = 0; row < numRows(rowClass); row++) {
			for (int col = 0; col < numCols(rowClass); col++) {
				Location seatLoc = new Location(rowClass, row, col);
				
				if (isSeatEmpty(seatLoc)) {
					return seatLoc;
				}
			}
		}
		return null;
	}

	public void printManifest() {
		System.out.println("Flight Manifest");
		System.out.println("Destination: " + getDestination());
		System.out.println("Departure time: " + getTime());
		System.out.println("Club Class passenger list:");
		printPassengers(true);
		System.out.println("Economy Class passenger list:");
		printPassengers(false);	
		System.out.println("");
	}

	public void printSeatingPlan(boolean rowClass) {

		System.out.println((rowClass) ? "  0 1 2 3" : "  0 1 2 3 4 5");
		
		for (int row = 0; row < numRows(rowClass); row++) {
			System.out.print(row + ":");
			for (int col = 0; col < numCols(rowClass); col++) {
				Location seatLoc = new Location(rowClass, row, col);
				System.out.print((isSeatEmpty(seatLoc)) ? "O " : "X ");
			}
			System.out.println("");
		}
	}
	
	public void printPassengers(boolean rowClass) {
		int passCount = 0;
		
		for (int row = 0; row < numRows(rowClass); row++) {
			for (int col = 0; col < numCols(rowClass); col++) {
				Location seatLoc = new Location(rowClass, row, col);

				if (!isSeatEmpty(seatLoc) && !rowClass) {
					System.out.println("Row: " + row + ", Column: " + col
							+ ", Name: "
							+ ecoClass[row][col].getSeat().getName()
							+ ", Nationality: "
							+ ecoClass[row][col].getSeat().getNationality());
					passCount++;
				} else if (!isSeatEmpty(seatLoc) && rowClass) {
					System.out.println("Row: " + row + ", Column: " + col
							+ ", Name: "
							+ clubClass[row][col].getSeat().getName()
							+ ", Nationality: "
							+ clubClass[row][col].getSeat().getNationality());
					passCount++;
				}
			}
		}
		if (passCount == 0) {
			System.out.println((rowClass) ? "No passengers in Club class" : "No passengers in Economy class");
		}	
	}
}
