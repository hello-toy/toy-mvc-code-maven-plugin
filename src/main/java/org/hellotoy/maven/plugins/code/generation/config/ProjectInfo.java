package org.hellotoy.maven.plugins.code.generation.config;

import lombok.Data;

@Data
public class ProjectInfo {

    private String basePackage;

    private String name;

    private String module;

}
