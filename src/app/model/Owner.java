package app.model;

import javafx.beans.property.*;

public class Owner {
    private final IntegerProperty idOwner;
    private final IntegerProperty idPlayer;
    private final IntegerProperty idMonster;
    private final IntegerProperty pointFood;
    private final IntegerProperty pointHappy;
    private final IntegerProperty pointLevelup;
    private final IntegerProperty level;
    
    public Owner() {
        this.idOwner = new SimpleIntegerProperty();
        this.idPlayer = new SimpleIntegerProperty();
        this.idMonster = new SimpleIntegerProperty();
        this.pointFood = new SimpleIntegerProperty();
        this.pointHappy = new SimpleIntegerProperty();
        this.pointLevelup = new SimpleIntegerProperty();
        this.level = new SimpleIntegerProperty();
    }
    
    public Owner(int idOwner, int idPlayer, int idMonster, int pointFood, int pointHappy, int pointLevelup, int level) {
        this();
        setIdOwner(idOwner);
        setIdPlayer(idPlayer);
        setIdMonster(idMonster);
        setPointFood(pointFood);
        setPointHappy(pointHappy);
        setPointLevelup(pointLevelup);
        setLevel(level);
    }
    
    // Getter dan Setter untuk idOwner
    public int getIdOwner() {
        return idOwner.get();
    }
    
    public void setIdOwner(int idOwner) {
        this.idOwner.set(idOwner);
    }
    
    public IntegerProperty idOwnerProperty() {
        return idOwner;
    }
    
    // Getter dan Setter untuk idPlayer
    public int getIdPlayer() {
        return idPlayer.get();
    }
    
    public void setIdPlayer(int idPlayer) {
        this.idPlayer.set(idPlayer);
    }
    
    public IntegerProperty idPlayerProperty() {
        return idPlayer;
    }
    
    // Getter dan Setter untuk idMonster
    public int getIdMonster() {
        return idMonster.get();
    }
    
    public void setIdMonster(int idMonster) {
        this.idMonster.set(idMonster);
    }
    
    public IntegerProperty idMonsterProperty() {
        return idMonster;
    }
    
    // Getter dan Setter untuk pointFood
    public int getPointFood() {
        return pointFood.get();
    }
    
    public void setPointFood(int pointFood) {
        this.pointFood.set(pointFood);
    }
    
    public IntegerProperty pointFoodProperty() {
        return pointFood;
    }
    
    // Getter dan Setter untuk pointHappy
    public int getPointHappy() {
        return pointHappy.get();
    }
    
    public void setPointHappy(int pointHappy) {
        this.pointHappy.set(pointHappy);
    }
    
    public IntegerProperty pointHappyProperty() {
        return pointHappy;
    }
    
    // Getter dan Setter untuk pointLevelup
    public int getPointLevelup() {
        return pointLevelup.get();
    }
    
    public void setPointLevelup(int pointLevelup) {
        this.pointLevelup.set(pointLevelup);
    }
    
    public IntegerProperty pointLevelupProperty() {
        return pointLevelup;
    }
    
    // Getter dan Setter untuk level
    public int getLevel() {
        return level.get();
    }
    
    public void setLevel(int level) {
        this.level.set(level);
    }
    
    public IntegerProperty levelProperty() {
        return level;
    }
    
    @Override
    public String toString() {
        return "Owner{" +
                "idOwner=" + getIdOwner() +
                ", idPlayer=" + getIdPlayer() +
                ", idMonster=" + getIdMonster() +
                ", pointFood=" + getPointFood() +
                ", pointHappy=" + getPointHappy() +
                ", pointLevelup=" + getPointLevelup() +
                ", level=" + getLevel() +
                '}';
    }
}