import Card from "../../molecules/card/Card";

const CardComment = ({ comment }) => {
  return (
    <Card width={"60%"} >
      <p>
        <b>Usuario: {comment.username}</b>
      </p>
      <p>{comment.content}</p>
    </Card>
  );
};

export default CardComment;
