package services.user;

import dto.user.User;

//TODO доработать генератор юзеров, чтобы вынести его из теста
public class CreateUserUtil {

  static User user = new User();

  public static User generateUser() {
    return user = User.builder()
        .firstName("Ivan")
        .lastName("Ivanovich")
        .username("Ivan123")
        .password("123")
        .id((int) (Math.random() * 100000))
        .userStatus(3)
        .email("example" + ((int) (Math.random() * 100000)) + "@ya.ru")
        .phone("+79" + ((int) (Math.random() * 999999)))
        .build();
  }
}
