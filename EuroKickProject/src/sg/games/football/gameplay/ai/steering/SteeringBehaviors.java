package sg.games.football.gameplay.ai.steering;

import java.util.ArrayList;
import java.util.List;
import sg.atom.entity.EntityManager;
import sg.games.football.entities.FootballEntityManager;
import sg.games.football.geom.Vector2D;
import static sg.games.football.geom.Vector2D.*;

import sg.games.football.entities.PlayerBase;
import sg.games.football.entities.SoccerBall;
import sg.games.football.gameplay.SoccerPitch;
import sg.games.football.gameplay.ai.info.Params;

public class SteeringBehaviors {

    private PlayerBase player;

    private SoccerBall ball;

    //the steering force created by the combined effect of all
    //the selected behaviors
    private Vector2D steeringForce;

    //the current target (usually the ball or predicted ball position)
    private Vector2D target;

    //the distance the player tries to interpose from the target
    private double interposeDist;

    //multipliers. 
    private double multSeparation;

    //how far it can 'see'
    private double viewDistance;

    //binary flags to indicate whether or not a behavior should be active
    private int flags;

    //a vertex buffer to contain the feelers rqd for dribbling
    List<Vector2D> Antenna;

    //used by group behaviors to tag neighbours
    public boolean tagged;

    //Arrive makes use of these to determine how quickly a vehicle
    //should decelerate to its target
    public enum Deceleration {

        slow(3), normal(2), fast(1);

        int value;

        Deceleration(int value) {
            this.value = value;
        }
    }

    public SteeringBehaviors(PlayerBase agent, SoccerPitch world) {
        this.player = agent;
        this.flags = 0;
        this.multSeparation = Params.Instance().SeparationCoefficient;
        this.tagged = false;
        this.viewDistance = Params.Instance().ViewDistance;

        this.interposeDist = 0.0;
        this.Antenna = new ArrayList<Vector2D>();
        this.steeringForce = new Vector2D();
    }

    public void setBall(SoccerBall ball) {
        this.ball = ball;

        //the current target (usually the ball or predicted ball position)
        this.target = new Vector2D();
    }

    //this function tests if a specific bit of flags is set
    private boolean isOn(BehaviorType bt) {
        //return true;
        return (flags & bt.value) == bt.value;
    }

    //this function tests if a specific bit of flags is set
    private boolean isOn(int bt) {
        //return true;
        return (flags & bt) == bt;
    }

    public Vector2D getForce() {
        return steeringForce;
    }

    public Vector2D getTarget() {
        return target;
    }

    public void setTarget(Vector2D t) {
        target = t;
    }

    public double getInterposeDistance() {
        return interposeDist;
    }

    public void setInterposeDistance(double d) {
        interposeDist = d;
    }

    public boolean isTagged() {
        return tagged;
    }

    public void setTag() {
        tagged = true;
    }

    public void unsetTag() {
        tagged = false;
    }

    public void seekOn() {
        flags |= BehaviorType.seek.value;
    }

    public void arriveOn() {
        flags |= BehaviorType.arrive.value;
    }

    public void pursuitOn() {
        flags |= BehaviorType.pursuit.value;
    }

    public void separationOn() {
        flags |= BehaviorType.separation.value;
    }

    public void interposeOn(double d) {
        flags |= BehaviorType.interpose.value;
        interposeDist = d;
    }

    public void seekOff() {
        if (isOn(BehaviorType.seek)) {
            flags ^= BehaviorType.seek.value;
        }
    }

    public void arriveOff() {
        if (isOn(BehaviorType.arrive)) {
            flags ^= BehaviorType.arrive.value;
        }
    }

    public void pursuitOff() {
        if (isOn(BehaviorType.pursuit)) {
            flags ^= BehaviorType.pursuit.value;
        }
    }

    public void separationOff() {
        if (isOn(BehaviorType.separation)) {
            flags ^= BehaviorType.separation.value;
        }
    }

    public void interposeOff() {
        if (isOn(BehaviorType.interpose)) {
            flags ^= BehaviorType.interpose.value;
        }
    }

    public boolean SeekIsOn() {
        return isOn(BehaviorType.seek.value);
    }

    public boolean ArriveIsOn() {
        return isOn(BehaviorType.arrive.value);
    }

