import React from 'react';

// 创建一个特性卡片组件，用于展示应用功能特点
const FeatureCard: React.FC<{
  icon: React.ReactNode;
  title: string;
  description: string;
  className?: string;
}> = ({ icon, title, description, className = '' }) => {
  return (
    <div className={`bg-white dark:bg-gray-800 shadow rounded-lg p-6 transition-all duration-300 hover:shadow-lg ${className}`}>
      <div className="flex items-center mb-4">
        <div className="h-12 w-12 flex items-center justify-center rounded-md bg-primary-50 dark:bg-primary-900 text-primary">
          {icon}
        </div>
        <h3 className="ml-4 text-lg font-medium text-gray-900 dark:text-white">{title}</h3>
      </div>
      <p className="text-gray-500 dark:text-gray-300">
        {description}
      </p>
    </div>
  );
};

export default FeatureCard;
