package app.model;


import javafx.beans.property.*;

public class Monster {
    private final IntegerProperty idMonster;
    private final StringProperty name;
    private final IntegerProperty happy;
    private final IntegerProperty food;
    
    public Monster() {
        this.idMonster = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.happy = new SimpleIntegerProperty();
        this.food = new SimpleIntegerProperty();
    }
    
    public Monster(int idMonster, String name, int happy, int food) {
        this();
        setIdMonster(idMonster);
        setName(name);
        setHappy(happy);
        setFood(food);
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
    
    // Getter dan Setter untuk happy
    public int getHappy() {
        return happy.get();
    }
    
    public void setHappy(int happy) {
        this.happy.set(happy);
    }
    
    public IntegerProperty happyProperty() {
        return happy;
    }
    
    // Getter dan Setter untuk food
    public int getFood() {
        return food.get();
    }
    
    public void setFood(int food) {
        this.food.set(food);
    }
    
    public IntegerProperty foodProperty() {
        return food;
    }
    
    @Override
    public String toString() {
        return "Monster{" +
                "idMonster=" + getIdMonster() +
                ", name='" + getName() + '\'' +
                ", happy=" + getHappy() +
                ", food=" + getFood() +
                '}';
    }
}