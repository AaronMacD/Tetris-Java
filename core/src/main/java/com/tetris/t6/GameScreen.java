package com.tetris.t6;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;


/**
 * The type Game screen.
 */
public final class GameScreen implements Screen {
    /**
     * Instance of the game.
     */
    private final TetrisGame game;
    /**
     * Number of players selected from the main menu. 1 or 2 always.
     */
    private final int numPlayers;

    /**
     * Max level the game can get up to. Only 10, as there are only 10 speeds.
     */
    private static final int MAX_LEVEL = 10;
    /**
     * Board for player 1.
     */
    private Square[][] p1Board;
    /**
     * Board for player2.
     */
    private Square[][] p2Board;
    /**
     * Accesses sound effects.
     */
    private SoundManager sfx;
    /**
     * Game music.
     */
    private final Music victory1Music;
    /**
     * Texture 1 for the background.
     */
    private Texture background;
    /**
     * Texture 2 for the background.
     */
    private Texture background2;
    /**
     * Player 1 object.
     */
    private PlayerLogic p1;
    /**
     * Player 2 object.
     */
    private PlayerLogic p2;

    /**
     * Instantiates a new Game screen.
     *
     * @param aGame     the main game containing cameras, batching,
     *                  texture drawing, and other objects used by all screens.
     * @param playerNum the number of players
     */
    public GameScreen(final TetrisGame aGame, final int playerNum) {
        this.numPlayers = playerNum;
        this.game = aGame;
        p1 = new PlayerLogic(1);
        p2 = new PlayerLogic(2);

        //Load graphical assets
        background = new Texture(Gdx.files.internal("bg_gamescreen.png"));
        background2 = new Texture(Gdx.files.internal("bg2_gamescreen.png"));

        sfx = new SoundManager();

        //Loading Music
        victory1Music = Gdx.audio.newMusic(Gdx.files.internal("Victory1.wav"));
        victory1Music.setLooping(true);
        victory1Music.play();
        victory1Music.setVolume(0.30f);
    }

