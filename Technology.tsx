import React from 'react';

const Technology: React.FC = () => {
  return (
    <div className="bg-white dark:bg-gray-900">
      {/* 技术架构头部 */}
      <div className="max-w-7xl mx-auto py-16 px-4 sm:py-24 sm:px-6 lg:px-8">
        <div className="text-center">
          <h1 className="text-3xl font-extrabold text-gray-900 dark:text-white sm:text-4xl">
            技术架构
          </h1>
          <p className="mt-4 max-w-2xl mx-auto text-xl text-gray-500 dark:text-gray-300">
            旅行时间计划应用采用现代化的技术架构和组件，提供高效、稳定的用户体验。
          </p>
        </div>
      </div>

      {/* MVVM架构介绍 */}
      <div className="max-w-7xl mx-auto pb-16 px-4 sm:pb-24 sm:px-6 lg:px-8">
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">MVVM架构</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div className="flex flex-col justify-center">
              <p className="text-gray-500 dark:text-gray-300 mb-6">
                应用采用MVVM（Model-View-ViewModel）架构模式，结合Android Jetpack组件，实现了清晰的关注点分离和高效的数据绑定。
              </p>
              <div className="space-y-4">
                <div className="border-l-4 border-primary pl-4">
                  <h3 className="text-lg font-semibold text-gray-900 dark:text-white">Model（数据层）</h3>
                  <p className="text-gray-500 dark:text-gray-300">
                    负责数据的存储、访问和管理，包括Room数据库、数据模型和仓库模式实现。
                  </p>
                </div>
                <div className="border-l-4 border-primary pl-4">
                  <h3 className="text-lg font-semibold text-gray-900 dark:text-white">View（视图层）</h3>
                  <p className="text-gray-500 dark:text-gray-300">
                    负责界面显示和用户交互，包括Activity、Fragment和各种UI组件。
                  </p>
                </div>
                <div className="border-l-4 border-primary pl-4">
                  <h3 className="text-lg font-semibold text-gray-900 dark:text-white">ViewModel（视图模型层）</h3>
                  <p className="text-gray-500 dark:text-gray-300">
                    连接View和Model，处理业务逻辑，管理UI相关数据，并在配置更改时保持数据。
                  </p>
                </div>
              </div>
            </div>
            <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-80">
              {/* MVVM架构图 */}
            </div>
          </div>
        </div>

        {/* 技术栈和组件 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">技术栈和组件</h2>
          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-6">
              <div className="flex items-center mb-4">
                <div className="h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
                  <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10 20l4-16m4 4l4 4-4 4M6 16l-4-4 4-4" />
                  </svg>
                </div>
                <h3 className="ml-4 text-lg font-medium text-gray-900 dark:text-white">Kotlin</h3>
              </div>
              <p className="text-gray-500 dark:text-gray-300">
                使用Kotlin作为主要编程语言，利用其简洁的语法、空安全特性和协程支持，提高开发效率和代码质量。
              </p>
            </div>
            <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-6">
              <div className="flex items-center mb-4">
                <div className="h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
                  <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 7v10c0 2.21 3.582 4 8 4s8-1.79 8-4V7M4 7c0 2.21 3.582 4 8 4s8-1.79 8-4M4 7c0-2.21 3.582-4 8-4s8 1.79 8 4" />
                  </svg>
                </div>
                <h3 className="ml-4 text-lg font-medium text-gray-900 dark:text-white">Room</h3>
              </div>
              <p className="text-gray-500 dark:text-gray-300">
                使用Room数据库提供本地数据持久化，简化SQLite操作，支持LiveData和Flow响应式查询。
              </p>
            </div>
            <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-6">
              <div className="flex items-center mb-4">
                <div className="h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
                  <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4" />
                  </svg>
                </div>
                <h3 className="ml-4 text-lg font-medium text-gray-900 dark:text-white">Coroutines & Flow</h3>
              </div>
              <p className="text-gray-500 dark:text-gray-300">
                使用Kotlin协程和Flow进行异步操作和响应式编程，简化异步代码，提高应用性能和响应性。
              </p>
            </div>
            <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-6">
              <div className="flex items-center mb-4">
                <div className="h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
                  <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" />
                  </svg>
                </div>
                <h3 className="ml-4 text-lg font-medium text-gray-900 dark:text-white">DataStore</h3>
              </div>
              <p className="text-gray-500 dark:text-gray-300">
                使用DataStore存储用户偏好设置，提供类型安全的数据访问和异步API，替代传统的SharedPreferences。
              </p>
            </div>
            <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-6">
              <div className="flex items-center mb-4">
                <div className="h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
                  <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 110-4m0 4v2m0-6V4" />
                  </svg>
                </div>
                <h3 className="ml-4 text-lg font-medium text-gray-900 dark:text-white">Hilt</h3>
              </div>
              <p className="text-gray-500 dark:text-gray-300">
                使用Hilt进行依赖注入，简化组件间依赖关系，提高代码可测试性和可维护性。
              </p>
            </div>
            <div className="bg-white dark:bg-gray-800 shadow rounded-lg p-6">
              <div className="flex items-center mb-4">
                <div className="h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
                  <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 5a1 1 0 011-1h14a1 1 0 011 1v2a1 1 0 01-1 1H5a1 1 0 01-1-1V5zM4 13a1 1 0 011-1h6a1 1 0 011 1v6a1 1 0 01-1 1H5a1 1 0 01-1-1v-6zM16 13a1 1 0 011-1h2a1 1 0 011 1v6a1 1 0 01-1 1h-2a1 1 0 01-1-1v-6z" />
                  </svg>
                </div>
                <h3 className="ml-4 text-lg font-medium text-gray-900 dark:text-white">Material Design 3</h3>
              </div>
              <p className="text-gray-500 dark:text-gray-300">
                采用Material Design 3设计系统和莫奈颜色系统，提供现代化、一致的用户界面和交互体验。
              </p>
            </div>
          </div>
        </div>

        {/* 地图API集成 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">地图API集成</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-80">
              {/* 地图API集成图 */}
            </div>
            <div className="flex flex-col justify-center">
              <p className="text-gray-500 dark:text-gray-300 mb-6">
                应用支持高德地图和百度地图API的灵活集成，提供丰富的地图功能和路线计算能力。
              </p>
              <div className="space-y-4">
                <div className="flex items-start">
                  <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                  </svg>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white">抽象接口设计</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      通过MapService接口抽象地图功能，支持不同地图提供商的无缝切换。
                    </p>
                  </div>
                </div>
                <div className="flex items-start">
                  <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                  </svg>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white">多种交通方式支持</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      支持步行、驾车、公交、骑行等多种交通方式的路线规划和用时计算。
                    </p>
                  </div>
                </div>
                <div className="flex items-start">
                  <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                  </svg>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white">实时路况和动态路线</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      根据实时路况数据，提供最优路线和准确的用时预估。
                    </p>
                  </div>
                </div>
                <div className="flex items-start">
                  <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                  </svg>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white">依赖注入集成</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      通过Hilt依赖注入框架，根据用户设置动态提供不同的地图服务实现。
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* 闹钟和通知系统 */}
        <div className="mb-16">
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">闹钟和通知系统</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div className="flex flex-col justify-center">
              <p className="text-gray-500 dark:text-gray-300 mb-6">
                应用实现了强大的闹钟和通知系统，确保用户不会错过任何重要时刻。
              </p>
              <div className="space-y-4">
                <div className="flex items-start">
                  <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                  </svg>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white">AlarmManager集成</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      使用Android AlarmManager服务，确保即使应用未运行也能准时触发闹钟。
                    </p>
                  </div>
                </div>
                <div className="flex items-start">
                  <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                  </svg>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white">广播接收器</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      通过BroadcastReceiver接收闹钟触发事件，并显示相应通知。
                    </p>
                  </div>
                </div>
                <div className="flex items-start">
                  <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                  </svg>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white">通知渠道</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      使用Android通知渠道，为不同类型的闹钟提供不同的通知设置。
                    </p>
                  </div>
                </div>
                <div className="flex items-start">
                  <svg className="h-5 w-5 text-primary mr-2 mt-0.5" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                  </svg>
                  <div>
                    <h3 className="text-lg font-semibold text-gray-900 dark:text-white">设备重启处理</h3>
                    <p className="text-gray-500 dark:text-gray-300">
                      监听设备重启事件，确保重启后闹钟设置不会丢失。
                    </p>
                  </div>
                </div>
              </div>
            </div>
            <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-80">
              {/* 闹钟和通知系统图 */}
            </div>
          </div>
        </div>

        {/* 自动构建流程 */}
        <div>
          <h2 className="text-2xl font-bold text-gray-900 dark:text-white mb-6">自动构建流程</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div className="bg-gray-200 dark:bg-gray-700 rounded-lg overflow-hidden h-80">
              {/* 自动构建流程图 */}
            </div>
            <div className="flex flex-col ju
(Content truncated due to size limit. Use line ranges to read in chunks)