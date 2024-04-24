import PropTypes from "prop-types";
import "../../styles/atoms/input/input.css";

const Input = ({
  type,
  value,
  name,
  placeholder,
  defaultValue,
  onChange,
  padding,
  margin,
  required,
  height,
  width,
}) => {
  const inputStyle = {
    padding: padding || "15px 20px",
    margin: margin || "20px",
    width: width || "auto",
    height: height || "auto",
  };
  return (
    <input
      className="input"
      style={inputStyle}
      type={type}
      value={value}
      name={name}
      placeholder={placeholder}
      defaultValue={defaultValue}
      onChange={onChange}
      required={required}
    />
  );
};

Input.propTypes = {
  type: PropTypes.string,
  value: PropTypes.string,
  name: PropTypes.string,
  placeholder: PropTypes.string,
  defaultValue: PropTypes.string,
  padding: PropTypes.string,
  height: PropTypes.string,
  width: PropTypes.string,
  margin: PropTypes.string,
  onChange: PropTypes.func,
  required: PropTypes.bool,
};

export default Input;
