// Products.jsx

export default function Products() {


  return (
    <main className="max-w-7xl mx-auto px-4 py-8">
      <div className="flex flex-wrap items-center gap-4 mb-8">
        <h1 className="text-3xl font-bold">Todos os Produtos</h1>
        <span className="text-gray-500 text-sm">12 produtos encontrados</span>
      </div>

      <div className="flex flex-wrap gap-4 mb-10 p-6 bg-gray-50 dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-2xl">
        <select className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700 text-sm">
          <option>Todas as Categorias</option>
          <option>Tecnologia</option>
          <option>Áudio</option>
          <option>Wearables</option>
        </select>
        <input type="number" placeholder="Preço mín." className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700 text-sm w-28" />
        <input type="number" placeholder="Preço máx." className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700 text-sm w-28" />
        <select className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-xl bg-white dark:bg-gray-700 text-sm">
          <option>Mais Relevantes</option>
          <option>Menor Preço</option>
          <option>Maior Preço</option>
        </select>
        <button className="px-6 py-2 bg-accent hover:bg-accent-dark text-white font-semibold rounded-xl transition-all">Filtrar</button>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
        {/* Mesmo card da Home, repetir com dados diferentes */}
      </div>

      <div className="flex justify-center gap-2 mt-12">
        <a href="#" className="w-10 h-10 flex items-center justify-center rounded-lg bg-accent text-white font-semibold">1</a>
        <a href="#" className="w-10 h-10 flex items-center justify-center rounded-lg border border-gray-300 dark:border-gray-600 hover:bg-gray-100 dark:hover:bg-gray-800">2</a>
        <a href="#" className="w-10 h-10 flex items-center justify-center rounded-lg border border-gray-300 dark:border-gray-600 hover:bg-gray-100 dark:hover:bg-gray-800">3</a>
        <a href="#" className="w-10 h-10 flex items-center justify-center rounded-lg border border-gray-300 dark:border-gray-600">→</a>
      </div>
    </main>
  );
}