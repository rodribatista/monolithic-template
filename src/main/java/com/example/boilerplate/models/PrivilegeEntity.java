package com.example.boilerplate.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode(exclude = "roles")
@ToString(exclude = {"roles"})
@Entity
@Table(name = "PRIVILEGES", uniqueConstraints = {
  @UniqueConstraint(columnNames = "name")
})
public class PrivilegeEntity implements Serializable {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(columnDefinition = "VARCHAR(50)")
  private String id;

  @Column(nullable = false, unique = true,
    columnDefinition = "VARCHAR(20)")
  private String name;

  @ManyToMany(mappedBy = "privileges")
  @JsonManagedReference
  private Set<RoleEntity> roles = new HashSet();

}