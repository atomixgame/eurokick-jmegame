package sg.games.football.geom;

import static sg.games.football.geom.Vector2D.*;

public class Wall2D {

    Vector2D vA, vB, vN;
    boolean RenderNormals = true;

    public void CalculateNormal() {
        Vector2D temp = Vec2DNormalize(vB.minus(vA));

        vN.x = -temp.y;
        vN.y = temp.x;
    }

    public Wall2D() {
    }

    public Wall2D(Vector2D A, Vector2D B) {
        vA = (A);
        vB = (B);
        vN = new Vector2D();
        CalculateNormal();
    }

    public Wall2D(Vector2D A, Vector2D B, Vector2D N) {
        vA = (A);
        vB = (B);
        vN = (N);

    }

    public Vector2D From() {
        return vA;
    }

    public void SetFrom(Vector2D v) {
        vA = v;
        CalculateNormal();
    }

    public Vector2D To() {
        return vB;
    }

    public void SetTo(Vector2D v) {
        vB = v;
        CalculateNormal();
    }

    public Vector2D Normal() {
        return vN;
    }

    public void SetNormal(Vector2D n) {
        vN = n;
    }

    public Vector2D getCenter() {
        return vA.plus(vB).divide(2.0);
    }
}
