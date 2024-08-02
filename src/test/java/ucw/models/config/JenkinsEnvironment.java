package ucw.models.config;


import lombok.Data;

@Data
public class JenkinsEnvironment {
    private String url, username, password;
}
