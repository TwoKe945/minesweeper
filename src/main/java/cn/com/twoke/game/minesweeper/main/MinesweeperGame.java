package cn.com.twoke.game.minesweeper.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import cn.com.twoke.game.minesweeper.constant.ImageResource;
import cn.com.twoke.game.minesweeper.framework.core.Game;
import cn.com.twoke.game.minesweeper.framework.core.GamePanel;

public class MinesweeperGame extends Game implements MouseMotionListener, MouseListener {
	

	public static final int ROW = 9;
	public static final int COL = ROW;
	public static final int DEFAULT_TILE_SIZE = 25;
	public static final float SCALE = 1f;
	public static final int TILE_SIZE = (int)(DEFAULT_TILE_SIZE * SCALE);
	public static final int MARGIN_TOP = 23 + 2 * 2  + 6;
	public static final int WIDTH = COL * TILE_SIZE;
	public static final int HEIGHT = ROW * TILE_SIZE + MARGIN_TOP;

	
	
	public static final BufferedImage[] NUMBER_IMAGES = new BufferedImage[]{
			ImageResource.NUM_0,
			ImageResource.NUM_1,
			ImageResource.NUM_2,
			ImageResource.NUM_3,
			ImageResource.NUM_4,
			ImageResource.NUM_5,
			ImageResource.NUM_6,
			ImageResource.NUM_7,	
			ImageResource.NUM_8
	};
	
	public static final BufferedImage[] DIODE_NUMBER_IMAGES = new BufferedImage[]{
			ImageResource.DIODE_NUM_0,
			ImageResource.DIODE_NUM_1,
			ImageResource.DIODE_NUM_2,
			ImageResource.DIODE_NUM_3,
			ImageResource.DIODE_NUM_4,
			ImageResource.DIODE_NUM_5,
			ImageResource.DIODE_NUM_6,
			ImageResource.DIODE_NUM_7,	
			ImageResource.DIODE_NUM_8,
			ImageResource.DIODE_NUM_9,
	};
	
	
//	private float mineDensity = 0.2063f;
	private float mineDensity = 0.1f;
	private Random random;
	private int[][] mines;
	private int[][] overlayer;
	private Point bombPoint;
	private int mineCount = 0;
	private int flagCount = 0;
	private long prevTime = 0;
	private long lostTime = 0;
	private boolean gameSuccess = false;
	public MinesweeperGame() {
		super(MinesweeperGame::buildGamePanel);
		initMines();
		addMouseListener();
	}
	
	private void addMouseListener() {
		this.panel.addMouseMotionListener(this);
		this.panel.addMouseListener(this);
	}

	private void initMines() {
		mineCount = (int)(ROW * COL * mineDensity);
		int x, y;
		for (int i = 0; i < mineCount; ) {
			y = random.nextInt(ROW);
			x = random.nextInt(COL);
			if (mines[y][x] != -1) {
				mines[y][x] = -1;
				i++;
			}
		}
		
//		生产数字
		for (x = 0; x < COL; x++) {
			for (y = 0; y < ROW; y++) {
				if (mines[y][x] == -1) continue;
				int count = 0;
				if (x - 1 >= 0) {
					if (y - 1 >= 0 && mines[y - 1][x - 1] == -1) {
						count++;
					}
					if (mines[y][x - 1] == -1) {
						count++;
					}
					if (y + 1<= ROW - 1 && mines[y + 1][x - 1] == -1) {
						count++;
					}
				}
				if (x + 1 <= COL - 1) {
					if (y - 1 >= 0 && mines[y - 1][x + 1] == -1) {
						count++;
					}
					if (mines[y][x + 1] == -1) {
						count++;
					}
					if (y + 1<= ROW - 1 && mines[y + 1][x + 1] == -1) {
						count++;
					}
				}
				
				if (y - 1 >= 0 && mines[y - 1][x ] == -1) {
					count++;
				}
				if (y + 1<= ROW - 1 && mines[y + 1][x ] == -1) {
					count++;
				}
				
				mines[y][x] = count;
				
				
			}
		}
		
		
	}

