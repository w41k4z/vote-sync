package ceni.system.votesync.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NewUserRequest {

    @NotBlank(message = "Identifier is required")
    private String identifier;

    @NotNull(message = "Role id is required")
    private Integer roleId;

    @NotBlank(message = "Name is required")
    private String name;

    private String firstName;

    @Pattern(regexp = "^03[0-9]{1}[0-9]{2}[0-9]{3}[0-9]{2}$", message = "Contact is not valid. (Ex: 0349315928)")
    private String contact;

    @NotBlank(message = "Password is required")
    private String password;

}
