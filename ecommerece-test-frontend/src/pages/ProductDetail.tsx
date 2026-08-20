// ProductDetail.jsx

import { useState } from 'react';
import { Link } from 'react-router-dom';
import toast from 'react-hot-toast';
import { useProducts } from '../hooks/useProducts';
import { useAuth } from '../contexts/AuthContext';
import { addItem } from '../api/carts';
import Card from '../components/ui/Card';
import Button from '../components/ui/Button';
import Spinner from '../components/ui/Spinner';
import PageContainer from '../components/layout/PageContainer';

export default function ProductDetail() {
  const [page, setPage] = useState(0);
  const { products, pageInfo, loading, error } = useProducts(page);
  const { isAuthenticated, cartId } = useAuth();

  const handleAddToCart = async (productId: number) => {
    if (!cartId) {
      toast.error('Faça login para adicionar ao carrinho');
      return;
    }
    try {
      await addItem({
        cartId, productId, quantity:1
      });
      toast.success('Adicionado ao carrinho!');
    } catch {
      toast.error('Não foi possível adicionar');
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
  
    <PageContainer>
          <h1 className="text-2xl font-bold mb-6">Produtos</h1>
          <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
            {products.map((product) => (
              <Card key={product.productId}>
                <Link to={`/products/${product.productId}`}>
                  <img
                    src={product.imageUrl}
                    alt={product.name}
                    className="w-full h-full object-cover"
                  />
                  <h2 className="text-4xl font-bold mt-4 mb-2">{product.name}</h2>
                  <span className="text-xs font-semibold text-accent uppercase tracking-wider bg-accent/10 px-3 py-1 rounded-full">Categoria</span>
                  <div className="flex items-center gap-4 mb-6">
                  <div className="text-yellow-500">★★★★★ 4.9</div>
                 <span className="text-gray-500">(128 avaliações)</span>
               </div>
                </Link>
                <div className="bg-gray-50 dark:bg-gray-800 rounded-2xl p-6 mb-8">
                 <h3 className="font-semibold mb-2">Descrição</h3>
                 <p className="text-gray-600 dark:text-gray-400">{/*product.description*/}</p>
               </div>
                <div className="flex items-center gap-4 mb-8">
            <input type="number" defaultValue="1" min="1" className="w-20 px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700" />
            <span className="text-4xl font-bold text-accent mb-8">R$ {product.price.toFixed(2)}</span>
            <span className="text-green-600 font-medium">Estoque: {product.stock}</span>
          </div>
                <div className="flex justify-between items-center mt-2">
                </div>
                {isAuthenticated && (
                  <Button
                    className="w-full bg-accent hover:bg-accent-dark text-white font-semibold py-4 rounded-xl text-lg transition-all"
                    onClick={() => handleAddToCart(product.productId)}
                  >
                    Adicionar ao carrinho
                  </Button>
                )}
              </Card>
            ))}
          </div>
    
          {pageInfo && pageInfo.totalPages > 1 && (
            <div className="flex justify-center gap-2 mt-8">
              <Button
                variant="secondary"
                disabled={pageInfo.first}
                onClick={() => setPage((p) => p - 1)}
              >
                Anterior
              </Button>
              <span className="px-4 py-2">
                {page + 1} de {pageInfo.totalPages}
              </span>
              <Button
                variant="secondary"
                disabled={pageInfo.last}
                onClick={() => setPage((p) => p + 1)}
              >
                Próxima
              </Button>
            </div>
          )}
        </PageContainer>
  );
  
  {/*<main className="max-w-7xl mx-auto px-4 py-8">
      <div className="text-sm text-gray-500 mb-6">
        <a href="/" className="hover:text-accent">Home</a> / <a href="/products" className="hover:text-accent">Produtos</a> / <span>Notebook Pro X1</span>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-2 gap-12">
        <div className="bg-gray-100 dark:bg-gray-800 rounded-3xl overflow-hidden aspect-square">
          <img src="https://placehold.co/600x600/6C5CE7/FFF?text=Notebook+Pro" alt="Produto" className="w-full h-full object-cover" />
        </div>
        <div>
          <span className="text-xs font-semibold text-accent uppercase tracking-wider bg-accent/10 px-3 py-1 rounded-full">Tecnologia</span>
          <h1 className="text-4xl font-bold mt-4 mb-2">Notebook Pro X1</h1>
          <div className="flex items-center gap-4 mb-6">
            <div className="text-yellow-500">★★★★★ 4.9</div>
            <span className="text-gray-500">(128 avaliações)</span>
          </div>
          <div className="text-4xl font-bold text-accent mb-8">R$ 4.599,00</div>
          <div className="bg-gray-50 dark:bg-gray-800 rounded-2xl p-6 mb-8">
            <h3 className="font-semibold mb-2">Descrição</h3>
            <p className="text-gray-600 dark:text-gray-400">Notebook de alta performance com Intel Core i7, 16GB RAM, SSD 512GB NVMe. Tela IPS 15.6" Full HD.</p>
          </div>
          <div className="flex items-center gap-4 mb-8">
            <input type="number" defaultValue="1" min="1" className="w-20 px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700" />
            <span className="text-green-600 font-medium">Em estoque</span>
          </div>
          <button className="w-full bg-accent hover:bg-accent-dark text-white font-semibold py-4 rounded-xl text-lg transition-all">
            Adicionar ao Carrinho
          </button>
        </div>
      </div>

      <section className="mt-20">
        <h2 className="text-2xl font-bold mb-8">Comentários (128)</h2>
        <div className="space-y-4">
          <div className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-6">
            <div className="flex items-center gap-3 mb-3">
              <div className="w-10 h-10 rounded-full bg-accent text-white flex items-center justify-center font-bold">M</div>
              <div><strong>Maria Silva</strong><p className="text-sm text-gray-500">15 de Janeiro, 2025</p></div>
              <div className="ml-auto text-yellow-500">★★★★★</div>
            </div>
            <p className="text-gray-600 dark:text-gray-400">Excelente notebook! Super rápido e a tela é incrível.</p>
          </div>
        </div>
      </section>
    </main>*/}
}

















