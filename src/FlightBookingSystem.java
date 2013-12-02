import java.lang.Character;

public class FlightBookingSystem {

	private static final char CREATE_FLIGHT = '1';
	private static final char MAKE_BOOKING = '2';
	private static final char FLIGHT_MANIFEST = '3';
	private static final char QUIT = 'Q';
	private static final int INITIAL = 0;
	private static final int MAX_FLIGHTS = 10;
	private static final int MIN_ROWS = 0;
	private static final int CLUB_CLASS_COL = 3;
	private static final int ECO_CLASS_COL = 5;
	private Flight[] flightsArray;
	private int flightNo = INITIAL;
	private int chosenFlight;
	private int clubClass, ecoClass;

	FlightBookingSystem(int max_flights) {
		flightsArray = new Flight[max_flights];
	}

	public static void main(String[] args) {
		// Do not modify this method!
		FlightBookingSystem fbs = new FlightBookingSystem(MAX_FLIGHTS);
		fbs.runMainMenu();
	}

	public void runMainMenu() {
		char userChoice = INITIAL;

		while (userChoice != 'Q') {
			System.out.println("Flight Booking Menu");
			System.out.println("===================\n");
			System.out.println("1) Create Flight");
			
			if (flightNo > INITIAL) {
				System.out.println("2) Make Booking on Flight");
				System.out.println("3) Print a Flight's Manifest");
			}
			System.out.println("Q) Quit\n");
			System.out.print("Enter your choice: ");
			userChoice = G51OOPInput.getChar();
			userChoice = Character.toUpperCase(userChoice);
			
			switch (userChoice) {
			case CREATE_FLIGHT:
				createFlight();
				break;
			case MAKE_BOOKING:
				availableFlights();
				bookSeat();
				break;
			case FLIGHT_MANIFEST:
				availableFlights();
				flightsArray[chosenFlight].printManifest();
				break;
			case QUIT:
				System.out.println("Quitting...");
				break;
			default:
				System.out.println("ERROR: Invalid menu choice!");
				break;
			}
		}
	}
	
	private void createFlight() {
		String destination, depTime;

		System.out.println("Flight Creator");
		System.out.println("===================\n");
		System.out.print("Enter Destination: ");
		destination = G51OOPInput.readString();
		System.out.print("Enter Departure Time: ");
		depTime = G51OOPInput.readString();

		System.out.println("Aeroplane Details");
		System.out.println("===================\n");
		System.out.print("Enter number of rows in Club Class: ");
		clubClass = G51OOPInput.readInt();
		System.out.print("Enter number of rows in Economy Class: ");
		ecoClass = G51OOPInput.readInt();
		
		if (clubClass < MIN_ROWS || ecoClass < MIN_ROWS) {
			System.out.println("\nERROR: Invalid Row Count");
			return;
		}

		if (flightNo < MAX_FLIGHTS) {
			flightsArray[flightNo] = new Flight(clubClass, ecoClass,
					destination, depTime);
			flightNo++;
		} else {
			System.out.println("Total Flights available exceeded");
		}
	}

	private void availableFlights() {

		System.out.println("\nAvailable flights:");
		
		for (int loopCount = INITIAL; loopCount < flightNo; loopCount++) {
			System.out.println("Flight number: " + loopCount + ", Destination: "
					+ flightsArray[loopCount].getDestination() + " Departure time: "
					+ flightsArray[loopCount].getTime() + "\n");
		}
		System.out.print("Enter flight number: ");
		chosenFlight = G51OOPInput.readInt();
		
		if (chosenFlight > flightNo - 1) {
			System.out.println("ERROR: Invalid menu choice!");
			availableFlights();
		}
	}

	private void bookSeat() {
		char seatTypeChoice, newBooking;
		int seatLocation, flightClass, classCol;
		boolean rowClass;

		System.out.println("\nSeat Type");
		System.out.println("=========\n");
		System.out.println("C) Book Club Class Seat");
		System.out.println("E) Book Economy Class Seat");
		System.out.print("Pick your seat type: ");
		seatTypeChoice = G51OOPInput.getChar();
		seatTypeChoice = Character.toUpperCase(seatTypeChoice);

		System.out.println("\nSeat Selection");
		System.out.println("==============\n");
		System.out.println("1) Pick Specific Seat");
		System.out.println("2) Any Window Seat");
		System.out.println("3) Any Aisle Seat");
		System.out.println("4) Any Seat");
		System.out.print("Enter Preference: ");
		seatLocation = G51OOPInput.readInt();

		switch (seatTypeChoice) {
		case 'C':
			rowClass = true;
			flightClass = clubClass - 1;
			classCol = CLUB_CLASS_COL;
			seatSelection(seatLocation, rowClass, flightClass, classCol);
			break;
		default:
			rowClass = false;
			flightClass = ecoClass - 1;
			classCol = ECO_CLASS_COL;
			seatSelection(seatLocation, rowClass, flightClass, classCol);
		}

		System.out.println("Book another seat? Y/N");
		newBooking = G51OOPInput.getChar();
		newBooking = Character.toUpperCase(newBooking);

		if (newBooking == 'Y') {
			bookSeat();
		}

	}
	
	private void seatSelection(int seatLocation, boolean rowClass, int classRows, int classCol) { 
		int row, col;
		Location location;
		
		switch (seatLocation) {
		case 1:																//SPECIFIC SEAT
			do {															//EMPTY SEAT ERROR LOOP
				do {														//ROW/COLUMN ERROR LOOP
					flightsArray[chosenFlight].printSeatingPlan(rowClass);
					System.out.print("Enter Row: ");
					row = G51OOPInput.readInt();
					
					if (row > classRows) {
						System.out.println("\nERROR: Invalid row number");
					} 
					
					System.out.print("Enter Column: ");
					col = G51OOPInput.readInt();
					
					if (col > classCol) {
						System.out.println("ERROR: Invalid column number");
					}
					
				} while (row > classRows || col > classCol);

				if (!flightsArray[chosenFlight].isSeatEmpty(new Location(
						rowClass, row, col))) {
					System.out.println("ERROR: Seat already taken");
				}
			} while (!flightsArray[chosenFlight].isSeatEmpty(new Location(
					rowClass, row, col)));

			Location seat = new Location(rowClass, row, col);
			flightsArray[chosenFlight]
					.addSeatBooking(seat, personDetails());
			break;
		case 2:																//WINDOW SEAT
			location = flightsArray[chosenFlight]
					.getNextAvailableWindowSeat(rowClass);
			if (location != null) {
				flightsArray[chosenFlight].addSeatBooking(location,
						personDetails());
			} else {
				System.out.println("Sorry, no window seats left");
			}
			break;
		case 3:																//AILE SEAT
			location = flightsArray[chosenFlight]
					.getNextAvailableAisleSeat(rowClass);
			if (location != null) {
				flightsArray[chosenFlight].addSeatBooking(location,
						personDetails());
			} else {
				System.out.println("Sorry, no window aisle left");
			}
			break;
		default:															//ANY SEAT
			location = flightsArray[chosenFlight]
					.getNextAvailableSeat(rowClass);
			if (location != null) {
				flightsArray[chosenFlight].addSeatBooking(location,
						personDetails());
			} else {
				System.out.println("Flight is fully booked, Sorry");
			}
		}
	}

	private Person personDetails() {
		String name, nationality;

		System.out.println("");
		System.out.print("Enter person's name: ");
		name = G51OOPInput.readString();
		System.out.print("Enter person's nationality: ");
		nationality = G51OOPInput.readString();
		Person details = new Person(name, nationality);

		return details;
	}
}
