package app.model;

import javafx.beans.property.*;


public class Mission {
    private final IntegerProperty idMission;
    private final StringProperty title;
    private final StringProperty description;
    private final StringProperty type;
    private final IntegerProperty goldReward;
    private final IntegerProperty expReward;
    
    public Mission() {
        this.idMission = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.type = new SimpleStringProperty();
        this.goldReward = new SimpleIntegerProperty();
        this.expReward = new SimpleIntegerProperty();
    }
    
    public Mission(int idMission, String title, String description, String type, int goldReward, int expReward) {
        this();
        setIdMission(idMission);
        setTitle(title);
        setDescription(description);
        setType(type);
        setGoldReward(goldReward);
        setExpReward(expReward);
    }
    
    // Getter dan Setter untuk idMission
    public int getIdMission() {
        return idMission.get();
    }
    
    public void setIdMission(int idMission) {
        this.idMission.set(idMission);
    }
    
    public IntegerProperty idMissionProperty() {
        return idMission;
    }
    
    // Getter dan Setter untuk title
    public String getTitle() {
        return title.get();
    }
    
    public void setTitle(String title) {
        this.title.set(title);
    }
    
    public StringProperty titleProperty() {
        return title;
    }
    
    // Getter dan Setter untuk description
    public String getDescription() {
        return description.get();
    }
    
    public void setDescription(String description) {
        this.description.set(description);
    }
    
    public StringProperty descriptionProperty() {
        return description;
    }
    
    // Getter dan Setter untuk type
    public String getType() {
        return type.get();
    }
    
    public void setType(String type) {
        this.type.set(type);
    }
    
    public StringProperty typeProperty() {
        return type;
    }
    
    // Getter dan Setter untuk goldReward
    public int getGoldReward() {
        return goldReward.get();
    }
    
    public void setGoldReward(int goldReward) {
        this.goldReward.set(goldReward);
    }
    
    public IntegerProperty goldRewardProperty() {
        return goldReward;
    }
    
    // Getter dan Setter untuk expReward
    public int getExpReward() {
        return expReward.get();
    }
    
    public void setExpReward(int expReward) {
        this.expReward.set(expReward);
    }
    
    public IntegerProperty expRewardProperty() {
        return expReward;
    }
    
    @Override
    public String toString() {
        return "Mission{" +
                "idMission=" + getIdMission() +
                ", title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", type='" + getType() + '\'' +
                ", goldReward=" + getGoldReward() +
                ", expReward=" + getExpReward() +
                '}';
    }
}