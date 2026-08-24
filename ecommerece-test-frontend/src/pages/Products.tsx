import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { notify } from '../utils/toast';
import { listProducts } from '../api/products';
import { addItem } from '../api/carts';
import { useAuth } from '../contexts/AuthContext';
import type { ProductSummaryResponse, Page } from '../types';
import Spinner from '../components/ui/Spinner';

export default function Products() {
  const [products, setProducts] = useState<ProductSummaryResponse[]>([]);
  const [pageInfo, setPageInfo] = useState<Page<ProductSummaryResponse> | null>(null);
  const [page, setPage] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [category, setCategory] = useState('');
  const [minPrice, setMinPrice] = useState('');
  const [maxPrice, setMaxPrice] = useState('');
  const [sort, setSort] = useState('name');
  const { isAuthenticated, cartId } = useAuth();

  useEffect(() => {
    const load = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await listProducts({
          page,
          size: 12,
          name: '',
          categoryId: Number(category),
          minPrice: Number(minPrice),
          maxPrice: Number(maxPrice),
        });
        setProducts(data.content);
        setPageInfo(data);
      } catch {
        setError('Não foi possível carregar os produtos.');
      } finally {
        setLoading(false);
      }
    };
    load();
  }, [page, category, minPrice, maxPrice, sort]);

const handleAddToCart = async (productId: number) => {
  if (!cartId) {
    notify.error('Faça login para adicionar ao carrinho');
    return;
  }
  try {
    await addItem({ cartId, productId, quantity: 1 });
    notify.success('Produto adicionado ao carrinho!');
  } catch {
    notify.error('Não foi possível adicionar o produto');
  }
};

  if (loading) return <div className="flex justify-center py-16"><Spinner /></div>;
  if (error) return <p className="text-center text-red-500 py-16">{error}</p>;

  return (
    <main className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Todos os Produtos</h1>

      {/* Filtros */}
      <div className="flex flex-wrap gap-4 mb-10 p-6 bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-2xl">
        <select
          value={category}
          onChange={(e) => setCategory(e.target.value)}
          className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700 text-sm"
        >
          <option value="">Todas as Categorias</option>
          <option value="1">Casa e Decoração</option>
          <option value="2">Beleza e Cuidados</option>
          <option value="3">Esportes e Lazer</option>
          <option value="4">Moda e Acessórios</option>
        </select>
        <input
          type="number"
          placeholder="Preço mín."
          value={minPrice}
          onChange={(e) => setMinPrice(e.target.value)}
          className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700 text-sm w-28"
        />
        <input
          type="number"
          placeholder="Preço máx."
          value={maxPrice}
          onChange={(e) => setMaxPrice(e.target.value)}
          className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700 text-sm w-28"
        />
        <select
          value={sort}
          onChange={(e) => setSort(e.target.value)}
          className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700 text-sm"
        >
          <option value="name">Mais Relevantes</option>
          <option value="price,asc">Menor Preço</option>
          <option value="price,desc">Maior Preço</option>
        </select>
        <button
          onClick={() => { setPage(0); }}
          className="px-6 py-2 bg-accent hover:bg-accent-dark text-white font-semibold rounded-xl transition-all"
        >
          Filtrar
        </button>
      </div>

      {/* Grid de produtos */}
      {products.length === 0 ? (
        <p className="text-center text-gray-500 py-12">Nenhum produto encontrado.</p>
      ) : (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
          {products.map((product) => (
            <div key={product.productId} className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-2xl overflow-hidden hover:border-accent-light hover:shadow-xl transition-all hover:-translate-y-1 group">
              <Link to={`/product/${product.productId}`}>
                <div className="h-52 bg-gray-100 dark:bg-gray-700 flex items-center justify-center">
                 <span className="text-xs font-semibold text-accent uppercase">
                {product.categoryName}
                 </span>
                  <img
                    src={product.imageUrl}
                    alt={product.name}
                    className="w-full h-full object-cover group-hover:scale-105 transition-transform"
                  />
                </div>
                <div className="p-6">
                  <h3 className="text-lg font-bold mt-2 mb-1">{product.name}</h3>
                  <div className="text-2xl font-bold mb-4">R$ {Number(product.price).toFixed(2)}</div>
                  <div className="text-sm text-gray-500 mb-2">Estoque: {product.stock}</div>
                </div>
              </Link>
              {isAuthenticated && (
                <button
                  onClick={() => handleAddToCart(product.productId)}
                  className="mx-6 mb-6 w-[calc(100%-48px)] bg-accent hover:bg-accent-dark text-white font-semibold py-2.5 rounded-xl transition-colors"
                >
                  Adicionar ao carrinho
                </button>
              )}
            </div>
          ))}
        </div>
      )}

      // Home.tsx (trecho da paginação)
{pageInfo && pageInfo.totalPages > 1 && (
  <div className="flex justify-center items-center gap-2 mt-10">
    <button
      onClick={() => setPage(p => Math.max(0, p - 1))}
      disabled={pageInfo.first}
      className="px-4 py-2 bg-gray-200 dark:bg-gray-700 rounded-lg disabled:opacity-50"
    >
      Anterior
    </button>
    <span className="px-4 py-2">
      Página {page + 1} de {pageInfo.totalPages}
    </span>
    <button
      onClick={() => setPage(p => p + 1)}
      disabled={pageInfo.last}
      className="px-4 py-2 bg-gray-200 dark:bg-gray-700 rounded-lg disabled:opacity-50"
    >
      Próxima
    </button>
  </div>
)}
    </main>
  );
}