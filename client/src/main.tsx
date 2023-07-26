import ModalProvider from '@/contexts/modal/ModalProvider';
import store from '@/redux/store';
import '@/styles/animation.css';
import '@/styles/global.css';
import '@/styles/index.css';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import { lazy, Suspense } from 'react';
import ReactDOM from 'react-dom/client';
import { ErrorBoundary } from 'react-error-boundary';
import { Provider } from 'react-redux';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import App from './App';
import Providers from './components/common/Providers';
import AxiosProvider from './components/helper/AxiosProvider';
import PlanSharePage from './pages/PlanSharePage';
import ErrorFallback from './components/helper/ErrorFallback';
import ToastContainer from './components/ui/toast/ToastContainer';
import MypageLayout from './MypageLayout';
import LoadingPage from './pages/LoadingPage';
import NotFound from './pages/NotFound';
import OauthRedirect from './pages/OauthRedirect';

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
const EventPage = lazy(() => import('./pages/EventPage'));

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
      { path: 'loading', element: <LoadingPage /> },
      { path: 'event', element: <EventPage /> },
      {
        path: 'mypage/',
        element: <MypageLayout />,
        children: [
          { path: 'info', element: <MyPage /> },
          { path: 'mytrip', element: <MyTripPage /> },
          { path: 'myrecord', element: <MyRecordPage /> },
          { path: 'footprint', element: <FootPrintPage /> },
          { path: 'signout', element: <SignOutPage /> },
          { path: 'bookmark', element: <Bookmark /> },
        ],
      },
    ],
  },
  {
    path: 'plan/map/:id',
    element: (
      <>
        <ToastContainer />
        <PlanMapPage />
      </>
    ),
  },
  {
    path: 'plan/detail/:id',
    element: (
      <>
        <ToastContainer />
        <PlanDetailPage />
      </>
    ),
  },
  { path: 'oauth', element: <OauthRedirect /> },
  { path: 'plan/detail/:id/share', element: <PlanSharePage /> },
]);

ReactDOM.createRoot(document.getElementById('root') as HTMLElement).render(
  <Provider store={store}>
    <Providers>
      <ModalProvider>
        <ErrorBoundary FallbackComponent={ErrorFallback}>
          <Suspense fallback={<LoadingPage />}>
            <AxiosProvider>
              <RouterProvider router={router} />
              <ReactQueryDevtools />
            </AxiosProvider>
          </Suspense>
        </ErrorBoundary>
      </ModalProvider>
    </Providers>
  </Provider>
);
