## 运行

Spring Profile用来解决本地开发，多种运行环境所属配置不一致的问题。

```sh
# 打包
mvn clean package
cd target

# profile为dev
java -jar profile-1.0-SNAPSHOT.jar # 默认profile为dev，见application.properties中的配置项spring.profiles.active
java -jar profile-1.0-SNAPSHOT.jar --spring.profiles.active=dev

# profile为prod
java -jar profile-1.0-SNAPSHOT.jar --spring.profiles.active=prod
```