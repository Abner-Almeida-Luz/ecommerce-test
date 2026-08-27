import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';

import App from './App.tsx';
// @ts-expect-error CSS is loaded as a side effect by the bundler.
import './index.css';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
  </StrictMode>
);
