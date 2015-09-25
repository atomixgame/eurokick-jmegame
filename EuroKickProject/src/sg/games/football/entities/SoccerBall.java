package sg.games.football.entities;

import sg.games.football.geom.Wall2D;
import sg.games.football.geom.Vector2D;
import sg.games.football.gameplay.ai.info.*;
import static sg.games.football.geom.Vector2D.*;
import static sg.games.football.geom.Geometry2DFunctions.*;
import static sg.games.football.geom.Transformations.*;
import sg.games.football.geom.Utils;
import static sg.games.football.geom.Utils.*;

import java.util.ArrayList;
import java.util.List;
import sg.games.football.geom.Geometry2DFunctions;

public class SoccerBall extends MovingEntity {

    //keeps a record of the ball's position at the last update
    Vector2D oldPos;

    //a local reference to the Walls that make up the pitch boundary
    List<Wall2D> pitchBoundary;

    public SoccerBall(Vector2D pos, double ballSize, double mass, ArrayList<Wall2D> pitchBoundary) {

        //set up the base class
        super();

        setPositionParams(pos);
        setDirectionParams(
                ballSize,
                new Vector2D(0d, 0d),
                new Vector2D(0d, 1d),
                new Vector2D(1d, 1d));
        setEnergy(mass,
                -1.0, //max speed - unused
                0, //turn rate - unused
                0); //max force - unused

        this.pitchBoundary = pitchBoundary;
        oldPos = new Vector2D(0d, 0d);
        //oldPos = pos.cloneVec();
    }

    //----------------------------- AddNoiseToKick --------------------------------
    //
    // this can be used to vary the accuracy of a player's kick. Just call it 
    // prior to kicking the ball using the ball's position and the ball target as
    // parameters.
    //-----------------------------------------------------------------------------
    public static Vector2D addNoiseToKick(Vector2D BallPos, Vector2D BallTarget) {
        double Pi = Utils.Pi;

        double displacement = (Pi - Pi * Params.Instance().PlayerKickingAccuracy) * RandomClamped();

        Vector2D toTarget = BallTarget.minus(BallPos);

        vec2DRotateAroundOrigin(toTarget, displacement);

        return toTarget.plus(BallPos);
    }

    //-------------------------- Kick ----------------------------------------
    // 
    // applys a force to the ball in the direction of heading. Truncates
    // the new velocity to make sure it doesn't exceed the max allowable.
    //------------------------------------------------------------------------
    public void kick(Vector2D direction, double force) {
        //ensure direction is normalized
        Vector2D d = direction.cloneVec();
        d.Normalize();

        //calculate the acceleration
        Vector2D acceleration = d.multiply(force).divide(mass);

        //update the velocity
        velocity.cloneVec(acceleration);
    }

    //----------------------------- Update -----------------------------------
    //
    // updates the ball physics, tests for any collisions and adjusts
    // the ball's velocity accordingly
    //------------------------------------------------------------------------
    public void update(float tpf) {
        //keep a record of the old position so the goal::scored method
        //can utilize it for goal testing
        if (position != null) {
            oldPos.cloneVec(position);
        }
        //Test for collisions
        testCollisionWithWalls(pitchBoundary);

        //Simulate Params.Instance().Friction. Make sure the speed is positive 
        //first though
        double friction = Params.Instance().Friction;
        //println (velocity)
        //println("LengthSq" + velocity.LengthSq() +" friction " + friction)
        if (velocity.LengthSq() > friction * friction) {
            velocity = velocity.plus(Vec2DNormalize(velocity).multiply(friction));
            //println (velocity)
            position = position.plus(velocity.multiply(tpf));
            //println (position)
            //update heading
            heading = Vec2DNormalize(velocity);
        }

        super.update(tpf);
    }

    //---------------------- TimeToCoverDistance -----------------------------
    //
    // Given a force and a distance to cover given by two vectors, this
    // method calculates how long it will take the ball to travel between
    // the two points
    //------------------------------------------------------------------------
    public double timeToCoverDistance(Vector2D A, Vector2D B, double force) {
        //this will be the velocity of the ball in the next time step *if*
        //the player was to make the pass. 
        double speed = force / mass;

        //calculate the velocity at B using the equation
        //
        // v^2 = u^2 + 2as
        //
        //first calculate s (the distance between the two positions)
        double distanceToCover = vec2DDistance(A, B);

        double term = speed * speed + 2.0 * distanceToCover * Params.Instance().Friction;

        //if (u^2 + 2as) is negative it means the ball cannot reach point B.
        if (term <= 0.0) {
            return -1.0;
        }

        double v = Math.sqrt(term);

        //it IS possible for the ball to reach B and we know its speed when it
        //gets there, so now it's easy to calculate the time using the equation
        //
        // t = v-u
        // ---
        // a
        //
        return (v - speed) / Params.Instance().Friction;
    }

