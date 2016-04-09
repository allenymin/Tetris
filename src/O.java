import java.awt.Color;

public class O extends Piece{
	
	public O() {
		this.w = 2;
		this.h = 2;
		piece = new int[4][h][w];
		for (int i = 0; i < 4; i++) {
			piece[i][0][0] = 1;
			piece[i][0][1] = 1;
			piece[i][1][1] = 1;
			piece[i][1][0] = 1;
		}
		this.frame = 0;
		this.c = Color.yellow;
	}

}
