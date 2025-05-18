import React from 'react';

const About: React.FC = () => {
  return (
    <div className="bg-white dark:bg-gray-900">
      {/* 关于我们头部 */}
      <div className="max-w-7xl mx-auto py-16 px-4 sm:py-24 sm:px-6 lg:px-8">
        <div className="text-center">
          <h1 className="text-3xl font-extrabold text-gray-900 dark:text-white sm:text-4xl">
            关于我们
          </h1>
          <p className="mt-4 max-w-2xl mx-auto text-xl text-gray-500 dark:text-gray-300">
            了解旅行时间计划应用的背景、团队和使命。
          </p>
        </div>
      </div>

      {/* 项目背景 */}
      <div className="max-w-7xl mx-auto pb-16 px-4 sm:pb-24 sm:px-6 lg:px-8">
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">项目背景</h2>
          <div className="bg-white dark:bg-gray-800 shadow rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div>
                  <p className="text-gray-500 dark:text-gray-300 mb-4">
                    旅行时间计划应用源于我们对旅行体验的深入思考。在现代快节奏的生活中，人们经常面临赶车压力和时间管理挑战，尤其是在不熟悉的城市或复杂的交通环境中。
                  </p>
                  <p className="text-gray-500 dark:text-gray-300 mb-4">
                    我们的团队成员在一次长途火车旅行中，因为低估了到车站的时间而差点错过火车。这次经历让我们意识到，一个能够智能管理旅行时间、提供及时提醒的应用将极大地改善旅行体验。
                  </p>
                  <p className="text-gray-500 dark:text-gray-300">
                    经过市场调研和用户需求分析，我们发现现有的旅行应用大多关注于行程规划和票务预订，而缺乏对"最后一公里"时间管理的关注。因此，我们决定开发这款专注于旅行时间管理的应用，帮助用户更好地掌控旅途中的每一分钟。
                  </p>
                </div>
                <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-64">
                  {/* 项目背景图片 */}
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* 我们的使命 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">我们的使命</h2>
          <div className="bg-primary-50 dark:bg-primary-900 rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <div className="text-center">
                <h3 className="text-xl font-semibold text-primary mb-4">让每一次旅行都准时、轻松、愉快</h3>
                <p className="text-gray-600 dark:text-gray-300 max-w-3xl mx-auto">
                  我们致力于通过智能技术和用户友好的设计，解决旅行者在时间管理方面的痛点，减轻赶车压力，提高旅行效率，让用户能够更加从容地享受旅途的每一刻。我们相信，良好的时间管理是愉快旅行体验的基础，而我们的应用正是为此而生。
                </p>
              </div>
            </div>
          </div>
        </div>

        {/* 开发团队 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">开发团队</h2>
          <div className="bg-white dark:bg-gray-800 shadow rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
                <div className="flex flex-col items-center">
                  <div className="h-32 w-32 rounded-full bg-gray-200 dark:bg-gray-700 mb-4">
                    {/* 团队成员头像 */}
                  </div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white">张明</h3>
                  <p className="text-primary">项目负责人</p>
                  <p className="text-gray-500 dark:text-gray-300 text-center mt-2">
                    拥有10年Android开发经验，负责项目整体架构设计和团队协调。
                  </p>
                </div>
                <div className="flex flex-col items-center">
                  <div className="h-32 w-32 rounded-full bg-gray-200 dark:bg-gray-700 mb-4">
                    {/* 团队成员头像 */}
                  </div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white">李华</h3>
                  <p className="text-primary">UI/UX设计师</p>
                  <p className="text-gray-500 dark:text-gray-300 text-center mt-2">
                    专注于移动应用界面设计，负责应用的用户体验和视觉设计。
                  </p>
                </div>
                <div className="flex flex-col items-center">
                  <div className="h-32 w-32 rounded-full bg-gray-200 dark:bg-gray-700 mb-4">
                    {/* 团队成员头像 */}
                  </div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white">王强</h3>
                  <p className="text-primary">后端开发工程师</p>
                  <p className="text-gray-500 dark:text-gray-300 text-center mt-2">
                    精通地图API和位置服务，负责应用的核心功能实现。
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* 技术特点 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">技术特点</h2>
          <div className="bg-white dark:bg-gray-800 shadow rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div className="space-y-4">
                  <div className="flex items-start">
                    <div className="flex-shrink-0 h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
                      <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                      </svg>
                    </div>
                    <div className="ml-4">
                      <h3 className="text-lg font-medium text-gray-900 dark:text-white">现代架构</h3>
                      <p className="mt-2 text-gray-500 dark:text-gray-300">
                        采用MVVM架构和Android Jetpack组件，确保代码清晰、可维护和可测试。
                      </p>
                    </div>
                  </div>
                  <div className="flex items-start">
                    <div className="flex-shrink-0 h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
                      <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 21a4 4 0 01-4-4V5a2 2 0 012-2h4a2 2 0 012 2v12a4 4 0 01-4 4zm0 0h12a2 2 0 002-2v-4a2 2 0 00-2-2h-2.343M11 7.343l1.657-1.657a2 2 0 012.828 0l2.829 2.829a2 2 0 010 2.828l-8.486 8.485M7 17h.01" />
                      </svg>
                    </div>
                    <div className="ml-4">
                      <h3 className="text-lg font-medium text-gray-900 dark:text-white">响应式设计</h3>
                      <p className="mt-2 text-gray-500 dark:text-gray-300">
                        使用Material Design 3和莫奈颜色系统，提供美观、一致的用户界面。
                      </p>
                    </div>
                  </div>
                </div>
                <div className="space-y-4">
                  <div className="flex items-start">
                    <div className="flex-shrink-0 h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
                      <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 10V3L4 14h7v7l9-11h-7z" />
                      </svg>
                    </div>
                    <div className="ml-4">
                      <h3 className="text-lg font-medium text-gray-900 dark:text-white">高性能</h3>
                      <p className="mt-2 text-gray-500 dark:text-gray-300">
                        使用Kotlin协程和Flow进行异步操作，确保应用响应迅速、运行流畅。
                      </p>
                    </div>
                  </div>
                  <div className="flex items-start">
                    <div className="flex-shrink-0 h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
                      <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z" />
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                      </svg>
                    </div>
                    <div className="ml-4">
                      <h3 className="text-lg font-medium text-gray-900 dark:text-white">高度可定制</h3>
                      <p className="mt-2 text-gray-500 dark:text-gray-300">
                        提供丰富的自定义选项，让用户能够根据个人喜好调整应用的各项功能和外观。
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* 联系方式 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">联系方式</h2>
          <div className="bg-white dark:bg-gray-800 shadow rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="flex flex-col items-center p-4 border border-gray-200 dark:border-gray-700 rounded-lg">
                  <svg className="h-8 w-8 text-primary mb-3" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                  </svg>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-2">电子邮件</h3>
                  <p className="text-gray-500 dark:text-gray-300 text-center">
                    contact@travelplanapp.com
                  </p>
                </div>
                <div className="flex flex-col items-center p-4 border border-gray-200 dark:border-gray-700 rounded-lg">
                  <svg className="h-8 w-8 text-primary mb-3" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                  </svg>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-2">电话</h3>
                  <p className="text-gray-500 dark:text-gray-300 text-center">
                    +86 123 4567 8910
                  </p>
                </div>
                <div className="flex flex-col items-center p-4 border border-gray-200 dark:border-gray-700 rounded-lg">
                  <svg className="h-8 w-8 text-primary mb-3" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                  </svg>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-2">地址</h3>
                  <p className="text-gray-500 dark:text-gray-300 text-center">
                    北京市海淀区中关村科技园区
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* 隐私政策和用户协议 */}
        <div>
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">法律信息</h2>
          <div className="bg-white dark:bg-gray-800 shadow rounded-lg overflow-hidden">
            <div className="px-6 py-8">
              <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                <div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-4">隐私政策</h3>
                  <p className="text-gray-500 dark:text-gray-300 mb-4">
                    我们非常重视用户的隐私保护，并严格遵守相关法律法规。我们的隐私政策详细说明了我们如何收集、使用和保护您的个人信息。
                  </p>
                  <a href="#" className="text-primary hover:text-primary-dark font-medium">
                    查看完整隐私政策 →
                  </a>
                </div>
                <div>
                  <h3 className="text-lg font-medium text-gray-900 dark:text-white mb-4">用户协议</h3>
                  <p className="text-gray-500 dark:text-gray-300 mb-4">
                    使用旅行时间计划应用即表示您同意我们的用户协议。该协议规定了您使用我们服务的权利和义务，以及我们的责任和限制。
                  </p>
                  <a href="#" className="text-primary hover:text-primary-dark font-medium">
                    查看完整用户协议 →
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default About;
