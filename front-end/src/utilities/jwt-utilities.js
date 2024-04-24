import { jwtDecode } from "jwt-decode";

export function validateTokenWithRole(role) {
  const token = localStorage.getItem("token");

  if (token === null) {
    localStorage.clear();
    window.location.href = "/login";
  }

  if (!token) {
    localStorage.clear();
    window.location.href = "/login";
  }

  const userRole = jwtDecode(token).ROLE;

  if (!(role === userRole)) {
    if (role === "CLIENTE") {
      window.location.href = "/admin-web-scraper";
    } else if (role === "ADMIN") {
      window.location.href = "/home";
    }
  }

  validateExpirationToken(token);
}

export function validateExpirationToken(token) {
  if (!token) {
    return false;
  }

  const decodedToken = jwtDecode(token);
  if (!decodedToken.exp) {
    return false;
  }

  const currentTime = Date.now() / 1000;
  if (decodedToken.exp < currentTime) {
    localStorage.clear();
    window.location.href = "/login";
  }
}

export function extractEmailFromToken(token) {
  return jwtDecode(token).EMAIL;
}

export function extractRoleFromToken() {
  const token = localStorage.getItem("token");

  if (token === null) {
    localStorage.clear();
    window.location.href = "/login";
  }

  if (!token) {
    localStorage.clear();
    window.location.href = "/login";
  }

  return jwtDecode(token).ROLE;
}