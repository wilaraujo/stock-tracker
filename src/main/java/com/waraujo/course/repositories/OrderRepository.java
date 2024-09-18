package com.waraujo.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waraujo.course.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
