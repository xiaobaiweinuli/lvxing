import React from 'react';

const Guide: React.FC = () => {
  return (
    <div className="bg-white dark:bg-gray-900">
      {/* 使用指南头部 */}
      <div className="max-w-7xl mx-auto py-16 px-4 sm:py-24 sm:px-6 lg:px-8">
        <div className="text-center">
          <h1 className="text-3xl font-extrabold text-gray-900 dark:text-white sm:text-4xl">
            使用指南
          </h1>
          <p className="mt-4 max-w-2xl mx-auto text-xl text-gray-500 dark:text-gray-300">
            详细了解如何安装和使用旅行时间计划应用，充分利用其所有功能。
          </p>
        </div>
      </div>

      {/* 安装说明 */}
      <div className="max-w-7xl mx-auto pb-16 px-4 sm:pb-24 sm:px-6 lg:px-8">
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">安装说明</h2>
          <div className="bg-white dark:bg-gray-800 shadow rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
                <div className="flex flex-col items-center text-center">
                  <div className="h-16 w-16 flex items-center justify-center rounded-full bg-primary-50 dark:bg-primary-900 text-primary mb-4">
                    <svg className="h-8 w-8" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
                    </svg>
                  </div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-2">下载应用</h3>
                  <p className="text-gray-500 dark:text-gray-300">
                    从官方网站或应用商店下载最新版本的旅行时间计划应用。
                  </p>
                </div>
                <div className="flex flex-col items-center text-center">
                  <div className="h-16 w-16 flex items-center justify-center rounded-full bg-primary-50 dark:bg-primary-900 text-primary mb-4">
                    <svg className="h-8 w-8" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
                    </svg>
                  </div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-2">安装应用</h3>
                  <p className="text-gray-500 dark:text-gray-300">
                    点击安装包进行安装，根据提示完成安装过程。
                  </p>
                </div>
                <div className="flex flex-col items-center text-center">
                  <div className="h-16 w-16 flex items-center justify-center rounded-full bg-primary-50 dark:bg-primary-900 text-primary mb-4">
                    <svg className="h-8 w-8" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                    </svg>
                  </div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-2">启动应用</h3>
                  <p className="text-gray-500 dark:text-gray-300">
                    安装完成后，点击应用图标启动旅行时间计划应用。
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* 权限设置 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">权限设置</h2>
          <div className="bg-white dark:bg-gray-800 shadow rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <p className="text-gray-500 dark:text-gray-300 mb-6">
                首次启动应用时，系统会请求以下必要权限，请确保授予这些权限以确保应用正常运行：
              </p>
              <div className="space-y-4">
                <div className="flex items-start">
                  <div className="flex-shrink-0 h-6 w-6 text-primary">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                    </svg>
                  </div>
                  <div className="ml-3">
                    <h3 className="text-lg font-medium text-gray-900 dark:text-white">位置权限</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      用于获取当前位置，计算到车站的距离和路线。如果拒绝此权限，应用将无法提供基于位置的功能。
                    </p>
                  </div>
                </div>
                <div className="flex items-start">
                  <div className="flex-shrink-0 h-6 w-6 text-primary">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
                    </svg>
                  </div>
                  <div className="ml-3">
                    <h3 className="text-lg font-medium text-gray-900 dark:text-white">通知权限</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      用于显示闹钟提醒和状态通知。如果拒绝此权限，应用将无法发送闹钟提醒。
                    </p>
                  </div>
                </div>
                <div className="flex items-start">
                  <div className="flex-shrink-0 h-6 w-6 text-primary">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                  </div>
                  <div className="ml-3">
                    <h3 className="text-lg font-medium text-gray-900 dark:text-white">闹钟权限</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      用于设置和触发闹钟提醒。如果拒绝此权限，应用将无法在后台触发闹钟。
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* 车票信息录入教程 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">车票信息录入教程</h2>
          <div className="bg-white dark:bg-gray-800 shadow rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-4">步骤说明</h3>
                  <ol className="space-y-4 list-decimal list-inside text-gray-500 dark:text-gray-300">
                    <li>
                      <span className="font-medium text-gray-900 dark:text-white">打开应用</span>：启动应用后，点击底部导航栏中的"车票"选项卡。
                    </li>
                    <li>
                      <span className="font-medium text-gray-900 dark:text-white">添加新车票</span>：点击右下角的"+"按钮，进入车票信息录入界面。
                    </li>
                    <li>
                      <span className="font-medium text-gray-900 dark:text-white">输入发车时间</span>：点击发车时间输入框，在弹出的日期时间选择器中选择发车时间。
                    </li>
                    <li>
                      <span className="font-medium text-gray-900 dark:text-white">输入停车时间</span>：点击停车时间输入框，在弹出的日期时间选择器中选择停车时间。
                    </li>
                    <li>
                      <span className="font-medium text-gray-900 dark:text-white">输入车站位置</span>：点击车站位置输入框，可以手动输入地址或点击地图图标在地图上选择位置。
                    </li>
                    <li>
                      <span className="font-medium text-gray-900 dark:text-white">保存信息</span>：填写完所有信息后，点击"保存"按钮完成车票信息录入。
                    </li>
                  </ol>
                </div>
                <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-80">
                  {/* 车票信息录入教程截图 */}
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* 地图和路线使用方法 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">地图和路线使用方法</h2>
          <div className="bg-white dark:bg-gray-800 shadow rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-80 order-2 md:order-1">
                  {/* 地图和路线使用方法截图 */}
                </div>
                <div className="order-1 md:order-2">
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-4">功能说明</h3>
                  <ul className="space-y-4 text-gray-500 dark:text-gray-300">
                    <li className="flex items-start">
                      <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                        <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                      </svg>
                      <div>
                        <span className="font-medium text-gray-900 dark:text-white">查看地图</span>：点击底部导航栏中的"地图"选项卡，进入地图界面。
                      </div>
                    </li>
                    <li className="flex items-start">
                      <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                        <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                      </svg>
                      <div>
                        <span className="font-medium text-gray-900 dark:text-white">选择车票</span>：在地图界面顶部的下拉菜单中选择要查看的车票。
                      </div>
                    </li>
                    <li className="flex items-start">
                      <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                        <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                      </svg>
                      <div>
                        <span className="font-medium text-gray-900 dark:text-white">选择交通方式</span>：点击地图底部的交通方式图标（步行、驾车、公交、骑行），选择您偏好的出行方式。
                      </div>
                    </li>
                    <li className="flex items-start">
                      <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                        <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                      </svg>
                      <div>
                        <span className="font-medium text-gray-900 dark:text-white">查看路线详情</span>：选择交通方式后，地图会自动显示从当前位置到车站的路线，并在底部卡片中显示距离和预计用时。
                      </div>
                    </li>
                    <li className="flex items-start">
                      <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                        <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                      </svg>
                      <div>
                        <span className="font-medium text-gray-900 dark:text-white">查看详细路线</span>：点击底部卡片中的"详情"按钮，查看详细的路线指引。
                      </div>
                    </li>
                  </ul>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* 闹钟设置和管理 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">闹钟设置和管理</h2>
          <div className="bg-white dark:bg-gray-800 shadow rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-4">闹钟类型</h3>
                  <ul className="space-y-4 text-gray-500 dark:text-gray-300">
                    <li className="flex items-start">
                      <div className="flex-shrink-0 h-6 w-6 rounded-full bg-green-100 flex items-center justify-center">
                        <div className="h-4 w-4 rounded-full bg-green-500"></div>
                      </div>
                      <div className="ml-3">
                        <span className="font-medium text-gray-900 dark:text-white">出发闹钟</span>：发车时间减去交通用时减去预留时间（默认45分钟）。提醒您开始前往车站。
                      </div>
                    </li>
                    <li className="flex items-start">
                      <div className="flex-shrink-0 h-6 w-6 rounded-full bg-yellow-100 flex items-center justify-center">
                        <div className="h-4 w-4 rounded-full bg-yellow-500"></div>
                      </div>
                      <div className="ml-3">
                        <span className="font-medium text-gray-900 dark:text-white">上车闹钟</span>：发车时间减去30分钟。提醒您准备上车。
                      </div>
                    </li>
                    <li className="flex items-start">
                      <div className="flex-shrink-0 h-6 w-6 rounded-full bg-gray-100 flex items-center justify-center">
                        <div className="h-4 w-4 rounded-full bg-gray-500"></div>
                      </div>
                      <div className="ml-3">
                        <span className="font-medium text-gray-900 dark:text-white">下车闹钟</span>：到站时间减去30分钟。提醒您准备下车。
                      </div>
                    </li>
                  </ul>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mt-6 mb-4">管理闹钟</h3>
                  <ul className="space-y-4 text-gray-500 dark:text-gray-300">
                    <li className="flex items-start">
                      <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                        <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                      </svg>
                      <div>
                        <span className="font-medium text-gray-900 dark:text-white">查看闹钟</span>：点击底部导航栏中的"闹钟"选项卡，查看所有设置的闹钟。
                      </div>
                    </li>
                    <li className="flex items-start">
                      <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                        <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1
(Content truncated due to size limit. Use line ranges to read in chunks)