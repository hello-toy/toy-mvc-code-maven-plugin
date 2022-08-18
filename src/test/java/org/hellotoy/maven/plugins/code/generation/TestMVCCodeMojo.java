package org.hellotoy.maven.plugins.code.generation;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.hellotoy.maven.plugins.code.generation.config.MetaInfo;
import org.hellotoy.maven.plugins.code.generation.config.ProjectInfo;

import java.io.File;

public class TestMVCCodeMojo {

    public static void main(String[] args) throws MojoExecutionException, MojoFailureException {
        GenerateMVCCodeMojo generateMVCCodeMojo = new GenerateMVCCodeMojo();
        MetaInfo metaInfo = new MetaInfo();
        metaInfo.setType("JSON");
        metaInfo.setPath("E:\\workspace\\test\\meta");
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setBasePackage("com.alibaba");
        projectInfo.setModule("hello");
        projectInfo.setName("test-auto-code");
        generateMVCCodeMojo.setMetaInfo(metaInfo);
        generateMVCCodeMojo.setProjectInfo(projectInfo);
        generateMVCCodeMojo.setOutputDirectory("E:\\workspace\\test\\target");
        MavenProject mavenProject = new MavenProject();
        mavenProject.setName("gts-test");
        generateMVCCodeMojo.setProject(mavenProject);
        generateMVCCodeMojo.setBaseDir(new File("E:\\workspace\\test\\"));
        generateMVCCodeMojo.execute();

    }

}
