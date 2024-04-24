import Navbar from "./components/navbar/Navbar";
import "./styles/app.css";
import "./styles/components/admin-menu/admin-menu.css";
import "./styles/pages/sections/admin-web-scraper.css";
import "./styles/pages/sections/admin-pqrs.css";
import "./styles/pages/auth/restart-password.css";
import "./styles/pages/not-found/not-found.css";
import "./styles/pages/complaint-box/complaint-box.css";
import "./assest/check.png";
import Home from "./pages/home/Home";
import Post from "./pages/posts/Post";
import Login from "./pages/auth/Login";
import ForgotPassword from "./pages/auth/ForgotPassword";
import RestartPassword from "./pages/auth/RestartPassword";
import React, { useState } from "react";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import SignUp from "./pages/auth/Signup";
import Cellphones from "./pages/sections/Cellphones";
import Computers from "./pages/sections/Computers";
import Tablets from "./pages/sections/Tablets";
import Monitors from "./pages/sections/Monitors";
import Others from "./pages/sections/Others";
import AdminWebScraper from "./pages/sections/AdminWebScraper";
import AdminPqrs from "./pages/sections/AdminPqrs";
import Footer from "./components/footer/Footer";
import AboutUs from "./pages/about-us/AboutUs";
import ComplaintBox from "./pages/complaint-box/ComplaintBox";
import CustomerPqrs from "./pages/customer-pqrs/CustomerPqrs";
import CustomerHistory from "./pages/User/CustomerHistory";
import NotFound from "./pages/not-found/NotFound";
import SearchResult from "./pages/search/SearchResult";
import CustomerViews from "./pages/User/CustomerViews";

const App = () => {
  const [isAuthenticated] = useState(
    localStorage.getItem("isAuthenticated") || false
  );

  return (
    <BrowserRouter>
      <div>
        <Navbar user={isAuthenticated} />
        <Routes>
          <Route
            path="/"
            element={
              isAuthenticated ? (
                <Navigate to="/home" />
              ) : (
                <Navigate to="/login" />
              )
            }
          />
          <Route
            path="/login"
            element={isAuthenticated ? <Navigate to="/home" /> : <Login />}
          />
          <Route
            path="/signup"
            element={isAuthenticated ? <Navigate to="/home" /> : <SignUp />}
          />
          <Route
            path="/forgot-password"
            element={
              isAuthenticated ? <Navigate to="/home" /> : <ForgotPassword />
            }
          />
          <Route
            path="/reset-password"
            element={
              isAuthenticated ? <Navigate to="/home" /> : <RestartPassword />
            }
          />
          <Route
            path="/home"
            element={isAuthenticated ? <Home /> : <Navigate to="/login" />}
          />
          <Route
            path="/post/:id"
            element={isAuthenticated ? <Post /> : <Navigate to="/home" />}
          />
          <Route
            path="/admin-web-scraper"
            element={
              isAuthenticated ? <AdminWebScraper /> : <Navigate to="/login" />
            }
          />
          <Route
            path="/admin-pqrs"
            element={isAuthenticated ? <AdminPqrs /> : <Navigate to="/login" />}
          />
          <Route
            path="/cellphones"
            element={
              isAuthenticated ? <Cellphones /> : <Navigate to="/login" />
            }
          />
          <Route
            path="/computers"
            element={isAuthenticated ? <Computers /> : <Navigate to="/login" />}
          />
          <Route
            path="/tablets"
            element={isAuthenticated ? <Tablets /> : <Navigate to="/login" />}
          />
          <Route
            path="/monitors"
            element={isAuthenticated ? <Monitors /> : <Navigate to="/login" />}
          />
          <Route
            path="/others"
            element={isAuthenticated ? <Others /> : <Navigate to="/login" />}
          />
          <Route
            path="/search"
            element={
              isAuthenticated ? <SearchResult /> : <Navigate to="/login" />
            }
          />
          <Route
            path="/complaint-box"
            element={
              isAuthenticated ? <ComplaintBox /> : <Navigate to="/login" />
            }
          />
          <Route
            path="/my-requests"
            element={
              isAuthenticated ? <CustomerPqrs /> : <Navigate to="/login" />
            }
          />
          <Route
            path="/my-history"
            element={
              isAuthenticated ? <CustomerHistory /> : <Navigate to="/login" />
            }
          />
          <Route
            path="/my-views"
            element={
              isAuthenticated ? <CustomerViews /> : <Navigate to="/login" />
            }
          />
          <Route path="/about-us" element={<AboutUs />} />
          <Route
            path="/*"
            element={isAuthenticated ? <NotFound /> : <Navigate to="/login" />}
          />
        </Routes>
        <Footer />
      </div>
    </BrowserRouter>
  );
};

export default App;
