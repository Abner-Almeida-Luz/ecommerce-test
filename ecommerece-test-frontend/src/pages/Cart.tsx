// src/pages/Cart.tsx
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { viewCart, removeItem } from '../api/carts';
import { checkout } from '../api/orders';
import { useAuth } from '../contexts/AuthContext';
import type { CartResponse } from '../types';
import Spinner from '../components/ui/Spinner';
import { notify } from '../utils/toast';

export default function Cart() {
  const [cart, setCart] = useState<CartResponse | null>(null);
  const [loading, setLoading] = useState(true);
  const { cartId } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!cartId) return;
    const load = async () => {
      setLoading(true);
      try {
        const data = await viewCart();
        setCart(data);
      } catch {
        notify.error('Erro ao carregar carrinho');
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [cartId]);

const handleRemove = async (itemId: number) => {
  try {
    await removeItem(itemId);
    const updated = await viewCart();
    setCart(updated);
    notify.success('Item removido do carrinho');
  } catch {
    notify.error('Não foi possível remover o item');
  }
};

const handleCheckout = async () => {
  try {
    await checkout();
    notify.success('Pedido realizado com sucesso!');
    navigate('/orders');
  } catch (error: any) {
    if (error.response?.status === 400) {
      notify.error('Estoque insuficiente para um ou mais itens.');
    } else {
      notify.error('Falha no checkout. Tente novamente.');
    }
  }
};

  if (loading) return <Spinner />;

  return (
    <main className="max-w-4xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Seu Carrinho</h1>
      {!cart || cart.cartItems.length === 0 ? (
        <p className="text-gray-500">Seu carrinho está vazio.</p>
      ) : (
        <div className="space-y-4">
          {cart.cartItems.map((item) => (
            <div
              key={item.cartItemId}
              className="flex items-center gap-4 bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-4"
            >
              
              <img
                src={item.imageUrl || 'https://placehold.co/80x80'}
                alt={item.productName}
                className="w-16 h-16 rounded-lg object-cover"
              />
              <div className="flex-1">
                <h3 className="font-semibold">{item.productName}</h3>
                <p className="text-sm text-gray-500">
                  Quantidade: {item.quantity}
                </p>
              </div>
              <p className="font-bold">R$ {Number(item.total).toFixed(2)}</p>
              <button
                onClick={() => handleRemove(item.cartItemId)}
                className="p-2 text-red-500 hover:text-red-700"
              >
                Remover
              </button>
            </div>
          ))}
          <div className="flex justify-between items-center mt-6 pt-6 border-t border-gray-200 dark:border-gray-700">
            <span className="text-xl font-bold">Total</span>
            <span className="text-xl font-bold text-accent">
              R$ {cart.cartItems.reduce((acc, item) => acc + Number(item.total), 0).toFixed(2)}
            </span>
          </div>
          <button
            onClick={handleCheckout}
            className="w-full mt-4 bg-accent hover:bg-accent-dark text-white font-semibold py-3 rounded-xl transition-colors"
          >
            Finalizar Compra
          </button>
        </div>
      )}
    </main>
  );
}