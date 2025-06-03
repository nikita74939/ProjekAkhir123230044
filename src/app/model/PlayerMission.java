package app.model;

import javafx.beans.property.*;
import java.time.LocalDateTime;

public class PlayerMission {
    private final IntegerProperty idPlayerMission;
    private final IntegerProperty idPlayer;
    private final IntegerProperty idMission;
    private final BooleanProperty completed;
    private final BooleanProperty claimed;
    private final ObjectProperty<LocalDateTime> completedAt;
    
    public PlayerMission() {
        this.idPlayerMission = new SimpleIntegerProperty();
        this.idPlayer = new SimpleIntegerProperty();
        this.idMission = new SimpleIntegerProperty();
        this.completed = new SimpleBooleanProperty();
        this.claimed = new SimpleBooleanProperty();
        this.completedAt = new SimpleObjectProperty<>();
    }
    
    public PlayerMission(int idPlayerMission, int idPlayer, int idMission, boolean completed, boolean claimed, LocalDateTime completedAt) {
        this();
        setIdPlayerMission(idPlayerMission);
        setIdPlayer(idPlayer);
        setIdMission(idMission);
        setCompleted(completed);
        setClaimed(claimed);
        setCompletedAt(completedAt);
    }
    
    // Getter dan Setter untuk idPlayerMission
    public int getIdPlayerMission() {
        return idPlayerMission.get();
    }
    
    public void setIdPlayerMission(int idPlayerMission) {
        this.idPlayerMission.set(idPlayerMission);
    }
    
    public IntegerProperty idPlayerMissionProperty() {
        return idPlayerMission;
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
    
    // Getter dan Setter untuk completedAt
    public LocalDateTime getCompletedAt() {
        return completedAt.get();
    }
    
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt.set(completedAt);
    }
    
    public ObjectProperty<LocalDateTime> completedAtProperty() {
        return completedAt;
    }
    
    @Override
    public String toString() {
        return "PlayerMission{" +
                "idPlayerMission=" + getIdPlayerMission() +
                ", idPlayer=" + getIdPlayer() +
                ", idMission=" + getIdMission() +
                ", completed=" + isCompleted() +
                ", claimed=" + isClaimed() +
                ", completedAt=" + getCompletedAt() +
                '}';
    }
}