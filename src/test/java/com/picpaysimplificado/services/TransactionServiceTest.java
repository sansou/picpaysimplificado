package com.picpaysimplificado.services;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;

import com.picpaysimplificado.domain.dto.TransactionDTO;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.repositories.TransactionRepository;

public class TransactionServiceTest {

  @Mock
  private UserService userService;

  @Mock
  private TransactionRepository repository;

  @Mock
  private AuthorizationService authService;

  @Mock
  private NotificationService notificationService;

  @Autowired
  @InjectMocks
  private TransactionService service;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testAutorizeTransaction() {

  }

  @Test
  @DisplayName("Should create transaction sucessfully  when everything is OK")
  void testCreateTransactionCase1() throws Exception {
    User sender = new User(1l, "Thales", "Souza", "123456789", "teste@example.com", "supersenha", new BigDecimal(100),
        UserType.COMMON);
    User receiver = new User(2l, "Miguel", "Souza", "987654321", "miguel@example.com", "supersenha",
        new BigDecimal(100), UserType.COMMON);

    when(userService.findUserById(1l)).thenReturn(sender);
    when(userService.findUserById(2l)).thenReturn(receiver);

    when(authService.autorizeTransaction(any(), any())).thenReturn(true);

    TransactionDTO request = new TransactionDTO(new BigDecimal(50), 1l, 2l);
    service.createTransaction(request);

    verify(repository, times(1)).save(any());

    sender.setBalance(new BigDecimal(50));
    verify(userService, times(1)).saveUser(sender);

    receiver.setBalance(new BigDecimal(150));
    verify(userService, times(1)).saveUser(receiver);

    verify(notificationService, times(1)).sendNotification(sender, "Transação realizada com sucesso");
    verify(notificationService, times(1)).sendNotification(receiver, "Transação recebida com sucesso");
  }

  @Test
  @DisplayName("Should throw Exception when Transaction is not allowed")
  void testCreateTransactionCase2() throws Exception {

    User sender = new User(1l, "Thales", "Souza", "123456789", "teste@example.com", "supersenha", new BigDecimal(100),
        UserType.COMMON);
    User receiver = new User(2l, "Miguel", "Souza", "987654321", "miguel@example.com", "supersenha",
        new BigDecimal(100), UserType.COMMON);

    when(userService.findUserById(1l)).thenReturn(sender);
    when(userService.findUserById(2l)).thenReturn(receiver);

    when(authService.autorizeTransaction(any(), any())).thenReturn(false);

    Exception thrown = Assertions.assertThrows(Exception.class, () -> {
      TransactionDTO request = new TransactionDTO(new BigDecimal(50), 1l, 2l);
      service.createTransaction(request);
    });

    Assertions.assertEquals("Transação não autorizada", thrown.getMessage());
  }
}
