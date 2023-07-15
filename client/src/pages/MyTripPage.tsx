import { MypageSideNav } from '@/components';
import Preparing from '@/components/common/Preparing';
import useAuthRedirect from '@/hooks/useAuthRedirect';
import instance from '@/queries/axiosinstance';
import { EMPTY_TOKEN } from '@/redux/slices/authSlice';
import { useEffect } from 'react';
import { Link, Navigate, useNavigate } from 'react-router-dom';

interface MyTripPageProps {}

const MyTripPage = ({}: MyTripPageProps) => {
  const auth = useAuthRedirect();
  if (auth.isRedirect) return auth.naviComponent;

  return (
    <div className=" flex">
      <MypageSideNav />
      <Preparing />
    </div>
  );
};

export default MyTripPage;
