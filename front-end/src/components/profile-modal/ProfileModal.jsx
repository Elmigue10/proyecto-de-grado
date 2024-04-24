import { useEffect, useState } from "react";
import "../../styles/components/profile-modal/profile-modal.css";
import Input from "../../atoms/input/Input";
import Button from "../../atoms/button/Button";
import { Link } from "react-router-dom";
import {
  extractEmailFromToken,
  validateTokenWithRole,
} from "../../utilities/jwt-utilities";
import Card from "../../molecules/card/Card.jsx";

const ProfileModal = ({ onClose }) => {
  const [isChangePasswordOpen, setIsChangePasswordOpen] = useState(false);
  const [isChangeCredentialsOpen, setIsChangeCredentialsOpen] = useState(false);
  const [oldPassword, setOldPassword] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [newUser, setNewUser] = useState("");
  const [newEmail, setNewEmail] = useState("");
  const [cUser, setCUser] = useState({});
  const [confirmPassword, setConfirmPassword] = useState("");
  const [passwordMismatch, setPasswordMismatch] = useState(false);
  const [passwordChangeSuccess, setPasswordChangeSuccess] = useState(false);
  const [credentialsChangeSuccess, setCredentialsChangeSuccess] =
    useState(false);
  const [showButtons, setShowButtons] = useState(false);
  const [showCredentials, setShowCredentials] = useState(false);
  const [showUpdate, setShowUpdate] = useState(false);
  const [showUpdatePassword, setShowUpdatePassword] = useState(false);
  const [emailExist, setEmailExist] = useState(false);
  const [incorrectPassword, setIncorrectPassword] = useState(false);
  const [passwordMismatchMessage, setPasswordMismatchMessage] = useState("Las contraseñas no coinciden.");

  const [token, setToken] = useState(localStorage.getItem("token"));
  const [email, setEmail] = useState(extractEmailFromToken(token));

  validateTokenWithRole("CLIENTE");

  const handleCloseModal = (e) => {
    if (e.target.classList.contains("modal")) {
      onClose();
    }
  };

  const handleToggleChangePassword = () => {
    setIsChangePasswordOpen(!isChangePasswordOpen);
    setShowButtons(!isChangePasswordOpen);
    setShowUpdate(!isChangePasswordOpen);
  };

  const handleToggleChangeCredentials = () => {
    setIsChangeCredentialsOpen(!isChangeCredentialsOpen);
    setShowCredentials(!isChangeCredentialsOpen);
    setShowButtons(!isChangeCredentialsOpen);
    setShowUpdatePassword(!isChangeCredentialsOpen);
  };

  const handleChangeCredentials = async (e) => {
    e.preventDefault();

    setCredentialsChangeSuccess(false);

    try {
      const uResponse = await fetch(
        `http://ec2-54-158-4-132.compute-1.amazonaws.com:8080/umb/v1/user/update`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({
            id: cUser.id,
            nombreCompleto: newUser,
            correoElectronico: newEmail,
          }),
        }
      );

      if (uResponse.status === 403) {
        localStorage.clear();
        window.location.href = "/login";
      } else if (uResponse.status === 400) {
        setEmailExist(true);
        return;
      }

      uResponse.json().then((response) => {
        localStorage.removeItem("token");
        localStorage.setItem("token", response.token);
        setToken(response.token);
        setCUser(response.user);
        setCredentialsChangeSuccess(true);
        setNewUser(response.user.nombreCompleto);
        setNewEmail(response.user.correoElectronico);
        setEmail(response.user.correoElectronico);
        setEmailExist(false);
      });
    } catch (error) {
      alert("Hubo  un error al cambiar las credenciales.");
    }
  };

  const handleChangePassword = async (e) => {
    e.preventDefault();

    setIncorrectPassword(false);
    setPasswordChangeSuccess(false);
    setPasswordMismatch(false);

    if (newPassword.length < 8) {
      setPasswordMismatchMessage("La contraseña debe contener mínimo 8 caracteres");
      setPasswordMismatch(true);
      return;
    }
    
    if (newPassword !== confirmPassword) {
      setPasswordMismatchMessage("Las contraseñas no coinciden.");
      setPasswordMismatch(true);
      return;
    }

    try {
      const uResponse = await fetch(
        `http://ec2-54-158-4-132.compute-1.amazonaws.com:8080/umb/v1/user/update-password`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({
            correoElectronico: email,
            oldPassword: oldPassword,
            newPassword: newPassword,
            confirmNewPassword: confirmPassword,
          }),
        }
      );

      if (uResponse.status === 403) {
        localStorage.clear();
        window.location.href = "/login";
      } else if (uResponse.status === 400) {
        setIncorrectPassword(true);
        return;
      } 

      uResponse.json().then(() => {
        setIncorrectPassword(false);
        setPasswordChangeSuccess(true);
      });

    } catch (error) {
      alert("Hubo  un error al Cambiar la contraseña");
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const uResponse = await fetch(
          `http://ec2-54-158-4-132.compute-1.amazonaws.com:8080/umb/v1/user/find-by-email?correoElectronico=${email}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Authorization: "Bearer " + localStorage.getItem("token"),
            },
          }
        );

        if (uResponse.status === 403) {
          localStorage.clear();
          window.location.href = "/login";
        } else if (!uResponse.ok) {
          alert("Hubo un error al recuperar los datos");
          return;
        }

        const userData = await uResponse.json();
        setCUser(userData.user);
        setNewUser(userData.user.nombreCompleto);
        setNewEmail(userData.user.correoElectronico);
      } catch (error) {
        alert("Hubo  un error al recuperar los datos");
      }
    };

    fetchData();
  }, [email]);

  return (
    <div className="modal" onClick={handleCloseModal}>
      <div className="modal-content" onClick={(e) => e.stopPropagation()}>
        <span className="close" onClick={onClose}>
          &times;
        </span>
        <h2 className="modal-title">Mi cuenta</h2>
        {!showCredentials && (
          <Card padding={"25px"} width={"50%"} margin={"15px auto"}>
            <p className="p-credentials">{cUser.nombreCompleto}</p>
            <p className="p-credentials">{cUser.correoElectronico}</p>
          </Card>
        )}
        <div className="modal-options">
          {!showButtons && (
            <>
              <Link className="modal-link" to="/my-history" onClick={onClose}>
                <Button
                  backgroundColor={"green"}
                  width={"100%"}
                  text="Mi historial"
                />
              </Link>
              <Link className="modal-link" to="/my-requests" onClick={onClose}>
                <Button
                  backgroundColor={"green"}
                  width={"100%"}
                  text="Mis solicitudes"
                />
              </Link>
              <Link className="modal-link" to="/my-views" onClick={onClose}>
                <Button
                  backgroundColor={"green"}
                  width={"100%"}
                  text="Productos vistos"
                />
              </Link>
            </>
          )}
        </div>
        {!showUpdate && (
          <div className="accordion">
            <Button
              width={"90%"}
              onClick={handleToggleChangeCredentials}
              margin={"0 20px"}
              text={isChangeCredentialsOpen ? "Cerrar" : "Editar información"}
            />
            {isChangeCredentialsOpen && (
              <div className="update-form user-email">
                <form onSubmit={handleChangeCredentials}>
                  <Input
                    type="text"
                    width={"80%"}
                    margin={"10px 20px"}
                    placeholder="Nuevo usuario"
                    defaultValue={cUser.nombreCompleto}
                    onChange={(e) => setNewUser(e.target.value)}
                  />
                  <Input
                    type="email"
                    width={"80%"}
                    margin={"10px 20px"}
                    placeholder="Nuevo correo electrónico"
                    defaultValue={cUser.correoElectronico}
                    onChange={(e) => setNewEmail(e.target.value)}
                  />
                  {!emailExist ? (
                    <p></p>
                  ) : (
                    <p className="email-exist-error">Este correo ya existe.</p>
                  )}
                  {credentialsChangeSuccess && (
                    <p className="success-message">
                      ¡La información de actualizó con éxito!
                    </p>
                  )}
                  <Button
                    onClick={handleChangeCredentials}
                    text="Actualizar información"
                    backgroundColor={"green"}
                    margin={"20px"}
                    width={"90%"}
                  />
                </form>
              </div>
            )}
          </div>
        )}
        {!showUpdatePassword && (
          <div className="accordion">
            <Button
              width={"90%"}
              onClick={handleToggleChangePassword}
              margin={"0 20px 20px"}
              text={isChangePasswordOpen ? "Cerrar" : "Cambiar contraseña"}
            />
            {isChangePasswordOpen && (
              <div className="update-form">
                <form onSubmit={handleChangePassword}>
                  <Input
                    type="password"
                    width={"80%"}
                    margin={"10px 20px"}
                    placeholder="Contraseña actual"
                    value={oldPassword}
                    onChange={(e) => setOldPassword(e.target.value)}
                    required
                  />
                  {!incorrectPassword ? (
                    <p></p>
                  ) : (
                    <p className="password-error">
                      Credenciales incorrectas.
                    </p>
                  )}
                  <Input
                    type="password"
                    width={"80%"}
                    margin={"10px 20px"}
                    placeholder="Nueva contraseña"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    required
                  />
                  <Input
                    type="password"
                    width={"80%"}
                    margin={"10px 20px"}
                    placeholder="Confirmar nueva contraseña"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    required
                  />
                  {passwordMismatch && (
                    <p className="error-message">
                      {passwordMismatchMessage}
                    </p>
                  )}
                  {passwordChangeSuccess && (
                    <p className="success-message">
                      ¡La contraseña se cambió con éxito!
                    </p>
                  )}
                  <Button
                    onClick={handleChangePassword}
                    text="Cambiar contraseña"
                    backgroundColor={"green"}
                    margin={"20px"}
                    width={"90%"}
                  />
                </form>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
};

export default ProfileModal;
