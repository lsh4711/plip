import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';

import '@/styles/animation.css';
import '@/styles/global.css';
import '@/styles/index.css';

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
