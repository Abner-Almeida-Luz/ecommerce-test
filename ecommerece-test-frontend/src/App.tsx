import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { ThemeProvider } from './contexts/ThemeContext';
import Navbar from './components/layout/Navbar';
import Footer from './components/layout/Footer';
import Home from './pages/Home';
import Products from './pages/Products';
import ProductDetail from './pages/ProductDetail';
import Ranking from './pages/Ranking';
import Blog from './pages/Blog';
import Login from './pages/Login';
import Register from './pages/Register';
import Profile from './pages/Profile';
import { AuthProvider } from './contexts/AuthContext';
import CreateProduct from './pages/CreateProduct';


/*
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Footer from './components/layout/Footer';
import { ThemeProvider } from './contexts/ThemeContext';
//import { Toaster } from 'react-hot-toast';
import ProtectedRoute from './components/ProtectedRoute';
import CNavbar from './components/layout/CNavBar';
import LoginPage from './cpages/LoginPage';
import RegisterPage from './pages/Register';
import ProductsPage from './cpages/ProductsPage';
import ProductDetailPage from './pages/ProductDetail';
import CartPage from './cpages/CartPage';
import OrdersPage from './cpages/OrdersPage';
//import AddProductPage from './pages/AddProductPage';
*/
/*
<!-- index.html, dentro de <head> -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
*/

export default function App() {
  return (
    <AuthProvider>
      <ThemeProvider>
      <BrowserRouter>
        <div className="flex flex-col min-h-screen bg-white dark:bg-gray-900 text-gray-900 dark:text-gray-100 font-sans transition-colors">
      
          <Navbar />
          <main className="flex-1">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/create_product" element={<CreateProduct />} />
              <Route path="/products" element={<Products />} />
              <Route path="/product/:id" element={<ProductDetail />} />
              <Route path="/ranking" element={<Ranking />} />
              <Route path="/blog" element={<Blog />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
              <Route path="/profile" element={<Profile />} />
            </Routes>
            </main>
           {/* <CNavbar />
          <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/products" element={<ProductsPage />} />
          <Route path="/products/:id" element={<ProductDetailPage />} />
          <Route path="/cart" element={<ProtectedRoute><CartPage /></ProtectedRoute>} />
          <Route path="/orders" element={<ProtectedRoute><OrdersPage /></ProtectedRoute>} />
          {/*<Route path="/products/new" element={<ProtectedRoute adminOnly><AddProductPage /></ProtectedRoute>} />
          <Route path="*" element={<Navigate to="/products" replace />} />
          </Routes>*/}
          <Footer />
        </div>
      </BrowserRouter>
    </ThemeProvider>
    </AuthProvider>
  );
}