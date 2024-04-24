import { Link } from "react-router-dom";
import "../../styles/components/navbar/navbar.css";
import { useState } from "react";
import ProfileModal from "../profile-modal/ProfileModal";
import { extractRoleFromToken } from "../../utilities/jwt-utilities";

const Navbar = ({ user }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);

  const [isAuthenticated] = useState(
    localStorage.getItem("isAuthenticated") || false
  );
  const [role] = useState(isAuthenticated === "true" ? extractRoleFromToken() : "");

  const handleLogout = () => {
    localStorage.removeItem("isAuthenticated");
    localStorage.removeItem("token");
    localStorage.removeItem("refreshToken");
    window.location.href = "/login";
  };

  const openProfileModal = () => {
    setIsModalOpen(true);
  };

  const closeProfileModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className="navbar">
      <span className="logo">
        <Link className="link comparatech" to="/">
          Comparatech
        </Link>
      </span>
      <div>
        {user ? (
          <ul className="list">
            {isAuthenticated && role === "CLIENTE" ? (
              <li className="listItem" onClick={openProfileModal}>
                <p>Mi cuenta</p>
              </li>
            ) : (
              <></>
            )}

            <li className="listItem" onClick={handleLogout}>
              Cerrar sesión
            </li>
          </ul>
        ) : (
          <>
            <Link className="link link--login" to="login">
              Iniciar sesión
            </Link>
            <Link className="link link--signup" to="signup">
              Registrarse
            </Link>
          </>
        )}
      </div>
      {isModalOpen && <ProfileModal user={user} onClose={closeProfileModal} />}
    </div>
  );
};

export default Navbar;
