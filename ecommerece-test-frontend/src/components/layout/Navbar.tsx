import { useState } from 'react';
import { Link, NavLink, useNavigate } from 'react-router-dom';
import ThemeToggle from '../layout/ThemeToggle';
import { useAuth } from '../../contexts/AuthContext';
import Button from '../ui/Button';

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

  const { logout } = useAuth();
  const navigate = useNavigate();

  return (
    <header className="sticky top-0 z-50 bg-white/90 dark:bg-gray-900/90 backdrop-blur-md border-b border-gray-200 dark:border-gray-800">
      <div className="max-w-7xl mx-auto px-4 h-[12vh] flex items-center justify-between">
        <Link to="/" className="text-3xl font-bold flex items-center gap-2">
          <span>🛍️</span>
          <span className="bg-gradient-to-r from-accent to-accent-light bg-clip-text text-transparent">
            Nexus Store
          </span>
        </Link>

        <nav className="hidden lg:flex items-center gap-8">
          <NavLink to="/" end className={linkClass}>
            Home
          </NavLink>
          <NavLink to="/create_product" className={linkClass}>
            Criar Produto
          </NavLink>
          <NavLink to="/products" className={linkClass}>
            Produtos
          </NavLink>
          <NavLink to="/ranking" className={linkClass}>
            Ranking
          </NavLink>
          <NavLink to="/blog" className={linkClass}>
            Blog
          </NavLink>
          <NavLink to="/login" className={linkClass}>
            Login
          </NavLink>

          <div className="flex items-center gap-4 ml-6">
            <input
              type="text"
              placeholder="Buscar..."
              className="pl-10 pr-4 py-2 text-sm bg-gray-100 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-full focus:outline-none focus:border-accent w-56"
            />
            <ThemeToggle />
          </div>
        </nav>

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

      {menuOpen && (
        <div className="lg:hidden border-t border-gray-200 dark:border-gray-700 bg-white dark:bg-gray-900 px-4 py-4 space-y-3">
          <NavLink
            to="/"
            className="block py-2 text-gray-600 dark:text-gray-400"
            onClick={() => setMenuOpen(false)}
          >
            Home
          </NavLink>
          <NavLink
            to="/create_product"
            className="block py-2 text-gray-600 dark:text-gray-400"
            onClick={() => setMenuOpen(false)}
          >
            Criar Produto
          </NavLink>
          <NavLink
            to="/products"
            className="block py-2 text-gray-600 dark:text-gray-400"
            onClick={() => setMenuOpen(false)}
          >
            Produtos
          </NavLink>
          <NavLink
            to="/ranking"
            className="block py-2 text-gray-600 dark:text-gray-400"
            onClick={() => setMenuOpen(false)}
          >
            Ranking
          </NavLink>
          <NavLink
            to="/blog"
            className="block py-2 text-gray-600 dark:text-gray-400"
            onClick={() => setMenuOpen(false)}
          >
            Blog
          </NavLink>
          <NavLink
            to="/login"
            className="block py-2 text-gray-600 dark:text-gray-400"
            onClick={() => setMenuOpen(false)}
          >
            Login
          </NavLink>
          <input
            type="text"
            placeholder="Buscar..."
            className="w-full pl-4 pr-4 py-2 text-sm bg-gray-100 dark:bg-gray-800 border rounded-full"
          />
          <Button
            variant="ghost"
            size="sm"
            className="text-white"
            onClick={() => {
              logout();
              navigate('/login');
            }}
          >
            Sair
          </Button>
        </div>
      )}
    </header>
  );
}
