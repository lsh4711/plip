import { QueryCache, QueryClient, QueryClientProvider } from '@tanstack/react-query';
import useError from '@/hooks/useError';
import MapDetailProvider from '@/contexts/MapDetailProvider';

interface ProvidersProps {
  children: React.ReactNode;
}

const Providers = ({ children }: ProvidersProps) => {
  const errorHandler = useError();
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        suspense: true,
        retry: 0,
      },
      mutations: {
        onError: errorHandler,
      },
    },
    queryCache: new QueryCache({
      onError: () => errorHandler,
    }),
  });

  return (
    <QueryClientProvider client={queryClient}>
      <MapDetailProvider>{children}</MapDetailProvider>
    </QueryClientProvider>
  );
};

export default Providers;
