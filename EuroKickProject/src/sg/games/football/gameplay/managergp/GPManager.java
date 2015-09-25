package sg.games.football.gameplay.managergp;

import java.util.ArrayList;
import java.util.List;
import sg.games.football.gameplay.managergp.business.*;
import sg.games.football.FootballGame;
import sg.games.football.gameplay.BaseGamePlay;
import sg.games.football.gameplay.info.FootballClubInfo;
import sg.games.football.gameplay.info.FootballPlayerInfo;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class GPManager extends BaseGamePlay {

    List<Task> tasks;
    List<Report> reports;
    String org;
    String club;
    String title;
    String estateMap;
    List<Message> msgs;
    List<Email> emails;

    public GPManager(FootballGame app) {
        super(app);
    }

    public void initGamePlay() {

    }

    public void loadGamePlay() {
        /*
         * loadManagerScreen
         * loadStadium
         * loadShop
         */
    }

    public void startGamePlay() {
        tasks = new ArrayList<Task>();
        reports = new ArrayList<Report>();
        msgs = new ArrayList<Message>();
        title = "Vice President";
    }

    public void endGamePlay() {

    }

    public void updateGamePlay(float tpf) {

    }
    /* Construction */

    public void buildConstruction(Construction house, int where) {
//        club.budget -= house.cost;
//        estateMap.add(house,where);
    }
    /* Advertisment */

    public void getAds(String brand, int where, int income) {
//        club.budget += brand.ads.cost;
        //stadium.addAds(brand, where);
    }
    /* Shops & Product*/

    public void addProduct(String name, int price, Shop shop) {

    }

    public void setPrice(String name, int price) {

    }
    /* Employee */

    public void recruit(Employee emp, int salary) {
//        club.staffs.add(emp);
//        emp.salary = salary;
    }

    public void hireACoach() {

    }
    /* Player related */

    public void loanPlayer(FootballClubInfo otherClub, FootballPlayerInfo player, int income) {
//        club.exchange(player,income);
//        otherClub.add(player);

    }
    /* Simulation of real manager jobs */

    public void todayJobs() {
//        println "To day is a nice day! Boss";
        tasks.clear();
        reports.clear();

//        (1..URandom.rInt(1,10)).each{i-> 
//            def nt = new Task([name:'Task'+i , description:'Task is hard'])
//            tasks << nt;
//            println "+ " + nt.name + " : " +nt.description;
//        };
//        
//        (1..URandom.rInt(1,10)).each{i-> 
//            def nt = new Report([name:'Report'+i , description:'Report is empty'])
//            reports << nt;
//            println "+ " + nt.name + " : " +nt.description;
//        };
//        
//        def c= new Contract();
//        println c.withPlayer();
    }

    public List<Email> getEmails() {
        return null;
    }

}
