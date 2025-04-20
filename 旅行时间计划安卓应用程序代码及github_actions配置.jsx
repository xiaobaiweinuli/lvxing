我将为您创建一个旅行时间计划应用的网页版UI，基于React实现，包含车票信息管理、地图集成和闹钟设置功能。

import React, { useState, useEffect } from 'react';
import { MapContainer, TileLayer, useMap } from 'react-leaflet';
import { Clock, MapPin, Train, Calendar, AlarmClock, Navigation } from 'lucide-react';
import { motion } from 'framer-motion';
import AMapLoader from '@amap/amap-jsapi-loader';

const TravelPlanner = () => {
  const [activeTab, setActiveTab] = useState('tickets');
  const [tickets, setTickets] = useState([]);
  const [formData, setFormData] = useState({
    departureTime: '',
    stopTime: '',
    stationLocation: ''
  });
  const [currentLocation, setCurrentLocation] = useState(null);
  const [routeInfo, setRouteInfo] = useState(null);
  const [alarmTime, setAlarmTime] = useState('08:00');

  // 模拟从数据库获取车票信息
  useEffect(() => {
    const mockTickets = [
      { id: 1, departureTime: '08:30', stopTime: '10:45', stationLocation: '北京南站' },
      { id: 2, departureTime: '14:15', stopTime: '16:30', stationLocation: '上海虹桥站' }
    ];
    setTickets(mockTickets);
  }, []);

  // 处理表单输入变化
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  // 保存车票信息
  const saveTicket = () => {
    if (!formData.departureTime || !formData.stopTime || !formData.stationLocation) {
      alert('请填写完整的车票信息');
      return;
    }

    const newTicket = {
      id: tickets.length + 1,
      ...formData
    };
    setTickets([...tickets, newTicket]);
    setFormData({ departureTime: '', stopTime: '', stationLocation: '' });
  };

  // 获取当前位置
  const getCurrentLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setCurrentLocation({
            lat: position.coords.latitude,
            lng: position.coords.longitude
          });
        },
        (error) => {
          console.error('Error getting location:', error);
        }
      );
    } else {
      alert('您的浏览器不支持地理位置功能');
    }
  };

  // 计算路线
  const calculateRoute = () => {
    if (!currentLocation) {
      alert('请先获取当前位置');
      return;
    }

    // 这里应该调用地图API计算路线
    // 模拟API返回结果
    const mockRoute = {
      duration: 45,
      distance: 12.5,
      mode: 'driving'
    };
    setRouteInfo(mockRoute);
  };

  // 设置闹钟
  const setAlarm = () => {
    alert(`闹钟已设置为 ${alarmTime}`);
  };

  return (
    <div className="min-h-screen bg-gray-50">
      {/* 导航栏 */}
      <nav className="bg-white shadow-sm">
        <div className="max-w-6xl mx-auto px-4">
          <div className="flex justify-between h-16">
            <div className="flex items-center">
              <Train className="h-8 w-8 text-blue-600 mr-2" />
              <span className="text-xl font-semibold text-gray-900">旅行时间计划</span>
            </div>
            <div className="flex space-x-8">
              <button
                onClick={() => setActiveTab('tickets')}
                className={`inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium ${activeTab === 'tickets' ? 'border-blue-500 text-blue-600' : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'}`}
              >
                <Calendar className="mr-1" size={16} />
                车票管理
              </button>
              <button
                onClick={() => setActiveTab('map')}
                className={`inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium ${activeTab === 'map' ? 'border-blue-500 text-blue-600' : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'}`}
              >
                <MapPin className="mr-1" size={16} />
                路线规划
              </button>
              <button
                onClick={() => setActiveTab('alarm')}
                className={`inline-flex items-center px-1 pt-1 border-b-2 text-sm font-medium ${activeTab === 'alarm' ? 'border-blue-500 text-blue-600' : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'}`}
              >
                <AlarmClock className="mr-1" size={16} />
                闹钟设置
              </button>
            </div>
          </div>
        </div>
      </nav>

      <main className="max-w-6xl mx-auto py-6 px-4">
        {/* 车票管理 */}
        {activeTab === 'tickets' && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.3 }}
          >
            <div className="bg-white shadow rounded-lg p-6 mb-6">
              <h2 className="text-lg font-medium text-gray-900 mb-4 flex items-center">
                <Calendar className="mr-2" size={20} />
                添加新车票信息
              </h2>
              <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
                <div>
                  <label htmlFor="departureTime" className="block text-sm font-medium text-gray-700 mb-1">
                    发车时间
                  </label>
                  <input
                    type="time"
                    name="departureTime"
                    id="departureTime"
                    value={formData.departureTime}
                    onChange={handleInputChange}
                    className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label htmlFor="stopTime" className="block text-sm font-medium text-gray-700 mb-1">
                    停车时间
                  </label>
                  <input
                    type="time"
                    name="stopTime"
                    id="stopTime"
                    value={formData.stopTime}
                    onChange={handleInputChange}
                    className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
                  />
                </div>
                <div>
                  <label htmlFor="stationLocation" className="block text-sm font-medium text-gray-700 mb-1">
                    车站位置
                  </label>
                  <input
                    type="text"
                    name="stationLocation"
                    id="stationLocation"
                    value={formData.stationLocation}
                    onChange={handleInputChange}
                    className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
                    placeholder="例如：北京南站"
                  />
                </div>
              </div>
              <div className="mt-4">
                <button
                  onClick={saveTicket}
                  className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                >
                  保存车票信息
                </button>
              </div>
            </div>

            <div className="bg-white shadow rounded-lg p-6">
              <h2 className="text-lg font-medium text-gray-900 mb-4 flex items-center">
                <Train className="mr-2" size={20} />
                我的车票
              </h2>
              <div className="overflow-x-auto">
                <table className="min-w-full divide-y divide-gray-200">
                  <thead className="bg-gray-50">
                    <tr>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        发车时间
                      </th>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        停车时间
                      </th>
                      <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                        车站位置
                      </th>
                    </tr>
                  </thead>
                  <tbody className="bg-white divide-y divide-gray-200">
                    {tickets.map((ticket) => (
                      <tr key={ticket.id}>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                          {ticket.departureTime}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                          {ticket.stopTime}
                        </td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-900">
                          {ticket.stationLocation}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </motion.div>
        )}

        {/* 路线规划 */}
        {activeTab === 'map' && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.3 }}
            className="space-y-6"
          >
            <div className="bg-white shadow rounded-lg p-6">
              <h2 className="text-lg font-medium text-gray-900 mb-4 flex items-center">
                <Navigation className="mr-2" size={20} />
                路线规划
              </h2>
              
              <div className="flex space-x-4 mb-4">
                <button
                  onClick={getCurrentLocation}
                  className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                >
                  <MapPin className="mr-2" size={16} />
                  获取当前位置
                </button>
                <button
                  onClick={calculateRoute}
                  className="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
                >
                  <Clock className="mr-2" size={16} />
                  计算路线
                </button>
              </div>

              {currentLocation && (
                <div className="mb-4 p-4 bg-blue-50 rounded-md">
                  <p className="text-sm text-blue-800">
                    当前位置: 纬度 {currentLocation.lat.toFixed(4)}, 经度 {currentLocation.lng.toFixed(4)}
                  </p>
                </div>
              )}

              {routeInfo && (
                <div className="mb-4 p-4 bg-green-50 rounded-md">
                  <p className="text-sm text-green-800">
                    预计{routeInfo.mode === 'driving' ? '驾车' : '步行'}时间: {routeInfo.duration} 分钟, 距离: {routeInfo.distance} 公里
                  </p>
                </div>
              )}

              <div className="h-96 bg-gray-200 rounded-lg overflow-hidden">
                <MapContainer 
                  center={currentLocation || [39.9042, 116.4074]} 
                  zoom={12} 
                  style={{ height: '100%', width: '100%' }}
                >
                  <TileLayer
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                  />
                </MapContainer>
              </div>
            </div>
          </motion.div>
        )}

        {/* 闹钟设置 */}
        {activeTab === 'alarm' && (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.3 }}
          >
            <div className="bg-white shadow rounded-lg p-6">
              <h2 className="text-lg font-medium text-gray-900 mb-4 flex items-center">
                <AlarmClock className="mr-2" size={20} />
                闹钟设置
              </h2>
              <div className="flex items-center space-x-4">
                <div className="w-48">
                  <label htmlFor="alarmTime" className="block text-sm font-medium text-gray-700 mb-1">
                    设置闹钟时间
                  </label>
                  <input
                    type="time"
                    id="alarmTime"
                    value={alarmTime}
                    onChange={(e) => setAlarmTime(e.target.value)}
                    className="w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"
                  />
                </div>
                <button
                  onClick={setAlarm}
                  className="mt-6 inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-blue-600 hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
                >
                  设置闹钟
                </button>
              </div>
            </div>
          </motion.div>
        )}
      </main>

      <footer className="bg-white border-t border-gray-200 py-4">
        <div className="max-w-6xl mx-auto px-4 text-center text-sm text-gray-500">
          <p>created by <a href="https://space.coze.cn" className="text-blue-600 hover:underline">coze space</a></p>
          <p>页面内容均由 AI 生成，仅供参考</p>
        </div>
      </footer>
    </div>
  );
};

export default TravelPlanner;