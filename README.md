# mvn-code-maven-plugin

1.下载
2.新建项目在pom.xml 添加如下依赖
<plugin> 
        <groupId>org.hellotoy.maven.plugins</groupId>  
        <artifactId>toy-mvc-code-maven-plugin</artifactId>  
        <version>1.0.0</version>  
        <executions> 
          <execution> 
            <id>gen-code</id>  
            <phase>package</phase>  
            <goals> 
              <goal>generateMVCCode</goal> 
            </goals> 
          </execution> 
        </executions>  
        <configuration> 
          <metaInfo> 
            <type>JSON</type>  
            <path>${basedir}/meta</path> 
          </metaInfo>  
          <projectInfo> 
            <basePackage>com.alibaba</basePackage>  
            <name>test-auto-code</name>  
            <module>hello</module> 
          </projectInfo> 
        </configuration> 
      </plugin>
3.在${basedir}/meta目录下创建类文件描述如下：
{
"name": "Test",
"businessRef": "测试业务",
"disabled": "false",
"describe": "描述测试业务",
"properties": [
{
"name": "id",
"lable": "第一个字段",
"type": "String",
"defaultVal": "abc",
"required": true,
"disabled": false,
"describe": "第一个字段",
"priKey": true
},
{
"name": "name",
"lable": "第二个字段",
"type": "String",
"defaultVal": "name",
"required": true,
"disabled": false,
"describe": "第二个字段"
}
]
}
4.执行package