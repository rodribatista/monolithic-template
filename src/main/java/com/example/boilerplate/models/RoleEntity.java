package com.example.boilerplate.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "ROLES", uniqueConstraints = {
  @UniqueConstraint(columnNames = "name")
})
public class RoleEntity implements Serializable {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(columnDefinition = "VARCHAR(50)")
  private String id;

  @Column(nullable = false, unique = true,
    columnDefinition = "VARCHAR(10)")
  private String name;

  @JsonBackReference
  @ManyToMany(
    fetch = FetchType.EAGER,
    cascade = {
      CascadeType.DETACH,
      CascadeType.MERGE,
      CascadeType.PERSIST,
      CascadeType.REFRESH
    })
  @JoinTable(name = "ROLES_PRIVILEGES",
    joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "privileges_id", referencedColumnName = "id"))
  private Set<PrivilegeEntity> privileges;

}