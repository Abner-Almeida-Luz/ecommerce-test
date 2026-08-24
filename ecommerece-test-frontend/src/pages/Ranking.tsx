// Ranking.jsx
export default function Ranking() {
  const rankingItems = [
    { position: '🥇', name: 'Produto 1', sales: '1.284 vendidos', price: 'R$ 4.599' },
    { position: '🥈', name: 'Produto 2', sales: '967 vendidos', price: 'R$ 899' },
    { position: '🥉', name: 'Produto 3', sales: '745 vendidos', price: 'R$ 1.299' },
  ];

  return (
    <main className="max-w-4xl mx-auto px-4 py-12 min-h-screen">
      <h1 className="text-3xl font-bold mb-2">Top Produtos Mais Vendidos</h1>
      <p className="text-gray-500 mb-10">Os favoritos da nossa comunidade</p>
      <div className="space-y-4">
        {rankingItems.map((item) => (
          <div key={item.name} className="flex items-center gap-4 bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-4">
            <span className="text-2xl font-bold text-yellow-600 w-8">{item.position}</span>
            <div className="flex-1">
              <h3 className="font-bold">{item.name}</h3>
              <p className="text-sm text-gray-500">{item.sales}</p>
            </div>
            <div className="text-right">
              <div className="font-bold text-lg">{item.price}</div>
              <a href="#" className="text-sm text-accent hover:underline">Ver Produto</a>
            </div>
          </div>
        ))}
      </div>
    </main>
  );
}