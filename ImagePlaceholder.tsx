import React from 'react';

// 创建一个图片占位符组件，用于替代实际的应用截图
const ImagePlaceholder: React.FC<{
  height?: string;
  width?: string;
  text?: string;
  className?: string;
}> = ({ height = 'h-64', width = 'w-full', text = '应用截图', className = '' }) => {
  return (
    <div className={`img-placeholder ${height} ${width} rounded-lg flex items-center justify-center ${className}`}>
      <div className="text-center">
        <svg 
          className="mx-auto h-12 w-12 mb-2" 
          xmlns="http://www.w3.org/2000/svg" 
          fill="none" 
          viewBox="0 0 24 24" 
          stroke="currentColor"
        >
          <path 
            strokeLinecap="round" 
            strokeLinejoin="round" 
            strokeWidth={1.5} 
            d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" 
          />
        </svg>
        <p>{text}</p>
      </div>
    </div>
  );
};

export default ImagePlaceholder;
