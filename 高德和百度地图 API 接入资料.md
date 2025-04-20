### 高德地图 API 接入相关资料
1. **官方文档链接**：[高德开放平台 | 高德地图 API](https://lbs.amap.com/api/)
2. **Web 服务 API 简介**：向开发者提供 HTTP 接口，可通过这些接口使用各类型的地理数据服务，返回结果支持 JSON 和 XML 格式。使用本组服务之前，需要申请应用 Key。不同类型用户可获取不同的数据访问能力。
3. **接入步骤**：
    - 注册成为高德开发者：点击[该链接](https://console.amap.com/dev/id/select)可注册并完善基本信息。
    - 获取 API Key：进入控制台，创建一个新应用；在创建的应用上点击“添加 Key”按钮，在弹出的对话框中，依次输入应用名称，选择绑定的服务为“Web 服务 API” （也可以选择其它服务），也可以设置域名白名单，只允许特定 IP 的访问；在阅读完高德地图 API 服务条款后，勾选“同意”选项，点击“提交”，完成 Key 的申请。
    - 拼接 HTTP 请求 URL：第一步申请的 key 需作为必填参数一同发送。
    - 接收 HTTP 请求返回的数据（JSON 或 XML 格式），解析数据。如无特殊声明，接口的输入参数和输出数据编码全部统一为 UTF - 8。
4. **调用示例**：在 Apifox 中克隆高德地图 API 项目，点击右上角的“环境变量”，然后选择“正式环境”，并填写环境变量 Key 的值 ，这个 Key 就是高德地图的 API Key。当 Key 有了之后，发起一个“天气查询” 请求，即可看到相关的数据信息返回。
5. **JS API 使用方式**：
    - **JS API Loader**：
        - 使用 script 标签加载 loader：
```html
<script src="https://webapi.amap.com/loader.js"></script>
<script type="text/javascript">
  window._AMapSecurityConfig = {
    securityJsCode: "「你申请的安全密钥」",
  };
  AMapLoader.load({
    key: "替换为你申请的 key", //申请好的 Web 端开发 Key，首次调用 load 时必填
    version: "2.0", //指定要加载的 JS API 的版本，缺省时默认为 1.4.15
    plugins: ["AMap.Scale"], //需要使用的的插件列表，如比例尺'AMap.Scale'，支持添加多个如：['AMap.Scale','...','...']
    AMapUI: {
      //是否加载 AMapUI，缺省不加载
      version: "1.1", //AMapUI 版本
      plugins: ["overlay/SimpleMarker"], //需要加载的 AMapUI ui 插件
    },
    Loca: {
      //是否加载 Loca， 缺省不加载
      version: "2.0", //Loca 版本
    },
  })
    .then((AMap) => {
      var map = new AMap.Map("container"); //"container"为 <div> 容器的 id
      map.addControl(new AMap.Scale()); //添加比例尺组件到地图实例上
    })
    .catch((e) => {
      console.error(e); //加载错误提示
    });
</script>
```
        - NPM 安装 loader：
```bash
npm i @amap/amap-jsapi-loader --save
```
```javascript
import AMapLoader from "@amap/amap-jsapi-loader";
window._AMapSecurityConfig = {
  securityJsCode: "「你申请的安全密钥」",
};
AMapLoader.load({
  key: "替换为你申请的 key", //申请好的 Web 端开发者 Key，首次调用 load 时必填
  version: "2.0", //指定要加载的 JS API 的版本，缺省时默认为 1.4.15
  plugins: ["AMap.Scale"], //需要使用的的插件列表，如比例尺'AMap.Scale'，支持添加多个如：['AMap.Scale','...','...']
})
  .then((AMap) => {
    var map = new AMap.Map("container"); //"container"为 <div> 容器的 id
  })
  .catch((e) => {
    console.log(e);
  });
```
    - **script 标签加载 JS API 脚本**：
        - 同步加载：
```html
<!-- 需要设置元素的宽高样式 -->
<div id="container"></div>
<script type="text/javascript">
  window._AMapSecurityConfig = {
    securityJsCode: "「你申请的安全密钥」",
  };
</script>
<script
  type="text/javascript"
  src="https://webapi.amap.com/maps?v=2.0&key=你申请的key值"
></script>
<script type="text/javascript">
  //地图初始化应该在地图容器 <div> 已经添加到 DOM 树之后
  var map = new AMap.Map("container", {
    zoom: 12,
  });
</script>
```
        - 异步加载：
```html
<script>
  //设置你的安全密钥
  window._AMapSecurityConfig = {
    securityJsCode: "「你申请的安全密钥」",
  };
  var script = document.createElement('script');
  script.src = 'https://webapi.amap.com/maps?v=2.0&key=你申请的key值';
  script.onload = function() {
    var map = new AMap.Map("container", {
      zoom: 12,
    });
  };
  document.head.appendChild(script);
</script>
```

### 百度地图 API 接入相关资料
1. **官方文档链接**：[百度地图 API - 首页](https://developer.baidu.com/map/index.html)
2. **功能介绍**：
    - **Web 开发**：使用 JavaScript 编写，适用网页中嵌入地图服务，支持 2D/3D、卫星、街景、室内实景及定制化地图展现。
    - **服务接口**：发送 HTTP 请求，获取数据存储和检索、POI 数据、地址解析、坐标转换、IP 定位、车联网等服务。
    - **Android 开发**：适用 Android 平台 APP 中实现地图展示、信息搜索、定位、导航等功能，提供全面的 LBS 解决方案。
    - **iOS 开发**：适用 iOS 移动设备应用中实现地图展示、POI 搜索、导航等功能，帮用户轻松搭建专业的 LBS 类应用。
3. **接入步骤（以 JavaScript API 为例）**：
    - 版本说明及申请 API ak：JavaScript API v1.4 及以前版本无须申请密钥（ak），自 v1.5 版本开始需要先申请密钥（ak） ，才可使用，如需获取更高配额，请点击申请认证企业用户。申请地址：[http://api.map.baidu.com/api?v=1.4](http://api.map.baidu.com/api?v=1.4) （使用 JavaScript API v1.4 及以前版本可使用此方式引用）；[http://api.map.baidu.com/api?v=2.0&ak=您的密钥](http://api.map.baidu.com/api?v=2.0&ak=您的密钥) （使用 JavaScript API v2.0 请先申请密钥 ak，按此方式引用）。
    - 获取 JavaScript API 服务方法：自 JS API v1.5 之后，最新版本为 2.0，需要首先申请密钥（ak） ，才可成功加载 API JS 文件。ak 的使用方法如下：
```html
<script src="http://api.map.baidu.com/api?v=2.0&ak=您的密钥" type="text/javascript"></script>
```
其中参数 v 为 API 当前的版本号，目前最新版本为 2.0。在 1.2 版本之前还可以设置 services 参数，以告知 API 是否加载服务部分，true 表示加载，false 表示不加载，默认为 true。
4. **调用示例（创建地图）**：
```html
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF - 8">
  <meta name="viewport" content="initial - scale = 1.0, user - scalable = no">
  <title>百度地图示例</title>
  <script src="http://api.map.baidu.com/api?v=2.0&ak=您的密钥" type="text/javascript"></script>
</head>
<body>
  <div id="container" style="width: 100%; height: 500px;"></div>
  <script type="text/javascript">
    // 创建地图实例
    var map = new BMap.Map("container");
    // 设置地图中心点
    var point = new BMap.Point(116.404, 39.915);
    // 初始化地图，设置中心点和缩放级别
    map.centerAndZoom(point, 15);
  </script>
</body>
</html>
```