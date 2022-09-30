package com.recommit.assignment.formula1.formula1dataservice.repository;

import com.recommit.assignment.formula1.formula1dataservice.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
}
