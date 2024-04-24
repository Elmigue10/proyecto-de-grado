import Card from "../../molecules/card/Card";

const CardSpecs = ({ spec }) => {
  return (
    <Card width={"40%"} padding={"30px"}>
      <p>
        <b>{spec.nombre}</b>
      </p>
      <p>{spec.valor}</p>
    </Card>
  );
};

export default CardSpecs;
