#faster-framework-core
##Getting Start

- 发布仓库到本地

```
gradle install
```

- gradle配置

```
processResources {
    from('src/main/java') {
       include '**/*.xml'
    }
}
repositories {
      mavenLocal()
}
```

- 引入

```
compile 'cn.faster.framework:faster-framework-core-spring-boot-starter:1.0-SNAPSHOT'
```
