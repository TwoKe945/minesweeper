package cn.com.twoke.game.minesweeper.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import cn.com.twoke.game.minesweeper.constant.ImageResource;
import static cn.com.twoke.game.minesweeper.main.MinesweeperGame.*;

public class DiodeNumberUtils {

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
	
	/**
	 * 截取数字为数组
	 * 如：123 -> [1,2,3] 12 -> [0,1,2]
	 * @param number 需要截取的数字
	 * @param length 截取长度
	 * @return
	 */
	public static Integer[] getNumbers(long number, int length) {
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
	
	/**
	 * 绘制数码管
	 * @param mineNumbers 数码管数字
	 * @param startX 开始X坐标
	 * @param startY 开始Y坐标
	 * @param dir 方向 1 左对齐 -1 右对齐
	 * @return
	 */
	public static  Consumer<Graphics> draw(Integer[] mineNumbers, int startX, int startY, int dir) {
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
	
	/**
	 * 绘制数码管
	 * @param number 数码管数字
	 * @param length 数码管数字长度
	 * @param startX 开始X坐标
	 * @param startY 开始Y坐标
	 * @param dir 方向 1 左对齐 -1 右对齐
	 * @return
	 */
	public static Consumer<Graphics> draw(long number, int length, int startX, int startY, int dir) {
		return draw(getNumbers(number, length), startX, startY, dir);
	}
	
	/**
	 * 左对齐绘制数码管
	 * @param number 数码管数字
	 * @param length 数码管数字长度
	 * @param startX 开始X坐标
	 * @param startY 开始Y坐标
	 * @return
	 */
	public static Consumer<Graphics> drawAlignLeft(long number, int length, int startX, int startY) {
		return draw(getNumbers(number, length), startX, startY, -1);
	}
	
	/**
	 * 右对齐绘制数码管
	 * @param number 数码管数字
	 * @param length 数码管数字长度
	 * @param startX 开始X坐标
	 * @param startY 开始Y坐标
	 * @return
	 */
	public static Consumer<Graphics> drawAlginRight(long number, int length, int startX, int startY) {
		return draw(getNumbers(number, length), startX, startY, 1);
	}
}
