package internship.project.election.dto.request;

import lombok.Data;

@Data
public class NewUserRequest {

    private String identifier;
    private Integer roleId;
    private String name;
    private String firstName;
    private String contact;
    private String password;

}
