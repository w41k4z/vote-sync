package internship.project.election.dto;

import java.util.Map;

import lombok.Data;

@Data
public class ApiResponse {
    private Object payload;
    private String message;
    private Map<String, String> errors;

    public ApiResponse() {
    }

    public ApiResponse(Object payload, String message) {
        this.payload = payload;
        this.message = message;
    }

    public ApiResponse(Object payload, String message, Map<String, String> errors) {
        this.payload = payload;
        this.message = message;
        this.errors = errors;
    }
}
