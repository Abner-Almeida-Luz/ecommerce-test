// src/contexts/AuthContext.tsx
import { createContext, useContext, useState, ReactNode } from 'react';
import { login as loginApi } from '../api/auth';
import { viewCart } from '../api/carts';
import { UserRole } from '../types';

interface AuthContextType {
  token: string | null;
  userLogin: string | null;
  cartId: number | null;
  isAuthenticated: boolean;
  isAdmin: boolean;
  login: (login: string, password: string) => Promise<void>;
  logout: () => void;
  setCartId: (id: number | null) => void;
}

const AuthContext = createContext<AuthContextType | null>(null);

export function AuthProvider({ children }: { children: ReactNode }) {
  const [token, setToken] = useState<string | null>(
    localStorage.getItem('token')
  );
  const [userLogin, setUserLogin] = useState<string | null>(
    localStorage.getItem('userLogin')
  );
  const [role, setRole] = useState<UserRole | null>(
    localStorage.getItem('role') as UserRole | null
  );
  const [cartId, setCartId] = useState<number | null>(
    localStorage.getItem('cartId')
      ? Number(localStorage.getItem('cartId'))
      : null
  );

  const login = async (loginStr: string, password: string) => {
    const data = await loginApi({ login: loginStr, password });
    localStorage.setItem('token', data.acessToken);
    localStorage.setItem('refreshToken', data.refreshToken);
    localStorage.setItem('userLogin', loginStr);
    setToken(data.acessToken);
    setUserLogin(loginStr);

    try {
      const cart = await viewCart();
      localStorage.setItem('cartId', String(cart.cartId));
      setCartId(cart.cartId);
    } catch {
      setCartId(null);
    }
  };

  const logout = () => {
    localStorage.clear();
    setToken(null);
    setUserLogin(null);
    setRole(null);
    setCartId(null);
  };

  return (
    <AuthContext.Provider
      value={{
        token,
        userLogin,
        cartId,
        isAuthenticated: !!token,
        isAdmin: role === 'ADMIN',
        login,
        logout,
        setCartId,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context)
    throw new Error('useAuth deve ser usado dentro de AuthProvider');
  return context;
}