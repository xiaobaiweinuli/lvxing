# 旅行时间计划应用架构设计

## 整体架构

应用将采用MVVM（Model-View-ViewModel）架构模式，结合Android Jetpack组件，以确保代码的可维护性、可测试性和可扩展性。

### 主要模块划分

1. **UI层（View）**
   - 主界面（MainActivity）：应用入口，提供导航到其他功能模块
   - 车票信息录入界面（TicketInputFragment）：用于手动输入发车时间、停车时间、车站位置
   - 地图和路线界面（MapFragment）：显示地图、路线和交通方式选择
   - 闹钟管理界面（AlarmFragment）：管理出发、上车、下车闹钟
   - 设置界面（SettingsFragment）：用户自定义各项功能和UI元素

2. **业务逻辑层（ViewModel）**
   - 车票信息ViewModel：管理车票信息的输入和存储
   - 地图ViewModel：处理地图API调用、路线计算和位置更新
   - 闹钟ViewModel：管理闹钟设置、触发和状态更新
   - 设置ViewModel：处理用户自定义设置的保存和应用

3. **数据层（Model）**
   - 数据实体：TicketInfo（车票信息）、LocationInfo（位置信息）、AlarmInfo（闹钟信息）、UserPreferences（用户设置）
   - 本地数据源：使用Room数据库存储车票信息和用户设置
   - 远程数据源：地图API服务接口（高德/百度）
   - 仓库：统一管理数据访问，提供单一数据源

4. **服务层**
   - 位置服务：获取用户当前位置，计算位置间距离
   - 闹钟服务：管理系统闹钟，处理闹钟触发事件
   - 通知服务：显示提醒通知，处理通知交互

## 数据流

1. **车票信息流**
   - 用户输入 → TicketInputFragment → TicketViewModel → TicketRepository → Room数据库
   - Room数据库 → TicketRepository → ViewModel → UI更新

2. **位置和地图数据流**
   - 位置服务 → LocationRepository → MapViewModel → MapFragment
   - 用户选择 → MapFragment → MapViewModel → 地图API → 路线和用时计算 → UI更新

3. **闹钟和提醒流**
   - 车票信息 + 路线用时 → AlarmViewModel → 闹钟计算 → AlarmRepository → 系统闹钟服务
   - 闹钟触发 → AlarmReceiver → 通知服务 → 用户提醒
   - 位置变化 → 位置服务 → 距离计算 → 状态更新 → 通知更新

4. **设置和自定义流**
   - 用户设置 → SettingsFragment → SettingsViewModel → PreferencesRepository → SharedPreferences
   - SharedPreferences → PreferencesRepository → 各ViewModel → UI更新

## 技术栈和依赖库

1. **核心框架**
   - Kotlin：主要编程语言
   - Android Jetpack：提供现代Android开发组件
   - Kotlin Coroutines：处理异步操作
   - Kotlin Flow：响应式数据流

2. **UI组件**
   - Material Design 3：实现现代UI和莫奈颜色系统
   - Navigation Component：处理界面导航
   - ViewBinding：类型安全的视图访问
   - DataBinding：声明式UI绑定

3. **数据存储**
   - Room：本地SQLite数据库抽象层
   - DataStore：替代SharedPreferences的现代数据存储方案

4. **位置和地图**
   - FusedLocationProvider：获取用户位置
   - 高德/百度地图SDK：地图显示和路线计算

5. **闹钟和通知**
   - AlarmManager：系统闹钟管理
   - NotificationManager：通知管理
   - WorkManager：后台任务调度

6. **依赖注入**
   - Hilt：简化依赖注入实现

7. **测试**
   - JUnit：单元测试
   - Espresso：UI测试

## 权限需求

- ACCESS_FINE_LOCATION：获取精确位置
- ACCESS_COARSE_LOCATION：获取大致位置
- INTERNET：访问网络和地图API
- VIBRATE：控制震动
- RECEIVE_BOOT_COMPLETED：设备重启后恢复闹钟
- SCHEDULE_EXACT_ALARM：设置精确闹钟
- POST_NOTIFICATIONS：发送通知

## 自定义功能实现方案

应用将通过PreferencesRepository统一管理用户自定义设置，包括：

1. 地图API选择：通过设置界面切换不同地图提供商
2. 空余时间设置：可调整默认45分钟的预留时间
3. 交通方式偏好：保存用户常用交通方式
4. 距离阈值：可调整1千米的默认阈值
5. 活动状态颜色：通过颜色选择器自定义各状态颜色
6. 提示内容：允许用户自定义各状态的提示文本
7. 闹钟设置：自定义铃声、震动强度和模式
8. UI自定义：通过拖拽、调色板和形状选择器实现UI元素自定义

## GitHub Actions自动构建

将配置GitHub Actions工作流，实现：

1. 代码检查和测试
2. APK构建
3. 自动发布到Release
4. 构建状态通知

工作流将使用标准Android Gradle构建工具，不依赖外部服务，确保CI/CD流程的稳定性和可靠性。
