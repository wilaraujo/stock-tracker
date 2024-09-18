package com.waraujo.course.entities;

import com.waraujo.course.entities.enums.StockMovementStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tb_movement_request")
public class MovementRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Instant requestDate;
    private Integer status;

    @OneToOne
    @JoinColumn(name = "initiated_by_id")
    private User initiatedBy;

    public MovementRequest() {
    }

    public MovementRequest(Integer id, Instant requestDate, StockMovementStatus status, User initiatedBy) {
        this.id = id;
        this.requestDate = requestDate;
        setStatus(status);
        this.initiatedBy = initiatedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Instant requestDate) {
        this.requestDate = requestDate;
    }

    public StockMovementStatus getStatus() {
        return StockMovementStatus.valueOf(status);
    }

    public void setStatus(StockMovementStatus status) {
        if (status != null) {
            this.status = status.getValue();
        }
    }

    public User getInitiatedBy() {
        return initiatedBy;
    }

    public void setInitiatedBy(User initiatedBy) {
        this.initiatedBy = initiatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovementRequest that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}