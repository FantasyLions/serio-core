# serio-core
Release your hands

# expect
Reduce repetitive code

# maven可以通过以下方式来配置 `pom.xml`:

```XML
<project>

    <properties>
        <version.serio>1.0.1</version.serio>
    </properties>
    
        <repositories>
                <repository>
                    <id>serio-repository</id>
                    <url>https://raw.githubusercontent.com/FantasyLions/serio-repository/master</url>
                </repository>
        </repositories> 

        
    <dependencies>
        <dependency>
            <groupId>com.serio</groupId>
            <artifactId>serio-core</artifactId>
            <version>${version.serio}</version>
        </dependency>
    </dependencies>
    
</project>
```

# maven 部署命令

```SHEEL
mvn deploy -DaltDeploymentRepository=serio-core::default::file:D:/DATA/code/serio-repository
```
