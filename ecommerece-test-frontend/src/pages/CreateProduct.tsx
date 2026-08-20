import { useState, FormEvent } from 'react';
import { ProductRequest } from '../types';
import toast from 'react-hot-toast';
import { createProduct } from '../api/products';

export default function CreateProduct() {
  const [form, setForm] = useState<ProductRequest>({
  categoryId: 0,
  name: '',
  description: '',
  price: 0,
  stock: 0,
  imageUrl: '',
});

  const handleChange = (field: keyof ProductRequest, value: string | number) => {
    setForm(prev => ({ ...prev, [field]: value }));
  };

const handleSubmit = async (e: FormEvent) => {
  e.preventDefault();
  try {
    await createProduct(form);
    toast.success('Produto criado!');
  } catch {
    toast.error('Erro ao criar produto');
  }
};

  return (
    <main className="max-w-md mx-auto px-4 py-24">
      <form
        onSubmit={handleSubmit}
        className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-3xl shadow-2xl p-12"
      >
        <h2 className="text-2xl font-bold text-center mb-8">Criar produto</h2>

        {/* Categoria */}
        <div className="mb-5">
          <label className="block text-sm font-semibold mb-2">Categoria</label>
          <input
            value={form.categoryId}
            onChange={(e) => handleChange('categoryId', e.target.value)}
            type="number"
            placeholder="ID da categoria"
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:bg-white dark:focus:bg-gray-800 outline-none transition-colors"
            required
          />
        </div>

        {/* Nome */}
        <div className="mb-5">
          <label className="block text-sm font-semibold mb-2">Nome</label>
          <input
            value={form.name}
            onChange={(e) => handleChange('name', e.target.value)}
            type="text"
            placeholder="Nome do produto"
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:bg-white dark:focus:bg-gray-800 outline-none transition-colors"
            required
          />
        </div>

        {/* Descrição */}
        <div className="mb-5">
          <label className="block text-sm font-semibold mb-2">Descrição</label>
          <input
            value={form.description}
            onChange={(e) => handleChange('description', e.target.value)}
            type="text"
            placeholder="Descrição"
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:bg-white dark:focus:bg-gray-800 outline-none transition-colors"
            required
          />
        </div>

        {/* Preço */}
        <div className="mb-5">
          <label className="block text-sm font-semibold mb-2">Preço</label>
          <input
            value={form.price}
            onChange={(e) => handleChange('price', e.target.value)}
            type="number"
            placeholder="Preço"
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:bg-white dark:focus:bg-gray-800 outline-none transition-colors"
            required
          />
        </div>

        {/* Estoque */}
        <div className="mb-5">
          <label className="block text-sm font-semibold mb-2">Estoque</label>
          <input
            value={form.stock}
            onChange={(e) => handleChange('stock', e.target.value)}
            type="number"
            placeholder="Quantidade em estoque"
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:bg-white dark:focus:bg-gray-800 outline-none transition-colors"
            required
          />
        </div>

        {/* URL da imagem */}
        <div className="mb-5">
          <label className="block text-sm font-semibold mb-2">Imagem</label>
          <input
            value={form.imageUrl}
            onChange={(e) => handleChange('imageUrl', e.target.value)}
            type="text"
            placeholder="URL da imagem"
            className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:bg-white dark:focus:bg-gray-800 outline-none transition-colors"
            required
          />
        </div>

        {/* Botão de submit */}
        <button
          type="submit"
          className="w-full bg-accent hover:bg-accent-dark text-white font-semibold py-3 rounded-xl transition-all mt-2"
        >
          Criar
        </button>
      </form>
    </main>
  );
}