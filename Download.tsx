import React from 'react';
import Navbar from '../components/layout/Navbar';
import Footer from '../components/layout/Footer';
import Button from '../components/ui/Button';

const Download = () => {
  return (
    <div className="download-page">
      <Navbar />
      
      {/* Hero Section */}
      <section className="py-16 bg-gradient-to-r from-blue-600 to-indigo-700 text-white">
        <div className="container mx-auto px-4 text-center">
          <h1 className="text-4xl md:text-5xl font-bold mb-6">下载旅行时间计划应用</h1>
          <p className="text-xl mb-8 max-w-3xl mx-auto">获取最新版本，体验智能旅行管理、历史行程统计、离线地图和多语言支持等功能</p>
        </div>
      </section>
      
      {/* Download Section */}
      <section className="py-16">
        <div className="container mx-auto px-4">
          <div className="max-w-4xl mx-auto">
            <div className="bg-white p-8 rounded-lg shadow-lg border border-gray-200">
              <h2 className="text-2xl font-bold mb-6 text-center">最新版本</h2>
              
              <div className="flex flex-col md:flex-row items-center justify-between mb-8 p-6 bg-gray-50 rounded-lg">
                <div>
                  <h3 className="text-xl font-semibold mb-2">旅行时间计划 v1.0.0</h3>
                  <p className="text-gray-600 mb-4">发布日期: 2025-05-18</p>
                  <ul className="text-gray-600 list-disc list-inside mb-4">
                    <li>新增历史行程记录与统计分析</li>
                    <li>新增离线地图功能</li>
                    <li>新增多语言支持（中文/英文）</li>
                    <li>优化用户界面和交互体验</li>
                  </ul>
                </div>
                <div className="mt-4 md:mt-0">
                  <a href="/download/TravelPlanApp.apk" download>
                    <Button variant="primary" size="large">
                      下载 APK
                    </Button>
                  </a>
                </div>
              </div>
              
              <div className="bg-yellow-50 border-l-4 border-yellow-400 p-4 mb-8">
                <div className="flex">
                  <div className="flex-shrink-0">
                    <svg className="h-5 w-5 text-yellow-400" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                      <path fillRule="evenodd" d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z" clipRule="evenodd" />
                    </svg>
                  </div>
                  <div className="ml-3">
                    <p className="text-sm text-yellow-700">
                      安装提示：下载APK后，您需要在设备上允许"未知来源"应用安装。
                    </p>
                  </div>
                </div>
              </div>
              
              <h3 className="text-xl font-semibold mb-4">系统要求</h3>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
                <div className="bg-gray-50 p-4 rounded-lg">
                  <h4 className="font-semibold mb-2">Android</h4>
                  <ul className="text-gray-600 list-disc list-inside">
                    <li>Android 8.0 或更高版本</li>
                    <li>至少 100MB 可用存储空间</li>
                    <li>GPS 和网络连接</li>
                    <li>建议：2GB 或更多内存</li>
                  </ul>
                </div>
                <div className="bg-gray-50 p-4 rounded-lg">
                  <h4 className="font-semibold mb-2">权限</h4>
                  <ul className="text-gray-600 list-disc list-inside">
                    <li>位置信息 - 用于计算到车站的路线</li>
                    <li>存储 - 用于保存离线地图数据</li>
                    <li>网络 - 用于地图和路线计算</li>
                    <li>通知 - 用于闹钟和提醒功能</li>
                  </ul>
                </div>
              </div>
              
              <h3 className="text-xl font-semibold mb-4">安装指南</h3>
              <ol className="text-gray-600 list-decimal list-inside mb-8">
                <li className="mb-2">下载APK文件到您的Android设备</li>
                <li className="mb-2">打开文件管理器，找到并点击下载的APK文件</li>
                <li className="mb-2">如果提示，请允许来自此来源的应用安装</li>
                <li className="mb-2">点击"安装"并等待安装完成</li>
                <li>点击"打开"启动应用</li>
              </ol>
              
              <div className="text-center">
                <a href="/download/TravelPlanApp.apk" download>
                  <Button variant="primary" size="large">
                    立即下载
                  </Button>
                </a>
              </div>
            </div>
          </div>
        </div>
      </section>
      
      {/* FAQ Section */}
      <section className="py-16 bg-gray-50">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center mb-12">常见问题</h2>
          <div className="max-w-3xl mx-auto">
            <div className="mb-6">
              <h3 className="text-xl font-semibold mb-2">如何设置地图API密钥？</h3>
              <p className="text-gray-600">在应用的"设置"页面中，选择"地图设置"，然后输入您的高德或百度地图API密钥。</p>
            </div>
            <div className="mb-6">
              <h3 className="text-xl font-semibold mb-2">如何切换语言？</h3>
              <p className="text-gray-600">在应用的"设置"页面中，选择"语言设置"，然后选择您偏好的语言（中文、英文或跟随系统）。</p>
            </div>
            <div className="mb-6">
              <h3 className="text-xl font-semibold mb-2">离线地图数据会占用多少存储空间？</h3>
              <p className="text-gray-600">这取决于您下载的区域大小。一般城市区域约占用10-50MB存储空间。您可以在应用中管理和删除不需要的离线地图数据。</p>
            </div>
            <div className="mb-6">
              <h3 className="text-xl font-semibold mb-2">应用是否支持自动更新？</h3>
              <p className="text-gray-600">目前不支持自动更新。请定期访问我们的网站获取最新版本。</p>
            </div>
            <div>
              <h3 className="text-xl font-semibold mb-2">如何查看历史行程统计？</h3>
              <p className="text-gray-600">在应用主界面中，点击"历史"选项卡，您可以查看所有历史行程记录和统计分析数据。</p>
            </div>
          </div>
        </div>
      </section>
      
      <Footer />
    </div>
  );
};

export default Download;
