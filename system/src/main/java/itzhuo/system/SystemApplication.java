package itzhuo.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"itzhuo"}) // scanBasePackages = {"itzhuo"}扫描指定包，是得common模块下的配置了类可以被加载进来
@MapperScan("itzhuo.**.mapper")
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
        System.out.println("系统服务启动成功！");
    }

}
