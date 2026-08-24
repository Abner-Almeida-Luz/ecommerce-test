// src/utils/toast.ts
import toast from 'react-hot-toast';

export const notify = {
  success(message: string) {
    toast.success(message);
  },
  error(message: string) {
    toast.error(message);
  },
  info(message: string) {
    toast(message, { icon: 'ℹ️' });
  },
  warning(message: string) {
    toast(message, { icon: '⚠️' });
  },
  loading(message: string) {
    return toast.loading(message);
  },
  dismiss(toastId?: string) {
    if (toastId) {
      toast.dismiss(toastId);
    } else {
      toast.dismiss();
    }
  }
};