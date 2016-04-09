import java.awt.Color;

public class L extends Piece{
	
	public L() {
		this.w = 3;
		this.h = 3;
		piece = new int[4][h][w];
		for (int i = 0; i < this.w; i++) {
			piece[0][1][i] = 1;
			piece[1][i][1] = 1;
			piece[2][1][i] = 1;
			piece[3][i][1] = 1;
		}
		piece[0][0][2] = 1;
		piece[1][2][2] = 1;
		piece[2][2][0] = 1;
		piece[3][0][0] = 1;
		this.frame = 0;
		this.c = Color.orange;

	}
}