    public boolean PursuitIsOn() {
        return isOn(BehaviorType.pursuit.value);
    }

    public boolean SeparationIsOn() {
        return isOn(BehaviorType.separation.value);
    }

    public boolean InterposeIsOn() {
        return isOn(BehaviorType.interpose.value);
    }

    //--------------------- AccumulateForce ----------------------------------
    //
    // This function calculates how much of its max steering force the 
    // vehicle has left to apply and then applies that amount of the
    // force to add.
    //------------------------------------------------------------------------
    public boolean accumulateForce(Vector2D sf, Vector2D ForceToAdd) {
        //first calculate how much steering force we have left to use
        double MagnitudeSoFar = sf.length();

        double magnitudeRemaining = player.getMaxForce() - MagnitudeSoFar;

        //return false if there is no more force left to use
        if (magnitudeRemaining <= 0.0) {
            return false;
        }

        //calculate the magnitude of the force we want to add
        double MagnitudeToAdd = ForceToAdd.length();

        //now calculate how much of the force we can really add 
        if (MagnitudeToAdd > magnitudeRemaining) {
            MagnitudeToAdd = magnitudeRemaining;
        }

        //add it to the steering force
        sf.minusLocal((Vec2DNormalize(ForceToAdd).multiply(MagnitudeToAdd)));

        return true;
    }

    //---------------------- Calculate ---------------------------------------
    //
    // calculates the overall steering force based on the currently active
    // steering behaviors. 
    //------------------------------------------------------------------------
    public Vector2D calculate() {
        //reset the force
        steeringForce.setZero();

        //this will hold the value of each individual steering force
        steeringForce = sumForces();

        //make sure the force doesn't exceed the vehicles maximum allowable
        steeringForce.Truncate(player.getMaxForce());

        //println("Player "+player.getID() +"--------------------------------" );
        /*
         if (player.getID() == 2){
         println(" SteeringForce : "+steeringForce);
         }
         */
        //println("---------------------------------------------")
        //steeringForce = new Vector2D(1,1)
        return steeringForce;
    }

    //-------------------------- SumForces -----------------------------------
    //
    // this method calls each active steering behavior and acumulates their
    // forces until the max steering force magnitude is reached at which
    // time the function returns the steering force accumulated to that 
    // point
    //------------------------------------------------------------------------
    public Vector2D sumForces() {
        Vector2D force = new Vector2D();

        //the soccer players must always tag their neighbors
        findNeighbours();

        if (isOn(BehaviorType.separation)) {
            //println("separation on");
            force.minusLocal(Separation().multiply(multSeparation));

            if (!accumulateForce(steeringForce, force)) {
                return steeringForce;
            }
        }

        if (isOn(BehaviorType.seek)) {
            //println("seek on");
            force.minusLocal(Seek(target));

            if (!accumulateForce(steeringForce, force)) {
                return steeringForce;
            }
        }

        if (isOn(BehaviorType.arrive)) {
            //println("arrive on");
            force.minusLocal(Arrive(target, Deceleration.fast));

            if (!accumulateForce(steeringForce, force)) {
                return steeringForce;
            }
        }

        if (isOn(BehaviorType.pursuit)) {
            //println("pursuit on");
            force.minusLocal(Pursuit(ball));

            if (!accumulateForce(steeringForce, force)) {
                return steeringForce;
            }
        }

        if (isOn(BehaviorType.interpose)) {
            //println("interpose on");
            force.minusLocal(Interpose(ball, target, interposeDist));

            if (!accumulateForce(steeringForce, force)) {
                return steeringForce;
            }
        }

        return steeringForce;
    }

    //------------------------- ForwardComponent -----------------------------
    //
    // calculates the forward component of the steering force
    //------------------------------------------------------------------------
    public double getForwardComponent() {
        return player.getHeading().dot(steeringForce);
    }

    //--------------------------- SideComponent ------------------------------
    //
    // // calculates the side component of the steering force
    //------------------------------------------------------------------------
    public double getSideComponent() {
        return player.getSide().dot(steeringForce) * player.getMaxTurnRate();
    }

