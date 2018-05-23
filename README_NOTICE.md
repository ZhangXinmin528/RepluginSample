# Replugin开发常见问题
------------------------------------------------------------------------
>1.使用Activity加载Fragment的问题：

* **ClassCastExcetion**:宿主和插件使用v4包的fragment的情况下，在宿主中加载Fragment会导致此类异常。解决方法参见官方FAQ[使用Fragment常见问题汇总](https://github.com/Qihoo360/RePlugin/wiki/FAQ)。个人建议使用app包下的Fragment。
* **Activity加载Fragment，但不在一个App情况下,Fragment所在App无法获取Context**：这种情况下一般需要在当前App的Activity的onCreate（）方法下调用一下对应插件的RePlugin.fetchContext("appmain")方法，相当于初始化吧（个人理解）。