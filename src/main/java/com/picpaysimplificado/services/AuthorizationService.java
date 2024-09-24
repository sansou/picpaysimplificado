package com.picpaysimplificado.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import com.picpaysimplificado.domain.user.User;

@Service
public class AuthorizationService {
  @Autowired
  private RestTemplate restTemplate;

  public boolean autorizeTransaction(User sender, BigDecimal value) {
    ResponseEntity<Map> autorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize",
        Map.class);

    if (autorizationResponse.getStatusCode() == HttpStatus.OK) {
      String message = (String) autorizationResponse.getBody().get("status");
      return "Success".equalsIgnoreCase(message);
    } else
      return false;

  }

}
