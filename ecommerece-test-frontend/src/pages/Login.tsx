// Login.jsx

import { useState } from "react";


export default function Login() {

  return (
    <main className="max-w-md mx-auto px-4 py-24">
      <div className="bg-white dark:bg-gray-800 border border-gray-200 dark:border-gray-700 rounded-3xl shadow-2xl p-12">
        <h2 className="text-2xl font-bold text-center mb-8">Entrar na Conta</h2>
        <div className="mb-5"><label className="block text-sm font-semibold mb-2">E-mail</label><input type="email" placeholder="seu@email.com" className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:bg-white dark:focus:bg-gray-800 outline-none transition-colors" /></div>
        <div className="mb-5"><label className="block text-sm font-semibold mb-2">Senha</label><input type="password" placeholder="••••••" className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-xl bg-gray-50 dark:bg-gray-700 focus:border-accent focus:bg-white dark:focus:bg-gray-800 outline-none transition-colors" /></div>
        <button className="w-full bg-accent hover:bg-accent-dark text-white font-semibold py-3 rounded-xl transition-all mt-2">Entrar</button>
        <p className="text-center mt-6 text-sm text-gray-500 dark:text-gray-400">Não tem conta? <a href="/register" className="text-accent font-semibold hover:text-accent-dark">Registre-se</a></p>
        <p className="text-center mt-2 text-sm text-gray-500"><a href="#" className="hover:text-accent">Esqueci minha senha</a></p>
      </div>
    </main>
  );
}