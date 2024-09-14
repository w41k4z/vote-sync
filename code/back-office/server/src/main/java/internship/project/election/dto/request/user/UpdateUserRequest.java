package internship.project.election.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class UpdateUserRequest {

    @NotBlank(message = "Id is required")
    private Integer id;

    @NotBlank(message = "Identifier is required")
    private String identifier;

    @NotBlank(message = "Role id is required")
    private Integer roleId;

    @NotBlank(message = "Name is required")
    private String name;

    private String firstName;

    @NotBlank(message = "Contact is required")
    private String contact;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "State is required")
    private Integer state;
}
