package org.hellotoy.maven.plugins.code.generation;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.hellotoy.maven.plugins.code.generation.metasource.MetaSource;
import org.hellotoy.maven.plugins.code.generation.metasource.model.Model;
import org.hellotoy.maven.plugins.code.generation.util.TemplateAssistant;

import java.io.IOException;
import java.util.List;

@Mojo(name = "generateMVCCode")
public class GenerateMVCCodeMojo extends AbstractGeneratorMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("meta info : "+this.getMetaInfo());
        getLog().info("project info : "+this.getProjectInfo());
        this.setTemplateAssistant(new TemplateAssistant(this.getOutputDirectory()));
        MetaSource metaSource = this.buildMetaSource(getMetaInfo());
        List<Model> models =  metaSource.loadAllModels();
        getLog().info("models is:"+models);
        try {
            this.exportJavaTpl(models);
            this.exportResourceTpl(models);
            //this.exportMavenTpl(models);
            exportMavenTpl();
        }
        catch (IOException e) {
           this.getLog().error("failed ",e);
        }
    }




}
