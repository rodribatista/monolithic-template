package com.example.boilerplate.repository;

import com.example.boilerplate.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
  RoleEntity findByName(String name);
}