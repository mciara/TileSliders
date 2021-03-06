package com.tr.tilesliders;

import android.text.TextUtils;

import com.tr.tilesliders.database.Score;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Author: Maciej Ciara
 * Created: 02/01/2015 11:59.
 */
public class GameController {
    public static final int TILE_EMPTY = R.drawable.tile_empty;

    private List<Integer> tiles = new ArrayList<>();
    private List<Integer> tilesInPlace = new ArrayList<>();

    private int moves;
    private long time;
    private long startTime;
    private boolean inProgress = false;

    public GameController() {
        tilesInPlace.add(R.drawable.tile1);
        tilesInPlace.add(R.drawable.tile2);
        tilesInPlace.add(R.drawable.tile3);
        tilesInPlace.add(R.drawable.tile4);
        tilesInPlace.add(R.drawable.tile5);
        tilesInPlace.add(R.drawable.tile6);
        tilesInPlace.add(R.drawable.tile7);
        tilesInPlace.add(R.drawable.tile8);
        tilesInPlace.add(TILE_EMPTY);

        tiles.addAll(tilesInPlace);

        randomizeTilesOrder();
    }

    public void startGame() {
        inProgress = true;
        moves = 0;
        time = 0L;
        startTime = System.nanoTime();
    }

    public Score endGame() {
        time = System.nanoTime() - startTime;
        inProgress = false;
        return new Score(moves, time);
    }

    public void addMove() {
        moves++;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public boolean isValidTileClicked(int position) {
        int tileEmptyPosition = getTileEmptyPosition();
        return position - 3 == tileEmptyPosition || position + 3 == tileEmptyPosition ||
                (position - 1 == tileEmptyPosition && position % 3 > 0) ||
                (position + 1 == tileEmptyPosition && position % 3 < 2);
    }

    public boolean isGameFinished() {
        return tilesInPlace.equals(tiles);
    }

    public void swapTileWithEmpty(int position) {
        Collections.swap(tiles, position, getTileEmptyPosition());
    }

    public int getTileEmptyPosition() {
        return tiles.indexOf(TILE_EMPTY);
    }

    public List<Integer> getTiles() {
        return tiles;
    }

    public String getTilesAsString() {
        return TextUtils.join(",", tiles);
    }

    public void restartGame() {
        inProgress = false;
        moves = 0;
        randomizeTilesOrder();
    }

    public int getMoves() {
        return moves;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setTiles(String tilesInString) {
        String[] tilesArray = TextUtils.split(tilesInString, ",");
        tiles = new ArrayList<>();

        for (String tile : tilesArray) {
            tiles.add(Integer.valueOf(tile));
        }

        if (isGameFinished()) {
            randomizeTilesOrder();
        }
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    private void randomizeTilesOrder() {
        Collections.shuffle(tiles, new Random(System.nanoTime()));
    }
}

