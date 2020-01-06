package wormgame.domain;

import wormgame.Direction;
import java.util.List;
import java.util.ArrayList;

public class Worm {

    private List<Piece> pieces;
    private Direction direction;
    private boolean isGrowing;

    public Worm(int originalX, int originalY, Direction originalDirection) {
        this.pieces = new ArrayList<Piece>();
        pieces.add(new Piece(originalX, originalY));

        this.direction = originalDirection;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction dir) {
        this.direction = dir;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public int getLength() {
        return pieces.size();
    }

    public void move() {
        int xHead = pieces.get(pieces.size() - 1).getX();
        int yHead = pieces.get(pieces.size() - 1).getY();

        if (direction == Direction.UP) {
            pieces.add(new Piece(xHead, yHead - 1));
        } else if (direction == Direction.DOWN) {
            pieces.add(new Piece(xHead, yHead + 1));
        } else if (direction == Direction.LEFT) {
            pieces.add(new Piece(xHead - 1, yHead));
        } else if (direction == Direction.RIGHT) {
            pieces.add(new Piece(xHead + 1, yHead));
        }

        if (getLength() > 3 && !isGrowing) {
            pieces.remove(0);
        }

        if (isGrowing) {
            isGrowing = false;
        }
    }

    public void grow() {
        isGrowing = true;
    }

    public boolean runsInto(Piece piece) {
        for (Piece wormPiece: pieces) {
            if (wormPiece.runsInto(piece)) {
                return true;
            }
        }

        return false;
    }

    public boolean runsIntoItself() {
        if (getLength() == 1) {
            return false;
        }

        Piece wormHead = pieces.get(pieces.size() - 1);

        for (int i = 0; i < pieces.size() - 1; i++) {
            if (wormHead.runsInto(pieces.get(i))) {
                return true;
            }
        }

        return false;
    }
}