    //--------------------- FuturePosition -----------------------------------
    //
    // given a time this method returns the ball position at that time in the
    // future
    //------------------------------------------------------------------------
    public Vector2D getFuturePosition(double time) {
        //using the equation s = ut + 1/2at^2, where s = distance, a = friction
        //u=start velocity

        //calculate the ut term, which is a vector
        Vector2D ut = velocity.multiply(time);

        //calculate the 1/2at^2 term, which is scalar
        double half_a_t_squared = 0.5 * Params.Instance().Friction * time * time;

        //turn the scalar quantity into a vector by multiplying the value with
        //the normalized velocity vector (because that gives the direction)
        Vector2D ScalarToVector = Vec2DNormalize(velocity).multiply(half_a_t_squared);

        //the predicted position is the balls position plus these two terms
        return getPos().plus(ut).plus(ScalarToVector);
    }

    //----------------------- TestCollisionWithWalls -------------------------
    //
    public void testCollisionWithWalls(List<Wall2D> walls) {
        //test ball against each wall, find out which is closest
        int idxClosest = -1;

        //println ("Colision " +velocity)
        Vector2D velNormal = Vec2DNormalize(velocity);

        Vector2D intersectionPoint = new Vector2D();
        Vector2D collisionPoint = new Vector2D();

        double distToIntersection = Double.MAX_VALUE;

        //iterate through each wall and calculate if the ball intersects.
        //If it does then store the index into the closest intersecting wall
        for (int w = 0; w < walls.size(); ++w) {
            //assuming a collision if the ball continued on its current heading 
            //calculate the point on the ball that would hit the wall. This is 
            //simply the wall's normal(inversed) multiplied by the ball's radius
            //and added to the balls center (its position)
            Wall2D wallSegment = walls.get(w);
            Vector2D thisCollisionPoint = getPos().minus(wallSegment.Normal().multiply(getBRadius()));

            //calculate exactly where the collision point will hit the plane 
            if (Geometry2DFunctions.whereIsPoint(thisCollisionPoint,
                    wallSegment.From(),
                    wallSegment.Normal()) == Geometry2DFunctions.SpanType.plane_backside) {
                double distToWall = distanceToRayPlaneIntersection(thisCollisionPoint,
                        wallSegment.Normal(),
                        wallSegment.From(),
                        wallSegment.Normal());

                intersectionPoint = thisCollisionPoint.plus(wallSegment.Normal().multiply(distToWall));

            } else {
                double distToWall = distanceToRayPlaneIntersection(thisCollisionPoint,
                        velNormal,
                        wallSegment.From(),
                        wallSegment.Normal());

                intersectionPoint = thisCollisionPoint.plus(velNormal.multiply(distToWall));
            }

            //check to make sure the intersection point is actually on the line
            //segment
            boolean OnLineSegment = false;
            Vector2D wallNormal = wallSegment.Normal().multiply(20.0);
            if (LineIntersection2D(wallSegment.From(),
                    wallSegment.To(),
                    thisCollisionPoint.minus(wallNormal),
                    thisCollisionPoint.plus(wallNormal))) {

                OnLineSegment = true;
            }

            //Note, there is no test for collision with the end of a line segment
            //now check to see if the collision point is within range of the
            //velocity vector. [work in distance squared to avoid Math.sqrt] and if it
            //is the closest hit found so far. 
            //If it is that means the ball will collide with the wall sometime
            //between this time step and the next one.
            double distSq = vec2DDistanceSq(thisCollisionPoint, intersectionPoint);

            if ((distSq <= velocity.LengthSq()) && (distSq < distToIntersection) && OnLineSegment) {
                distToIntersection = distSq;
                idxClosest = w;
                collisionPoint = intersectionPoint;
            }
        }//next wall

        //to prevent having to calculate the exact time of collision we
        //can just check if the velocity is opposite to the wall normal
        //before reflecting it. This prevents the case where there is overshoot
        //and the ball gets reflected back over the line before it has completely
        //reentered the playing area.
        if ((idxClosest >= 0) && velNormal.dot(walls.get(idxClosest).Normal()) < 0) {
            velocity.Reflect(walls.get(idxClosest).Normal());
        }
    }

    //----------------------- PlaceAtLocation -------------------------------------
    //
    // positions the ball at the desired location and sets the ball's velocity to
    // zero
    //-----------------------------------------------------------------------------
    public void placeAtPosition(Vector2D NewPos) {
        position = NewPos;

        oldPos.cloneVec(position);

        velocity.setZero();
    }

    //this is used by players and goalkeepers to 'trap' a ball -- to stop
    //it dead. That player is then assumed to be in possession of the ball
    //and pOwner is adjusted accordingly
    public void trap() {
        velocity.setZero();
    }

    public Vector2D getOldPos() {
        return oldPos;
    }
}
