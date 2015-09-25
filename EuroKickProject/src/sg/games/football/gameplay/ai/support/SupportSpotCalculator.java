package sg.games.football.gameplay.ai.support;

import java.util.ArrayList;
import java.util.List;
import sg.atom.core.execution.Regulator;
import sg.games.football.gameplay.*;
import sg.games.football.gameplay.ai.info.*;
import sg.games.football.geom.Vector2D;
import static sg.games.football.geom.Vector2D.*;

public class SupportSpotCalculator {

    //a data structure to hold the values and positions of each spot
    public class SupportSpot {

        Vector2D pos;
        double score;

        public SupportSpot(Vector2D pos, double value) {
            this.pos = pos;
            this.score = value;
        }
    }

    SoccerTeam team = null;
    List<SupportSpot> spots = new ArrayList<SupportSpot>();
    //a pointer to the highest valued spot from the last update
    SupportSpot bestSupportingSpot = null;
    //this will regulate how often the spots are calculated (default is
    //one update per second)
    Regulator pRegulator;

    public SupportSpotCalculator(int numX, int numY, SoccerTeam team) {

        bestSupportingSpot = null;
        this.team = team;
        Region PlayingField = team.getPitch().getPlayingArea();

        //calculate the positions of each sweet spot, create them and 
        //store them in Spots
        double heightOfSSRegion = PlayingField.getHeight() * 0.8;
        double widthOfSSRegion = PlayingField.getWidth() * 0.9;
        double sliceX = widthOfSSRegion / numX;
        double sliceY = heightOfSSRegion / numY;

        double left = PlayingField.getLeft() + (PlayingField.getWidth() - widthOfSSRegion) / 2.0 + sliceX / 2.0;
        double right = PlayingField.getRight() - (PlayingField.getWidth() - widthOfSSRegion) / 2.0 - sliceX / 2.0;
        double top = PlayingField.getTop() + (PlayingField.getHeight() - heightOfSSRegion) / 2.0 + sliceY / 2.0;

        for (int x = 0; x < (numX / 2) - 1; ++x) {
            for (int y = 0; y < numY; ++y) {
                if (team.getColor() == SoccerTeam.TeamColor.blue) {
                    this.spots.add(new SupportSpot(new Vector2D(left + x * sliceX, top + y * sliceY), 0.0));
                } else {
                    this.spots.add(new SupportSpot(new Vector2D(right - x * sliceX, top + y * sliceY), 0.0));
                }
            }
        }

        //create the regulator
        pRegulator = new Regulator(Params.Instance().SupportSpotUpdateFreq);
    }

    //this method iterates through each possible spot and calculates its
    //score.
    public Vector2D determineBestSupportingPosition() {
        //only update the spots every few frames                              
        if (!pRegulator.isReady() && bestSupportingSpot != null) {
            return bestSupportingSpot.pos;
        }

        //reset the best supporting spot
        bestSupportingSpot = null;

        double bestScoreSoFar = 0.0;
        for (SupportSpot curSpot : spots) {
            //first remove any previous score. (the score is set to one so that
            //the viewer can see the positions of all the spots if he has the 
            //aids turned on)
            curSpot.score = 1.0;

            //Test 1. is it possible to make a safe pass from the ball's position 
            //to this position?
            if (team.isPassSafeFromAllOpponents(team.getControllingPlayer().getPos(),
                    curSpot.pos,
                    null,
                    Params.Instance().MaxPassingForce)) {
                curSpot.score += Params.Instance().Spot_PassSafeScore;
            }

            //Test 2. Determine if a goal can be scored from this position.  
            if (team.canShoot(curSpot.pos, Params.Instance().MaxShootingForce, null)) {
                curSpot.score += Params.Instance().Spot_CanScoreFromPositionScore;
            }

            //Test 3. calculate how far this spot is away from the controlling
            //player. The further away, the higher the score. Any distances further
            //away than optimalDistance pixels do not receive a score.
            if (team.getSupportingPlayer() != null) {
                double optimalDistance = 200.0;

                double dist = vec2DDistance(team.getControllingPlayer().getPos(),
                        curSpot.pos);

                double temp = Math.abs(optimalDistance - dist);

                if (temp < optimalDistance) {

                    //normalize the distance and add it to the score
                    curSpot.score += Params.Instance().Spot_DistFromControllingPlayerScore
                            * (optimalDistance - temp) / optimalDistance;
                }
            }

            //check to see if this spot has the highest score so far
            if (curSpot.score > bestScoreSoFar) {
                bestScoreSoFar = curSpot.score;

                bestSupportingSpot = curSpot;
            }

        }

        return bestSupportingSpot.pos;
    }

    //returns the best supporting spot if there is one. If one hasn't been
    //calculated yet, this method calls DetermineBestSupportingPosition and
    //returns the result.
    public Vector2D getBestSupportingSpot() {
        if (bestSupportingSpot != null) {
            return bestSupportingSpot.pos.cloneVec();
        } else {
            return determineBestSupportingPosition();
        }
    }

}
