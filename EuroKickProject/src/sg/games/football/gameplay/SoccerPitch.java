package sg.games.football.gameplay;

import com.jme3.math.*;
import java.util.ArrayList;
import java.util.List;
import sg.games.football.entities.Goal;
import sg.games.football.geom.Vector2D;
import sg.games.football.geom.Wall2D;
import sg.games.football.gameplay.ai.info.Params;

import sg.games.football.world.StadiumMaker;

public class SoccerPitch {

    // 
    FootballGamePlayManager gamePlayManager;
    Goal redGoal;
    Goal blueGoal;
    //container for the boundary walls
    public ArrayList<Wall2D> vecWalls = new ArrayList<Wall2D>();
    //defines the dimensions of the playing area
    public Region playingArea;
    //the playing field is broken up into regions that the team
    //can make use of to implement strategies.
    public List<Region> regions = new ArrayList<Region>();

    //local copy of client window dimensions
    public float cxClient, cyClient;
    public int numRegionsHorizontal = 6;
    public int numRegionsVertical = 4;
    public int border = 20;
    public Params Prm;
    public StadiumMaker stadium;
    public Transform transformTo3D;

    static SoccerPitch _defaultInstance;

    public SoccerPitch(int cx, int cy, int cborder) {
        cxClient = cx;
        cyClient = cy;
        border = cborder;
        _defaultInstance = this;
        Prm = Params.Instance();
    }

    // get the default size football pitch which is
    public static SoccerPitch getDefault() {
        if (_defaultInstance == null) {
            _defaultInstance = new SoccerPitch(100, 75, 20);
        }
        return _defaultInstance;
    }

    public void init(StadiumMaker stadium) {
        setStadium(stadium);
    }

    public void calculateArea(StadiumMaker stadium) {
        if (stadium != null) {

            //define the playing area
            //playingArea = new Region(stadium.getConnerLoc(1),stadium.getConnerLoc(2),stadium.getConnerLoc(3),stadium.getConnerLoc(4));
            //cxClient=stadium.getFieldX();
            //cyClient=stadium.getFieldY();
        } else {
            //define the playing area

        }
        playingArea = new Region(0, 0, cxClient, cyClient);

        //create the regions 
        createRegions(getPlayingArea().getWidth() / (double) numRegionsHorizontal,
                getPlayingArea().getHeight() / (double) numRegionsVertical);

        //create the goals
        redGoal = new Goal(new Vector2D(playingArea.getLeft(),
                (cyClient - Prm.GoalWidth) / 2),
                new Vector2D(playingArea.getLeft(),
                        cyClient - (cyClient - Prm.GoalWidth) / 2),
                new Vector2D(1, 0));
        blueGoal = new Goal(new Vector2D(playingArea.getRight(),
                (cyClient - Prm.GoalWidth) / 2),
                new Vector2D(playingArea.getRight(),
                        cyClient - (cyClient - Prm.GoalWidth) / 2),
                new Vector2D(-1, 0));

        //create the walls
        Vector2D topLeft = new Vector2D(playingArea.getLeft(), playingArea.getTop());
        Vector2D topRight = new Vector2D(playingArea.getRight(), playingArea.getTop());
        Vector2D bottomRight = new Vector2D(playingArea.getRight(), playingArea.getBottom());
        Vector2D bottomLeft = new Vector2D(playingArea.getLeft(), playingArea.getBottom());

        if (!vecWalls.isEmpty()) {
            vecWalls.clear();
        }
        vecWalls.add(new Wall2D(bottomLeft, redGoal.getRightPost()));
        vecWalls.add(new Wall2D(redGoal.getLeftPost(), topLeft));
        vecWalls.add(new Wall2D(topLeft, topRight));
        vecWalls.add(new Wall2D(topRight, blueGoal.getLeftPost()));
        vecWalls.add(new Wall2D(blueGoal.getRightPost(), bottomRight));
        vecWalls.add(new Wall2D(bottomRight, bottomLeft));
    }

