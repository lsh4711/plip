import FileResizer from 'react-image-file-resizer';

export const resizeFile = (file: File) => {
  return new Promise((resolve) => {
    FileResizer.imageFileResizer(
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
