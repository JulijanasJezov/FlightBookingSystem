/**
 * 
 * @author azp
 * 
 *         Used to wrap up two ints and a boolean so they can be returned or passed together.
 *         
 *         You should not need to modify this class.
 * 
 */

public class Location {
	
	private int row;
	private int col;
	private boolean isClub; // true => club, false => economy 
	
	public Location(boolean isClub, int row, int col) {
		this.isClub = isClub;
		this.row = row;
		this.col = col;
	}
	
	public boolean isClub() {
		return isClub;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
}
