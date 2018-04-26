package com.training.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.training.demo.model.Transection;
import com.training.demo.model.User;

public interface TransectionRepository extends JpaRepository<Transection, Integer> {

}
