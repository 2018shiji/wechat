#mysql dockerfile
FROM openjdk:11
#将当前目录下的jar包复制到docker容器的/目录下
ADD ./target/wechat-0.0.1-SNAPSHOT.jar /wechat.jar
#运行过程中创建一个httpclient.jar文件
RUN bash -c 'touch /wechat.jar'
#声明服务运行在80端口
EXPOSE 80
#指定docker容器启动时运行jar包
ENTRYPOINT ["java", "-jar", "/wechat.jar"]