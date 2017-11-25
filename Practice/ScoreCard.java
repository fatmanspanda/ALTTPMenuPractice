package Practice;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static Practice.MenuGameConstants.*;

public class ScoreCard {
	public int startPresses;
	public int moves;
	public final long startTime;
	public long endTime;
	public int finalScore;
	public int finalTime;
	public final int minMoves;
	public final Difficulty d;
	public final ItemSlot[] itemsPlaced;
	public final PlayerMovement[] bestPath;
	public PlayerMovement[] yourMoves;
	public int turn;

	public ScoreCard(Difficulty d, int minMoves, PlayerMovement[] bestPath, ItemSlot[] itemsPlaced) {
		this.d = d;
		this.minMoves = minMoves;
		startPresses = 0;
		moves = 0;
		this.bestPath = bestPath;
		this.itemsPlaced = itemsPlaced;
		startTime = System.currentTimeMillis();
	}

	public int calcScore() {
		// score for how long it took
		endTime = System.currentTimeMillis(); // calculate end time on score request
		long timeDiff = endTime - startTime;
		finalTime = (int) (timeDiff);
		int timeScore = (4000 - finalTime);

		// difference between moves made and optimal moves
		int diffScore = ( 500 * ( (minMoves + 1) - moves) );
		int moveBonus = (moves == minMoves) ? 1000 : 0; // bonus for being optimal

		// penalty for pressing start on the wrong item
		int startPenalty = 0;
		if (startPresses > 1) {
			startPenalty = startPresses * 1000;
		}

		finalScore = timeScore + diffScore + moveBonus - startPenalty + d.bonus;
		return finalScore;
	}

	public void setPlayerPath(PlayerMovement[] yourMoves) {
		this.yourMoves = yourMoves;
	}

	public void setTurn(int t) {
		turn = t;
	}

	public BufferedImage drawTurn() {
		BufferedImage ret = new BufferedImage(BG_WIDTH, BG_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = ret.getGraphics();
		g.drawImage(BACKGROUND, 0, 0, null);
		int i = 0;
		ItemPoint cursorLoc;
		for (ItemSlot s : itemsPlaced) {
			int pos = i++;
			if (s == null) {
				continue;
			}
			cursorLoc = ItemPoint.valueOf("SLOT_" + pos);
			g.drawImage(s.getCurrentImage(),
					ITEM_ORIGIN_X + cursorLoc.x,
					ITEM_ORIGIN_Y + cursorLoc.y,
					null);
		}
		cursorLoc = ItemPoint.valueOf("SLOT_" + yourMoves[0].LOCATION);
		g.drawImage(CURSOR,
				ITEM_ORIGIN_X + cursorLoc.x - CURSOR_OFFSET,
				ITEM_ORIGIN_Y + cursorLoc.y - CURSOR_OFFSET,
				null);

		g.drawImage(PlayerMovement.drawOptimalPath(bestPath, true), 0, 0, null);
		g.drawImage(PlayerMovement.drawPlayerPath(yourMoves), 0, 0, null);

		return ret;
	}

	public int[] toArray() {
		return new int[] {
				turn,
				finalScore,
				moves,
				minMoves,
				finalTime,
				startPresses - 1
		};
	}
}