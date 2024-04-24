import PropTypes from "prop-types";
import "../../styles/molecules/card/card.css";

const Card = ({ width, padding, height, margin, children }) => {
  const cardStyle = {
    width: width || "30%",
    padding: padding || "10px",
    margin: margin || "auto",
    height: height || "auto",
  };
  return (
    <div className="card" style={cardStyle} >
      {children}
    </div>
  );
};

Card.propTypes = {
  width: PropTypes.string,
  padding: PropTypes.string,
  height: PropTypes.string,
  margin: PropTypes.string,
};

export default Card;
