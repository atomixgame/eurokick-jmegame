package sg.games.football.gameplay.info;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.Date;
import sg.games.football.entities.PlayerBase;
import sg.games.football.gameplay.FootballGamePlayManager;
import sg.games.football.gameplay.PlayerRole;
import sg.games.football.gameplay.SoccerTeam;
import sg.games.football.geom.shape.Tube;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class PlayerCustomizeSystem {

    public static String names = "and marc son bert wick ness ton shire step ley ing sley";
    public static String[] namePattern = names.split(" ");
    public static String[] shirtList = {
        "acmilanhome1213fifa07.png",
        "ajaxawayy.png",
        "ajaxgk.png",
        "ajaxhomebyjeanpi.png",
        "arsenalhome1213fifa07.png",
        "birmin3rd.png",
        "blackpool3rd.png",
        "bragagk.png",
        "bragahome.png",
        "brazilgk1214fifa07.png",
        "cehiaaway1214fifa07.png",
        "cehiagk1214fifa07.png",
        "cehiahome1214fifa07.png",
        "chelseahome1213v1fifa07.png",
        "croatiaaway1214fifa07.png",
        "croatiahome1214fifa07.png",
        "englandgk1214fifa07.png",
        "englandhome1214fifa07.png",
        "italiagk1214fifa07v1.png",
        "italiagk1214fifa07v2.png",
        "muaway1213fifa07.png",
        "muhome1213fifa07.png",
        "reading3rd.png",
        "realmadridaway1213fifa0.png",
        "realmadridhome1213fifa0.png",
        "romaniagk1214fifa07.png",
        "romaniahome1214fifa07.png",
        "santos3rd1213.png"
    };

    public String getRandomName() {
        String rname = namePattern[FastMath.rand.nextInt(namePattern.length)];
        return rname;
    }

    public FootballPlayerInfo getRandomPlayerInfo(FootballPlayerInfo info) {
        info.name = getRandomName();
        info.speed = 6 + FastMath.rand.nextInt(3);
        info.role = PlayerRole.Attacker;
        info.skillSpeed = 4 + FastMath.rand.nextInt(3);
        info.skillBallControl = 6 + FastMath.rand.nextInt(3);
        info.skillBallTake = 5 + FastMath.rand.nextInt(3);
        info.skillBallKeep = 6 + FastMath.rand.nextInt(3);
        info.skillGoalKeep = 4 + FastMath.rand.nextInt(3);
        info.skillPass = 3 + FastMath.rand.nextInt(3);
        info.energy = 6 + FastMath.rand.nextInt(3);
        info.attitude = 6 + FastMath.rand.nextInt(3);
        info.birthDate = new Date();
        return info;
    }
    AssetManager assetManager;

    public static String getRandomShirt() {
        return "/Textures/shirt/" + shirtList[FastMath.rand.nextInt(shirtList.length)];
    }
    private Node playerModel;

    void loadModel(FootballClubInfo club) {
        //playerModel = (Node) assetManager.loadModel("Models/Player/PlayerOld/PlayerAni.j3o");
        playerModel = (Node) assetManager.loadModel("Models/Player/Player2/base_male.j3o");
//        myFont = assetManager.loadFont("Interface/Fonts/Console.fnt");

        Node armature = (Node) playerModel.getChild("Armature");

        //TODO: Change the Cloth
        Node cloth = (Node) armature.getChild("Body");
        Geometry clothGeo = (Geometry) cloth.getChild("cloth1");
        String texPath = club.getShirt();
        clothGeo.getMaterial().setTexture("DiffuseMap", assetManager.loadTexture(texPath)); //armature.setShadowMode(ShadowMode.Cast);

        AnimControl animControl = cloth.getControl(AnimControl.class);
        AnimChannel animChannel = animControl.createChannel();
        animChannel.setAnim("Stand");

        createHighLighter(armature, null);
        createDebug();
    }

    void createHighLighter(Node armature, PlayerBase playerBase) {

        Material redMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        if (playerBase.getTeam().getColor() == SoccerTeam.TeamColor.red) {
            redMat.setColor("Color", new ColorRGBA(1, 0, 0, 1));
        } else {
            redMat.setColor("Color", new ColorRGBA(0, 0, 1, 1));
        }
        Tube tube = new Tube(1.2f, 1f, 0.02f);
        Geometry tubeGeo = new Geometry("Tube1", tube);
        tubeGeo.setMaterial(redMat);
        armature.attachChild(tubeGeo);
    }

    void setLocation(Vector3f pos) {
        /*
         if (initialLocation == null) {
         initialLocation = pos;
         }
         if (playerModel != null) {
         playerModel.setLocalTranslation(pos);
         }
         */
    }

    void initPlayer(FootballGamePlayManager gamePlayManager) {
        //playerControl = new FootballPlayerControl();
        //playerControl.initPlayerControl(this, gamePlayManager.getStageManager().getWorldManager().getBallSpatial());

    }

    void attachPlayer(Node rootNode) {
        //playerModel.scale(0.02f);
        //initalRot = new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X);
        //playerModel.setLocalRotation(initalRot);
//        rootNode.attachChild(playerModel);
//        playerModel.setLocalScale(1f);
//
//        this.playerBase.setSpatial(playerModel);

        //setLocation(initialLocation);
        //playerModel.addControl(playerControl);
    }

    void createDebug() {
        //addNumber();
    }

//    void addNumber() {
//        this.number = new BitmapText(myFont, true);
//        this.number.setText(this.name);
//        this.number.setLocalTranslation(new Vector3f(0f, 3f, 0f));
//        this.number.addControl(new BillboardControl());
//        this.number.scale(0.1f);
//        playerModel.attachChild(this.number);
//    }
    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

}
