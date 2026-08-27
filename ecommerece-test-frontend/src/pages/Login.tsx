import { useState} from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { notify } from '../utils/toast';
import { useAuth } from '../contexts/AuthContext';

export default function Login() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ login: '', password: '' });
  const [loading, setLoading] = useState(false);

const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault();
  setLoading(true);
  try {
    await login(form.login, form.password);
    notify.success('Login realizado com sucesso!');
    navigate('/');
  } catch {
    notify.error('Credenciais inválidas. Tente novamente.');
  } finally {
    setLoading(false);
  }
};
  return (
    <main className="min-h-screen flex items-center justify-center px-4 py-16">
      <form onSubmit={handleSubmit} className="w-full max-w-md bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-3xl shadow-2xl p-8 md:p-12 space-y-5">
        <h2 className="text-2xl font-bold text-center">Entrar na Conta</h2>

        <div>
          <label htmlFor="login" className="block text-sm font-semibold mb-2">E-mail</label>
          <input
            id="login"
            type="email"
            placeholder="seu@email.com"
            value={form.login}
            onChange={(e) => setForm(prev => ({ ...prev, login: e.target.value }))}
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:ring-1 focus:ring-accent outline-none transition-colors"
            required
          />
        </div>

        <div>
          <label htmlFor="password" className="block text-sm font-semibold mb-2">Senha</label>
          <input
            id="password"
            type="password"
            placeholder="••••••"
            value={form.password}
            onChange={(e) => setForm(prev => ({ ...prev, password: e.target.value }))}
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:ring-1 focus:ring-accent outline-none transition-colors"
            required
          />
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-accent hover:bg-accent-dark text-white font-semibold py-3 rounded-xl transition-all disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {loading ? 'Entrando...' : 'Entrar'}
        </button>

        <p className="text-center text-sm text-gray-500 dark:text-gray-400">
          Não tem conta?{' '}
          <Link to="/register" className="text-accent font-semibold hover:text-accent-dark">
            Registre-se
          </Link>
        </p>
        <p className="text-center text-sm text-gray-500">
          <a href="#" className="hover:text-accent">Esqueci minha senha</a>
        </p>
      </form>
    </main>
  );
}