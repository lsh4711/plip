type Props = {
  content: string;
  styles?: string;
};

const TextContent = ({ content, styles }: Props) => {
  return (
    <div className={styles}>
      {content.split('\n').map((line) => {
        return (
          <span>
            {line} <br />
          </span>
        );
      })}
    </div>
  );
};

export default TextContent;
