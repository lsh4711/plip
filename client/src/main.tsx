import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import App from './App';

import '@/styles/animation.css';
import '@/styles/global.css';
import '@/styles/index.css';
import FootPrintPage from './pages/FootPrintPage';
import MyPage from './pages/MyPage';
import MyRecordPage from './pages/MyRecordPage';
import MyTripPage from './pages/MyTripPage';
import NotFound from './pages/NotFound';
import PlanDetailPage from './pages/PlanDetailPage';
import PlanMapPage from './pages/PlanMapPage';
import PlanPage from './pages/PlanPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <NotFound />,
  },
  {
    path: 'mypage',
    element: <MyPage />,
    children: [
      { path: 'mypage/mytrip', element: <MyTripPage /> },
      { path: 'mypage/myrecord', element: <MyRecordPage /> },
      { path: 'mypage/footprint', element: <FootPrintPage /> },
    ],
  },
  { path: 'plan', element: <PlanPage /> },
  { path: 'plan/map', element: <PlanMapPage /> },
  { path: 'plan/detail', element: <PlanDetailPage /> },
]);

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <RouterProvider router={router} />
);
