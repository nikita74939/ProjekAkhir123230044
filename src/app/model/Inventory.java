package app.model;

import javafx.beans.property.*;

public class Inventory {
    private final IntegerProperty idInventory;
    private final IntegerProperty idPlayer;
    private final IntegerProperty idProperty;
    private final IntegerProperty stock;
    
    public Inventory() {
        this.idInventory = new SimpleIntegerProperty();
        this.idPlayer = new SimpleIntegerProperty();
        this.idProperty = new SimpleIntegerProperty();
        this.stock = new SimpleIntegerProperty();
    }
    
    public Inventory(int idInventory, int idPlayer, int idProperty, int stock) {
        this();
        setIdInventory(idInventory);
        setIdPlayer(idPlayer);
        setIdProperty(idProperty);
        setStock(stock);
    }
    
    // Getter dan Setter untuk idInventory
    public int getIdInventory() {
        return idInventory.get();
    }
    
    public void setIdInventory(int idInventory) {
        this.idInventory.set(idInventory);
    }
    
    public IntegerProperty idInventoryProperty() {
        return idInventory;
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
    
    // Getter dan Setter untuk idProperty
    public int getIdProperty() {
        return idProperty.get();
    }
    
    public void setIdProperty(int idProperty) {
        this.idProperty.set(idProperty);
    }
    
    public IntegerProperty idPropertyProperty() {
        return idProperty;
    }
    
    // Getter dan Setter untuk stock
    public int getStock() {
        return stock.get();
    }
    
    public void setStock(int stock) {
        this.stock.set(stock);
    }
    
    public IntegerProperty stockProperty() {
        return stock;
    }
    
    @Override
    public String toString() {
        return "Inventory{" +
                "idInventory=" + getIdInventory() +
                ", idPlayer=" + getIdPlayer() +
                ", idProperty=" + getIdProperty() +
                ", stock=" + getStock() +
                '}';
    }
}