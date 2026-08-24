import { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { notify } from '../utils/toast';
import { getProduct } from '../api/products';
import { addItem } from '../api/carts';
import { useAuth } from '../contexts/AuthContext';
import type { ProductResponse } from '../types';
import Spinner from '../components/ui/Spinner';

export default function ProductDetail() {
  const { id } = useParams<{ id: string }>();
  const [product, setProduct] = useState<ProductResponse | null>(null);
  const [quantity, setQuantity] = useState(1);
  const [loading, setLoading] = useState(true);
  const [adding, setAdding] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { isAuthenticated, cartId } = useAuth();

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await getProduct(Number(id));
        setProduct(data);
      } catch {
        setError('Produto não encontrado.');
      } finally {
        setLoading(false);
      }
    };
    if (id) load();
  }, [id]);

  const handleAddToCart = async () => {
    if (!cartId || !product) {
      notify.error('Faça login para adicionar ao carrinho');
      return;
    }
    if (product.stock <= 0) {
      notify.error('Produto fora de estoque');
      return;
    }
    setAdding(true);
    try {
      await addItem({ cartId, productId: product.productId, quantity });
      notify.success('Produto adicionado ao carrinho!');
    } catch {
      notify.error('Não foi possível adicionar o produto');
    } finally {
      setAdding(false);
    }
  };

  if (loading) return <div className="flex justify-center py-16"><Spinner /></div>;
  if (error || !product) return <p className="text-center text-red-500 py-16">{error}</p>;

  return (
    <main className="max-w-7xl mx-auto px-4 py-8">
      {/* Breadcrumb */}
      <div className="text-sm text-gray-500 mb-6">
        <Link to="/" className="hover:text-accent">Home</Link> /{' '}
        <Link to="/products" className="hover:text-accent">Produtos</Link> /{' '}
        <span>{product.name}</span>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-12">
        <div className="bg-gray-100 dark:bg-gray-800 rounded-3xl overflow-hidden aspect-square">
          <img src={product.imageUrl} alt={product.name} className="w-full h-full object-cover" />
        </div>
        <div>
          <span className="text-xs font-semibold text-accent uppercase tracking-wider bg-accent/10 px-3 py-1 rounded-full">
            {product.categoryName}
          </span>
          <h1 className="text-4xl font-bold mt-4 mb-2">{product.name}</h1>
          <div className="flex items-center gap-4 mb-6">
            <div className="text-yellow-500">★★★★★</div>
            <span className="text-gray-500">(avaliações)</span>
          </div>
          <div className="text-4xl font-bold text-accent mb-8">
            R$ {Number(product.price).toFixed(2)}
          </div>
          <div className="bg-gray-50 dark:bg-gray-800 rounded-2xl p-6 mb-8">
            <h3 className="font-semibold mb-2">Descrição</h3>
            <p className="text-gray-600 dark:text-gray-400">{product.description}</p>
          </div>
          <div className="flex items-center gap-4 mb-8">
            <input
              type="number"
              min="1"
              max={product.stock}
              value={quantity}
              onChange={(e) => setQuantity(Math.max(1, Number(e.target.value)))}
              className="w-20 px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700"
              aria-label="Quantidade"
            />
            <span className="text-green-600 font-medium">
              {product.stock > 0 ? `Estoque: ${product.stock}` : 'Fora de estoque'}
            </span>
          </div>
          {isAuthenticated ? (
            <button
              onClick={handleAddToCart}
              disabled={adding || product.stock <= 0}
              className="w-full bg-accent hover:bg-accent-dark text-white font-semibold py-4 rounded-xl text-lg transition-all disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {adding ? 'Adicionando...' : 'Adicionar ao Carrinho'}
            </button>
          ) : (
            <Link
              to="/login"
              className="w-full bg-accent hover:bg-accent-dark text-white font-semibold py-4 rounded-xl text-lg transition-all text-center block"
            >
              Faça login para comprar
            </Link>
          )}
        </div>
      </div>
    </main>
  );
}