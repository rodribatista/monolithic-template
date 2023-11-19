package com.example.boilerplate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "USERS", uniqueConstraints = {
  @UniqueConstraint(columnNames = "email")
})
public class UserEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(columnDefinition = "VARCHAR(50)")
  private String id;

  @Column(columnDefinition = "VARCHAR(50)")
  private String name;

  @Column(columnDefinition = "VARCHAR(50)")
  private String surname;

  @Email
  @Column(nullable = false, unique = true,
    columnDefinition = "VARCHAR(100)")
  private String email;

  @JsonIgnore
  @Column(nullable = false,
    columnDefinition = "VARCHAR(100)")
  private String password;

  @ManyToMany(
    fetch = FetchType.EAGER,
    cascade = {
      CascadeType.DETACH,
      CascadeType.MERGE,
      CascadeType.REFRESH
    })
  @JoinTable(name = "USERS_ROLES",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
  private Set<RoleEntity> roles = new HashSet<>();

}