    //------------------------- Createregions --------------------------------
    public void createRegions(double width, double height) {
        //index into the vector
        int idx = numRegionsHorizontal * numRegionsVertical - 1;

        for (int col = 0; col < numRegionsHorizontal; ++col) {
            for (int row = 0; row < numRegionsVertical; ++row) {
                int rindex = idx - (col * numRegionsVertical + row);
                regions.add(rindex, new Region(getPlayingArea().getLeft() + col * width,
                        getPlayingArea().getTop() + row * height,
                        getPlayingArea().getLeft() + (col + 1) * width,
                        getPlayingArea().getTop() + (row + 1) * height, rindex));
            }
        }
    }

    public Region getRegionFromIndex(int idx) {
        return regions.get(idx);
    }

    public float getCxClient() {
        return cxClient;
    }

    public float getCyClient() {
        return cyClient;
    }

    public Region getPlayingArea() {
        return playingArea;
    }

    ArrayList<Wall2D> getWalls() {
        return vecWalls;
    }

    // coordinate transform
    public Region fromPosToRegion() {
        return new Region();
    }

    public Vector3f fromRegionToPos() {
        return new Vector3f();
    }

    public void setStadium(StadiumMaker stadium) {
        this.stadium = stadium;
        calculateArea(stadium);
        setTransform(stadium);
    }

    public void setTransform(Transform tr) {
        this.transformTo3D = tr;
    }

    public void setTransform(StadiumMaker stadium) {
        transformTo3D = new Transform();
        transformTo3D.setScale((float) (stadium.getFieldX() / cxClient), 1f, -(float) (stadium.getFieldY() / cyClient));
        Vector3f halfSize = new Vector3f((float) (-stadium.getFieldX() / 2), 0f, (float) (stadium.getFieldY() / 2));

        transformTo3D.setTranslation(halfSize.add(stadium.getCenter()));
        //transformTo3D.setRotation(new Quaternion().fromAngleAxis(FastMath.HALF_PI,Vector3f.UNIT_Y));
        //transformTo3D.setRotation(new Quaternion());
    }
    /*
     public void convertCoordinate(StadiumMaker stadium){
        
     // MAKE GOAL KEEPER
     Vector3f goalKeeperPosition = stadium.getGoalKeeperPos(0);
     goalKeeperPosition.interpolate(stadium.getCenter(), 0.05f);
     teamPos.setPosFor(PlayerRole.GoalKeeper,goalKeeperPosition);
        
     // MAKE TEAM
     for (int number = 2; number <= postions.size(); number++) {
     FootballPlayerInfo player = teamList.get(number - 1);
     String stragegyTitle = stragegy.posTitle;
     PlayerRoleInMatch roleExtra = getRoleByNumber(stragegyTitle, number);
     Vector3f vecX = stadium.getGoalLoc(2).clone();
     vecX.interpolate(stadium.getGoalLoc(1), posArr[roleExtra.roleIndex]);

     float stepCount =(float)( 1f / (roleExtra.rowTotal + 1) * (roleExtra.rowNum + 1));
     Vector3f vecZ = stadium.getConnerLoc(1).clone();
     vecZ.interpolate(stadium.getConnerLoc(2), stepCount);
     Vector3f vecY = stadium.getCenter().clone();
     Vector3f loc = new Vector3f(vecX.x, vecY.y, vecZ.z);

     //player.setLocation(new Vector3f(randX, 0, randY));
     teamPos.setPosFor(player,loc);
     }
     }
     */

    public Vector3f vec2DToVec3D(Vector2D vec2D) {
        Vector3f result = new Vector3f();
        Vector3f vec3D = new Vector3f((float) vec2D.x, 0f, (float) vec2D.y);

        if (this.stadium != null) {
            transformTo3D.transformVector(vec3D, result);
        }
        return result;
    }

    public Transform alignVec2D(Vector2D vec2D, Vector2D angle) {
        return null;
    }
}
