package ${basePackage}.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author lich
 * @date 2020/4/26
 */
@SpringBootApplication(exclude = {GsonAutoConfiguration.class},scanBasePackages={"${basePackage}.*","org.hellotoy.mvc.infr.core.*"})
@EnableAsync(proxyTargetClass = true)
@MapperScan("${basePackage}.*.mappers")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}