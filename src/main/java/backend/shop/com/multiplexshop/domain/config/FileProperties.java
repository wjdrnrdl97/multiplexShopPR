package backend.shop.com.multiplexshop.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {
    private String dir;
    private String webpath;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getWebpath() {
        return webpath;
    }

    public void setWebpath(String webpath) {
        this.webpath = webpath;
    }
}
