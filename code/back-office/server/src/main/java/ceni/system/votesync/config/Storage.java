package ceni.system.votesync.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "storage")
public class Storage {

    @NotBlank(message = "Location must not be blank for the storage configuration")
    private String location;
}
