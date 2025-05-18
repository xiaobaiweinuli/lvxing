import React from 'react';

const Features: React.FC = () => {
  return (
    <div className="bg-white dark:bg-gray-900">
      {/* 功能介绍头部 */}
      <div className="max-w-7xl mx-auto py-16 px-4 sm:py-24 sm:px-6 lg:px-8">
        <div className="text-center">
          <h1 className="text-3xl font-extrabold text-gray-900 dark:text-white sm:text-4xl">
            功能介绍
          </h1>
          <p className="mt-4 max-w-2xl mx-auto text-xl text-gray-500 dark:text-gray-300">
            旅行时间计划应用提供多种实用功能，帮助您更好地管理火车或汽车旅行计划。
          </p>
        </div>
      </div>

      {/* 功能详情 */}
      <div className="max-w-7xl mx-auto pb-16 px-4 sm:pb-24 sm:px-6 lg:px-8">
        {/* 功能1：车票信息录入 */}
        <div className="lg:flex lg:items-center lg:justify-between mb-20">
          <div className="lg:w-1/2 lg:pr-8">
            <h2 className="text-2xl font-bold text-gray-900 dark:text-white">车票信息录入</h2>
            <div className="mt-3 text-lg text-gray-500 dark:text-gray-300">
              <p className="mb-4">
                通过简洁直观的界面，轻松录入您的车票信息：
              </p>
              <ul className="list-disc pl-5 space-y-2">
                <li>手动输入发车时间、停车时间和车站位置</li>
                <li>支持从历史记录中快速选择常用车站</li>
                <li>自动保存录入信息，随时查看和编辑</li>
                <li>支持多种交通方式的车票信息管理</li>
              </ul>
            </div>
          </div>
          <div className="mt-8 lg:mt-0 lg:w-1/2 lg:pl-8">
            <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-64">
              {/* 这里将放置车票信息录入界面截图 */}
            </div>
          </div>
        </div>

        {/* 功能2：位置获取和路线计算 */}
        <div className="lg:flex lg:items-center lg:justify-between mb-20 flex-row-reverse">
          <div className="lg:w-1/2 lg:pl-8">
            <h2 className="text-2xl font-bold text-gray-900 dark:text-white">位置获取和路线计算</h2>
            <div className="mt-3 text-lg text-gray-500 dark:text-gray-300">
              <p className="mb-4">
                智能计算最佳出行路线和时间：
              </p>
              <ul className="list-disc pl-5 space-y-2">
                <li>自动获取用户当前位置</li>
                <li>接入高德/百度地图API，计算到车站的路线</li>
                <li>支持多种交通方式选择（步行、驾车、公交、骑行）</li>
                <li>实时显示距离和预计用时</li>
                <li>根据交通状况动态调整路线建议</li>
              </ul>
            </div>
          </div>
          <div className="mt-8 lg:mt-0 lg:w-1/2 lg:pr-8">
            <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-64">
              {/* 这里将放置地图和路线计算界面截图 */}
            </div>
          </div>
        </div>

        {/* 功能3：多重闹钟提醒 */}
        <div className="lg:flex lg:items-center lg:justify-between mb-20">
          <div className="lg:w-1/2 lg:pr-8">
            <h2 className="text-2xl font-bold text-gray-900 dark:text-white">多重闹钟提醒</h2>
            <div className="mt-3 text-lg text-gray-500 dark:text-gray-300">
              <p className="mb-4">
                智能闹钟系统，确保您不会错过任何重要时刻：
              </p>
              <ul className="list-disc pl-5 space-y-2">
                <li>出发闹钟：发车时间减去交通用时减去预留时间</li>
                <li>上车闹钟：发车前30分钟提醒</li>
                <li>下车闹钟：到站前30分钟提醒</li>
                <li>支持自定义铃声和震动</li>
                <li>闹钟触发后自动显示路线导航</li>
              </ul>
            </div>
          </div>
          <div className="mt-8 lg:mt-0 lg:w-1/2 lg:pl-8">
            <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-64">
              {/* 这里将放置闹钟界面截图 */}
            </div>
          </div>
        </div>

        {/* 功能4：基于位置的状态提示 */}
        <div className="lg:flex lg:items-center lg:justify-between mb-20 flex-row-reverse">
          <div className="lg:w-1/2 lg:pl-8">
            <h2 className="text-2xl font-bold text-gray-900 dark:text-white">基于位置的状态提示</h2>
            <div className="mt-3 text-lg text-gray-500 dark:text-gray-300">
              <p className="mb-4">
                根据您的位置提供智能状态提示：
              </p>
              <ul className="list-disc pl-5 space-y-2">
                <li>距离车站超过1千米：显示绿色状态，提示"请注意时间"</li>
                <li>距离车站不到1千米：显示红色状态，提示"请抓紧时间"</li>
                <li>上车提醒：显示黄色状态，提示"请注意上车"</li>
                <li>下车提醒：显示黑色状态，提示"请注意下车"</li>
                <li>支持自定义状态颜色和提示内容</li>
              </ul>
            </div>
          </div>
          <div className="mt-8 lg:mt-0 lg:w-1/2 lg:pr-8">
            <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-64">
              {/* 这里将放置状态提示界面截图 */}
            </div>
          </div>
        </div>

        {/* 功能5：高度自定义 */}
        <div className="lg:flex lg:items-center lg:justify-between">
          <div className="lg:w-1/2 lg:pr-8">
            <h2 className="text-2xl font-bold text-gray-900 dark:text-white">高度自定义</h2>
            <div className="mt-3 text-lg text-gray-500 dark:text-gray-300">
              <p className="mb-4">
                根据个人喜好自定义应用的各项功能：
              </p>
              <ul className="list-disc pl-5 space-y-2">
                <li>地图API选择：高德地图或百度地图</li>
                <li>空余时间自定义：调整默认的45分钟预留时间</li>
                <li>交通方式偏好设置</li>
                <li>距离阈值自定义：调整默认的1千米阈值</li>
                <li>状态颜色和提示内容自定义</li>
                <li>闹钟铃声和震动自定义</li>
                <li>UI元素位置、形状、颜色、背景自定义</li>
              </ul>
            </div>
          </div>
          <div className="mt-8 lg:mt-0 lg:w-1/2 lg:pl-8">
            <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-64">
              {/* 这里将放置设置界面截图 */}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Features;
