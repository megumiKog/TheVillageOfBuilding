package org.example;

/**
 * Program: The Village of Testing
 * A class that stores information of construction projects
 * @author Megumi Kogo
 * @version 2023.4
 */
import java.util.ArrayList;

// Building here means more like construction!
public class Building extends ArrayList<Building> {

    // varje byggnad skall ha hur mycket det kostar i träprodukter och metall att påbörja bygget!!

    private String buildingName;
    private int woodCost;
    private int metalCost;
    private int daysWorked;
    private int daysToComplete;
    private boolean complete;

    // ☆　CONSTRUCTOR

    public Building(String buildingName, int woodCost, int metalCost, int daysWorked, int daysToComplete, boolean complete) {
        this.buildingName = buildingName;
        this.woodCost = woodCost;
        this.metalCost = metalCost;
        this.daysWorked = daysWorked;
        this.daysToComplete = daysToComplete;
        this.complete = complete;
    }

    public Building() {
    }

    public String getBuildingName() {
        return buildingName;
    }
    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    public int getWoodCost() {
        return woodCost;
    }
    public void setWoodCost(int woodCost) {
        this.woodCost = woodCost;
    }
    public int getMetalCost() {
        return metalCost;
    }
    public void setMetalCost(int metalCost) {
        this.metalCost = metalCost;
    }
    public int getDaysWorked() {
        return daysWorked;
    }
    public void setDaysWorked(int daysWorked) {
        this.daysWorked = daysWorked;
    }
    public int getDaysToComplete() {
        return daysToComplete;
    }

    public void setDaysToComplete(int daysToComplete) {
        this.daysToComplete = daysToComplete;
    }
    public boolean isCompete() {
        return complete;
    }
    public void setCompete(boolean compete) {
        this.complete = compete;
    }


}