    //------------------------------- Seek -----------------------------------
    //
    // Given a target, this behavior returns a steering force which will
    // allign the agent with the target and move the agent in the desired
    // direction
    //------------------------------------------------------------------------
    public Vector2D Seek(Vector2D target) {

        Vector2D DesiredVelocity = Vec2DNormalize(target.minus(player.getPos()).multiply(player.getMaxSpeed()));

        return DesiredVelocity.minus(player.getVelocity());
    }

    //--------------------------- Arrive -------------------------------------
    //
    // This behavior is similar to seek but it attempts to arrive at the
    // target with a zero velocity
    //------------------------------------------------------------------------
    public Vector2D Arrive(Vector2D target, Deceleration deceleration) {
        Vector2D ToTarget = target.minus(player.getPos());

        //calculate the distance to the target
        double dist = ToTarget.length();

        if (dist > 0) {
            //because Deceleration is enumerated as an int, this value is required
            //to provide fine tweaking of the deceleration..
            double DecelerationTweaker = 0.3;

            //calculate the speed required to reach the target given the desired
            //deceleration
            double speed = dist / ((double) deceleration.value * DecelerationTweaker);

            //make sure the velocity does not exceed the max
            speed = Math.min(speed, player.getMaxSpeed());

            //from here proceed just like Seek except we don't need to normalize 
            //the ToTarget vector because we have already gone to the trouble
            //of calculating its length: dist. 
            Vector2D DesiredVelocity = ToTarget.multiply(speed / dist);

            return DesiredVelocity.minus(player.getVelocity());
        }

        return new Vector2D(0, 0);
    }

    //------------------------------ Pursuit ---------------------------------
    //
    // this behavior creates a force that steers the agent towards the 
    // ball
    //------------------------------------------------------------------------
    public Vector2D Pursuit(SoccerBall ball) {
        Vector2D ToBall = ball.getPos().minus(player.getPos());

        //the lookahead time is proportional to the distance between the ball
        //and the pursuer; 
        double LookAheadTime = 0.0;

        if (ball.getSpeed() != 0.0) {
            LookAheadTime = ToBall.length() / ball.getSpeed();
        }

        //calculate where the ball will be at this time in the future
        target = ball.getFuturePosition(LookAheadTime);

        //now seek to the predicted future position of the ball
        return Arrive(target, Deceleration.fast);
    }

    //-------------------------- FindNeighbours ------------------------------
    //
    // tags any vehicles within a predefined radius
    //------------------------------------------------------------------------
    public void findNeighbours() {
        List<PlayerBase> AllPlayers = FootballEntityManager.Instance().getAllEntitiesByClass(PlayerBase.class);
        for (PlayerBase curPlyr : AllPlayers) {
            //first clear any current tag
            curPlyr.getSteering().unsetTag();

            //work in distance squared to avoid sqrts
            Vector2D to = curPlyr.getPos().minus(player.getPos());

            if (to.LengthSq() < (viewDistance * viewDistance)) {
                curPlyr.getSteering().setTag();
            }
        }//next
    }

    //---------------------------- Separation --------------------------------
    //
    // this calculates a force repelling from the other neighbors
    //------------------------------------------------------------------------
    public Vector2D Separation() {
        //iterate through all the neighbors and calculate the vector from the
        Vector2D SteeringForce = new Vector2D();

        List<PlayerBase> AllPlayers = FootballEntityManager.Instance().getAllEntitiesByClass(PlayerBase.class);
        for (PlayerBase curPlyr : AllPlayers) {
            //make sure this agent isn't included in the calculations and that
            //the agent is close enough
            if ((curPlyr != player) && curPlyr.getSteering().isTagged()) {
                Vector2D ToAgent = player.getPos().minus(curPlyr.getPos());

                //scale the force inversely proportional to the agents distance 
                //from its neighbor.
                SteeringForce = SteeringForce.plus(Vec2DNormalize(ToAgent).divide(ToAgent.length()));
            }
        }

        return SteeringForce;
    }

    //--------------------------- Interpose ----------------------------------
    //
    // Given an opponent and an object position this method returns a 
    // force that attempts to position the agent between them
    //------------------------------------------------------------------------
    public Vector2D Interpose(SoccerBall ball, Vector2D target, double DistFromTarget) {
        return Arrive(target.plus(Vec2DNormalize(ball.getPos().minus(target.multiply(DistFromTarget)))), Deceleration.normal);
    }
}
