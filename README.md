## 前言

此项目是根据自己对 MVVM 的理解，结合 Google 官方 JetPack 组件与一些优秀的第三方库，
整理出来的 Android 开发模板。

项目本身代码不多，包含一些固定的基础库和用于演示的 Demo 代码，在使用这套框架前，需要您熟悉以下知识点：

* [什么是 MVVM ？](https://baike.baidu.com/item/MVVM/96310?fr=aladdin)
* [Android Jetpack 组件基础知识](https://developer.android.google.cn/jetpack)
* [RxJava 基础知识](https://github.com/ReactiveX/RxJava)
* 项目中使用到的其它第三方组件基本用法

## 框架思想

框架整体思想与 Android 官方的[**应用架构指南**](https://developer.android.google.cn/jetpack/docs/guide)基本相同，基础结构如下图：

![框架结构图](https://developer.android.google.cn/topic/libraries/architecture/images/final-architecture.png)

与[**应用架构指南**](https://developer.android.google.cn/jetpack/docs/guide)不同点有：

* 没有使用 Dagger 2 来做依赖注入（使用后耦合性会更低，但上手难度会更大）
* 没有要求 ViewModel 只能使用 Repository 获取数据
* 使用了 RxJava 2 但没有使用 [LiveDataReactiveStreams](https://developer.android.google.cn/reference/android/arch/lifecycle/LiveDataReactiveStreams)
处理 RxJava 的生命周期，而是通过 BaseViewModel 来管理，如果有需要可以自行引入。
* 使用了 ARouter 作为导航图，解耦 module 间的页面跳转

## 项目结构规则

在本项目中包含四个 module，其中 **base** 与 **common** 是基础 module，**app_demo**
和 **simple_demo** 是演示项目。

* base 作为项目整体基础库，只包含通用性极强的基础类，不应该包含单独某一个项目需要用到的类。
* common 是对 base 的补充，也是某一个项目的公用库，所有该项目公用的或者需要对 base 进一步封装的代码放到该 module
* app_demo 是 app module 的演示项目
* simple_demo 是 libary module 的演示项目

## base 主要组件介绍

看过[**应用架构指南**](https://developer.android.google.cn/jetpack/docs/guide)这篇文章后，大家应该对
**ViewModel**、**LiveData**、**Repository** 这几个基本组件有一定了解。项目中的组件也是围绕这些组件和网络请求库进行封装。

### BaseApp

Application 基类，实现以下功能：

* RxJava 异常全局捕获
* 初始化和管理 `RetrofitManager` 对象，默认对协议提供 JSON 解析方式。
* 初始化和管理 `NetworkStatusEvent` 对象

### BaseViewModel

ViewModel 基类，结合 View 基类实现了以下功能：

* 结合 `BaseViewModel.ViewModelObserver` 统一管理 RxJava 的 Disposable
* 结合 `BaseViewModel.ViewModelObserver` 向 View 自动分发异步请求状态
* 封装消息 `LiveData`

**BasePageViewModel**

* 结合 `BasePageActivity`、`BasePageFragment`、`BasePageDialogFragment` 实现分页逻辑，子类只需要很少量的代码就可以完成自动分页加载

### BaseActivity、BaseFragment、BaseDialogFragment

View 基类，结合 `BaseViewModel` 实现以下功能：

* 规范 View 初始化流程 `initStatusBar()` -> `setupLayout()` -> `setupView()` -> `setupViewModel()`
* 通过 `getStatusBarColor()` 和 `isDarkStatusBarText()` 方法状态栏颜色
* 结合 `NetworkStatusEvent` 监听网络状态改变，并触发 BaseViewModel 刷新方法
* 监听 `BaseViewModel` 加载状态，显示默认的消息提示与进度弹窗

### RetrofitManager

Http 请求 Service 管理类，在 Application 以单例形式存在，用于对 Service 接口初始化和实例管理。

> 通过 `XMLConverterFactory` 可以实现对 xml 协议的解析和生成

### BaseRepository

基础 Repository 抽象类，定义 Repository 需要实现和提供的方法

**FileRepository**

使用JSON文件形式作为数据持久化的 Repository，适用于数据量小，增量更新的数据。

**RoomRepository**

使用Room数据库作为数据持久化的 Repository，适用于数据量大，分页请求的数据。

## common 主要组件介绍

common module 虽然会因为项目不同而发生变化，但也有一些通用或相似的地方。

### LoginUserEvent

LoginUserEvent 主要用于对登录用户的缓存管理与全局事件分发，实现以下功能：

* 对用户登录、登出、登录失效事件全局分发
* 使用 SharePreferences 对用户做本地存储，下一次打开应用自动加载

其中 `BaseUserActivity`、`BaseUserFragment`、`BaseUserDialogFragment` 自动注册到了 `LoginUserEvent`，子类重写 `onLoginUserChanged` 即可观察登录用户的变化。

### CommonApp

项目实际要使用到的 Application 类，初始化项目需要用到的三方库，以及管理项目用到的单例类。

### RouteMap

管理各个模块的路由表，如果页面之间有参数传递，那么参数的 key 也再此类定义。

### util 包

存放公用的工具类

