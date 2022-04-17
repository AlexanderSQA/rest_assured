package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize
public class GetUserResponseBody {
  private String firstName;
  private String lastName;
  private int userStatus;
  private String phone;
  private int id;
  private String email;
  private String username;
  private String password;
}