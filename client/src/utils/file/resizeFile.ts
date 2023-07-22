import Resizer from 'react-image-file-resizer';
// @ts-expect-error https://github.com/onurzorluer/react-image-file-resizer/issues/68
const resizer: typeof Resizer = Resizer.default || Resizer;

export const resizeFile = (file: File) => {
  return new Promise((resolve) => {
    resizer.imageFileResizer(
      file,
      300,
      300,
      'JPEG',
      100,
      0,
      (uri) => {
        resolve(uri);
      },
      'file',
      200,
      200
    );
  });
};