	@Override
	protected void initialzer() {
		mines = new int[ROW][COL];
		overlayer = new int[ROW][COL];
		random = new Random();
		bombPoint = new Point(-1, -1);
	}

	private void drawTileBlock(Graphics g, BufferedImage image, int x, int y) {
		g.drawImage(image, x * TILE_SIZE, y * TILE_SIZE + MARGIN_TOP, TILE_SIZE,  TILE_SIZE, null);
	}
	
	/**
	 * 绘制地雷以及数字分布
	 * @param g
	 */
	private void drawMines(Graphics g) {
		for (int x = 0; x < COL; x++) {
			for (int y = 0; y < ROW; y++) {
				if (mines[y][x] == -1) {
					if (gameOver && bombPoint.equals(new Point(x, y))) {
						drawTileBlock(g, ImageResource.MINE_BOMB, x, y);
					} else {
						drawTileBlock(g, ImageResource.MINE, x, y);
					}
				} else {
					drawTileBlock(g, NUMBER_IMAGES[mines[y][x]], x, y);
				}
			}
		}
	}
	
	/**
	 * 绘制覆盖层
	 * @param g
	 */
	private void drawOverlayer(Graphics g) {
		for (int x = 0; x < COL; x++) {
			for (int y = 0; y < ROW; y++) {
				if (overlayer[y][x] == -1) {
					if (gameOver) {
						if (mines[y][x] != -1) {
							drawTileBlock(g, ImageResource.FLAG_WRONG, x, y);
						} else {
							drawTileBlock(g, ImageResource.FLAG, x, y);
						}
					} else {
						drawTileBlock(g, ImageResource.FLAG, x, y);
					}
					continue;
				}
				if (mines[y][x] == -1) {
					if (gameOver) continue;
					if (overlayer[y][x] == 0) {
						drawTileBlock(g, ImageResource.OVERLAYER, x, y);
					}
				} else if (overlayer[y][x] == 0) {
					drawTileBlock(g, ImageResource.OVERLAYER, x, y);
				}
			}
		}
	}
	
	@Override
	public void doDraw(Graphics g) {
		drawMines(g);
		drawOverlayer(g);
		drawGameState(g);
	}
	
	/**
	 * 绘制游戏状态
	 * @param g
	 */
	private void drawGameState(Graphics g) {
		// 地雷剩余总数
		drawScore(getNumbers(mineCount - flagCount, 3), 2, 4, -1).accept(g);
		drawScore(getNumbers(lostTime, 3), 2, 4, 1).accept(g);
		g.drawImage( gameOver ? ImageResource.OVER : ImageResource.SMILE, (WIDTH - 21 ) / 2, (int)(6 * SCALE), 
				(int)(21 * SCALE), (int)(21 * SCALE), null);
		//
		if(gameSuccess) {
			g.setColor(new Color(0,0,0, 200));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(Color.WHITE);
			g.drawString("Game SUCCESS!!", WIDTH / 2 - 30, HEIGHT / 2);
		}
		
	}
	
	private static Integer[] getNumbers(long number, int length) {
		String numberText = String.valueOf(number);
		if (numberText.length()>= length) {
			return Arrays.asList(numberText.substring(numberText.length() - 3, numberText.length() ).split("")).stream().map(Integer::parseInt).collect(Collectors.toList())
					.toArray(new Integer[length]);
		} else {
			int zeroCount = length - numberText.length();
			StringBuffer tempBuffer = new StringBuffer();
			for (int i = 0; i < zeroCount; i++) {
				tempBuffer.append("0");
			}
			return Arrays.asList(  (tempBuffer.append(numberText).toString()).split("")).stream().map(Integer::parseInt).collect(Collectors.toList())
					.toArray(new Integer[length]);
		}
	}

