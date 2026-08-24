import { useState, FormEvent } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { notify } from '../utils/toast';
import { register } from '../api/auth';
import type { RegisterRequest, UserRole } from '../types';

export default function Register() {
  const navigate = useNavigate();
  const [form, setForm] = useState<RegisterRequest>({
    username: '',
    login: '',
    password: '',
    role: 'USER',
  });
  const [confirmPassword, setConfirmPassword] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (field: keyof RegisterRequest | 'confirmPassword', value: string) => {
    if (field === 'confirmPassword') {
      setConfirmPassword(value);
    } else {
      setForm(prev => ({ ...prev, [field]: value }));
    }
  };

const handleSubmit = async (e: React.FormEvent) => {
  e.preventDefault();
  if (form.password !== confirmPassword) {
    notify.error('As senhas não coincidem');
    return;
  }
  if (form.password.length < 6) {
    notify.error('A senha deve ter pelo menos 6 caracteres');
    return;
  }
  setLoading(true);
  try {
    await register(form);
    notify.success('Conta criada com sucesso!');
    navigate('/login');
  } catch {
    notify.error('Não foi possível criar a conta. Tente novamente.');
  } finally {
    setLoading(false);
  }
};

  return (
    <main className="min-h-screen flex items-center justify-center px-4 py-16">
      <form onSubmit={handleSubmit} className="w-full max-w-md bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-3xl shadow-2xl p-8 md:p-12 space-y-5">
        <h2 className="text-2xl font-bold text-center">Criar Conta</h2>

        <div>
          <label htmlFor="username" className="block text-sm font-semibold mb-2">Nome</label>
          <input
            id="username"
            type="text"
            placeholder="Seu nome"
            value={form.username}
            onChange={(e) => handleChange('username', e.target.value)}
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:ring-1 focus:ring-accent outline-none transition-colors"
            required
          />
        </div>

        <div>
          <label htmlFor="login" className="block text-sm font-semibold mb-2">E-mail</label>
          <input
            id="login"
            type="email"
            placeholder="seu@email.com"
            value={form.login}
            onChange={(e) => handleChange('login', e.target.value)}
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
            onChange={(e) => handleChange('password', e.target.value)}
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:ring-1 focus:ring-accent outline-none transition-colors"
            required
            minLength={6}
          />
        </div>

        <div>
          <label htmlFor="confirmPassword" className="block text-sm font-semibold mb-2">Confirmar Senha</label>
          <input
            id="confirmPassword"
            type="password"
            placeholder="••••••"
            value={confirmPassword}
            onChange={(e) => handleChange('confirmPassword', e.target.value)}
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:ring-1 focus:ring-accent outline-none transition-colors"
            required
          />
        </div>

        <div>
          <label htmlFor="role" className="block text-sm font-semibold mb-2">Tipo de Conta</label>
          <select
            id="role"
            value={form.role}
            onChange={(e) => handleChange('role', e.target.value as UserRole)}
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:ring-1 focus:ring-accent outline-none transition-colors"
          >
            <option value="USER">Usuário</option>
            <option value="ADMIN">Administrador</option>
          </select>
        </div>

        <button
          type="submit"
          disabled={loading}
          className="w-full bg-accent hover:bg-accent-dark text-white font-semibold py-3 rounded-xl transition-all disabled:opacity-50 disabled:cursor-not-allowed"
        >
          {loading ? 'Criando...' : 'Criar Conta'}
        </button>

        <p className="text-center text-sm text-gray-500 dark:text-gray-400">
          Já tem conta?{' '}
          <Link to="/login" className="text-accent font-semibold hover:text-accent-dark">
            Faça login
          </Link>
        </p>
      </form>
    </main>
  );
}