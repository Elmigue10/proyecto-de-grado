import { useState } from "react";
import Button from "../../atoms/button/Button";
import "../../styles/pages/auth/signup.css";

const SignUp = () => {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [noCoincidence, setNoCoincidence] = useState(false);
  const [userAlreadyExists, setUserAlreadyExists] = useState(false);
  const [passwordErrorMessage, setPasswordErrorMessage] = useState("Las contraseñas no coinciden.");

  const handleSignup = async (e) => {
    e.preventDefault();
    setNoCoincidence(false);
    
    try {

      if (password.length < 8){
        setPasswordErrorMessage("La contraseña debe tener mínimo 8 caracteres");
        setNoCoincidence(true);
        return;
      }
  
      if (password !== confirmPassword) {
        setPasswordErrorMessage("Las contraseñas no coinciden.");
        setNoCoincidence(true);
        return;
      }

      const response = await fetch(
        "http://ec2-54-158-4-132.compute-1.amazonaws.com:8080/umb/v1/user/save",
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            nombreCompleto: name,
            correoElectronico: email,
            contrasena: password,
          }),
        }
      );

      if (response.status === 400) {
        setUserAlreadyExists(true);
      } else {
        setUserAlreadyExists(false);
        window.location.href = "/login";
        alert("Usuario creado exitosamente.");
      }
    } catch (error) {
      console.error("Error al crear usuario:", error);
      alert("Hubo un problema al registrarse");
    }
  };

  return (
    <div className="signup">
      <h1 className="title">Crea una cuenta</h1>
      <div className="wrapper">
        <form className="signup-form" onSubmit={handleSignup}>
          <label className="label">Nombre</label>
          <input
            type="text"
            value={name}
            name="name"
            placeholder="Nombre"
            onChange={(e) => setName(e.target.value)}
            required
          />
          <label className="label">Correo electrónico</label>
          <input
            type="email"
            value={email}
            name="email"
            placeholder="Correo electrónico"
            onChange={(e) => setEmail(e.target.value)}
            required
          />
          {!userAlreadyExists ? (
            <p></p>
          ) : (
            <p className="no-coincidence-error-text">Este usuario ya existe</p>
          )}
          <label className="label">Contraseña</label>
          <input
            type="password"
            value={password}
            name="password"
            placeholder="Contraseña"
            onChange={(e) => setPassword(e.target.value)}
            required
          />
          <label className="label">Confirmar contraseña</label>
          <input
            type="password"
            value={confirmPassword}
            name="confirm-password"
            placeholder="Repite tu contraseña"
            onChange={(e) => setConfirmPassword(e.target.value)}
            required
          />
          {!noCoincidence ? (
            <p></p>
          ) : (
            <p className="no-coincidence-error-text">
              {passwordErrorMessage}
            </p>
          )}
          <Button
            className="button login-sign-up"
            text="Registrarse"
            margin={"10px 0"}
            height={"50px"}
            width={"244px"}
          />
        </form>
      </div>
    </div>
  );
};

export default SignUp;
