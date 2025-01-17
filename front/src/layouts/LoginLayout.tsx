import { useState } from "react";
import Login from "../pages/Login";
import "./Layout.css";
import Signup from "../pages/Signup";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome } from "@fortawesome/free-solid-svg-icons";

export default function LoginLayout({ setIsLogin }: any) {
  const [clicked, setClicked] = useState(false);

  return (
    <div className="container">
      <nav>
        <button className="fixed-button" onClick={() => setClicked(false)}>
          <FontAwesomeIcon icon={faHome} />
        </button>
        <div className="title">
          <span>BASIC ARTICLE</span>
        </div>
        <button className="fixed-button" onClick={() => setClicked(!clicked)}>
          {clicked ? "로그인" : "회원가입"}
        </button>
      </nav>
      <div className="login-box">
        {clicked ? (
          <Signup setIsLogin={setIsLogin} />
        ) : (
          <Login setIsLogin={setIsLogin} />
        )}
      </div>
    </div>
  );
}
