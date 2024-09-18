package com.waraujo.course.entities;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_stock_adjustment")
public class StockAdjustment {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int originalQuantity;
    private int adjustedQuantity;
    private String reason;
    private Instant adjustmentDate;

    @ManyToOne
    @JoinColumn(name = "adjusted_by_id")
    private User adjustedBy;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private  Location location;

    public StockAdjustment() {
    }

    public StockAdjustment(Integer id, Product product, int originalQuantity, int adjustedQuantity, String reason, Instant adjustmentDate, User adjustedBy, Location location) {
        this.id = id;
        this.product = product;
        this.originalQuantity = originalQuantity;
        this.adjustedQuantity = adjustedQuantity;
        this.reason = reason;
        this.adjustmentDate = adjustmentDate;
        this.adjustedBy = adjustedBy;
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOriginalQuantity() {
        return originalQuantity;
    }

    public void setOriginalQuantity(int originalQuantity) {
        this.originalQuantity = originalQuantity;
    }

    public int getAdjustedQuantity() {
        return adjustedQuantity;
    }

    public void setAdjustedQuantity(int adjustedQuantity) {
        this.adjustedQuantity = adjustedQuantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Instant getAdjustmentDate() {
        return adjustmentDate;
    }

    public void setAdjustmentDate(Instant adjustmentDate) {
        this.adjustmentDate = adjustmentDate;
    }

    public User getAdjustedBy() {
        return adjustedBy;
    }

    public void setAdjustedBy(User adjustedBy) {
        this.adjustedBy = adjustedBy;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StockAdjustment that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
