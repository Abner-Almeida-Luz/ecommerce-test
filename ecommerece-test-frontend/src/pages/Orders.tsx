// src/pages/Orders.tsx
import { useEffect, useState } from 'react';
import { listOrders } from '../api/orders';
import type { OrderResponse } from '../types';
import Spinner from '../components/ui/Spinner';
import { notify } from '../utils/toast';

export default function Orders() {
  const [orders, setOrders] = useState<OrderResponse[]>([]);
  const [loading, setLoading] = useState(true);

useEffect(() => {
  const load = async () => {
    try {
      const data = await listOrders();
      setOrders(data);
    } catch {
      notify.error('Erro ao carregar seus pedidos');
    } finally {
      setLoading(false);
    }
  };
  load();
}, []);

  if (loading) return <Spinner />;

  return (
    <main className="max-w-4xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Meus Pedidos</h1>
      {orders.length === 0 ? (
        <p className="text-gray-500">Nenhum pedido encontrado.</p>
      ) : (
        <div className="space-y-4">
          {orders.map((order) => (
            <div
              key={order.orderId}
              className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-6"
            >
              <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-4">
                <div>
                  <h2 className="font-semibold text-lg">Pedido #{order.orderId}</h2>
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
              </div>
              {order.items && order.items.length > 0 && (
                <ul className="space-y-2">
                  {order.items.map((item) => (
                    <li key={item.orderItemId} className="flex justify-between text-sm">
                      <span>{item.productName} x {item.quantity}</span>
                      <span>R$ {item.total.toFixed(2)}</span>
                    </li>
                  ))}
                </ul>
              )}
              <div className="flex justify-between mt-4 pt-4 border-t border-gray-200 dark:border-gray-700">
                <span className="font-semibold">Total</span>
                <span className="font-bold text-accent">R$ {order.total.toFixed(2)}</span>
              </div>
            </div>
          ))}
        </div>
      )}
    </main>
  );
}