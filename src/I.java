import java.awt.Color;

public class I extends Piece{

	public I() {
		this.w = 4;
		this.h = 4;
		piece = new int[4][w][h];
		for (int i = 0; i < this.w; i++) {
			piece[0][1][i] = 1;
			piece[1][i][2] = 1;
			piece[2][2][i] = 1;
			piece[3][i][1] = 1;
		}
		this.frame = 0;
		this.c = Color.cyan;
	}
	
}
