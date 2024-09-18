package com.waraujo.course.entities.enums;

public enum StockMovementStatus {
    PENDING(1),        // Created but not yet started
    IN_PROGRESS(2),    // Currently being processed
    COMPLETED(3),      // Successfully executed
    CANCELLED(4),      // Cancelled before completion
    ON_HOLD(5),        // Temporarily paused
    FAILED(6),         // Stock movement attempt failed
    PARTIALLY(7),      // Only part of the stock movement is completed
    RETURNED(8);       // The stock has been returned to the inventory

    private final int value;

    StockMovementStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static StockMovementStatus valueOf(int value) {
        for (StockMovementStatus status : StockMovementStatus.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid value for StockMovementStatus: " + value);
    }
}
