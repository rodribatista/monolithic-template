package com.example.boilerplate.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;

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
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
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

}