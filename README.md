# serio-core
Release your hands

## expect
Reduce repetitive code

## maven可以通过以下方式来配置 `pom.xml`:

```XML
<project>

    <properties>
        <version.serio>1.0.3</version.serio>
    </properties>
    
        <repositories>
                <repository>
                    <id>serio-repository</id>
                    <url>https://raw.githubusercontent.com/fantasylion/serio-repository/master</url>
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

## abstract

#### `com.serio.core.parser`
这个包下的类用于处理接口的返回结果。经常会接到一些任务，去调用第三方公司的接口去查询一些信息，而这些信息一般都是直接返回一个`list`很难知道哪个值是属于哪个字段的。通过`parser`配置后可以快速的解析出结果

#### `com.serio.core.media`
这个包主要用于处理视频、 音频、 图片等一些媒体资源

##### MediaDemo

```JAVA

	// 视频截图
	public void testCut() {
		
		File source = new File("C:\\Users\\serio.shi\\Videos\\3zhzI640.mp4");
		File target = new File("C:\\Users\\serio.shi\\Videos\\3zhzI64ssss17.jpg");
		try {
			VideoProcessor videoProcessor = new VideoProcessor();
			videoProcessor.videoCapture(source, target, 340f, 640, 360);
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		
	}
	
	// 视频转码
	public void testTrasncoder() {
		
		File source = new File("C:\\Users\\serio.shi\\Videos\\3zhzI640.mp4");
		File target = new File("C:\\Users\\serio.shi\\Videos\\3zhzI64ssss17.3gp");
		try {
			VideoProcessor videoProcessor = new VideoProcessor();
			videoProcessor.transcode(source, target);
		} catch (EncoderException e) {
			e.printStackTrace();
		}
		
	}
	
```


## maven 部署命令

```SHEEL
mvn deploy -DaltDeploymentRepository=serio-core::default::file:D:/DATA/code/serio-repository
```
