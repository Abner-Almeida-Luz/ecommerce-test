// Ranking.jsx
export default function Ranking() {
  return (
    <main className="max-w-4xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-2">Top Produtos Mais Vendidos</h1>
      <p className="text-gray-500 mb-10">Os favoritos da nossa comunidade</p>
      <div className="space-y-4">
        <div className="flex items-center gap-4 bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-4">
          <span className="text-2xl font-bold text-yellow-600 w-8">🥇</span>
          <img src="https://placehold.co/80x80/6C5CE7/FFF?text=1" alt="1" className="w-16 h-16 rounded-lg object-cover" />
          <div className="flex-1"><h3 className="font-bold">Notebook Pro X1</h3><p className="text-sm text-gray-500">1.284 vendidos</p></div>
          <div className="text-right"><div className="font-bold text-lg">R$ 4.599</div><a href="/product/1" className="text-sm text-accent hover:underline">Ver Produto</a></div>
        </div>
      </div>
    </main>
  );
}