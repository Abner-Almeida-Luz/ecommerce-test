// Blog.jsx
export default function Blog() {
  return (
    <main className="max-w-7xl mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-2">Blog Nexus Store</h1>
      <p className="text-gray-500 mb-10">Dicas, guias e novidades sobre tecnologia</p>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
        <article className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-2xl overflow-hidden hover:shadow-lg">
          <div className="h-40 bg-gradient-to-br from-accent to-accent-light flex items-center justify-center"><span className="text-white text-4xl">📚</span></div>
          <div className="p-6"><span className="text-xs font-semibold text-accent uppercase">Guias</span><h3 className="text-lg font-bold mt-2 mb-2">Como escolher notebook para dev</h3><p className="text-sm text-gray-600 dark:text-gray-400 mb-4">Dicas essenciais para programadores.</p><a href="#" className="text-sm font-semibold text-accent">Ler artigo →</a></div>
        </article>
      </div>
    </main>
  );
}