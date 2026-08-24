// src/components/layout/Navbar.tsx
import { useState } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import ThemeToggle from '../layout/ThemeToggle';
import { useAuth } from '../../contexts/AuthContext';
import Button from '../ui/Button';
import { notify } from '../../utils/toast';

interface NavbarProps {
  isActive: boolean;
}

const linkClass = ({ isActive }: NavbarProps) =>
  `text-2xl font-medium border-b-2 py-1 transition-all ${
    isActive
      ? 'text-accent border-accent'
      : 'text-gray-600 dark:text-gray-400 border-transparent hover:text-gray-900 dark:hover:text-white hover:border-accent'
  }`;

export default function Navbar() {
  const [menuOpen, setMenuOpen] = useState(false);
  const { logout, isAuthenticated, isAdmin } = useAuth();
  const navigate = useNavigate();

const handleLogout = () => {
  logout();
  notify.success('Logout realizado com sucesso');
  navigate('/login');
};

  const navLinks = [
    { to: '/', label: 'Home', end: true },
    { to: '/products', label: 'Produtos' },
    { to: '/ranking', label: 'Ranking' },
    { to: '/blog', label: 'Blog' },
    { to: '/cart', label: 'Carrinho' },
    { to: '/orders', label: 'Pedidos' },
  ];

  return (
    <header className="sticky top-0 z-50 bg-white/90 dark:bg-gray-900/90 backdrop-blur-md border-b border-gray-200 dark:border-gray-800">
      <div className="max-w-7xl mx-auto px-4 h-[12vh] flex items-center justify-between">
        <Link to="/" className="text-3xl font-bold flex items-center gap-2">
          <span>🛍️</span>
          <span className="bg-gradient-to-r from-accent to-accent-light bg-clip-text text-transparent">
            Nexus Store
          </span>
        </Link>

        {/* Menu desktop */}
        <nav className="hidden lg:flex items-center gap-6">
          {navLinks.map(({ to, label, end }) => (
            <NavLink
              key={to}
              to={to}
              end={end}
              className={linkClass}
            >
              {label}
            </NavLink>
          ))}

          {isAdmin && (
            <NavLink to="/create_product" className={linkClass}>
              Criar Produto
            </NavLink>
          )}

          <NavLink to="/profile" className={linkClass}>
            Perfil
          </NavLink>

          {!isAuthenticated ? (
            <NavLink to="/login" className={linkClass}>
              Login
            </NavLink>
          ) : (
            <Button
              variant="ghost"
              size="sm"
              onClick={handleLogout}
              className="text-gray-700 dark:text-gray-300 hover:text-red-500"
            >
              Sair
            </Button>
          )}

          <div className="flex items-center gap-4 ml-4">
            <input
              type="text"
              placeholder="Buscar..."
              className="pl-10 pr-4 py-2 text-sm bg-gray-100 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-full focus:outline-none focus:border-accent w-48 lg:w-56"
            />
            <ThemeToggle />
          </div>
        </nav>

        {/* Botão hamburguer */}
        <button
          className="lg:hidden p-2"
          onClick={() => setMenuOpen(!menuOpen)}
        >
          <svg
            className="w-6 h-6"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth="2"
              d="M4 6h16M4 12h16M4 18h16"
            />
          </svg>
        </button>
      </div>

      {/* Menu mobile */}
      {menuOpen && (
        <div className="lg:hidden border-t border-gray-200 dark:border-gray-700 bg-white dark:bg-gray-900 px-4 py-4 space-y-3">
          {navLinks.map(({ to, label, end }) => (
            <NavLink
              key={to}
              to={to}
              end={end}
              className="block py-2 text-gray-600 dark:text-gray-400"
              onClick={() => setMenuOpen(false)}
            >
              {label}
            </NavLink>
          ))}
          {isAdmin && (
            <NavLink
              to="/create_product"
              className="block py-2 text-gray-600 dark:text-gray-400"
              onClick={() => setMenuOpen(false)}
            >
              Criar Produto
            </NavLink>
          )}
          <NavLink
            to="/profile"
            className="block py-2 text-gray-600 dark:text-gray-400"
            onClick={() => setMenuOpen(false)}
          >
            Perfil
          </NavLink>
          {!isAuthenticated ? (
            <NavLink
              to="/login"
              className="block py-2 text-gray-600 dark:text-gray-400"
              onClick={() => setMenuOpen(false)}
            >
              Login
            </NavLink>
          ) : (
            <Button
              variant="ghost"
              size="sm"
              onClick={handleLogout}
              className="w-full text-left py-2 text-gray-600 dark:text-gray-400"
            >
              Sair
            </Button>
          )}
          <input
            type="text"
            placeholder="Buscar..."
            className="w-full pl-4 pr-4 py-2 text-sm bg-gray-100 dark:bg-gray-800 border rounded-full"
          />
        </div>
      )}
    </header>
  );
}