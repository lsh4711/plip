import ReactDOM from 'react-dom/client';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import App from './App';
import Providers from './components/common/Providers';
import store from '@/redux/store';
import ModalProvider from '@/contexts/modal/ModalProvider';
import '@/styles/animation.css';
import '@/styles/global.css';
import '@/styles/index.css';
import { lazy, Suspense } from 'react';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { Provider } from 'react-redux';
import LoadingSpinner from './components/atom/LoadingSpinner';
import NotFound from './pages/NotFound';
import { ErrorBoundary } from 'react-error-boundary';
import ErrorFallback from './components/helper/ErrorFallback';
import LoadingPage from './pages/LoadingPage';

const Home = lazy(() => import('./pages/Home'));
const SignUpPage = lazy(() => import('./pages/SignUpPage'));
const SignOutPage = lazy(() => import('./pages/SignOutPage'));
const LoginPage = lazy(() => import('./pages/LoginPage'));
const ResetPasswordPage = lazy(() => import('./pages/ResetPasswordPage'));
const PlanPage = lazy(() => import('./pages/PlanPage'));
const MyPage = lazy(() => import('./pages/MyPage'));
const MyTripPage = lazy(() => import('./pages/MyTripPage'));
const MyRecordPage = lazy(() => import('./pages/MyRecordPage'));
const FootPrintPage = lazy(() => import('./pages/FootPrintPage'));
const PlanMapPage = lazy(() => import('./pages/PlanMapPage'));
const PlanDetailPage = lazy(() => import('./pages/PlanDetailPage'));
const Bookmark = lazy(() => import('./pages/Bookmark'));
const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <NotFound />,
    children: [
      { index: true, element: <Home /> },
      { path: 'login', element: <LoginPage /> },
      { path: 'signup', element: <SignUpPage /> },
      { path: 'signout', element: <SignOutPage /> },
      { path: 'login/password', element: <ResetPasswordPage /> },
      { path: 'plan', element: <PlanPage /> },
      { path: 'mypage', element: <MyPage /> },
      { path: 'mypage/mytrip', element: <MyTripPage /> },
      { path: 'mypage/myrecord', element: <MyRecordPage /> },
      { path: 'mypage/footprint', element: <FootPrintPage /> },
      { path: 'mypage/bookmark', element: <Bookmark /> },
      { path: 'loading', element: <LoadingPage /> },
    ],
  },
  { path: 'plan/map/:region', element: <PlanMapPage /> },
  { path: 'plan/detail', element: <PlanDetailPage /> },
]);

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <Provider store={store}>
    <Providers>
      <ModalProvider>
        <ErrorBoundary FallbackComponent={ErrorFallback}>
          <Suspense fallback={<LoadingPage />}>
            <RouterProvider router={router} />
            <ReactQueryDevtools />
          </Suspense>
        </ErrorBoundary>
      </ModalProvider>
    </Providers>
  </Provider>
);
