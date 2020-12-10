测试多模块下 公共模块打包的问题
```
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <layout>NONE</layout>  <!--让maven不打包可执行jar，不扫描项目的main函数-->
                    <classifier>exec</classifier> <!--普通jar和可执行jar不同名，普通jar为xx.jar ， 可执行jar为 xx-exec.jar-->
                </configuration>
            </plugin>
        </plugins>
    </build>
```