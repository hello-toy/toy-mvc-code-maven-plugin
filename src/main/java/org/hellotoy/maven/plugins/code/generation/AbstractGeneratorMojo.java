package org.hellotoy.maven.plugins.code.generation;

import lombok.Getter;
import lombok.Setter;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.model.Resource;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.QName;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.hellotoy.maven.plugins.code.generation.config.MetaInfo;
import org.hellotoy.maven.plugins.code.generation.config.ProjectInfo;
import org.hellotoy.maven.plugins.code.generation.metasource.JSONMetaSource;
import org.hellotoy.maven.plugins.code.generation.metasource.MetaSource;
import org.hellotoy.maven.plugins.code.generation.metasource.model.Model;
import org.hellotoy.maven.plugins.code.generation.model.TemplateInfo;
import org.hellotoy.maven.plugins.code.generation.util.TemplateAssistant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Getter
@Setter
public abstract class AbstractGeneratorMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project}")
    private MavenProject project;

    @Parameter(defaultValue = "${basedir}")
    private File baseDir;

    @Parameter(defaultValue = "${project.build.resources}", readonly = true, required = true)
    private List<Resource> resources;

    @Parameter(defaultValue = "${project.build.sourceDirectory}", required = true, readonly = true)
    private File sourceDir;

    @Parameter(defaultValue = "${project.build.testResources}", readonly = true, required = true)
    private List<Resource> testResources;

    @Parameter(defaultValue = "${project.build.testSourceDirectory}", readonly = true, required = true)
    private File testSourceDir;

    @Parameter(defaultValue = "${project.compileClasspathElements}", readonly = true, required = true)
    private List<String> compilePath;

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(defaultValue = "${project.build.directory}", readonly = true, required = true)
    private String outputDirectory;

    /**
     * 元数据信息
     */
    @Parameter(readonly = false, required = true)
    private MetaInfo metaInfo;

    @Parameter(readonly = false, required = true)
    private ProjectInfo projectInfo;


    private TemplateAssistant templateAssistant;


    protected MetaSource buildMetaSource(MetaInfo metaInfo){
        switch (metaInfo.getType().toUpperCase()){
            case "JSON":
                return new JSONMetaSource(metaInfo,this.getLog());
            case "DB":
                return null;
            default:
                return null;

        }
    }

    protected void exportJavaTpl(List<Model> models) throws IOException {
        List<TemplateInfo> tpls = templateAssistant.getJavaTpl();
        for (Model table : models) {
            for (TemplateInfo tpl : tpls) {
                Map<String, Object> vars = templateAssistant.convertTplVars(table);
                setBaseVars(vars);
                String content = templateAssistant.generateJavaByTplName(tpl.getPath(), vars);
                templateAssistant.generateJavaFile(content,this.getBaseDir() + "/src/main/java");
            }
        }
    }

    protected void exportResourceTpl(List<Model> models) throws IOException {
        if(new File(this.getBaseDir().getPath()+File.separator+"src/main/resources/application.yml").exists()){
            return;
        }
        List<TemplateInfo> tpls = templateAssistant.getResourcesTpl();
        for (Model table : models) {
            for (TemplateInfo tpl : tpls) {
                Map<String, Object> vars = templateAssistant.convertTplVars(table);
                setBaseVars(vars);
                String content = templateAssistant.generateJavaByTplName(tpl.getPath(), vars);
                String name = new File(tpl.getPath()).getName();
                templateAssistant.generateCommonFile(content, this.getBaseDir() + "/src/main/resources",name);
            }
        }
    }
    protected void exportMavenTpl(List<Model> models) throws IOException {
        if(new File(this.getBaseDir().getPath()+File.separator+"pom.xml").exists()){
            return;
        }
        List<TemplateInfo> tpls = templateAssistant.getPomTpl();
        for (Model table  : models) {
            for (TemplateInfo tpl : tpls) {
                Map<String, Object> vars = templateAssistant.convertTplVars(table);
                setBaseVars(vars);
                String content = templateAssistant.generateJavaByTplName(tpl.getPath(), vars);
                String name = new File(tpl.getPath()).getName();
                templateAssistant.generateCommonFile(content, this.getBaseDir().getPath(),name);
            }
        }
    }

    /**
     * <groupId>org.projectlombok</groupId>
     * 			<artifactId>lombok</artifactId>
     * 			<version>${lombok.version}</version>
     * @return
     */
    private Element addDependency(String groupId, String artifactId, String version, Namespace namespace){
        Element dependencyEle = DocumentHelper.createElement(new QName("dependency",namespace));
        dependencyEle.add(DocumentHelper.createElement(new QName("groupId",namespace)).addText(groupId));
        dependencyEle.add(DocumentHelper.createElement(new QName("artifactId",namespace)).addText(artifactId));
        dependencyEle.add(DocumentHelper.createElement(new QName("version",namespace)).addText(version));
        return dependencyEle;
    }

    private Element addDependency(String groupId, String artifactId, Namespace namespace){
        Element dependencyEle = DocumentHelper.createElement(new QName("dependency",namespace));
        dependencyEle.add(DocumentHelper.createElement(new QName("groupId",namespace)).addText(groupId));
        dependencyEle.add(DocumentHelper.createElement(new QName("artifactId",namespace)).addText(artifactId));
        return dependencyEle;
    }

    protected void exportMavenTpl() throws IOException {
        String pomPath = this.getBaseDir().getPath()+File.separator+"pom.xml";
        if(!new File(pomPath).exists()){
            return;
        }
        try {
            SAXReader sr = new SAXReader(); // 需要导入jar包:dom4j
            // 关联xml
            Document document = sr.read(pomPath);
            Element rootEle = document.getRootElement();
            addParent(rootEle);
            addDependencies(rootEle);
            OutputStream out = new FileOutputStream(pomPath);
            XMLWriter xmlWriter = new XMLWriter(out, OutputFormat.createPrettyPrint());
            xmlWriter.write(document);
            xmlWriter.close();
        }
        catch (DocumentException e) {
            e.printStackTrace();
        }
        finally {
        }
    }

    private void addParent(Element rootEle){
        Element parent = rootEle.elements().stream().filter(e->"parent".equals(e.getName())).findFirst().orElse(null);
        if(parent == null){
            parent = DocumentHelper.createElement(new QName("parent", rootEle.getNamespace()));
            int  index = 0;
            for (Element element : rootEle.elements()) {
                index++;
                if("version".equals(element.getName())){
                    break;
                }
            }
            parent.add(DocumentHelper.createElement(new QName("groupId",rootEle.getNamespace())).addText("org.springframework.boot"));
            parent.add(DocumentHelper.createElement(new QName("artifactId",rootEle.getNamespace())).addText("spring-boot-starter-parent"));
            parent.add(DocumentHelper.createElement(new QName("version",rootEle.getNamespace())).addText("2.1.4.RELEASE"));
            rootEle.elements().add(index,((Element)parent));
        }
    }

    private void addDependencies(Element rootEle) {
        Element dependencies = dependencies = rootEle.elements().stream().filter(e->"dependencies".equals(e.getName())).findFirst().orElse(null);
        if(dependencies == null){
            dependencies = DocumentHelper.createElement(new QName("dependencies", rootEle.getNamespace()));
            int  buildIndex = 0;
            for (Element element : rootEle.elements()) {
                buildIndex++;
                if("build".equals(element.getName())){
                    break;
                }
            }
            rootEle.elements().add(buildIndex-1,((Element)dependencies));
        }
        String xml = dependencies.asXML();
        if(xml.indexOf("toy-spring-mvc-infr")==-1){
            ((Element)dependencies).add(addDependency("org.hellotoy.infr","toy-spring-mvc-infr","1.0.0", rootEle.getNamespace()));
        }
        if(xml.indexOf("spring-boot-starter-web")==-1){
            ((Element)dependencies).add(addDependency("org.springframework.boot","spring-boot-starter-web", rootEle.getNamespace()));
        }
        if(xml.indexOf("spring-boot-starter-validation")==-1){
            ((Element)dependencies).add(addDependency("org.springframework.boot","spring-boot-starter-validation",rootEle.getNamespace()));
        }
        if(xml.indexOf("druid-spring-boot-starter")==-1){
            ((Element)dependencies).add(addDependency("com.alibaba","druid-spring-boot-starter","1.1.10",rootEle.getNamespace()));
        }
    }


    public void setBaseVars(Map<String, Object> vars) {
        vars.put("basePackage", this.getProjectInfo().getBasePackage());
        vars.put("author", "admin");
        vars.put("module", this.getProjectInfo().getModule());
        vars.put("projectName",this.getProjectInfo().getName());
        vars.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }



}
