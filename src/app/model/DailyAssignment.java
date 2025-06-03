package app.model;

import javafx.beans.property.*;
import java.time.LocalDate;


public class DailyAssignment {
    private final IntegerProperty idAssignment;
    private final IntegerProperty idPlayer;
    private final IntegerProperty idMission;
    private final ObjectProperty<LocalDate> dateGiven;
    private final BooleanProperty completed;
    private final BooleanProperty claimed;
    
    public DailyAssignment() {
        this.idAssignment = new SimpleIntegerProperty();
        this.idPlayer = new SimpleIntegerProperty();
        this.idMission = new SimpleIntegerProperty();
        this.dateGiven = new SimpleObjectProperty<>();
        this.completed = new SimpleBooleanProperty();
        this.claimed = new SimpleBooleanProperty();
    }
    
    public DailyAssignment(int idAssignment, int idPlayer, int idMission, LocalDate dateGiven, boolean completed, boolean claimed) {
        this();
        setIdAssignment(idAssignment);
        setIdPlayer(idPlayer);
        setIdMission(idMission);
        setDateGiven(dateGiven);
        setCompleted(completed);
        setClaimed(claimed);
    }
    
    // Getter dan Setter untuk idAssignment
    public int getIdAssignment() {
        return idAssignment.get();
    }
    
    public void setIdAssignment(int idAssignment) {
        this.idAssignment.set(idAssignment);
    }
    
    public IntegerProperty idAssignmentProperty() {
        return idAssignment;
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
    
    // Getter dan Setter untuk dateGiven
    public LocalDate getDateGiven() {
        return dateGiven.get();
    }
    
    public void setDateGiven(LocalDate dateGiven) {
        this.dateGiven.set(dateGiven);
    }
    
    public ObjectProperty<LocalDate> dateGivenProperty() {
        return dateGiven;
    }
    
    // Getter dan Setter untuk completed
    public boolean isCompleted() {
        return completed.get();
    }
    
    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }
    
    public BooleanProperty completedProperty() {
        return completed;
    }
    
    // Getter dan Setter untuk claimed
    public boolean isClaimed() {
        return claimed.get();
    }
    
    public void setClaimed(boolean claimed) {
        this.claimed.set(claimed);
    }
    
    public BooleanProperty claimedProperty() {
        return claimed;
    }
    
    @Override
    public String toString() {
        return "DailyAssignment{" +
                "idAssignment=" + getIdAssignment() +
                ", idPlayer=" + getIdPlayer() +
                ", idMission=" + getIdMission() +
                ", dateGiven=" + getDateGiven() +
                ", completed=" + isCompleted() +
                ", claimed=" + isClaimed() +
                '}';
    }
}