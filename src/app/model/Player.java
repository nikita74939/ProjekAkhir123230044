package app.model;

import javafx.beans.property.*;

public class Player {
    private final IntegerProperty idPlayer;
    private final StringProperty name;
    private final IntegerProperty level;
    private final IntegerProperty pointToLevelUp;
    private final IntegerProperty gold;
    
    public Player() {
        this.idPlayer = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.level = new SimpleIntegerProperty();
        this.pointToLevelUp = new SimpleIntegerProperty();
        this.gold = new SimpleIntegerProperty();
    }
    
    public Player(int idPlayer, String name, int level, int pointToLevelUp, int gold) {
        this();
        setIdPlayer(idPlayer);
        setName(name);
        setLevel(level);
        setPointToLevelUp(pointToLevelUp);
        setGold(gold);
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
    
    // Getter dan Setter untuk name
    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty() {
        return name;
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
    
    // Getter dan Setter untuk pointToLevelUp
    public int getPointToLevelUp() {
        return pointToLevelUp.get();
    }
    
    public void setPointToLevelUp(int pointToLevelUp) {
        this.pointToLevelUp.set(pointToLevelUp);
    }
    
    public IntegerProperty pointToLevelUpProperty() {
        return pointToLevelUp;
    }
    
    // Getter dan Setter untuk gold
    public int getGold() {
        return gold.get();
    }
    
    public void setGold(int gold) {
        this.gold.set(gold);
    }
    
    public IntegerProperty goldProperty() {
        return gold;
    }
    
    @Override
    public String toString() {
        return "Player{" +
                "idPlayer=" + getIdPlayer() +
                ", name='" + getName() + '\'' +
                ", level=" + getLevel() +
                ", pointToLevelUp=" + getPointToLevelUp() +
                ", gold=" + getGold() +
                '}';
    }
}