    /**
     * Renders the game screen every "delta" seconds. Handles player input.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(final float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        ///////////////////////////////////////////////////////////////////////
        ////////////////////////////////Player 1 Functions/////////////////////
        ///////////////////////////////////////////////////////////////////////
        p1.setTimeControls(p1.getTimeControls() + delta);
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            p1.hardDrop();
            p1.setTimeMovement(100f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            p1.moveDownLogically();
            p1.setTimeMovement(100f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)
            && p1.getTimeControls() > 0.15f) {
            p1.moveLeftRight(-1);
            p1.setTimeControls(0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)
            && p1.getTimeControls() > 0.15f) {
            p1.moveLeftRight(1);
            p1.setTimeControls(0f);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            p1.rotate(1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            p1.rotate(-1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            p1.drawPiece(Color.BLACK);
            if (p1.getHeldBlock().getHeldPiece() == null) {
                p1.getHeldBlock().setHeldPiece(p1.getCurrentPiece());
                p1.setCurrentPiece(p1.getNextBlock().getNextPiece());
                p1.getNextBlock().generateNextPiece();
                sfx.playHold();
            } else {
                if (!p1.getSwapUsed()) {
                    p1.setCurrentPiece(p1.getHeldBlock()
                        .swapPiece(p1.getCurrentPiece()));
                    sfx.playHold();
                }
            }

            p1.setSwapUsed(true);
        }
        p1.drawPiece(p1.getCurrentPiece().getColor());
        if (p1.getLevel() < MAX_LEVEL) {
            p1.levelUp();
        }
        p1.moveDownLogically();

        String scoreText = String.format("Score: %d", p1.getScore());
        String levelText = String.format("Level: %d", p1.getLevel());
        String linesClearedText = String.format("Lines Cleared: %d",
            p1.getLinesCleared());
        String heldText = "Held Block";
        String nextText = "Next Block";
        String controlsText = "Controls : \n"
            + "Move Left: A Key\n"
            + "Move Right: D Key\n"
            + "Soft Drop: S Key\n"
            + "Hard Drop: W Key\n"
            + "Rotate Clockwise: E Key\n"
            + "Rotate C-Clockwise: Q Key\n"
            + "Hold Block: Space Key\n"
            + "Pause Menu: Escape Key";

        ///////////////////////////////////////////////////////////////////////
        ////////////////////////////////Player 2 Functions/////////////////////
        ///////////////////////////////////////////////////////////////////////
        if (numPlayers == 2) {
            p2.setTimeControls(p2.getTimeControls() + delta);
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_8)) {
                p2.hardDrop();
                p2.setTimeMovement(100f);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_2)) {
                p2.setTimeMovement(100f);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_4)
                && p2.getTimeControls() > 0.15f) {
                p2.moveLeftRight(-1);
                p2.setTimeControls(0f);
            }

            if (Gdx.input.isKeyPressed(Input.Keys.NUMPAD_6)
                && p2.getTimeControls() > 0.15f) {
                p2.moveLeftRight(1);
                p2.setTimeControls(0f);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_9)) {
                p2.rotate(1);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_7)) {
                p2.rotate(-1);
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_0)) {
                p2.drawPiece(Color.BLACK);
                if (p2.getHeldBlock().getHeldPiece() == null) {
                    p2.getHeldBlock().setHeldPiece(p2.getCurrentPiece());
                    p2.setCurrentPiece(p2.getNextBlock().getNextPiece());
                    p2.getNextBlock().generateNextPiece();
                    sfx.playHold();
                } else {
                    if (!p2.getSwapUsed()) {
                        p2.setCurrentPiece(p2.getHeldBlock()
                            .swapPiece(p2.getCurrentPiece()));
                        sfx.playHold();
                    }
                }

                p2.setSwapUsed(true);
            }
            p2.drawPiece(p2.getCurrentPiece().getColor());
            if (p2.getLevel() < MAX_LEVEL) {
                p2.levelUp();
            }
            p2.moveDownLogically();
        }
        String scoreText2 = String.format("Score: %d", p2.getScore());
        String levelText2 = String.format("Level: %d", p2.getLevel());
        String linesClearedText2 = String.format("Lines Cleared: %d",
            p2.getLinesCleared());
        String heldText2 = "Held Block";
        String nextText2 = "Next Block";
        String controlsText2 = "Controls : \n"
            + "Move Left: Numpad 4\n"
            + "Move Right: Numpad 6\n"
            + "Soft Drop: Numpad 2\n"
            + "Hard Drop: Numpad 8\n"
            + "Rotate Clockwise: Numpad 9\n"
            + "Rotate C-Clockwise: Numpad 7\n"
            + "Hold Block: Numpad 0\n"
            + "Pause Menu: Escape Key";
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.pause();
            this.hide();
            game.setScreen(new PauseScreen(game, this));
        }
        if (p1.checkLoss()) {
            loseGame(1);
        }
        if (p2.checkLoss()) {
            loseGame(2);
        }

        /////////////////////////////Drawing//////////////////////////////
        game.batch.begin();
        //draw bg first
        game.batch.draw(background, 0, 0);

        if (numPlayers == 2) {
            game.batch.draw(background2, 650, 0);
        }

        ////////////////////////////////////////////////////////////////////////
        ////////////////////////////////Player 1 Drawing////////////////////////
        ////////////////////////////////////////////////////////////////////////
        p1Board = p1.getBoard();
        for (int i = 1; i < PlayerLogic.ROWS; i++) {
            for (int j = 0; j < PlayerLogic.COLS; j++) {
                p1Board[i][j].drawSquare(game.drawer);
            }
        }

        p1.getHeldBlock().drawNext(game.drawer);
        p1.getNextBlock().drawNext(game.drawer);

        game.font.draw(game.batch, scoreText, 30, 825);
        game.font.draw(game.batch, levelText, 175, 825);
        game.font.draw(game.batch, linesClearedText, 300, 825);
        game.font.draw(game.batch, heldText, 495, 540);
        game.font.draw(game.batch, nextText, 495, 780);
        game.font.draw(game.batch, controlsText, 420, 300);

        ///////////////////////////////////////////////////////////////////////
        ////////////////////////////////Player 2 Drawing///////////////////////
        ///////////////////////////////////////////////////////////////////////
        p2Board = p2.getBoard();
        for (int i = 1; i < PlayerLogic.ROWS; i++) {
            for (int j = 0; j < PlayerLogic.COLS; j++) {
                p2Board[i][j].drawSquare(game.drawer);
            }
        }

        p2.getHeldBlock().drawNext(game.drawer);
        p2.getNextBlock().drawNext(game.drawer);

        game.font.draw(game.batch, scoreText2, 30 + 650, 825);
        game.font.draw(game.batch, levelText2, 175 + 650, 825);
        game.font.draw(game.batch, linesClearedText2, 300 + 650, 825);
        game.font.draw(game.batch, heldText2, 495 + 650, 540);
        game.font.draw(game.batch, nextText2, 495 + 650, 780);
        game.font.draw(game.batch, controlsText2, 420 + 650, 300);

        game.batch.end();
    }

    private void loseGame(final int playerNum) {
        victory1Music.stop();
        this.pause();
        this.hide();
        game.setScreen(new LossScreen(this.game, playerNum));
        this.dispose();
    }

    @Override
    public void show() {
        if (numPlayers == 2) {
            Gdx.graphics.setWindowedMode(650 * 2, Gdx.graphics.getHeight());
            game.camera.setToOrtho(false, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
            game.camera.update();
            game.batch.setProjectionMatrix(game.camera.combined);
        }
    }

    @Override
    public void resize(final int width, final int height) {
        game.viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void pause() {
        victory1Music.pause();
    }

    @Override
    public void resume() {
        victory1Music.play();
    }

    @Override
    public void hide() {
        //unused
    }

    @Override
    public void dispose() {
        //unused
    }
}
