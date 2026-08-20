export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  darkMode: 'class', // permite alternância via classe "dark"
  theme: {
    extend: {
      colors: {
        accent: {
          DEFAULT: '#6C5CE7',
          light: '#8B7CF6',
          dark: '#5A4BD1',
        },
        primary: {
          50: '#eff6ff',
          100: '#dbeafe',
          500: '#3b82f6',
          600: '#2563eb',
          700: '#1d4ed8',
        },
        success: {
          100: '#dcfce7',
          600: '#16a34a',
        },
        danger: {
          100: '#fee2e2',
          600: '#dc2626',
        },
        warning: {
          100: '#fef9c3',
          600: '#ca8a04',
        },
        neutral: {
          50: '#f8fafc',
          100: '#f1f5f9',
          200: '#e2e8f0',
          400: '#94a3b8',
          600: '#475569',
          800: '#1e293b',
          900: '#0f172a',
        },
      },
      fontFamily: {
        sans: ['Inter', 'system-ui', 'sans-serif'],
      },
      borderRadius: {
        DEFAULT: '0.5rem',
        lg: '0.75rem',
      },
      boxShadow: {
        card: '0 1px 3px rgba(0,0,0,0.08), 0 1px 2px rgba(0,0,0,0.04)',
      },
    },
  },
  plugins: [],
};
