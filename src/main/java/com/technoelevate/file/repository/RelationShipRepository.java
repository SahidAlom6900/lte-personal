package com.technoelevate.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.technoelevate.file.dto.RelationShip;

@Repository
public interface RelationShipRepository extends JpaRepository<RelationShip, Integer> {

}
