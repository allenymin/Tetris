import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class Grid {

	public static final int GRID_H = 22;
	public static final int GRID_W = 10;
	
	int[][] grid;
	Piece currentPiece;
	Piece nextPiece;
	Piece holdPiece;
	boolean hold;
	int r,c;
	int sr;
	ArrayList<Piece> bag;
	int linesCleared;
	
	public Grid() {
		grid = new int[GRID_H][GRID_W];
		bag = new ArrayList<Piece>(7);
		currentPiece = generatePiece();
		nextPiece = generatePiece();
		hold = false;
		r = 0;
		updateShadow();
		linesCleared = 0;
		if (currentPiece instanceof O) {
			c = 4;
		} else {
			c = 3;
		}
	}
	
	public void movePieceDown() {
		r++;
		if (outBoundsDown() || invalidPlace()) {
			r--;
			setPiece();
		}
	}
	
	public void hardDropPiece() {
		r = sr;
		setPiece();
	}
	
	public void updateShadow() {
		int temp = r;
		while (!outBoundsDown() && !invalidPlace()) {
			r++;
		}
		sr = r - 1;
		r = temp;
	}
	
	public void movePieceLeft() {
		c--;
		if (outBoundsLeft() || invalidPlace()) c++;
		updateShadow();
	}
	
	public void movePieceRight() {
		c++;
		if (outBoundsRight() || invalidPlace()) c--;
		updateShadow();
	}
	
	public void setPiece() {
		for (int i = 0; i < currentPiece.h; i++) {
			for (int j = 0; j < currentPiece.w; j++) {
				if (currentPiece.getPiece()[i][j] == 1) {
					grid[r+i][c+j] = currentPiece.c.getRGB();
				}
			}
		}
		hold = false;
		clearLines();
		currentPiece = nextPiece;
		nextPiece = generatePiece();
		r = 0;
		if (currentPiece instanceof O) {
			c = 4;
		} else {
			c = 3;
		}
		updateShadow();
	}
	
	public void holdPiece() {
		if (!hold) {
			hold = true;
			if (holdPiece == null) {
				holdPiece = currentPiece;
				currentPiece = nextPiece;
				nextPiece = generatePiece();
			} else {
				Piece temp = holdPiece;
				holdPiece = currentPiece;
				currentPiece = temp;
			}
			holdPiece.frame = 0;
			r = 0;
			if (currentPiece instanceof O) {
				c = 4;
			} else {
				c = 3;
			}
			updateShadow();
		}
	}
	
	public void clearLines() {
		for(int i = 2; i < GRID_H; i++) {
			boolean line = true;
			for (int j = 0; j < GRID_W; j++) {
				if (grid[i][j] == 0) {
					line = false;
				}
			}
			if (line) {
				linesCleared++;
				for(int j = 0; j < GRID_W; j++) {
					grid[i][j] = 0;
				}
				for (int k = i-1; k > 2; k--) {
					for (int j = 0; j < GRID_W; j++) {
						grid[k+1][j] = grid[k][j];
					}
				}
			}
		}
	}
	
	public void refillBag() {
		bag.clear();
		bag.add(new I());
		bag.add(new O());
		bag.add(new L());
		bag.add(new J());
		bag.add(new S());
		bag.add(new Z());
		bag.add(new T());
		Collections.shuffle(bag);
	}
	
	public Piece generatePiece() {
		if (bag.isEmpty()) {
			refillBag();
		}
		return bag.remove(0);
	}
	
	public void rotateRight() {
		currentPiece.rotateRight();
		int tempc = c;
		int tempr = r;
		if (outBoundsRight()) {
			c--;
		} else if (outBoundsLeft()) {
			c++;
		} else if (outBoundsDown()) {
			r--;
		}
		if (invalidPlace()) {
			c = tempc;
			r = tempr;
			currentPiece.rotateLeft();
		}
		updateShadow();
	}
	
	public void rotateLeft() {
		currentPiece.rotateLeft();
		int tempc = c;
		int tempr = r;
		if (outBoundsLeft()) {
			c++;
		} else if (outBoundsDown()) {
			r--;
		} else if (outBoundsRight()) {
			c--;
		}
		if (invalidPlace()) {
			c = tempc;
			r = tempr;
			currentPiece.rotateRight();
		}
		updateShadow();
	}
	
	public boolean invalidPlace() {
		for (int i = 0; i < currentPiece.h; i++) {
			for (int j = 0; j < currentPiece.w; j++) {
				if (currentPiece.getPiece()[i][j] == 1) {
					if (r+i > GRID_H-1 || c+j < 0 || c+j > GRID_W-1) {
						return true;
					}
					else if (grid[r+i][c+j] != 0) return true;
				}
			}
		}
		return false;
	}
	
	public boolean outBoundsDown() {
		if (currentPiece.frame == 0) {
			if (r > 20) return true;
		} else if (currentPiece instanceof I && currentPiece.frame == 2) {
			if (r > 19) return true;
		} else {
			if (r+currentPiece.h > 22) return true;
		}
		return false;
	}
	
	public boolean outBoundsLeft() {
		if (currentPiece.frame == 1) {
			if (currentPiece instanceof O) {
				if (c < 0) return true;
			} else if (currentPiece instanceof I) {
				if (c < -2) return true;
			} else {
				if (c < -1) {
					return true;
				}
			}
		} else if (currentPiece instanceof I && currentPiece.frame == 3) {
			if (c < -1) return true;
		} else {
			if (c < 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean outBoundsRight() {
		if (currentPiece.frame == 3) {
			if (c > 8) return true;
		} else if (currentPiece instanceof I && currentPiece.frame == 1) {
			if (c > 7) return true;
		} else {
			if (c+currentPiece.w > 10) {
				return true;
			}
		}
		return false;
	}
	
	public boolean gameOver() {
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < GRID_W; j++) {
				if (grid[i][j] != 0) return true;
			}
		}
		return false;
	}
	
}
