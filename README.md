# 旅行时间计划安卓应用 - 开发文档

## 项目概述

旅行时间计划安卓应用是一款帮助用户管理火车或汽车旅行计划的工具，具有以下主要功能：

1. 车票信息手动录入（发车时间、停车时间、车站位置）
2. 自动获取用户当前位置并计算到车站的交通时间
3. 多重闹钟提醒系统（出发闹钟、上车闹钟、下车闹钟）
4. 基于位置的状态提示（距离车站远近的不同提示）
5. 高度自定义功能（地图API、空余时间、交通方式、颜色、提示内容等）

应用采用现代UI设计和莫奈颜色系统，使用Kotlin语言开发，支持GitHub Actions自动构建。

## 技术架构

应用采用MVVM（Model-View-ViewModel）架构模式，结合Android Jetpack组件，主要包括以下层次：

1. **UI层（View）**：负责界面显示和用户交互
2. **业务逻辑层（ViewModel）**：处理业务逻辑，连接UI和数据层
3. **数据层（Model）**：管理数据访问和存储
4. **服务层**：提供位置、地图、闹钟等核心服务

### 主要技术栈

- Kotlin：主要编程语言
- Android Jetpack：提供现代Android开发组件
- Room：本地SQLite数据库抽象层
- DataStore：用户偏好设置存储
- Hilt：依赖注入
- Coroutines & Flow：异步操作和响应式编程
- Material Design 3：现代UI和莫奈颜色系统
- 高德/百度地图SDK：地图显示和路线计算
- FusedLocationProvider：位置服务
- AlarmManager：闹钟管理

## 项目结构

```
com.example.travelplanapp/
├── TravelPlanApplication.kt          # 应用程序类
├── alarm/                            # 闹钟相关
│   ├── AlarmReceiver.kt              # 闹钟接收器
│   ├── AlarmService.kt               # 闹钟服务
│   └── BootReceiver.kt               # 设备重启接收器
├── data/                             # 数据层
│   ├── dao/                          # 数据访问对象
│   │   └── TicketInfoDao.kt          # 车票信息DAO
│   ├── database/                     # 数据库
│   │   └── AppDatabase.kt            # 应用数据库
│   ├── model/                        # 数据模型
│   │   └── TicketInfo.kt             # 车票信息模型
│   ├── preferences/                  # 用户偏好设置
│   │   └── UserPreferencesManager.kt # 用户偏好管理器
│   └── repository/                   # 数据仓库
│       └── TicketRepository.kt       # 车票信息仓库
├── di/                               # 依赖注入
│   └── AppModule.kt                  # 应用依赖注入模块
├── location/                         # 位置服务
│   └── LocationService.kt            # 位置服务实现
├── map/                              # 地图服务
│   ├── AMapService.kt                # 高德地图服务实现
│   ├── MapService.kt                 # 地图服务接口
│   └── RouteModels.kt                # 路线相关数据模型
├── ui/                               # 用户界面
│   ├── main/                         # 主界面
│   │   └── MainActivity.kt           # 主活动
│   ├── map/                          # 地图界面
│   │   └── MapFragment.kt            # 地图Fragment
│   ├── settings/                     # 设置界面
│   │   └── SettingsViewModel.kt      # 设置ViewModel
│   └── ticket/                       # 车票界面
│       └── TicketViewModel.kt        # 车票ViewModel
└── util/                             # 工具类
    ├── DateConverter.kt              # 日期转换器
    └── PermissionUtil.kt             # 权限工具类
```

## 功能模块详解

### 1. 车票信息管理

- **数据模型**：TicketInfo类存储发车时间、停车时间、车站位置等信息
- **数据访问**：Room数据库提供本地存储，TicketRepository提供统一数据访问接口
- **业务逻辑**：TicketViewModel处理车票信息的CRUD操作和业务规则

### 2. 位置服务

- **位置获取**：使用FusedLocationProvider获取用户当前位置
- **权限管理**：动态申请位置权限，提供权限说明对话框
- **距离计算**：计算用户当前位置到车站的直线距离

### 3. 地图服务

- **地图接口**：MapService定义通用地图服务接口，支持切换不同地图提供商
- **路线计算**：支持步行、驾车、公交、骑行等多种交通方式的路线和用时计算
- **高德实现**：AMapService提供基于高德地图SDK的具体实现

### 4. 闹钟系统

- **闹钟设置**：AlarmService负责设置出发、上车、下车等多种闹钟
- **闹钟触发**：AlarmReceiver接收闹钟触发广播，显示通知并执行相应操作
- **状态提示**：根据用户位置和车站距离，提供不同颜色和内容的状态提示

### 5. 自定义设置

- **偏好存储**：UserPreferencesManager使用DataStore存储和管理用户自定义设置
- **设置项目**：包括地图API选择、空余时间、交通方式、距离阈值、状态颜色、提示内容等
- **设置界面**：SettingsViewModel处理设置的业务逻辑，连接UI和数据层

### 6. CI/CD自动构建

- **GitHub Actions**：配置自动构建、测试、签名和发布APK
- **工作流程**：push到main分支时自动触发构建和发布流程
- **构建产物**：生成debug和release版本的APK，并发布到GitHub Releases

## 使用说明

### 安装和权限

1. 安装应用后首次启动会自动申请位置和通知权限
2. 必须授予位置权限才能使用核心功能
3. 建议授予通知权限以接收闹钟提醒

### 车票信息录入

1. 点击"添加"按钮创建新车票
2. 输入发车时间、停车时间和车站位置
3. 保存后系统会自动计算并设置相关闹钟

### 地图和路线

1. 在地图界面可查看当前位置到车站的路线
2. 选择不同交通方式可查看对应的路线和用时
3. 系统会根据选择的交通方式和用时自动调整闹钟时间

### 闹钟和提醒

1. 出发闹钟：发车时间减去交通用时减去空余时间
2. 上车闹钟：发车时间减去30分钟
3. 下车闹钟：到站时间减去30分钟
4. 闹钟触发时会显示通知并震动

### 自定义设置

1. 在设置界面可自定义各项功能和UI元素
2. 可选择高德或百度地图API
3. 可调整空余时间、距离阈值等参数
4. 可自定义状态颜色、提示内容、闹钟铃声等

## 开发者指南

### 环境要求

- Android Studio Arctic Fox或更高版本
- JDK 17
- Gradle 7.4.2或更高版本
- Android SDK 34（targetSdk）
- Android SDK 24（minSdk）

### 构建说明

1. 克隆项目仓库
2. 在Android Studio中打开项目
3. 在`AndroidManifest.xml`中替换高德/百度地图API密钥
4. 使用Gradle构建项目

### 自动构建

项目配置了GitHub Actions自动构建流程：

1. 推送到main分支会自动触发构建
2. 构建成功后会生成debug和release版本的APK
3. release版本会自动签名并发布到GitHub Releases

### 扩展指南

1. **添加新地图提供商**：
   - 实现MapService接口
   - 在AppModule中提供新的实现

2. **自定义UI主题**：
   - 修改colors.xml和themes.xml
   - 遵循Material Design 3规范

3. **添加新功能**：
   - 遵循MVVM架构模式
   - 使用依赖注入管理依赖关系
   - 确保代码有完整的中文注释

## 总结

旅行时间计划安卓应用是一款功能完善、高度可定制的旅行助手，帮助用户管理火车或汽车旅行计划。应用采用现代架构和技术栈，具有良好的可维护性和可扩展性。通过多重闹钟和基于位置的状态提示，用户可以更好地掌握旅行时间，避免误车或赶车压力。
