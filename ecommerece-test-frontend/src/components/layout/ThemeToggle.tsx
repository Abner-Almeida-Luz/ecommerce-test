import { useTheme } from "../../contexts/ThemeContext"; // ou caminho correto

export default function ThemeToggle() {
  const { theme, toggle } = useTheme();
  return (
    <button onClick={toggle} className="p-2 rounded-xl border border-gray-200 dark:border-gray-700 hover:bg-gray-100 dark:hover:bg-gray-800 transition-all text-xl">
      {theme === 'dark' ? '☀️' : '🌙'}
    </button>
  );
}