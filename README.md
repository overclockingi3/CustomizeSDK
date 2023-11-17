自定义SDK通常方便新建项目时直接导入一些工具类以及其它东西，方便更快捷开发，这里准备把自己写的以及一些看到不错的几个工具类集合到一起做个简单的SDK。
# 环境：
Android Studio 2.3.3 + JDK 1.8
# 首先创建项目：
项目名什么的随便就好，创建项目之后创建新安卓库如图：

![创建安卓库](http://upload-images.jianshu.io/upload_images/5544786-f465d6c250a6ee44.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
创建完之后会发现库的文件结构和项目文件结构是一样的（- - 有点废话）
![文件结构](http://upload-images.jianshu.io/upload_images/5544786-d5fc96858b3e6425.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
接着在自定义的库里编写SDK内容（各种开源框架、工具类什么的）。代码懒得弄了，这里随便铺一个方法的图。
![自定义SDK内容](http://upload-images.jianshu.io/upload_images/5544786-9bde6a4ee71e4055.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#配置依赖
接着用app来测试SDK，需要在app中配置依赖util，在app的build.gradle里加入依赖语句即可。
```
compile project(':util')
```
然后找个Activity测试一下

![调用](http://upload-images.jianshu.io/upload_images/5544786-546e4d0a25485b65.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![试用](http://upload-images.jianshu.io/upload_images/5544786-b01993886b2d47f9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
输出aar文件，一个简易的SDK制作完成

![输出aar文件](http://upload-images.jianshu.io/upload_images/5544786-7e4672e29341e3a5.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

最后再补充一下，在另一个项目中引用需要在gradle加上
```
repositories {
    flatDir {
        dirs 'libs'
    }
}
```
然后在依赖里加入```compile(name: 'util-release', ext: 'aar')```即可

# 发布到私有库
在SDK的module中的build.gradle添加配置
### Gradle7以上：
```
apply plugin: 'maven-publish'
task generateSourcesJar(type: Jar) {
    from android.sourceSets.main.java.srcDirs
//    classifier "sources"
    archiveClassifier.set('sources')
}
project.afterEvaluate {
    publishing {
        repositories {
            //定义一个 maven 仓库
            maven {
                // 多个仓库时必须指定 name
                // name = "test"
                //允许使用非https加密地址仓库地址
                allowInsecureProtocol = true
                url = "http://172.16.1.1:8081/repository/maven-releases/"
                credentials {
                    username = "admin"
                    password = "admin123"
                }
            }
        }
        publications {PublicationContainer publication->
            // 任务名称（名称可以随便定义）：releaseMaven
            releaseMaven(MavenPublication) {// 容器可配置的信息 MavenPublication
                // 上传source，这样使用放可以看到方法注释
                artifact generateSourcesJar
                // 方式一：生成aar包
                afterEvaluate { artifact(tasks.getByName("bundleReleaseAar")) }
                // 方式二：指定生成的aar路径
                // artifact "$buildDir/outputs/aar/${project.name}-release.aar"
                groupId = 'com.overc_i3'
                artifactId = 'customizesdk'
                version = '1.0.0'
                // pom文件中声明依赖，从而传递到使用方
                pom.withXml {
                    def dependenciesNode = asNode().appendNode('dependencies')
                    configurations.implementation.allDependencies.each {
                        // 避免出现空节点或 artifactId=unspecified 的节点
                        if (it.group != null && (it.name != null && "unspecified" != it.name) && it.version != null) {
                            println it.toString()
                            def dependencyNode = dependenciesNode.appendNode('dependency')
                            dependencyNode.appendNode('groupId', it.group)
                            dependencyNode.appendNode('artifactId', it.name)
                            dependencyNode.appendNode('version', it.version)
                            dependencyNode.appendNode('scope', 'implementation')
                        }
                    }
                }
            }
        }
    }
}
```
需要注意的是使用maven插件时，生成的.pom文件中包括所有的依赖，从而保证使用方能够获取所有依赖。但是maven-publish插件生成的.pom文件并不包含依赖，所以我们通过pom.withXml增加了依赖。

刷新后可以在Android Studio的Gradle工具栏中看到如下配置，直接执行publish即可打包发布
![Gradle工具栏](https://upload-images.jianshu.io/upload_images/5544786-17e1111106f494b9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

- **publish**：依赖于所有publishReleaseMavenPublicationToMavenRepository任务。将所有已定义的发布发布到所有已定义存储库的聚合任务。它不包括将发布复制到本地Maven缓存。
- **publishToMavenLocal**：依赖于所有publishReleaseMavenPublicationToMavenLocal任务。将所有已定义的发布复制到本地Maven缓存，包括它们的元数据(POM文件等)。
- **publishReleaseMavenPublicationToMavenLocal**：将**ReleaseMaven**发布复制到本地Maven缓存—通常是连同发布的POM文件和其他元数据一起。
- **publishReleaseMavenPublicationToMavenRepository**：将名为**ReleaseMaven**的仓库设发布到名为**Maven**的存储库中。如果您有一个没有显式名称的存储库定义，那么**Maven**将是Maven。
- **generatePomFileForReleaseMavenPublication**：为名为**ReleaseMaven**的发布创建一个POM文件，填充已知元数据，如项目名称、项目版本和依赖项。生成的POM文件默认放在 *build/publications/$releaseMaven/pom-default.xml*。

##### 可能遇到问题：
- 若在Android Studio的Gradle工具栏中看不到对应任务，则检查设置是否未开启配置同步，以2022.3.1版本AS为例，确认勾选**Configure all Gradle tasks during Gradle Sync**。
![Android Studio 2022.3.1](https://upload-images.jianshu.io/upload_images/5544786-2178bb473e7d3645.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
- 发布SDK上传的时候可能会遇到如下错误
```
Cannot upload checksum for module-maven-metadata.xml because the remote repository doesn't support SHA-256. This will not fail the build.
Cannot upload checksum for module-maven-metadata.xml because the remote repository doesn't support SHA-512. This will not fail the build.
```
解决办法也简单，在gradle.properties中添加一行配置即可。
```
systemProp.org.gradle.internal.publish.checksums.insecure=true
```

### Gradle7以下：
```
apply plugin: 'maven'
uploadArchives {
    repositories {
        mavenDeployer {
            pom.groupId = "com.overc_i3"
            pom.artifactId = "customizesdk"
            //对应版本号
            pom.version = "1.0.0"
            //仓库地址
            repository(url: "http://172.16.1.1:8081/repository/maven-releases/") {
                //仓库用户名和密码认证
                authentication(userName: "admin", password: "admin123")
            }
        }
    }
}
```
执行gradle中的**uploadArchives**任务即可
