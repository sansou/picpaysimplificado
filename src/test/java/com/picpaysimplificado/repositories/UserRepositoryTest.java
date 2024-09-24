package com.picpaysimplificado.repositories;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.picpaysimplificado.domain.dto.UserDTO;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
  @Autowired
  EntityManager entityManager;

  @Autowired
  UserRepository repository;

  private User createUser(UserDTO data) {
    User newUser = new User(data);
    this.entityManager.persist(newUser);
    return newUser;
  }

  @Test
  @DisplayName("Should get User sucessfulle from DB")
  void testFindUserByDocument() {
    String document = "123456789";
    UserDTO dto = new UserDTO("Thales", "Souza", document, new BigDecimal(100), "thales@example.com", "supersenha",
        UserType.COMMON);
    this.createUser(dto);

    Optional<User> result = this.repository.findUserByDocument(document);

    assertThat(result.isPresent()).isTrue();

  }

  @Test
  @DisplayName("Should not get User sucessfulle from DB")
  void testFindUserByDocumentCase2() {
    String document = "123456789";

    Optional<User> result = this.repository.findUserByDocument(document);

    assertThat(result.isEmpty()).isTrue();

  }

  @Test
  void testFindUserById() {

  }

}
