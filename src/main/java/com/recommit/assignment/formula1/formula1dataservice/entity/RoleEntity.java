package com.recommit.assignment.formula1.formula1dataservice.entity;

import com.recommit.assignment.formula1.formula1dataservice.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "roles",uniqueConstraints = {@UniqueConstraint(columnNames = {"role"})})
@AllArgsConstructor
@Data
@NoArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String role;
    private String description;
}
