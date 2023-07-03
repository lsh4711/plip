import ReactDOM from 'react-dom/client';
import App from './App';
import Providers from './components/common/Providers';

import '@/styles/animation.css';
import '@/styles/global.css';
import '@/styles/index.css';

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <Providers>
    <App />
  </Providers>
);
