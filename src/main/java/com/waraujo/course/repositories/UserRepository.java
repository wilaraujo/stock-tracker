package com.waraujo.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waraujo.course.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
