import PropTypes from "prop-types";
import "../../styles/atoms/image/image.css";

const Image = ({
  width,
  height,
  margin,
  padding,
  src,
  alt,
  minWidth,
  cursor,
  onClick,
}) => {
  const imageStyle = {
    width: width || "auto",
    height: height || "auto",
    margin: margin || "auto",
    padding: padding || "auto",
    minWidth: minWidth || "auto",
    cursor: cursor || "none",
  };

  return (
    <img
      className="image"
      style={imageStyle}
      src={src}
      alt={alt}
      onClick={onClick}
    />
  );
};

Image.propTypes = {
  width: PropTypes.string,
  height: PropTypes.string,
  margin: PropTypes.string,
  padding: PropTypes.string,
  minWidth: PropTypes.string,
  cursor: PropTypes.string,
  alt: PropTypes.string,
  src: PropTypes.string.isRequired,
  onClick: PropTypes.func,
};

export default Image;
