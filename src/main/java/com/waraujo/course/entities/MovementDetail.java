package com.waraujo.course.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_movement_detail")
public class MovementDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "movement_request_id")
    private MovementRequest movementRequest;

    @ManyToOne
    @JoinColumn(name = "stock_item_id")
    private StockItem stockItem;

    @ManyToOne
    @JoinColumn(name = "from_location_id")
    private Location fromLocation;

    @ManyToOne
    @JoinColumn(name = "to_location_id")
    private Location toLocation;

    private int quantityMoved;
    private Instant movementDate;

    public MovementDetail() {
    }

    public MovementDetail(Integer id, MovementRequest movementRequest, StockItem stockItem, Location fromLocation, Location toLocation, int quantityMoved, Instant movementDate) {
        this.id = id;
        this.movementRequest = movementRequest;
        this.stockItem = stockItem;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.quantityMoved = quantityMoved;
        this.movementDate = movementDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public MovementRequest getMovementRequest() {
        return movementRequest;
    }

    public void setMovementRequest(MovementRequest movementRequest) {
        this.movementRequest = movementRequest;
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }

    public Location getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(Location fromLocation) {
        this.fromLocation = fromLocation;
    }

    public Location getToLocation() {
        return toLocation;
    }

    public void setToLocation(Location toLocation) {
        this.toLocation = toLocation;
    }

    public int getQuantityMoved() {
        return quantityMoved;
    }

    public void setQuantityMoved(int quantityMoved) {
        this.quantityMoved = quantityMoved;
    }

    public Instant getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Instant movementDate) {
        this.movementDate = movementDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovementDetail that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}