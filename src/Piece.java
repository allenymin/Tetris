import java.awt.Color;

public abstract class Piece {

	int h,w;
	int[][][] piece;
	int frame;
	Color c;
	
	public void rotateRight() {
		frame++;
		if (frame > 3) frame = 0;
	}
	public void rotateLeft() {
		frame--;
		if (frame < 0) frame = 3;
	}
	
	public int[][] getPiece() {
		return piece[frame];
	}
	
}
