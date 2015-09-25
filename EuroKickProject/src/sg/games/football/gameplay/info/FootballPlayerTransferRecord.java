package sg.games.football.gameplay.info;

import org.joda.time.DateTime;

/**
 *
 * @author cuongnguyen
 */
public class FootballPlayerTransferRecord {

    String club;
    int salary;
    int salaryPerWeek;
    int tranactionCost;
    DateTime fromTime;
    DateTime toTime;

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalaryPerWeek() {
        return salaryPerWeek;
    }

    public void setSalaryPerWeek(int salaryPerWeek) {
        this.salaryPerWeek = salaryPerWeek;
    }

    public int getTranactionCost() {
        return tranactionCost;
    }

    public void setTranactionCost(int tranactionCost) {
        this.tranactionCost = tranactionCost;
    }

    public DateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(DateTime fromTime) {
        this.fromTime = fromTime;
    }

    public DateTime getToTime() {
        return toTime;
    }

    public void setToTime(DateTime toTime) {
        this.toTime = toTime;
    }

}
