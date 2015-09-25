package sg.games.football.gameplay.info;

import sg.games.football.gameplay.coachgp.training.*;
import sg.games.football.entities.PlayerBase;
import sg.games.football.gameplay.PlayerRole;
import java.util.Date;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class FootballPlayerInfo {

    // MainInfo
    public FootballClubInfo club;
    public PlayerRole role;
    public PlayerRole matchRole;
    public String name;
    public String country;

    // static attribute
    public int skillSpeed;
    public int skillBallControl;
    public int skillBallTake;
    public int skillBallKeep;
    public int skillGoalKeep;
    public int skillPass;

    // physic attribute
    public float height;
    public float weight;
    public float eyeSight;
    public Date birthDate;

    // real time Control things
    public int speed;
    public int energy;
    public int attitude;
    public int decay;

    float realSpeed;

    // training result
    TrainingResult training;
    int status = 0;
    PlayerBase playerBase;

    // Career paths
    public FootballPlayerInfo(FootballClubInfo club, String name) {
        this.name = name;
        this.club = club;
        this.role = null;
    }

    public FootballPlayerInfo(FootballClubInfo club, String name, PlayerRole role) {
        this.name = name;
        this.club = club;
        this.role = role;
    }

    void setEnergy(int speed, int energy, int attitude, int decay) {

        this.speed = speed;
        this.energy = energy;
        this.attitude = attitude;
        this.decay = decay;

    }

    void setSkills(int skillBallControl, int skillBallTake, int skillBallKeep, int skillGoalKeep, int skillPass) {

        this.skillBallControl = skillBallControl;
        this.skillBallTake = skillBallTake;
        this.skillBallKeep = skillBallKeep;
        this.skillGoalKeep = skillGoalKeep;
        this.skillPass = skillPass;
    }

    /* SETTER & GETTER */
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
        //realSpeed = speed * 0.02f;
    }

    public FootballClubInfo getClub() {
        return club;
    }

    public PlayerRole getMatchRole() {
        return matchRole;
    }

    public void setMatchRole(PlayerRole matchRole) {
        this.matchRole = matchRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getSkillSpeed() {
        return skillSpeed;
    }

    public void setSkillSpeed(int skillSpeed) {
        this.skillSpeed = skillSpeed;
    }

    public int getSkillBallControl() {
        return skillBallControl;
    }

    public void setSkillBallControl(int skillBallControl) {
        this.skillBallControl = skillBallControl;
    }

    public int getSkillBallTake() {
        return skillBallTake;
    }

    public void setSkillBallTake(int skillBallTake) {
        this.skillBallTake = skillBallTake;
    }

    public int getSkillBallKeep() {
        return skillBallKeep;
    }

    public void setSkillBallKeep(int skillBallKeep) {
        this.skillBallKeep = skillBallKeep;
    }

    public int getSkillGoalKeep() {
        return skillGoalKeep;
    }

    public void setSkillGoalKeep(int skillGoalKeep) {
        this.skillGoalKeep = skillGoalKeep;
    }

    public int getSkillPass() {
        return skillPass;
    }

    public void setSkillPass(int skillPass) {
        this.skillPass = skillPass;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getEyeSight() {
        return eyeSight;
    }

    public void setEyeSight(float eyeSight) {
        this.eyeSight = eyeSight;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getDecay() {
        return decay;
    }

    public void setDecay(int decay) {
        this.decay = decay;
    }

    public float getRealSpeed() {
        return realSpeed;
    }

    public void setRealSpeed(float realSpeed) {
        this.realSpeed = realSpeed;
    }

    public TrainingResult getTraining() {
        return training;
    }

    public void setTraining(TrainingResult training) {
        this.training = training;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PlayerBase getPlayerBase() {
        return playerBase;
    }

    public void setPlayerBase(PlayerBase playerBase) {
        this.playerBase = playerBase;
    }

    public int getAttitude() {
        return attitude;
    }

    public void setClub(FootballClubInfo club) {
        this.club = club;
    }

    public void setRole(PlayerRole newRole) {
        this.role = newRole;
    }

    public PlayerRole getRole() {
        return role;
    }

    public String toString() {
        return "Player " + name + " : "
                + club + " "
                + role + " "
                + skillSpeed + " "
                + skillBallControl + " "
                + skillBallTake + " "
                + skillBallKeep + " "
                + skillGoalKeep + " "
                + skillPass + " "
                + height + " "
                + weight + " "
                + eyeSight + " "
                + birthDate + " "
                + speed + " "
                + energy + " "
                + attitude + " "
                + decay;
    }
}
