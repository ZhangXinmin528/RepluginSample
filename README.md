# RepluginSample
------------------------------------------------------------------------
<p align="center">
  <a href="https://github.com/Qihoo360/RePlugin/wiki">
    <img alt="RePlugin Logo" src="https://github.com/Qihoo360/RePlugin/wiki/img/RePlugin.png" width="400"/>
  </a>
</p>

项目描述
-------
>1.本项目是使用Qihoo360/RePlugin进行插件化开发的示例项目，[点击查看框架地址](https://github.com/Qihoo360/RePlugin)

>2.本项目是依据360Replugin的官方Simple改造而成，通过该项目能够了解360Replugin框架的原理和基本的使用方法[请点击这里查看Sample源代码](https://github.com/Qihoo360/RePlugin/blob/master/replugin-sample)。

项目结构
-------
>app:项目的主工程，在插件化开发中称之为“宿主”，可单独编译运行。

>app.one:(内置)插件工程，可单独编译运行。

>app.two:(内置)插件工程，可单独编译运行。

>app.web:(内置)插件工程，可单独编译运行。

>lib.appone:Android Library,主要测试aar文件的生成。

>lib.common:Android Library,主要用于测试jar的生成和copy功能。

注意事项
-------
>在开发过程中总结了使用插件化的常见问题，提供大家一个解决思路。[详情查阅文档](https://github.com/ZhangXinmin528/RepluginSample/blob/master/README_NOTICE.md)