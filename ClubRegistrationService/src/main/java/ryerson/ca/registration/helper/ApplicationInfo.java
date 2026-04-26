/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ryerson.ca.registration.helper;

public class ApplicationInfo {
    private String username;
    private int clubId;
    private String statement;

    public ApplicationInfo(String username, int clubId, String statement) {
        this.username = username;
        this.clubId = clubId;
        this.statement = statement;
    }
    // Getters
    public String getUsername() { return username; }
    public int getClubId() { return clubId; }
    public String getStatement() { return statement; }
}