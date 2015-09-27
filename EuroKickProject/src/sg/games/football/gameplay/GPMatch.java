package sg.games.football.gameplay;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;
import com.jme3.scene.Node;
import sg.games.football.FootballGame;
import sg.games.football.entities.SoccerBall;
import sg.games.football.entities.SoccerCoach;
import static sg.games.football.gameplay.SoccerTeam.TeamColor.blue;
import static sg.games.football.gameplay.SoccerTeam.TeamColor.red;

import sg.games.football.gameplay.info.*;
import sg.games.football.gameplay.ai.states.team.*;
import sg.games.football.gameplay.ai.info.*;
import sg.games.football.managers.FootballGameGUIManager;

import sg.games.football.ui.UIIngameScreenController;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class GPMatch extends BaseGamePlay implements PhysicsCollisionListener {

    //character -----------------------------------------------
    SoccerCoach currentPlayerCoach;
    League league;
    public FootballClubMatchTeam clubA;
    public FootballClubMatchTeam clubB;
    public FootballMatch fbMatch;
    public SoccerPitch pitch;
    public Spatial ballSpatial;
    protected boolean firstPersonView = true;

    // Timing
    float matchTimeSpeed = 10;

    float goalTimeInit = 3;
    float goalTime = 0;

    boolean normalUI = true;

    // simulation
    int simLevel = 0;

    // more
    Params Prm;
    public SoccerBall ballController;
    public SoccerTeam redTeam;
    public SoccerTeam blueTeam;
    //true if a goal keeper has possession
    boolean goalKeeperHasBall;
    //true if the game is in play. Set to false whenever the players
    //are getting ready for kickoff
    boolean gameOn = false;
    //set true to pause the motion
    boolean paused = false;

    // CINEMATIC
    FootballCommentator commentator;

    public GPMatch(FootballGame app) {
        super(app);
        Prm = Params.Instance();
    }

    public void initGamePlay() {

    }

    public void loadGamePlay() {

    }

    public void startGamePlay() {
        // start the sound
        //soundManager.play(STADIUM_BACKGROUND);
        //soundManager.play(MUSIC);
        prepareMatch();

    }

    public void endGamePlay() {

    }

    private void setupKeys() {
    }

    public void updateMatch(float tpf) {

        //updateBall(tpf);
        if (gameOn) {
            //FIXME: Let the timing be right!
            fbMatch.matchTime += tpf * matchTimeSpeed;

            if (fbMatch.matchTime > fbMatch.halfTime) {
                breakMatch();
            }
            //update the balls
            ballController.update(tpf);
            //update the teams. Should be with tpf
            redTeam.update(tpf);
            blueTeam.update(tpf);

            // Update the UI
            UIIngameScreenController sc = app.getGameGUIManager().getCurrentScreenController("MatchScreen", UIIngameScreenController.class);
            if (sc != null) {
                sc.setMatchTime(fbMatch.matchTime);
                sc.updatePlayerPos();
            }

        }
    }

    public void updateGamePlay(float tpf) {
        updateMatch(tpf * matchTimeSpeed);
    }
    /*
     * GOAL CHECKING
     */

    void checkGoal(float tpf) {
        //if a goal has been detected reset the pitch ready for kickoff
        if (pitch.blueGoal.checkScored(ballController) || pitch.redGoal.checkScored(ballController)) {
            gameOn = false;

            //reset the ball    
            ballController.placeAtPosition(pitch.getPlayingArea().getCenter());
            //get the teams ready for kickoff
            redTeam.getFSM().changeState(PrepareForKickOff.Instance());
            blueTeam.getFSM().changeState(PrepareForKickOff.Instance());
        }

    }
    /*
     *CINEMATIC SYSTEM
     */

    public void cineGoalBackNormal(float tpf) {
        // 3D
        if (normalUI == false) {
            if (goalTime > 0) {
                goalTime -= tpf;
            } else {
                goalTime = 0;
                gameGUIManager.goToScreen("ingameScreen");
                normalUI = true;
            }
        }
    }

    public void cineGoal() {
        goalTime = goalTimeInit;
        normalUI = false;
        //
        System.out.println("Goal");
        gameGUIManager.goToScreen("goal");
        moveBall(getApp().getWorldManager().getStadiumMaker().getCenter());
    }

    public void cineShowTeamPositions() {

    }

    public void cineOpenGame() {
        // Two team out of the waitroom of the stadium
        // To the feild
        // Waving and clapping
        // Line up
        // Center passing
        // First half begin. Time start
    }

    public void prepareMatch() {
//        fbMatch = league.getNextMatch(currentPlayerCoach.getClub());
//        this.clubA = currentPlayerCoach.getClub();
//        this.clubB = fbMatch.opponent(this.clubA);
        /*
         if (clubA ==fbMatch.getHomeClub()){
         // The player club play in home stadium
         } else {
            
         }
         */

        //FIXME: Prepare the interview and the commentator
        commentator = new FootballCommentator("Bob", this);

        //FIXME: Change to coordinate to user's input
        //startMatch();
    }

    void arrangeTeams() {
        //create the teams AI
        redTeam = clubA.createTeamForMatch(this.gpManager, fbMatch, 0);
        blueTeam = clubB.createTeamForMatch(this.gpManager, fbMatch, 1);
        //
        //make sure each team knows who their opponents are
        /*
         redTeam.setOpponents(blueTeam);
         blueTeam.setOpponents(redTeam); 
         */
        redTeam.initMatch(blueTeam, pitch, pitch.redGoal, red);
        blueTeam.initMatch(redTeam, pitch, pitch.blueGoal, blue);

//        Node worldNode = getStageManager().getWorldManager().getWorldNode();
//        clubA.attachModels(assetManager, worldNode);
//        clubB.attachModels(assetManager, worldNode);
//        clubA.arrangeStartPos(0);
//        clubB.arrangeStartPos(1);
        // attach
    }

    public void startMatch() {
        paused = false;
        goalKeeperHasBall = false;

        pitch = SoccerPitch.getDefault();
        pitch.init(getApp().getWorldManager().getStadiumMaker());

        try {
            createBall();
            arrangeTeams();

        } catch (Error er) {
            er.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        gameOn = true;
        fbMatch.startMatch();
        redTeam.startMatch();
        blueTeam.startMatch();

        // Update the UI
        UIIngameScreenController sc = app.getGameGUIManager().getCurrentScreenController("MatchScreen", UIIngameScreenController.class);
        if (sc != null) {
            sc.createMiniView();
        }

        commentator.sayScript("MatchStart");
    }

    public void breakMatch() {
        //fbMatch.matchTime = 0;
        app.getGameGUIManager().goToScreen("MatchBreakScreen");
        gameOn = false;
    }

    public void continueMatch() {
        gameOn = true;
        fbMatch.halfTime *= 2;
    }

    /* GAME ROUNTINE */
    public void gameStart() {

    }

    public void gamePause() {
        // All character pause
        // Show the pause panel
    }

    public void gameBreak() {
        // Two team out
        // Review the match
        // After wait
        // Next half
    }

    public void gameEnd() {
        // Timeout reach
        // Refree whistle
        // Two team out
    }

    /**
     * BALL CONTROL, GAME SIMULATION CONTROL
     */
    public void createBall() {

        ballController = new SoccerBall(pitch.getPlayingArea().getCenter(), Prm.BallSize, Prm.BallMass, pitch.getWalls());
        ballSpatial = getApp().getWorldManager().createBall();
        ballController.setSpatial(ballSpatial);
        getApp().getWorldManager().getPhysicsSpace().addCollisionListener(this);

    }

    public void updateBall(float tpf) {
        Camera cam = stageManager.getCurrentCamera();
        Vector3f origin = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.0f);
        Vector3f direction = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0.3f);
        direction.subtractLocal(origin).normalizeLocal();

        Ray ray = new Ray(origin, direction);
        CollisionResults results = new CollisionResults();
        getApp().getWorldManager().getStadiumMaker().getFieldNode().collideWith(ray, results);
        if (results.size() > 0) {
            CollisionResult closest = results.getClosestCollision();
            moveBall(closest.getContactPoint());
        }
    }

    public void moveBall(Vector3f pos) {
        //RigidBodyControl physicsBallControl = ball.getControl(RigidBodyControl.class);
        //physicsBallControl.applyForce(pos, pos);

        ballSpatial.setLocalTranslation(pos);
        /*
         Quaternion q = new Quaternion();
         q.lookAt(closest.getContactNormal(), Vector3f.UNIT_Y);
         ball.setLocalRotation(q);
         */
    }

    /**
     * STRAGEGY
     */
    public void changeStragegy(String selectedItem) {

    }
    /* 
     * PHYSIC 
     */

    public boolean isCollisonBetween(PhysicsCollisionEvent event, String nodeA, String nodeB) {
        if (nodeA.equals(event.getNodeA().getName()) || nodeA.equals(event.getNodeB().getName())) {
            if (nodeB.equals(event.getNodeA().getName()) || nodeB.equals(event.getNodeB().getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        if (isCollisonBetween(event, "goalA", "Soccer ball") || isCollisonBetween(event, "goalB", "Soccer ball")) {
            if (goalTime <= 0) {
                cineGoal();
            }
        }
    }
    /* 
     * SIMULATION SETTINGS
     */

    public void simulate(int simLevel) {
        // level 0
        // just calculate the points and random the score
        // level 1
        // short simulation in another thread and count the score
        // level 2
        // full simulation with the 3d models
    }

    /* getter setter
     */
    public void togglePause() {
        paused = !paused;
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isGoalKeeperHasBall() {
        return goalKeeperHasBall;
    }

    public void setGoalKeeperHasBall(boolean b) {
        goalKeeperHasBall = b;
    }

    public SoccerBall getBall() {
        return ballController;
    }

    public boolean isGameOn() {
        return gameOn;
    }

    public void setGameOn() {
        gameOn = true;
    }

    public void setGameOff() {
        gameOn = false;
    }

    public SoccerTeam getRedTeam() {
        return redTeam;
    }

    public SoccerTeam getBlueTeam() {
        return blueTeam;
    }

    // gameplay
    public SoccerPitch getPitch() {
        return pitch;
    }

}
