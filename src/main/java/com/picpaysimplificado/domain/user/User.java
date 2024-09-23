package com.picpaysimplificado.domain.user;

import java.math.BigDecimal;

import com.picpaysimplificado.domain.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;

  private String lastName;

  @Column(unique = true)
  private String document;

  @Column(unique = true)
  private String email;

  private String password;

  private BigDecimal balance;

  private UserType userType;

  public User(UserDTO dto) {
    this.firstName = dto.firstName();
    this.lastName = dto.lastName();
    this.balance = dto.balance();
    this.document = dto.document();
    this.userType = dto.userType();
    this.email = dto.email();
    this.password = dto.password();
  }
}
