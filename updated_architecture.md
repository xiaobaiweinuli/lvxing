# 旅行时间计划应用架构更新文档

## 新增功能架构设计

### 1. 地图API密钥输入与路线计算逻辑

#### 架构变更
- 在`UserPreferencesManager`中添加地图API密钥存储功能
- 在设置界面添加API密钥输入选项
- 修改`MapService`接口，支持用户自定义API密钥
- 增强`RouteCalculator`组件，支持多种交通方式计算并选择最长用时

#### 数据流
```
用户输入API密钥 -> UserPreferencesManager存储 -> MapService初始化时读取 -> 路线计算时使用
```

#### 关键类修改
- `UserPreferencesManager`: 添加API密钥存储方法
- `SettingsViewModel`: 添加API密钥设置逻辑
- `AMapService`/`BMapService`: 修改初始化方法，支持动态API密钥
- `RouteCalculator`: 增强多交通方式计算，实现最长用时选择逻辑

### 2. 历史行程记录和统计分析

#### 架构变更
- 新增`TripHistory`数据模型
- 新增`TripHistoryDao`和相关数据库操作
- 新增`TripHistoryRepository`处理历史数据业务逻辑
- 新增`TripHistoryViewModel`和相关UI界面
- 新增统计分析功能模块

#### 数据流
```
行程完成 -> 保存到TripHistory -> 历史记录展示 -> 统计分析生成
```

#### 关键类新增
- `TripHistory`: 历史行程数据模型
- `TripHistoryDao`: 数据库访问对象
- `TripHistoryRepository`: 业务逻辑层
- `TripHistoryViewModel`: 视图模型
- `TripHistoryFragment`: 历史记录UI
- `TripStatisticsFragment`: 统计分析UI
- `StatisticsGenerator`: 统计数据生成工具类

### 3. 离线地图功能优化

#### 架构变更
- 新增离线地图管理模块
- 修改地图服务接口，支持离线/在线模式切换
- 新增离线地图数据下载和管理功能
- 新增离线路线计算功能

#### 数据流
```
用户下载离线地图 -> 存储到本地 -> 检测网络状态 -> 自动切换离线/在线模式 -> 路线计算
```

#### 关键类新增/修改
- `OfflineMapManager`: 离线地图管理类
- `OfflineMapDownloader`: 地图数据下载工具
- `MapService`: 接口增加离线模式支持
- `AMapService`/`BMapService`: 实现离线模式
- `NetworkStateMonitor`: 网络状态监测
- `OfflineRouteCalculator`: 离线路线计算实现

### 4. 多语言支持

#### 架构变更
- 实现本地化资源文件结构
- 新增语言选择设置
- 修改UI组件，支持动态语言切换

#### 数据流
```
用户选择语言 -> 保存语言设置 -> 应用重启或动态加载资源 -> UI显示对应语言
```

#### 关键类新增/修改
- `LocaleHelper`: 语言切换辅助类
- `UserPreferencesManager`: 添加语言设置存储
- `SettingsViewModel`: 添加语言设置逻辑
- 资源文件: 新增多语言字符串资源

## 自动化构建与发布流程

### GitHub Actions工作流设计

#### 触发条件
- 推送到main分支
- 创建新的tag/release

#### 工作流程
1. 检出代码
2. 设置JDK环境
3. 缓存Gradle依赖
4. 运行单元测试
5. 构建Debug和Release APK
6. 签名Release APK
7. 发布到GitHub Releases
8. 更新网站内容和下载链接

#### 关键文件
- `.github/workflows/android.yml`: 主CI/CD工作流配置
- `.github/workflows/website-update.yml`: 网站更新工作流配置

### 网站更新与下载集成

#### 架构变更
- 修改网站下载页面，集成GitHub Releases API
- 添加自动版本检测和下载链接更新
- 增加新功能展示内容

#### 数据流
```
GitHub Actions构建APK -> 发布到Releases -> 网站检测新版本 -> 更新下载链接
```

#### 关键文件修改
- `Download.tsx`: 更新下载链接逻辑
- `Features.tsx`: 添加新功能展示
- `website-update.js`: 自动更新脚本

## 数据库架构更新

### 新增表结构

#### TripHistory表
```sql
CREATE TABLE trip_history (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    ticket_id INTEGER NOT NULL,
    start_time INTEGER NOT NULL,
    end_time INTEGER NOT NULL,
    departure_station TEXT NOT NULL,
    arrival_station TEXT NOT NULL,
    travel_mode TEXT NOT NULL,
    estimated_duration INTEGER NOT NULL,
    actual_duration INTEGER,
    on_time BOOLEAN,
    notes TEXT,
    FOREIGN KEY (ticket_id) REFERENCES ticket_info(id) ON DELETE CASCADE
);
```

#### OfflineMap表
```sql
CREATE TABLE offline_map (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    region_name TEXT NOT NULL,
    download_time INTEGER NOT NULL,
    file_size INTEGER NOT NULL,
    file_path TEXT NOT NULL,
    version TEXT NOT NULL,
    last_used INTEGER
);
```

## 权限更新

### 新增权限
```xml
<!-- 存储权限 - 用于离线地图 -->
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

<!-- 网络状态权限 - 用于检测网络变化 -->
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 依赖更新

### 新增依赖
```gradle
// 多语言支持
implementation 'androidx.appcompat:appcompat:1.6.1'

// 统计图表
implementation 'com.github.PhilJay:MPAndroidChart:v3.1.0'

// 离线地图相关
implementation 'com.squareup.okhttp3:okhttp:4.10.0'
implementation 'com.squareup.okio:okio:3.0.0'

// 后台任务 - 用于地图下载
implementation 'androidx.work:work-runtime-ktx:2.8.1'
```

## 总结

本架构更新文档详细描述了旅行时间计划应用的功能增强和自动化流程的设计方案。通过这些更新，应用将支持用户自定义地图API密钥、多种交通方式的最长用时计算、历史行程记录与统计分析、离线地图功能以及多语言支持。同时，引入GitHub Actions自动化构建和发布流程，实现代码推送到main分支后的自动构建、测试、签名和发布，并自动更新网站内容和下载链接。
