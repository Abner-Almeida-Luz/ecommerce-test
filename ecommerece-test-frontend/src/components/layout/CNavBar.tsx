import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../contexts/AuthContext';
import Button from '../ui/Button';

export default function Navbar() {
  const { isAuthenticated, isAdmin, logout } = useAuth();
  const navigate = useNavigate();

  return (
    <nav className="bg-neutral-900 text-white px-6 py-4 flex justify-between items-center shadow-card">
      <Link to="/products" className="text-xl font-bold">E-commerce</Link>
      <div className="flex items-center gap-4">
        <Link to="/products" className="hover:text-neutral-300">Produtos</Link>
        {isAuthenticated ? (
          <>
            <Link to="/cart" className="hover:text-neutral-300">Carrinho</Link>
            <Link to="/orders" className="hover:text-neutral-300">Pedidos</Link>
            {isAdmin && (
              <Link to="/products/new" className="hover:text-neutral-300">+ Produto</Link>
            )}
            <Button variant="ghost" size="sm" className="text-white" onClick={() => { logout(); navigate('/login'); }}>
              Sair
            </Button>
          </>
        ) : (
          <Link to="/login">
            <Button size="sm">Entrar</Button>
          </Link>
        )}
      </div>
    </nav>
  );
}