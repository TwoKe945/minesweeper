package cn.com.twoke.game.minesweeper.constant;

import java.awt.image.BufferedImage;

import cn.com.twoke.game.minesweeper.framework.utils.ResourceLoader;

public final class ImageResource {
//	地雷数量显示数字
	public static final BufferedImage NUM_0;
	public static final BufferedImage NUM_1;
	public static final BufferedImage NUM_2;
	public static final BufferedImage NUM_3;
	public static final BufferedImage NUM_4;
	public static final BufferedImage NUM_5;
	public static final BufferedImage NUM_6;
	public static final BufferedImage NUM_7;
	public static final BufferedImage NUM_8;
//	二极管数字显示图片
	public static final BufferedImage DIODE_NUM_0;
	public static final BufferedImage DIODE_NUM_1;
	public static final BufferedImage DIODE_NUM_2;
	public static final BufferedImage DIODE_NUM_3;
	public static final BufferedImage DIODE_NUM_4;
	public static final BufferedImage DIODE_NUM_5;
	public static final BufferedImage DIODE_NUM_6;
	public static final BufferedImage DIODE_NUM_7;
	public static final BufferedImage DIODE_NUM_8;
	public static final BufferedImage DIODE_NUM_9;
//	地雷图片
	public static final BufferedImage MINE;
	public static final BufferedImage MINE_BOMB;
//	旗帜
	public static final BufferedImage FLAG;
	public static final BufferedImage FLAG_WRONG;
	public static final BufferedImage OVERLAYER;
	
	public static final BufferedImage EMOJI_SMILE;
	public static final BufferedImage EMOJI_OVER;
	public static final BufferedImage EMOJI_COOL;
	
	static {
		NUM_0 = ResourceLoader.loadImage("/0.gif");
		NUM_1 = ResourceLoader.loadImage("/1.gif");
		NUM_2 = ResourceLoader.loadImage("/2.gif");
		NUM_3 = ResourceLoader.loadImage("/3.gif");
		NUM_4 = ResourceLoader.loadImage("/4.gif");
		NUM_5 = ResourceLoader.loadImage("/5.gif");
		NUM_6 = ResourceLoader.loadImage("/6.gif");
		NUM_7 = ResourceLoader.loadImage("/7.gif");
		NUM_8 = ResourceLoader.loadImage("/8.gif");
		
		DIODE_NUM_0 = ResourceLoader.loadImage("/c0.gif");
		DIODE_NUM_1 = ResourceLoader.loadImage("/c1.gif");
		DIODE_NUM_2 = ResourceLoader.loadImage("/c2.gif");
		DIODE_NUM_3 = ResourceLoader.loadImage("/c3.gif");
		DIODE_NUM_4 = ResourceLoader.loadImage("/c4.gif");
		DIODE_NUM_5 = ResourceLoader.loadImage("/c5.gif");
		DIODE_NUM_6 = ResourceLoader.loadImage("/c6.gif");
		DIODE_NUM_7 = ResourceLoader.loadImage("/c7.gif");
		DIODE_NUM_8 = ResourceLoader.loadImage("/c8.gif");
		DIODE_NUM_9 = ResourceLoader.loadImage("/c9.gif");
		
		MINE = ResourceLoader.loadImage("/mine.gif");
		MINE_BOMB = ResourceLoader.loadImage("/bomb.gif");
		FLAG = ResourceLoader.loadImage("/flag.gif");
		FLAG_WRONG = ResourceLoader.loadImage("/flag_wrong.svg");
		OVERLAYER = ResourceLoader.loadImage("/overlayer.gif");
		
		EMOJI_SMILE = ResourceLoader.loadImage("/smile.gif");
		EMOJI_OVER = ResourceLoader.loadImage("/over.gif");
		EMOJI_COOL = ResourceLoader.loadImage("/cool.gif");
	}
	
	
	
}
