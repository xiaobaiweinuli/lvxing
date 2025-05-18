import React from 'react';

// 创建一个按钮组件，用于统一网站按钮样式
const Button = React.forwardRef<
  HTMLButtonElement,
  React.ButtonHTMLAttributes<HTMLButtonElement> & {
    variant?: 'primary' | 'secondary' | 'outline' | 'ghost';
    size?: 'sm' | 'md' | 'lg' | 'icon' | 'default';
  }
>(({ 
  children, 
  variant = 'primary', 
  size = 'md', 
  className = '',
  ...props
}, ref) => {
  // 基础样式
  const baseStyles = "inline-flex items-center justify-center font-medium rounded-md transition-all duration-300";
  
  // 变体样式
  const variantStyles = {
    primary: "text-white bg-primary hover:bg-primary-dark",
    secondary: "text-primary bg-primary-50 hover:bg-primary-100",
    outline: "text-primary border border-primary hover:bg-primary-50",
    ghost: "text-gray-700 hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-gray-800"
  };
  
  // 尺寸样式
  const sizeStyles = {
    sm: "px-3 py-2 text-sm",
    md: "px-5 py-3 text-base",
    lg: "px-8 py-4 text-lg",
    icon: "p-2 w-9 h-9",
    default: "px-4 py-2"
  };
  
  return (
    <button
      ref={ref}
      className={`${baseStyles} ${variantStyles[variant]} ${sizeStyles[size]} ${className}`}
      {...props}
    >
      {children}
    </button>
  );
});

Button.displayName = "Button";

// 导出按钮变体样式函数，用于其他组件
export const buttonVariants = ({
  variant = 'primary' as 'primary' | 'secondary' | 'outline' | 'ghost',
  size = 'md' as 'sm' | 'md' | 'lg' | 'icon' | 'default',
  className = ''
}: {
  variant?: 'primary' | 'secondary' | 'outline' | 'ghost';
  size?: 'sm' | 'md' | 'lg' | 'icon' | 'default';
  className?: string;
} = {}): string => {
  // 基础样式
  const baseStyles = "inline-flex items-center justify-center font-medium rounded-md transition-all duration-300";
  
  // 变体样式
  const variantStyles = {
    primary: "text-white bg-primary hover:bg-primary-dark",
    secondary: "text-primary bg-primary-50 hover:bg-primary-100",
    outline: "text-primary border border-primary hover:bg-primary-50",
    ghost: "text-gray-700 hover:bg-gray-100 dark:text-gray-300 dark:hover:bg-gray-800"
  };
  
  // 尺寸样式
  const sizeStyles = {
    sm: "px-3 py-2 text-sm",
    md: "px-5 py-3 text-base",
    lg: "px-8 py-4 text-lg",
    icon: "p-2 w-9 h-9",
    default: "px-4 py-2"
  };
  
  return `${baseStyles} ${variantStyles[variant]} ${sizeStyles[size]} ${className}`;
};

// 导出按钮属性类型
export type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
  variant?: 'primary' | 'secondary' | 'outline' | 'ghost';
  size?: 'sm' | 'md' | 'lg' | 'icon' | 'default';
};

export { Button };
export default Button;
