package sg.games.football.gameplay;

import static sg.games.football.geom.Utils.*;
import sg.games.football.geom.Vector2D;

public class Region {

    public static enum RegionModifier {

        halfsize, normal
    };

    public static enum RegionDirection {

        North(0, -1), NorthEast(1, -1), East(1, 0), SouthEast(1, 1), South(0, 1), SouthWest(-1, 1), West(-1, 0), NorthWest(0, 0);
        float x;
        float y;

        RegionDirection(float x, float y) {
            this.x = x;
            this.y = y;
        }
    };

    double top;
    double left;
    double right;
    double bottom;

    double width;
    double height;

    public Vector2D center;

    int iID = -1;

    public Region() {
        this.top = 0;
        this.bottom = 0;
        this.left = 0;
        this.right = 0;
    }

    public Region(double left,
            double top,
            double right,
            double bottom) {
        this(left, top, right, bottom, -1);
    }

    public Region(double left,
            double top,
            double right,
            double bottom, int id) {
        this.top = top;
        this.right = right;
        this.left = left;
        this.bottom = bottom;
        this.iID = id;

        calculate();
    }

    public void calculate() {
        //calculate center of region
        center = new Vector2D((left + right) * 0.5, (top + bottom) * 0.5);
        width = Math.abs(right - left);
        height = Math.abs(bottom - top);
    }

    //-------------------------------
    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getWidth() {
        return Math.abs(right - left);
    }

    public double getHeight() {
        return Math.abs(top - bottom);
    }

    public double length() {
        return Math.max(getWidth(), getHeight());
    }

    public double getBreadth() {
        return Math.min(getWidth(), getHeight());
    }

    public Vector2D getCenter() {
        return center;
    }

    public int getID() {
        return iID;
    }

    public Vector2D getPosDirection(RegionDirection dir) {
        if (dir == RegionDirection.North) {
            return new Vector2D((right + left) / 2, top);
        }

        return null;
    }

    //returns a vector representing a random location
    //within the region
    public Vector2D getRandomPosition() {
        return new Vector2D(RandInRange(left, right),
                RandInRange(top, bottom));
    }

    //returns true if the given position lays inside the region. The
    //region modifier can be used to contract the region bounderies
    public boolean isInside(Vector2D pos, RegionModifier r) {
        if (r == RegionModifier.normal) {
            return ((pos.x > left) && (pos.x < right)
                    && (pos.y > top) && (pos.y < bottom));
        } else {
            double marginX = width * 0.25;
            double marginY = height * 0.25;

            return ((pos.x > (left + marginX)) && (pos.x < (right - marginX))
                    && (pos.y > (top + marginY)) && (pos.y < (bottom - marginY)));
        }

    }

    public boolean isInside(Vector2D pos) {
        return isInside(pos, Region.RegionModifier.normal);

    }
}
