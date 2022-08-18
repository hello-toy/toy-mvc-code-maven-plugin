<?xml version="1.0"?>
<project
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"
	xmlns="http://maven.apache.org/POM/4.0.0"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.4.RELEASE</version>
		<relativePath />
	</parent>
	<groupId>${basePackage}</groupId>
	<artifactId>${projectName}</artifactId>
	<name>tpl</name>
	<url>http://maven.apache.org</url>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.hellotoy.infr</groupId>
			<artifactId>toy-spring-mvc-infr</artifactId>
			<version>1.0.0</version>
		</dependency>
			<dependency>
			<groupId>com.baomidou</groupId>
			<artifactId>mybatis-plus-boot-starter</artifactId>
			<version>3.4.1</version>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
		</dependency>
		<dependency>
				<groupId>com.alibaba</groupId>
				<artifactId>druid-spring-boot-starter</artifactId>
				<version>1.1.10</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-dependency-plugin</artifactId>
				<executions>
					<execution>
						<id>copy-dependencies</id>
						<phase>package</phase>
						<goals>
							<goal>copy-dependencies</goal>
						</goals>
						<configuration>
							<outputDirectory>target/lib</outputDirectory>
							<excludeTransitive>false</excludeTransitive>
							<stripVersion>false</stripVersion>
							<includeScope>runtime</includeScope>
						</configuration>
					</execution>
				</executions>
			</plugin>

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-jar-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>**/*.properties</exclude>
						<exclude>**/*.xml</exclude>
						<exclude>**/*.yml</exclude>
						<exclude>static/**</exclude>
						<exclude>templates/**</exclude>
					</excludes>
				</configuration>
			</plugin>

			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<layout>ZIP</layout>
					<includes>
						<include>
							<groupId>non-exists</groupId>
							<artifactId>non-exists</artifactId>
						</include>
					</includes>
				</configuration>
				<executions>
					<execution>
						<goals>
							<goal>repackage</goal>
						</goals>
						<configuration>
							<classifier>classes</classifier>
							<attach>false</attach>
						</configuration>
					</execution>
				</executions>
			</plugin>

			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-antrun-plugin</artifactId>
				<executions>
					<execution>
						<phase>package</phase>
						<goals>
							<goal>run</goal>
						</goals>
						<configuration>
							<target>
								<property name="dist">target/distribution</property>
								<property name="dist-tmp">target/distribution/tmp</property>
								<property name="app-name">${'$'}{project.artifactId}-${'$'}{project.version}</property>
								<mkdir dir="${'$'}{dist-tmp}" />
								<copy file="target/${'$'}{app-name}.jar"
									tofile="${'$'}{dist-tmp}/${'$'}{app-name}.jar" />
								<unzip src="${'$'}{dist-tmp}/${'$'}{app-name}.jar"
									dest="${'$'}{dist-tmp}" />
								<delete file="${'$'}{dist-tmp}/${'$'}{app-name}.jar" />

								<!-- <zip destfile="${'$'}{dist}/${'$'}{app-name}-pages.jar">
									<zipfileset dir="${'$'}{dist-tmp}/META-INF"
										prefix="META-INF" />
									<zipfileset dir="target/classes/static"
										prefix="static" />
									<zipfileset dir="target/classes/templates"
										prefix="templates" />
								</zip> -->

								<move file="target/${'$'}{app-name}-classes.jar" todir="${'$'}{dist}" />
								<move todir="${'$'}{dist}/3rd-lib">
									<fileset dir="target/lib" />
								</move>

								<delete dir="${'$'}{dist-tmp}" />

								<copy todir="${'$'}{dist}">
									<fileset dir="target/classes">
										<include name="**/*.properties" />
										<include name="**/*.xml" />
										<include name="**/*.yml" />
									</fileset>
								</copy>
							</target>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
		<resources>
			<resource>
				<directory>src/main/resources</directory>
				<!-- 是否替换@xx@表示的maven properties属性值 -->
				<filtering>true</filtering>
				<!-- 引入资源 -->
				<includes>
					<include>bootstrap.yml</include>
					<include>application.yml</include>
				</includes>
			</resource>
		</resources>
	</build>
</project>
