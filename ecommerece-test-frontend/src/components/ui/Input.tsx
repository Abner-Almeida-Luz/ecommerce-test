import { InputHTMLAttributes, forwardRef } from 'react';

interface InputProps extends InputHTMLAttributes<HTMLInputElement> {
  label?: string;
  error?: string;
}

const Input = forwardRef<HTMLInputElement, InputProps>(
  ({ label, error, className = '', ...props }, ref) => {
    return (
      <div className="w-full">
        {label && (
          <label className="block text-sm font-medium text-neutral-700 mb-1">
            {label}
          </label>
        )}
        <input
          ref={ref}
          className={`w-full border rounded px-3 py-2 outline-none transition-colors
            ${error ? 'border-danger-600 focus:border-danger-600' : 'border-neutral-200 focus:border-primary-500'}
            ${className}`}
          {...props}
        />
        {error && <p className="text-danger-600 text-sm mt-1">{error}</p>}
      </div>
    );
  }
);
Input.displayName = 'Input';
export default Input;