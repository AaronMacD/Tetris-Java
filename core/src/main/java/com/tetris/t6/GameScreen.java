package com.tetris.t6;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen implements Screen {
    GameController game;
    public static Square[][] board;

    public final int ROWS = 22;
    public final int COLS = 10;


    //Sounds



    public GameScreen(GameController game) {
        this.game = game;
        //gameplay = new GameLogic();
        board = new Square[ROWS][COLS];
        //initialize board
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = new Square(i, j, Color.BLACK);
            }
        }

        //Loading Sounds

        //Loading Music

    }

    @Override
    public void show() {

    }

    public void drawPiece(Piece p) {
        int row = p.getRow();
        int col = p.getCol();
        Color color = p.getColor();
        int rNum = p.getRotationNum();
        int[][][] dimensions = p.getDimensions();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (dimensions[rNum][i][j] == 1) {
                    board[row+i][col+j] = new Square(row+i, col+j, color);
                }
            }
        }
    }

    @Override
    public void render(float delta) {
//  TODO: consider something like this for updating game logic?
//        if(!paused)
//            GameLogic.update(deltaTime);

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < ROWS - 2; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j].drawSquare(game.shapeRenderer);
            }
        }
        game.shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
