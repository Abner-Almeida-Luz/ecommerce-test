// Home.jsx
import { useState } from 'react';
import { Link } from 'react-router-dom';
import { notify } from '../utils/toast';
import { useProducts } from '../hooks/useProducts';
import { useAuth } from '../contexts/AuthContext';
import { addItem } from '../api/carts';
import Spinner from '../components/ui/Spinner';

export default function Home() {
  const [page, setPage] = useState(0);
  const { products, pageInfo, loading, error } = useProducts(page);
  const { isAuthenticated, cartId } = useAuth();

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

  if (loading)
    return (
      <div className="flex justify-center py-16">
        <Spinner />
      </div>
    );
  if (error)
    return <p className="text-center text-danger-600 py-16">{error}</p>;

  return (
    <>
      <section className="py-24 lg:py-32 bg-gradient-to-br from-gray-50 to-white dark:from-gray-800 dark:to-gray-900 text-center">
        <h1 className="text-4xl sm:text-5xl lg:text-6xl font-extrabold tracking-tight mb-6">
          Encontre os melhores{' '}
          <span className="bg-gradient-to-r from-accent to-accent-light bg-clip-text text-transparent">
            produtos digitais
          </span>
        </h1>
        <p className="text-lg sm:text-xl text-gray-600 dark:text-gray-400 max-w-2xl mx-auto mb-10">
          Experiência de compra moderna, rápida e segura.
        </p>
        <div className="flex flex-wrap justify-center gap-4">
          <a href="/products" className="px-8 py-3 bg-accent hover:bg-accent-dark text-white font-semibold rounded-xl transition-all hover:-translate-y-0.5">
            Explorar Produtos
          </a>
          <a href="/ranking" className="px-8 py-3 border border-gray-300 dark:border-gray-600 text-gray-900 dark:text-gray-100 font-semibold rounded-xl hover:border-accent hover:text-accent transition-all">
            Ver Ranking
          </a>
        </div>
      </section>

      <section className="max-w-7xl mx-auto px-4 py-16">
        <div className="flex justify-between items-center mb-10">
          <h2 className="text-3xl font-bold">Produtos em Destaque</h2>
          <a href="/products" className="text-sm font-semibold text-accent hover:text-accent-dark">Ver Todos →</a>
         </div>
         
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
          
          {products.map((product) => (
  <div
    key={product.productId}
    className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-2xl overflow-hidden hover:border-accent-light hover:shadow-xl transition-all hover:-translate-y-1 group"
  >
    {/* Imagem */}
    <div className="h-52 bg-gray-100 dark:bg-gray-700 flex items-center justify-center">
      <img
        src={product.imageUrl}
        alt={product.name}
        className="w-full h-full object-cover group-hover:scale-105 transition-transform"
      />
    </div>

    {/* Conteúdo */}
    <div className="p-6">
      <span className="text-xs font-semibold text-accent uppercase">
        {product.categoryName}
      </span>
      <h3 className="text-lg font-bold mt-2 mb-1">{product.name}</h3>

      {/* Opcional: avaliação (se o ProductResponse tiver rating) */}
      {/* <div className="text-yellow-500 text-sm mb-3">★★★★★ {product.rating}</div> */}

      <div className="text-2xl font-bold mb-4">
        R$ {Number(product.price).toFixed(2)}
      </div>

      <Link
        to={`/products/${product.productId}`}
        className="block text-center py-2.5 border border-gray-300 dark:border-gray-600 rounded-xl font-semibold text-sm hover:border-accent hover:text-accent transition-colors"
      >
        Ver Detalhes
      </Link>

      {isAuthenticated && (
        <button
          onClick={() => handleAddToCart(product.productId)}
          className="w-full mt-3 bg-accent hover:bg-accent-dark text-white font-semibold py-2.5 rounded-xl transition-colors"
        >
          Adicionar ao carrinho
        </button>
      )}
    </div>
  </div>
))}

          <div className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-2xl overflow-hidden hover:border-accent-light hover:shadow-xl transition-all hover:-translate-y-1 group">
            <div className="h-52 bg-gray-100 dark:bg-gray-700 flex items-center justify-center">
              <img src="https://placehold.co/400x400/6C5CE7/FFF?text=Notebook" alt="Notebook" className="w-full h-full object-cover group-hover:scale-105 transition-transform" />
            </div>
            <div className="p-6">
              <span className="text-xs font-semibold text-accent uppercase">Tecnologia</span>
              <h3 className="text-lg font-bold mt-2 mb-1">Notebook Pro X1</h3>
              <div className="text-yellow-500 text-sm mb-3">★★★★★ 4.9</div>
              <div className="text-2xl font-bold mb-4">R$ 4.599</div>
              <a href="/product/1" className="block text-center py-2.5 border border-gray-300 dark:border-gray-600 rounded-xl font-semibold text-sm hover:border-accent hover:text-accent">
                Ver Detalhes
              </a>
            </div>
          </div>

          {/* Repita para outros produtos */}
        </div>
      </section>

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
    </>
  );
}