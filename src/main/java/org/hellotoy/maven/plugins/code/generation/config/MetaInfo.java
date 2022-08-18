package org.hellotoy.maven.plugins.code.generation.config;

import lombok.Data;

@Data
public class MetaInfo {
    private String type;
    private String username;
    private String password;
    private String url;
    private String path;
}
