// Register.jsx
export default function Register() {
  return (
    <main className="max-w-md mx-auto px-4 py-24">
      <div className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-3xl shadow-2xl p-12">
        <h2 className="text-2xl font-bold text-center mb-8">Criar Conta</h2>
        <div className="mb-4"><label className="block text-sm font-semibold mb-2">Nome</label><input type="text" placeholder="Seu nome" className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent outline-none" /></div>
        <div className="mb-4"><label className="block text-sm font-semibold mb-2">E-mail</label><input type="email" placeholder="seu@email.com" className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent outline-none" /></div>
        <div className="mb-4"><label className="block text-sm font-semibold mb-2">Senha</label><input type="password" placeholder="••••••" className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent outline-none" /></div>
        <div className="mb-4"><label className="block text-sm font-semibold mb-2">Confirmar Senha</label><input type="password" placeholder="••••••" className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent outline-none" /></div>
        <button className="w-full bg-accent hover:bg-accent-dark text-white font-semibold py-3 rounded-xl transition-all mt-2">Criar Conta</button>
        <p className="text-center mt-6 text-sm text-gray-500 dark:text-gray-400">Já tem conta? <a href="/login" className="text-accent font-semibold">Faça login</a></p>
      </div>
    </main>
  );
}