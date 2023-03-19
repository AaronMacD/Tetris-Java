package com.tetris.t6;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GameScreen implements Screen {
    GameController game;
    public Square[][] board;


    //Sounds



    public GameScreen(GameController game) {
        this.game = game;
        //gameplay = new GameLogic();
        board = new Square[10][22];
        //initialize board
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 22; y++) {
                board[x][y] = new Square(x, y, Color.BLACK);
            }
        }

        //Loading Sounds

        //Loading Music

    }

    public void setSquare(int x, int y, Color color) {
        board[x][y] = new Square(x, y, color);
    }


    @Override
    public void show() {

    }

    public void drawPiece(Piece p) {
        int x = p.getxCoord();
        int y = p.getyCoord();
        Color color = p.getColor();
        int rNum = p.getRotationNum();
        int[][][] dimensions = p.getDimensions();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (dimensions[rNum][i][j] == 1) {
                    board[x+i][y-j] = new Square(x+i, y-j, color);
                }
            }
        }
    }

    @Override
    public void render(float delta) {

        //Inputs
        float squareWidth = game.camera.viewportWidth / 10;
        float squareHeight = game.camera.viewportHeight / 20;

        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 20; y++) {
                game.shapeRenderer.rect(x, y, x * squareWidth*100, y * squareHeight*100);
                game.shapeRenderer.setColor(board[x][y].getColor());
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
