// Profile.jsx
export default function Profile() {
  return (
    <main className="max-w-4xl mx-auto px-4 py-12">
      <div className="flex flex-wrap items-center gap-6 bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-2xl p-8 mb-8">
        <div className="w-20 h-20 rounded-full bg-accent text-white flex items-center justify-center text-2xl font-bold">J</div>
        <div><h1 className="text-2xl font-bold">João Silva</h1><p className="text-gray-600 dark:text-gray-400">joao@email.com</p><p className="text-sm text-gray-500">Membro desde Janeiro 2025</p></div>
        <a href="#" className="ml-auto px-6 py-2 border border-gray-300 dark:border-gray-600 rounded-xl font-semibold text-sm hover:border-accent hover:text-accent transition-all">Editar Perfil</a>
      </div>
      <div className="grid grid-cols-3 gap-4 mb-10">
        <div className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-6 text-center"><div className="text-3xl font-bold text-accent">12</div><div className="text-sm text-gray-500">Pedidos</div></div>
        <div className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-6 text-center"><div className="text-3xl font-bold text-accent">R$ 3.599</div><div className="text-sm text-gray-500">Total Gasto</div></div>
        <div className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-6 text-center"><div className="text-3xl font-bold text-accent">5</div><div className="text-sm text-gray-500">Avaliações</div></div>
      </div>
      <h2 className="text-xl font-bold mb-4">Histórico de Pedidos</h2>
      <div className="space-y-4">
        <div className="flex items-center gap-4 bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-4">
          <span className="text-lg font-bold text-gray-400">#1</span>
          <div className="flex-1"><strong>Notebook Pro X1</strong><p className="text-sm text-gray-500">Pedido #1023 - Entregue em 15/01/2025</p></div>
          <span className="text-green-600 bg-green-100 dark:bg-green-900 px-3 py-1 rounded-full text-xs font-semibold">Entregue</span>
          <strong>R$ 4.599</strong>
        </div>
        <div className="flex items-center gap-4 bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-xl p-4">
          <span className="text-lg font-bold text-gray-400">#2</span>
          <div className="flex-1"><strong>Headphone Studio</strong><p className="text-sm text-gray-500">Pedido #1056 - Em transporte</p></div>
          <span className="text-yellow-600 bg-yellow-100 dark:bg-yellow-900 px-3 py-1 rounded-full text-xs font-semibold">Em transporte</span>
          <strong>R$ 899</strong>
        </div>
      </div>
    </main>
  );
}