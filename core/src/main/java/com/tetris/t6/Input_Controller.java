package com.tetris.t6;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input_Controller {




    /**
     * Private Class for tracking keyboard input
     */
    private class KeyboardListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyPressed(KeyEvent e) {
            /*
            Example:

            if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'w' || e.getKeyChar() == 'W' ){
                game.recurseUp(0,0);
            }
            */

        }
        @Override
        public void keyReleased(KeyEvent e) {}
    }
}
