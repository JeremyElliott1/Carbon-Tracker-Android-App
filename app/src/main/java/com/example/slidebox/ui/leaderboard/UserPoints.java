package com.example.slidebox.ui.leaderboard;

public class UserPoints {

    private String firstName;
    private String lastName;
    private int weeklyPoints;
    private int monthlyPoints;
    private int totalPoints;
    private int currentPoints;

    public UserPoints(String firstName, String lastName, int weeklyPoints, int monthlyPoints, int totalPoints, int currentPoints) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.weeklyPoints = weeklyPoints;
        this.monthlyPoints = monthlyPoints;
        this.totalPoints = totalPoints;
        this.currentPoints = currentPoints;
    }

    public UserPoints() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getWeeklyPoints() {
        return weeklyPoints;
    }

    public int getMonthlyPoints() {
        return monthlyPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getCurrentPoints() {
        return currentPoints;
    }
}
