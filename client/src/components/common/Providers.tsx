import { Provider as RTKProvider } from 'react-redux';
import { DropDownMenuProvider } from '@/contexts/DropDownMenuContext';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';

import { store as RTKStore } from '@/stores/store';

interface ProvidersProps {
  children: React.ReactNode;
}

const Providers = ({ children }: ProvidersProps) => {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        suspense: true,
      },
    },
  });

  return (
    <RTKProvider store={RTKStore}>
      <QueryClientProvider client={queryClient}>
        <DropDownMenuProvider>{children}</DropDownMenuProvider>
      </QueryClientProvider>
    </RTKProvider>
  );
};

export default Providers;
