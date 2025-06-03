package app.model;

import javafx.beans.property.*;

/**
 * Model class untuk tabel property
 */
public class Property {
    private final IntegerProperty idProperty;
    private final StringProperty name;
    private final IntegerProperty price;
    private final StringProperty type;
    private final IntegerProperty point;
    private final IntegerProperty exp;
    
    public Property() {
        this.idProperty = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.price = new SimpleIntegerProperty();
        this.type = new SimpleStringProperty();
        this.point = new SimpleIntegerProperty();
        this.exp = new SimpleIntegerProperty();
    }
    
    public Property(int idProperty, String name, int price, String type, int point, int exp) {
        this();
        setIdProperty(idProperty);
        setName(name);
        setPrice(price);
        setType(type);
        setPoint(point);
        setExp(exp);
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
    
    // Getter dan Setter untuk price
    public int getPrice() {
        return price.get();
    }
    
    public void setPrice(int price) {
        this.price.set(price);
    }
    
    public IntegerProperty priceProperty() {
        return price;
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
    
    // Getter dan Setter untuk point
    public int getPoint() {
        return point.get();
    }
    
    public void setPoint(int point) {
        this.point.set(point);
    }
    
    public IntegerProperty pointProperty() {
        return point;
    }
    
    // Getter dan Setter untuk exp
    public int getExp() {
        return exp.get();
    }
    
    public void setExp(int exp) {
        this.exp.set(exp);
    }
    
    public IntegerProperty expProperty() {
        return exp;
    }
    
    @Override
    public String toString() {
        return "Property{" +
                "idProperty=" + getIdProperty() +
                ", name='" + getName() + '\'' +
                ", price=" + getPrice() +
                ", type='" + getType() + '\'' +
                ", point=" + getPoint() +
                ", exp=" + getExp() +
                '}';
    }
}