	private Consumer<Graphics> drawScore(Integer[] mineNumbers, int startX, int startY, int dir) {
		if (dir == 1) {
			return g -> {
				for (int i = 0; i < mineNumbers.length; i++) {
					g.drawImage(DIODE_NUMBER_IMAGES[mineNumbers[i]], (int)(( WIDTH - startX - (mineNumbers.length - i) * 13 ) * SCALE), (int)(startY * SCALE), 
							(int)(13 * SCALE), (int)(23 * SCALE), null);
				}
			};
		} else if (dir == -1) {
			return g -> {
				for (int i = 0; i < mineNumbers.length; i++) {
					g.drawImage(DIODE_NUMBER_IMAGES[mineNumbers[i]], (int)((startX + i * 13 ) * SCALE), (int)(startY * SCALE), 
							(int)(13 * SCALE), (int)(23 * SCALE), null);
				}
			};
		}else {
			return null;
		}
		
	}
	
	

	@Override
	protected void update() {
		updateLostTime();
		updateGameState();
	}

	private void updateGameState() {
		int overlayerCount = 0;
		for (int x = 0; x < COL; x++) {
			for (int y = 0; y < ROW; y++) {
				if (overlayer[y][x]== 0) {
					overlayerCount++;
				}
			}
		}
		if (mineCount == flagCount && overlayerCount == 0) {
			gameSuccess = true;
		}
		
	}

	private void updateLostTime() {
		if (gameOver || gameSuccess) return;
		if (prevTime == 0) {
			prevTime = System.currentTimeMillis();
		}
		long tempTime = System.currentTimeMillis();
		if (tempTime - prevTime >= 1000) {
			lostTime++;
			prevTime = tempTime;
		}
	}

	private static GamePanel buildGamePanel(Game game) {
		return new GamePanel(game, WIDTH, HEIGHT);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	private void openOverlayer(int x, int y) {
		if (x < 0 || x >= COL || y < 0 || y >= ROW)return;
		
		if (mines[y][x] != 0){
			overlayer[y][x]= 1;
			return;
		}
		overlayer[y][x]= 1;
		// 判断四周
		if (x - 1 >= 0) {
			if (y - 1 >= 0 && mines[y - 1][x - 1] != -1 && overlayer[y - 1][x - 1] == 0) {
				openOverlayer( x - 1, y - 1);
			}
			if (mines[y][x - 1] != -1 && overlayer[y][x - 1] == 0) {
				openOverlayer(x - 1, y);
			}
			if (y + 1<= ROW - 1 && mines[y + 1][x - 1] != -1 && overlayer[y + 1][x - 1]  == 0) {
				openOverlayer(x - 1, y + 1);
			}
		}
		if (x + 1 <= COL - 1) {
			if (y - 1 >= 0 && mines[y - 1][x + 1] != -1 && overlayer[y  -1][x + 1] == 0) {
				openOverlayer(x + 1, y - 1);
			}
			if (mines[y][x + 1] != -1 && overlayer[y][x + 1] == 0) {
				openOverlayer(x + 1, y );
			}
			if (y + 1<= ROW - 1 && mines[y + 1][x + 1] != -1 && overlayer[y + 1][x + 1]  == 0) {
				openOverlayer(x + 1, y + 1);
			}
		}
		
		if (y - 1 >= 0 && mines[y - 1][x ] != -1 &&  overlayer[y - 1][x]  == 0) {
			openOverlayer(x, y - 1);
		}
		if (y + 1<= ROW - 1 && mines[y + 1][x ] != -1 && overlayer[y + 1][x] == 0) {
			openOverlayer(x, y + 1);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	
	protected boolean gameOver;

	@Override
	public void mouseReleased(MouseEvent e) {
		if (gameOver || gameSuccess) return;
		int x = e.getX() / TILE_SIZE;
		int y = (e.getY()- MARGIN_TOP) / TILE_SIZE;
		
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (e.getY() < MARGIN_TOP || overlayer[y][x] == -1) return;
			if (mines[y][x] == -1) {
				gameOver = true;
				bombPoint.setLocation(x, y);
			}
			openOverlayer(x, y);
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			if (e.getY() < MARGIN_TOP) return;
			if (overlayer[y][x] == 0) {
				overlayer[y][x] = -1;
				flagCount++;
				if (flagCount > mineCount) {
					flagCount = mineCount;
				}
			} else if (overlayer[y][x] == -1) {
				overlayer[y][x] = 0;
				flagCount--;
				if (flagCount < 0) {
					flagCount = 0;
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

}
