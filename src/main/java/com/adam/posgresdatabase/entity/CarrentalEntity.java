package com.adam.posgresdatabase.entity;

import javax.persistence.*;

@Entity
@Table(name = "carrental", schema = "public", catalog = "postgres")
public class CarrentalEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "mark")
    private String mark;
    @Basic
    @Column(name = "model")
    private String model;
    @Basic
    @Column(name = "price")
    private int price;
    @Basic
    @Column(name = "availability")
    private boolean availability;

    public CarrentalEntity(int id, String mark, String model, int price, boolean availability) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.price = price;
        this.availability = availability;
    }

    public CarrentalEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarrentalEntity that = (CarrentalEntity) o;

        if (id != that.id) return false;
        if (price != that.price) return false;
        if (availability != that.availability) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + price;
        result = 31 * result + (availability ? 1 : 0);
        return result;
    }
}
