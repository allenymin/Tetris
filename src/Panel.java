import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements ActionListener, KeyListener{
	
	Grid grid;
	Timer timer;
	int level;
	int gx, gy;
	Random rand;
	boolean gameover;
	boolean pause;
	
	public Panel() {
		super();
		grid = new Grid();
		timer = new Timer(1000, this);
		timer.start();
		gx = 20;
		gy = 40+75;
		level = 1;
		rand = new Random();
		gameover = false;
		pause = false;
	}
	
	public void paintComponent(Graphics g) {
		paintBackground(g);
		paintGrid(g);
		paintPieces(g);
		paintScore(g);
		if (gameover) {
			paintGameEnd(g);
		} else if (pause) {
			paintPause(g);
		}
	}
	
	public void paintScore(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		g.drawString("Lines cleared: " + grid.linesCleared, gx+275, gy+150);
		g.drawString("Level: " + level, gx+275, gy+200);
	}
	
	public void paintBackground(Graphics g) {
		g.setColor(Color.lightGray);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());		
		g.setColor(Color.white);
		g.fillRect(gx, gy, 250, 500);
		g.fillRect(gx, 20, 100, 75);
		g.fillRect(gx+275, gy, 100, 75);
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		g.setColor(Color.BLACK);
		g.drawString("Hold", gx+300, gy+95);
	}
	
	public void paintGrid(Graphics g) {
		for (int i = 2; i < Grid.GRID_H; i++) {
			for (int j = 0; j < Grid.GRID_W; j++) {
				if (grid.grid[i][j] != 0) {
					g.setColor(new Color(grid.grid[i][j]));
					g.fillRect(gx+j*25, gy+i*25-50, 25, 25);
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(3));
					g2.setColor(Color.BLACK);
					g2.drawRect(gx+j*25, gy+i*25-50, 25, 25);
				}
			}
		}
	}
	
	public void paintPieces(Graphics g) {
		g.setColor(grid.currentPiece.c);
		for (int i = 0; i < grid.currentPiece.h; i++) {
			for (int j = 0; j < grid.currentPiece.w; j++) {
				if (grid.currentPiece.getPiece()[i][j] == 1) {
					g.setColor(Color.lightGray);
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(3));
					//Painting shadow piece
					if (grid.sr+i > 1) {
						g2.fillRect(gx+(grid.c+j)*25, gy+(grid.sr+i)*25-50, 25, 25);
						g2.setColor(Color.BLACK);
						g2.drawRect(gx+(grid.c+j)*25, gy+(grid.sr+i)*25-50, 25, 25);
					}
					//Painting current piece
					if (grid.r+i > 1) {
						g.setColor(grid.currentPiece.c);
						g2.fillRect(gx+(grid.c+j)*25, gy+(grid.r+i)*25-50, 25, 25);
						g2.setColor(Color.BLACK);
						g2.drawRect(gx+(grid.c+j)*25, gy+(grid.r+i)*25-50, 25, 25);
					}
				}
			}
		}
		//Painting nextPiece
		for (int i = 0; i < grid.nextPiece.h; i++) {
			for(int j = 0; j < grid.nextPiece.w; j++) {
				if (grid.nextPiece.getPiece()[i][j] == 1) {
					g.setColor(grid.nextPiece.c);
					g.fillRect(gx+(j*25), 20+(i*25), 25, 25);
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(3));
					g2.setColor(Color.BLACK);
					g2.drawRect(gx+(j*25), 20+(i*25), 25, 25);
				}
			}
		}
		//Painting hold piece
		if (grid.holdPiece != null) {
			for (int i = 0; i < grid.holdPiece.h; i++) {
				for(int j = 0; j < grid.holdPiece.w; j++) {
					if (grid.holdPiece.getPiece()[i][j] == 1) {
						g.setColor(grid.holdPiece.c);
						g.fillRect(gx+275+(j*25), gy+(i*25), 25, 25);
						Graphics2D g2 = (Graphics2D) g;
						g2.setStroke(new BasicStroke(3));
						g2.setColor(Color.BLACK);
						g2.drawRect(gx+275+(j*25), gy+(i*25), 25, 25);
					}
				}
			}
		}
		
	}
	
	public void paintGameEnd(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.lightGray);
		g2.fillRect(gx+10, gy+250, 230, 100);
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawRect(gx+10, gy+250, 230, 100);
		g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		g2.drawString("Game Over", gx+50, gy+290);
		g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		g2.drawString("Press Enter to Restart", gx+30, gy+320);
	}
	
	public void paintPause(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.lightGray);
		g2.fillRect(gx+10, gy+250, 230, 100);
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		g2.drawRect(gx+10, gy+250, 230, 100);
		g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
		g2.drawString("Game Paused", gx+40, gy+290);
		g2.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
		g2.drawString("Press Escape to Unpause", gx+10, gy+320);
	}
	
	public void restart() {
		gameover = false;
		grid = new Grid();
		level = 1;
		timer.setDelay(1000);
		timer.setInitialDelay(0);
		timer.start();
	}
	
	public void update() {
		if (grid.linesCleared > level*10 && level < 9) {
			level = grid.linesCleared/10+1;
			timer.setDelay(1000-level*100);
		}
		if (grid.gameOver()) {
			timer.stop();
			gameover = true;
		}
	}
	
	public void pause() {
		if (pause) {
			timer.start();
			pause = false;
			repaint();
		} else {
			timer.stop();
			pause = true;
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		grid.movePieceDown();
		update();
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!gameover && !pause) {
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				grid.movePieceRight();
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				grid.movePieceLeft();
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				grid.rotateRight();
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				grid.movePieceDown();
				repaint();
				timer.restart();
			} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				grid.hardDropPiece();
				repaint();
				timer.restart();
				update();
			} else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
				grid.holdPiece();
				timer.restart();
				repaint();
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				pause();
			}
		} else if (gameover){
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				restart();
			}
		} else if (pause){
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				pause();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
