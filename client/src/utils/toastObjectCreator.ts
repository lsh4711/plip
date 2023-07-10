import { ToastInterface } from '@/redux/slices/toastSlice';

const DEFAULT_VALUE: ToastInterface = {
  type: 'default',
  content: '',
  visible: true,
};

export const toastObjectCreator = (arg: string | ToastInterface) => {
  let toastObject = {} as ToastInterface;
  if (typeof arg === 'string') {
    Object.keys(DEFAULT_VALUE).forEach((key) => {
      toastObject[key] = DEFAULT_VALUE[key];
    });
    toastObject['id'] = Number(new Date());
    toastObject['content'] = arg;
  } else {
    Object.keys(DEFAULT_VALUE).forEach((key) => {
      toastObject[key] = arg[key] ? arg[key] : DEFAULT_VALUE[key];
    });
    toastObject['id'] = Number(new Date());
  }
  console.log(toastObject);
  return toastObject;
};
