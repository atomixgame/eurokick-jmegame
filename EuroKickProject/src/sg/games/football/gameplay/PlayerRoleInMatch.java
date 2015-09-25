package sg.games.football.gameplay;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class PlayerRoleInMatch {

    PlayerRole role;
    int rowNum;
    int rowTotal;
    int roleIndex;

    public PlayerRoleInMatch(PlayerRole role, int rowNum, int rowTotal, int roleIndex) {
        this.role = role;
        this.rowNum = rowNum;
        this.rowTotal = rowTotal;
        this.roleIndex = roleIndex;
    }

    public PlayerRole getRole() {
        return role;
    }

    public void setRole(PlayerRole role) {
        this.role = role;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getRowTotal() {
        return rowTotal;
    }

    public void setRowTotal(int rowTotal) {
        this.rowTotal = rowTotal;
    }

    public int getRoleIndex() {
        return roleIndex;
    }

    public void setRoleIndex(int roleIndex) {
        this.roleIndex = roleIndex;
    }

}
