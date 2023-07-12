import { createToastAsyncThunk } from '@/redux/createToastAsyncThunk';
import { AppDispatch } from '@/redux/store';
import { ToastInterface } from '@/redux/slices/toastSlice';
import { useDispatch } from 'react-redux';

const useToast = () => {
  const dispatch = useDispatch<AppDispatch>();
  const toastCreator = (toastcontent: ToastInterface) =>
    dispatch(createToastAsyncThunk(toastcontent));
  return toastCreator;
};

export default useToast;
