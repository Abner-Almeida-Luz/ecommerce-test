import { createContext, useContext, useState, useEffect, ReactNode } from 'react';

// Define o formato do valor que o contexto fornecerá
interface ThemeContextType {
  theme: string;
  toggle: () => void;
}

// Cria o contexto com um valor inicial (pode ser undefined para forçar uso do Provider)
const ThemeContext = createContext<ThemeContextType | undefined>(undefined);

// Props do Provider (apenas children)
interface ThemeProviderProps {
  children: ReactNode;
}

export function ThemeProvider({ children }: ThemeProviderProps) {
  const [theme, setTheme] = useState<string>(() => {
    return localStorage.getItem('theme') || 'light';
  });

  useEffect(() => {
    document.documentElement.className = theme;
    localStorage.setItem('theme', theme);
  }, [theme]);

  const toggle = () => {
    setTheme(prev => (prev === 'dark' ? 'light' : 'dark'));
  };

  return (
    <ThemeContext.Provider value={{ theme, toggle }}>
      {children}
    </ThemeContext.Provider>
  );
}

// Hook personalizado com verificação de contexto
export function useTheme(): ThemeContextType {
  const context = useContext(ThemeContext);
  if (context === undefined) {
    throw new Error('useTheme deve ser usado dentro de um ThemeProvider');
  }
  return context;
}