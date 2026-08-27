// src/hooks/useProducts.ts
import { useEffect, useState } from 'react';
import { listProducts } from '../api/products';
import { Page, ProductSummaryResponse } from '../types';

export function useProducts(page: number) {
  const [data, setData] = useState<Page<ProductSummaryResponse> | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    setLoading(true);
    listProducts({ page, size: 12, name: '', categoryId: 0, minPrice: 0, maxPrice: 0 })
      .then(setData)
      .catch(() => setError('Não foi possível carregar os produtos'))
      .finally(() => setLoading(false));
  }, [page]);

  return { products: data?.content ?? [], pageInfo: data, loading, error };
}
