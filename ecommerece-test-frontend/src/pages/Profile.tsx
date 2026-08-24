// src/pages/Profile.tsx
import { useEffect, useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { listOrders } from '../api/orders';
import type { OrderResponse, UserResponse } from '../types';
import Spinner from '../components/ui/Spinner';
import { Link } from 'react-router-dom';
import { notify } from '../utils/toast';

export default function Profile() {
  const { userLogin, isAuthenticated } = useAuth();
  const [user, setUser] = useState<UserResponse | null>(null);
  const [orders, setOrders] = useState<OrderResponse[]>([]);
  const [loading, setLoading] = useState(true);

const handleSave = async () => {
  try {
    // chamada para atualizar usuário
    notify.success('Perfil atualizado com sucesso!');
  } catch {
    notify.error('Não foi possível atualizar o perfil');
  }
};

useEffect(() => {
  const load = async () => {
    try {
      // carregar dados
    } catch {
      notify.error('Erro ao carregar histórico de pedidos');
    }
  };
  load();
}, []);

  if (loading) return <Spinner />;

  return (
    <main className="max-w-6xl mx-auto px-4 py-8">
      <div className="flex flex-col md:flex-row items-start gap-8">
        {/* Card do usuário */}
        <aside className="w-full md:w-1/3 bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-2xl p-6">
          <div className="flex items-center gap-4 mb-6">
            <div className="w-16 h-16 rounded-full bg-accent text-white flex items-center justify-center text-2xl font-bold">
              {user?.username?.charAt(0)?.toUpperCase() || 'U'}
            </div>
            <div>
              <h1 className="text-xl font-bold">{user?.username || 'Usuário'}</h1>
              <p className="text-sm text-gray-500">{user?.login || userLogin}</p>
            </div>
          </div>
          <div className="space-y-2 text-sm">
            <p className="text-gray-600 dark:text-gray-400">
              <span className="font-semibold">Role:</span>{' '}
              {user?.role || 'USER'}
            </p>
          </div>
          <div className="mt-6 space-y-2">
            <Link
              to="/orders"
              className="block w-full text-center py-2 bg-accent text-white rounded-lg hover:bg-accent-dark transition-colors"
            >
              Meus Pedidos
            </Link>
            <button
              className="w-full py-2 border border-gray-300 dark:border-gray-600 rounded-lg text-gray-700 dark:text-gray-300 hover:border-accent"
            >
              Editar Perfil
            </button>
            <button
              className="w-full py-2 text-red-500 border border-red-300 dark:border-red-700 rounded-lg hover:bg-red-50 dark:hover:bg-red-900/20"
            >
              Excluir Conta
            </button>
          </div>
        </aside>

        {/* Configurações e pedidos */}
        <div className="flex-1 space-y-8">
          <section className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-2xl p-6">
            <h2 className="text-lg font-bold mb-4">Configurações da Conta</h2>
            <form className="space-y-4">
              <div>
                <label className="block text-sm font-semibold mb-1">Nome</label>
                <input
                  type="text"
                  defaultValue={user?.username || ''}
                  className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-gray-50 dark:bg-gray-700"
                />
              </div>
              <div>
                <label className="block text-sm font-semibold mb-1">E-mail</label>
                <input
                  type="email"
                  defaultValue={user?.login || ''}
                  disabled
                  className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-gray-100 dark:bg-gray-600 opacity-70"
                />
              </div>
              <div>
                <label className="block text-sm font-semibold mb-1">Nova Senha</label>
                <input
                  type="password"
                  placeholder="Digite para alterar"
                  className="w-full px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg bg-gray-50 dark:bg-gray-700"
                />
              </div>
              <button
                type="submit"
                className="px-6 py-2 bg-accent text-white rounded-lg hover:bg-accent-dark"
              >
                Salvar Alterações
              </button>
            </form>
          </section>

          <section>
            <h2 className="text-lg font-bold mb-4">Histórico de Pedidos</h2>
            {orders.length === 0 ? (
              <p className="text-gray-500">Você ainda não possui pedidos.</p>
            ) : (
              <div className="space-y-4">
                {orders.map((order) => (
                  <div
                    key={order.orderId}
                    className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-4 flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4"
                  >
                    <div>
                      <p className="font-semibold">
                        Pedido #{order.orderId}
                      </p>
                      <p className="text-sm text-gray-500">
                        {new Date(order.createdAt).toLocaleDateString()}
                      </p>
                    </div>
                    <span
                      className={`px-3 py-1 rounded-full text-xs font-semibold ${
                        order.status === 'PAID'
                          ? 'bg-green-100 text-green-700 dark:bg-green-900 dark:text-green-300'
                          : 'bg-yellow-100 text-yellow-700 dark:bg-yellow-900 dark:text-yellow-300'
                      }`}
                    >
                      {order.status}
                    </span>
                    <p className="font-bold">R$ {order.total.toFixed(2)}</p>
                  </div>
                ))}
              </div>
            )}
          </section>
        </div>
      </div>
    </main>
  );
}