package practice;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public final class MenuGameConstants {
	private MenuGameConstants() {};

	// official name
	public static final String GAME_YEAR = "2K18";

	// location within the menu image to start for calculating
	public static final int ITEM_ORIGIN_X = 24;
	public static final int ITEM_ORIGIN_Y = 16;

	public static final int CURSOR_OFFSET = 8; // number of pixels to shift the cursor
	public static final int BLOCK_SIZE = 24; // size in pixels of an item block (for offsets, not drawing)
	public static final int ITEM_SIZE = 16; // size of the image itself
	public static final int CURSOR_SIZE = 32;
	public static final Dimension BLOCK_D = new Dimension(BLOCK_SIZE, BLOCK_SIZE);

	public static final int BOARD_SIZE = 19;
	public static final int BOARD_SIZE_NO_BORDER = BOARD_SIZE - 2;

	// items
	public static final int ITEM_COUNT = 20;
	public static final Item[] ALL_ITEMS = Item.values(); // for easy access
	public static final int MIN_ITEMS = 4;

	// all possible moves
	public static final ArrayList<Integer> ALL_POSSIBLE_MOVES = new ArrayList<Integer>();

	// how this works
	// each int holds 14 possible moves:
	public static final byte MOVE_UP = 0b000;
	public static final byte MOVE_DOWN = 0b001;
	public static final byte MOVE_RIGHT = 0b010;
	public static final byte MOVE_LEFT = 0b011;
	public static final byte PRESS_START = 0b100; // used for playback of user
	public static final byte[] MOVES = { MOVE_UP, MOVE_DOWN, MOVE_RIGHT, MOVE_LEFT };

	// the first 4 bits are the number of moves in this set
	public static final int COUNT_OFFSET = 28;

	// starting from the least significant bit
	// every 2 bits form the token for a single move
	// the moves are counted from right to left

	static { // do all move patterns
		for (int i = 1; i < 5; i++) {
			addToPattern(0, 1, i);
		}
	}

	/**
	 * @param p - pattern
	 * @param l - current nest level
	 * @param m - number of moves
	 */
	private static void addToPattern(int p, int l, int m) {
		for (int i = 0; i < 4; i++) {
			int pattern = p | (MOVES[i] << (2 * (l - 1)));
			if (l > 1) { // not the first move
				int prevMove = (p >> (2 * (l-2))) & 0b11; // get previous move
				// uses an XOR to see if the move is the opposite of the last move
				// because the first bit plane defines the axis
				// and the second bit plane defines the direction
				// only same axis different direction will produce 0b01
				if ((MOVES[i] ^ prevMove) == 0b01) {
					continue;
				}
			}
			if (l == m) { // bottom level, just add
				pattern |= m << COUNT_OFFSET; // add number of moves
				ALL_POSSIBLE_MOVES.add(pattern);
			} else { // next move
				addToPattern(pattern, l+1, m);
			}
		}
	}

	// safety
	public static final int WORD_WIDTH = 400;
	public static final int WORD_HEIGHT = 100;

	// display
	public static final int BG_WIDTH = 152;
	public static final int BG_HEIGHT = 120;
	public static final int ZOOM = 3;

	public static final Dimension MENU_SIZE = new Dimension(BG_WIDTH * ZOOM + 5, BG_HEIGHT * ZOOM + 5);
	public static final Dimension MENU_SIZE_X2 = new Dimension(BG_WIDTH * 2 + 5, BG_HEIGHT * 2 + 5);

	// images
	public static final BufferedImage EMPTY_BG = new BufferedImage(BG_WIDTH, BG_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
	public static final BufferedImage BACKGROUND;
	public static final BufferedImage CREDITS;

	public static final BufferedImage EMPTY_CURSOR = new BufferedImage(CURSOR_SIZE, CURSOR_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
	public static final BufferedImage CURSOR;
	public static final BufferedImage TARGET_CURSOR;

	public static final BufferedImage EMPTY_ITEM = new BufferedImage(ITEM_SIZE, ITEM_SIZE, BufferedImage.TYPE_4BYTE_ABGR);
	public static final BufferedImage COMPASS;
	public static final BufferedImage MAP;

	public static final BufferedImage[] OPTIMAL_MOVES = new BufferedImage[5];
	public static final BufferedImage[] PLAYER_MOVES = new BufferedImage[5];

	public static final BufferedImage FONT_SPRITES;
	public static final BufferedImage FONT_SPRITES_SMALL;

	public static final BufferedImage EMPTY_BORDER = new BufferedImage(WORD_WIDTH, WORD_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
	public static final BufferedImage PRETTY_BORDER;
	public static final BufferedImage PRETTY_BORDER_RIGHT;
	public static final BufferedImage PRETTY_BORDER_LEFT;

	public static final BufferedImage PRETTY_BORDER_INSET;
	public static final BufferedImage PRETTY_BORDER_INSET_RIGHT;
	public static final BufferedImage PRETTY_BORDER_INSET_LEFT;

	public static final BufferedImage PRETTY_BORDER_DISABLED;
	public static final BufferedImage PRETTY_BORDER_RIGHT_DISABLED;
	public static final BufferedImage PRETTY_BORDER_LEFT_DISABLED;

	static {
		// main background
		BACKGROUND = fetchImageResource("/images/backgrounds/menu background.png", EMPTY_BG);
		CREDITS = fetchImageResource("/images/backgrounds/about bg.png", EMPTY_BG);

		// menu cursors
		CURSOR = fetchImageResource("/images/game-icons/menu cursor.png", EMPTY_CURSOR);
		TARGET_CURSOR = fetchImageResource("/images/game-icons/target cursor.png", EMPTY_CURSOR);

		// wait icon
		COMPASS = fetchImageResource("/images/game-icons/icon-compass.png", EMPTY_ITEM);
		MAP = fetchImageResource("/images/game-icons/icon-map.png", EMPTY_ITEM);

		// optimal path arrows
		OPTIMAL_MOVES[MOVE_UP] = fetchImageResource("/images/analysis/optimal up.png", EMPTY_CURSOR);
		OPTIMAL_MOVES[MOVE_DOWN] = fetchImageResource("/images/analysis/optimal down.png", EMPTY_CURSOR);
		OPTIMAL_MOVES[MOVE_RIGHT] = fetchImageResource("/images/analysis/optimal right.png", EMPTY_CURSOR);
		OPTIMAL_MOVES[MOVE_LEFT] = fetchImageResource("/images/analysis/optimal left.png", EMPTY_CURSOR);
		OPTIMAL_MOVES[PRESS_START] = TARGET_CURSOR;

		// player path arrows
		PLAYER_MOVES[MOVE_UP] = fetchImageResource("/images/analysis/player up.png", EMPTY_CURSOR);
		PLAYER_MOVES[MOVE_DOWN] = fetchImageResource("/images/analysis/player down.png", EMPTY_CURSOR);
		PLAYER_MOVES[MOVE_RIGHT] = fetchImageResource("/images/analysis/player right.png", EMPTY_CURSOR);
		PLAYER_MOVES[MOVE_LEFT] = fetchImageResource("/images/analysis/player left.png", EMPTY_CURSOR);
		PLAYER_MOVES[PRESS_START] = fetchImageResource("/images/analysis/player start.png", EMPTY_CURSOR);

		// sprite sheet for fonts
		FONT_SPRITES = fetchImageResource("/images/meta/font sheet.png", EMPTY_BG);
		FONT_SPRITES_SMALL = fetchImageResource("/images/meta/font sheet small.png", EMPTY_BG);

		// normal border
		PRETTY_BORDER = fetchImageResource("/images/meta/pretty-border.png", EMPTY_BORDER);
		PRETTY_BORDER_RIGHT = fetchImageResource("/images/meta/pretty-border-right.png", EMPTY_BORDER);
		PRETTY_BORDER_LEFT = fetchImageResource("/images/meta/pretty-border-left.png", EMPTY_BORDER);

		// inset border
		PRETTY_BORDER_INSET = fetchImageResource("/images/meta/pretty-border-inset.png", EMPTY_BORDER);
		PRETTY_BORDER_INSET_RIGHT = fetchImageResource("/images/meta/pretty-border-inset-right.png", EMPTY_BORDER);
		PRETTY_BORDER_INSET_LEFT = fetchImageResource("/images/meta/pretty-border-inset-left.png", EMPTY_BORDER);

		// disabled gray border
		PRETTY_BORDER_DISABLED = fetchImageResource("/images/meta/pretty-border-disabled.png", EMPTY_BORDER);
		PRETTY_BORDER_RIGHT_DISABLED = fetchImageResource("/images/meta/pretty-border-right-disabled.png", EMPTY_BORDER);
		PRETTY_BORDER_LEFT_DISABLED = fetchImageResource("/images/meta/pretty-border-left-disabled.png", EMPTY_BORDER);
	}

	// list of items with repeats to give some items higher chance of appearing
	// only needs to be defined once
	public static final ArrayList<Integer> ITEM_CHOOSER = new ArrayList<Integer>();

	static {
		for (Item i : Item.values()) { // for each item
			int tag = i.index;
			for (int j = 0; j < i.weight; j++) { // add X times based on weight
				ITEM_CHOOSER.add(tag);
			}
		}
	}

	/**
	 * Draws an image onto a graphics with coordinates multiplied by 8
	 * @param g
	 * @param word
	 * @param x
	 * @param y
	 */
	public static final void draw(Graphics g, BufferedImage i, int x, int y) {
		g.drawImage(i, (x * 8), (y * 8), null);
	}

	/**
	 * Draws an image onto a graphics with coordinates multiplied by 6
	 * @param g
	 * @param word
	 * @param x
	 * @param y
	 */
	public static final void drawSmall(Graphics g, BufferedImage i, int x, int y) {
		g.drawImage(i, (x * 6), (y * 6), null);
	}

	public static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 .,-:<>!=/";
	public enum WordColor {
		C_WHITE (0),
		C_YELLOW (1),
		C_GRAY (2);

		private final int v;
		private WordColor(int v) {
			this.v = v;
		}
	};

	public static final WordColor WHITE = WordColor.C_WHITE;
	public static final WordColor YELLOW = WordColor.C_YELLOW;
	public static final WordColor GRAY = WordColor.C_GRAY;

	/**
	 *
	 * @param s
	 * @param flag: white | yellow | gray
	 * @return
	 */
	public static BufferedImage makeWordImage(String s, WordColor flag) {
		BufferedImage ret = new BufferedImage(s.length() * 8, 8, BufferedImage.TYPE_INT_ARGB);
		char[] temp = s.toUpperCase().toCharArray();
		Graphics g = ret.getGraphics();
		int y = 8 * flag.v;
		for (int i = 0; i < temp.length; i++) {
			int loc = CHARS.indexOf(temp[i]);
			BufferedImage t = FONT_SPRITES.getSubimage(loc * 8, y, 8, 8);
			g.drawImage(t, i * 8, 0, null);
		}
		return ret;
	}

	public static BufferedImage makeNumberImage(int i, WordColor flag) {
		String w = "";
		char[] temp = Integer.toString(i).toCharArray();
		int pos = 1;

		for (int j = temp.length - 1; j >= 0; j--, pos++ ) {
			w = temp[j] + w;
			if ((pos % 3 == 0) && (j != 0)) {
				w = ',' + w;
			}
		}

		w = w.replace("-,", "-"); // lol

		return makeWordImage(w, flag);
	}

	public static BufferedImage makeWordImageSmall(String s) {
		BufferedImage ret = new BufferedImage(s.length() * 6, 6, BufferedImage.TYPE_INT_ARGB);
		char[] temp = s.toUpperCase().toCharArray();
		Graphics g = ret.getGraphics();
		for (int i = 0; i < temp.length; i++) {
			int loc = CHARS.indexOf(temp[i]);
			BufferedImage t = FONT_SPRITES_SMALL.getSubimage(loc * 6, 0, 6, 6);
			g.drawImage(t, i * 6, 0, null);
		}
		return ret;
	}

	/**
	 *
	 * @param size - number of characters
	 * @return
	 */
	public static BufferedImage makePrettyBorder(int size) {
		BufferedImage ret = new BufferedImage((size + 2) * 8, 24, BufferedImage.TYPE_INT_ARGB);
		Graphics g = ret.getGraphics();
		int i = 0;
		g.drawImage(PRETTY_BORDER_LEFT, i++ * 8, 0, null);

		for ( ; i <= size; i++) {
			g.drawImage(PRETTY_BORDER, i * 8, 0, null);
		}

		g.drawImage(PRETTY_BORDER_RIGHT, i++ * 8, 0, null);

		return ret;
	}

	public static BufferedImage makePrettyBorderInset(int size) {
		BufferedImage ret = new BufferedImage((size + 2) * 8, 24, BufferedImage.TYPE_INT_ARGB);
		Graphics g = ret.getGraphics();
		int i = 0;
		g.drawImage(PRETTY_BORDER_INSET_LEFT, i++ * 8, 0, null);

		for ( ; i <= size; i++) {
			g.drawImage(PRETTY_BORDER_INSET, i * 8, 0, null);
		}

		g.drawImage(PRETTY_BORDER_INSET_RIGHT, i++ * 8, 0, null);

		return ret;
	}

	public static BufferedImage makePrettyBorderDisabled(int size) {
		BufferedImage ret = new BufferedImage((size + 2) * 8, 24, BufferedImage.TYPE_INT_ARGB);
		Graphics g = ret.getGraphics();

		int i = 0;
		g.drawImage(PRETTY_BORDER_LEFT_DISABLED, i++ * 8, 0, null);

		for ( ; i <= size; i++) {
			g.drawImage(PRETTY_BORDER_DISABLED, i * 8, 0, null);
		}

		g.drawImage(PRETTY_BORDER_RIGHT_DISABLED, i++ * 8, 0, null);

		return ret;
	}

	public static BufferedImage fetchImageResource(String path, BufferedImage defaultImage) {
		BufferedImage ret;
		try {
			ret = ImageIO.read(MenuGameConstants.class.getResourceAsStream(path));
		} catch (Exception e) {
			ret = defaultImage;
			System.out.println("Unable to find resource: " + path);
		}
		return ret;
	}
}