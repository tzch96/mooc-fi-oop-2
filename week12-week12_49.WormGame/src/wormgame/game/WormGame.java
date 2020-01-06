package wormgame.game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;
import wormgame.Direction;
import wormgame.gui.Updatable;
import wormgame.domain.*;

public class WormGame extends Timer implements ActionListener {

    private int width;
    private int height;
    private boolean continues;
    private Updatable updatable;
    private Worm worm;
    private Apple apple;

    public WormGame(int width, int height) {
        super(1000, null);

        this.width = width;
        this.height = height;
        this.continues = true;

        this.worm = new Worm(width / 2, height / 2, Direction.DOWN);
        this.apple = new Apple((int)(Math.random() * width), (int)(Math.random() * height));
        checkWormAppleCollision();

        addActionListener(this);
        setInitialDelay(2000);
    }


    public boolean continues() {
        return continues;
    }

    public void setUpdatable(Updatable updatable) {
        this.updatable = updatable;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (!continues) {
            return;
        }

        worm.move();

        if (worm.runsInto(apple)) {
            worm.grow();
            checkWormAppleCollision();
        }

        if (worm.runsIntoItself()) {
            this.continues = false;
        }

        for (Piece piece: worm.getPieces()) {
            if (piece.getX() >= width || piece.getX() <= 0 || piece.getY() >= height || piece.getY() <= 0) {
                this.continues = false;
            }
        }

        this.updatable.update();
        setDelay(1000 / worm.getLength());
    }

    public Worm getWorm() {
        return worm;
    }

    public void setWorm(Worm worm) {
        this.worm = worm;
    }

    public Apple getApple() {
        return apple;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public void checkWormAppleCollision() {
        while (worm.runsInto(apple)) {
            this.apple = new Apple((int)(Math.random() * width), (int)(Math.random() * height));
        }
    }
}
