public class Seat {
	
	private Person person;
	private boolean window, aile;
	
	Seat (boolean window, boolean aile ) {
		this.window = window;
		this.aile = aile;
	}
	
	public Person getSeat() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public boolean isWindow() {
		return window;
	}
	
	public boolean isAile() {
		return aile;
	}
}
