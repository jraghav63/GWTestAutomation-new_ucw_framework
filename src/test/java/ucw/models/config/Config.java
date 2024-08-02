package ucw.models.config;

import lombok.Data;
import ucw.enums.Environments;

import java.util.List;

@Data
public class Config {
    public Environments environment;
    private String ccAccount, bcAccount, pcAccount, portalAccount, ssoAccount,
            accountWithoutCode, defaultRegion, defaultLanguage;
    private List<CenterUrl> centerUrls;
    private GithubEnvironment github;
    private JenkinsEnvironment jenkins;
    private int timeout, futureYear;
    private boolean headless, selfHealing;
}