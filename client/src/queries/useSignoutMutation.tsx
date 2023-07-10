import React from 'react';
import BASE_URL from './BASE_URL';

interface useSignoutMutationProps {}

const deleteSignoutFetch = async () => {
  const response = await fetch(`${BASE_URL}/api/users`, {
    method: 'DELETE',
    credentials: 'include',
  });
  const result = await response.json();
  const ok = response.ok;

  return {
    result,
    ok,
    response,
  };
};

const useSignoutMutation = ({}: useSignoutMutationProps) => {
  return <div>useSignoutMutation</div>;
};

export default useSignoutMutation;
