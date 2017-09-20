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
