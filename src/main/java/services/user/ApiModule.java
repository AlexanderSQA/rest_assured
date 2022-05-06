package services.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiModule {

  @Bean
  public UserApi getUserApi() {
    return new UserApi();
  }
}
