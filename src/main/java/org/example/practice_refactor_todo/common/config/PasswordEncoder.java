package org.example.practice_refactor_todo.common.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

  /**
   * 비밀번호 암호화
   *
   * @param rawPassword 비밀번호
   * @return 암호화된 비밀번호
   */
  public String encode(String rawPassword) {
    return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
  }

  /**
   * 비밀번호 일치 여부 확인
   *
   * @param rawPassword 비밀번호
   * @param encodedPassword 암호화된 비밀번호
   * @return 일치 여부
   */
  public boolean matches(String rawPassword, String encodedPassword) {
    BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
    return result.verified;
  }
}
