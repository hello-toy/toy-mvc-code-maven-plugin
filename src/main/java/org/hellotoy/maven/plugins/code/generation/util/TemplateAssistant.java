package org.hellotoy.maven.plugins.code.generation.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.hellotoy.maven.plugins.code.generation.metasource.model.Model;
import org.hellotoy.maven.plugins.code.generation.model.TemplateInfo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

@Slf4j
public class TemplateAssistant {

	private String outputDirectory;

	public TemplateAssistant(String baseDirectory){
		this.outputDirectory = baseDirectory+ File.separator+"gen-code-tmp";
		File file = new File(outputDirectory);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	public String generateJavaByTplName(String tplPath, Map<String, Object> vars) {
		String retData = null;
		ByteArrayOutputStream tempOut = new ByteArrayOutputStream();
		Writer out = null;
		try {
			File tpl = new File(tplPath);
			Template template = getConfiguration(tpl.getParentFile()).getTemplate(tpl.getName());
			out = new OutputStreamWriter(tempOut, "UTF-8");
			template.process(vars, out);
			out.flush();
			tempOut.flush();
			retData = tempOut.toString("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(out);
			IOUtils.closeQuietly(tempOut);
		}
		return retData;
	}

	public void generateJavaFile(String content, String path) throws IOException {
		ASTParser parser = ASTParser.newParser(AST.JLS3);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(content.toCharArray());
		parser.setResolveBindings(true);
		CompilationUnit result = (CompilationUnit) parser.createAST(null);
		TypeDeclaration type = (TypeDeclaration) result.types().get(0);
		String fullClassName = result.getPackage().getName().toString() + "." + type.getName().toString() + ".java";
		fullClassName = fullClassName.replaceAll("[.]", "/").replace("/java", ".java");
		File temp = new File(path+"/"+fullClassName);
		FileUtils.forceMkdir(new File(temp.getParentFile().getAbsolutePath()));
		IOUtils.write(content.getBytes("UTF-8"), new FileOutputStream(temp));
	}
	
	public void generateCommonFile(String content, String path,String name) throws IOException {
		String fullClassName = name.substring(0,name.lastIndexOf(".ftl"));
		File temp = new File(path+"/"+fullClassName);
		FileUtils.forceMkdir(new File(temp.getParentFile().getAbsolutePath()));
		IOUtils.write(content.getBytes("UTF-8"), new FileOutputStream(temp));
	}

	private Configuration getConfiguration(File path) throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(path);
		cfg.setLocale(Locale.CHINA);
		cfg.setDefaultEncoding("UTF-8");
		return cfg;
	}

	public List<TemplateInfo> getAllTpl() throws IOException {
		List<TemplateInfo> retData = new ArrayList<>();
		File tplParentFolder = new File(getTemplatePath());
		FileUtil.readFile(tplParentFolder, new FileUtil.FileProcessor() {
			@Override
			public void file(File file) {
				if (file.getName().endsWith(".ftl")) {
					TemplateInfo info = new TemplateInfo();
					File tmp = file;
					while(true) {
						if(tmp.getParentFile().getAbsoluteFile().equals(tplParentFolder.getAbsoluteFile())) {
							info.setModule(tmp.getName());
							break;
						}
						tmp = tmp.getParentFile();
					}
					info.setName(file.getName());
					info.setPath(file.getAbsolutePath());
					retData.add(info);
				}
			}
		});
		return retData;
	}
	
	public List<TemplateInfo> getJavaTpl() throws IOException{
		List<TemplateInfo> allTpls = getAllTpl();
		return allTpls.stream().filter(t->{
			return t.getPath().indexOf("java")>-1;
		}).collect(Collectors.toList());
	}
	
	public List<TemplateInfo> getResourcesTpl() throws IOException{
		List<TemplateInfo> allTpls = getAllTpl();
		return allTpls.stream().filter(t->{
			return t.getPath().indexOf("resources")>-1;
		}).collect(Collectors.toList());
	}
	public List<TemplateInfo> getPomTpl() throws IOException{
		List<TemplateInfo> allTpls = getAllTpl();
		return allTpls.stream().filter(t->{
			return t.getPath().indexOf("pom.xml")>-1;
		}).collect(Collectors.toList());
	}
	

	public String getTemplatePath() throws IOException {
		JarFile jarFile = null;
		try {
			String jarPath = this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile();
			if(!jarPath.endsWith(".jar")){
				return jarPath+File.separator+"tpls";
			}
			jarFile = new JarFile(jarPath);
			Enumeration<JarEntry> entrys = jarFile.entries();
			while (entrys.hasMoreElements()) {
				JarEntry jar = entrys.nextElement();
				String name = jar.getName();
				if(name.endsWith(".ftl")){
					InputStream in = this.getClass().getClassLoader().getResourceAsStream(name);
					String outPath = this.outputDirectory+File.separator+name;
					File file = new File(outPath);
					if(!file.getParentFile().exists()){
						file.getParentFile().mkdirs();
					}
					IOUtils.copy(in,new FileOutputStream(file));
				}
			}
		}
		catch (IOException e) {
			log.error("copy file to temp occured error!",e);
		}
		finally {
			if(jarFile!=null) {
				IOUtils.closeQuietly(jarFile);
			}
		}

		return outputDirectory+File.separator+"tpls";

	}

	public Map<String, Object> convertTplVars(Model info) {
		Map<String, Object> retData = new HashMap<String, Object>();
		//retData.put("module", info.getDomain());
		retData.put("ModelDescription", info.getDescribe());
		retData.put("Model", info.getName());
		retData.put("HumpModel", info.getHumpName());
		retData.put("ID", info.getPriKey().getType());
		retData.put("IDName", info.getPriKey().getName());
		retData.put("TableName", info.getName());
		List<Map<String, String>> properties = new ArrayList<>();
		info.getProperties().forEach(action -> {
				Map<String, String> property = new HashMap<>();
				property.put("comment", action.getDescribe());
				property.put("fieldType", action.getType());
				property.put("fieldName", action.getName());
				properties.add(property);
		});
		retData.put("modelProreties",properties);
		retData.put("entityProreties", properties);
		return retData;
	}
}
