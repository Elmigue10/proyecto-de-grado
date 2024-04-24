import { useState } from "react";
import Check from "../../assest/check.png";
import Button from "../../atoms/button/Button";

const ForgotPassword = () => {
  const [email, setEmail] = useState("");
  const [emailSent, setEmailSent] = useState(false);
  const [userExists, setUserExists] = useState(false);

  const handleForgotPassword = async (e) => {
    console.log("this is the forgot password button");

    e.preventDefault();

    try {
      const response = await fetch(
        `http://ec2-54-158-4-132.compute-1.amazonaws.com:8080/umb/v1/user/forgot-password?correoElectronico=${email}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        console.log("correo enviado");
        setEmailSent(true);
      } else if (response.status === 404) {
        setEmailSent(false);
        setUserExists(true);
      } else {
        console.log("error al enviar el correo de recuperación");
      }
    } catch (error) {
      console.error("Error al crear usuario:", error);
      alert("Hubo un problema al enviar el correo de recuperación");
    }
  };

  return (
    <div>
      {!emailSent ? (
        <div className="forgot-password">
          <h1 className="title">¿Tienes problemas para ingresar?</h1>
          <p className="forgot-password-text">
            Introduce tu correo electrónico y te enviarmos un enlace para
            restablecer tu contraseña.
          </p>
          <div className="forgot-password-wrapper">
            <div className="right">
              <form
                className="forgot-password-form"
                onSubmit={handleForgotPassword}
              >
                <label className="label">Correo electrónico</label>
                <input
                  className="forgot-pw-input"
                  type="email"
                  value={email}
                  name="email"
                  placeholder="Correo electrónico"
                  onChange={(e) => setEmail(e.target.value)}
                  required
                />
                {!userExists ? (
                  <p></p>
                ) : (
                  <p className="no-coincidence-error-text">
                    Este usuario no existe
                  </p>
                )}
                <Button
                  alignSelf="center"
                  text="Enviar correo de restauración"
                  width={"244px"}
                />
              </form>
            </div>
          </div>
        </div>
      ) : (
        <div className="forgot-password">
          <img src={Check} alt="" className="mail-send-check-img" />
          <h1 className="title">Correo electrónico enviado</h1>
          <p className="forgot-password-text">
            Hemos enviado un correo electronico al correo especificado con un
            enlace para que restablezcas tu contraseña.
          </p>
        </div>
      )}
    </div>
  );
};

export default ForgotPassword;
