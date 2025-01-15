import { useState } from "react";
import Login from "../pages/Login";
import "./LoginLayout.css";
import Signup from "../pages/signup";

export default function LoginLayout({ setIsLogin }: any) {
  const [clicked, setClicked] = useState(false);

  return (
    <div className="container">
      <nav>
        <button>Home</button>
        <button onClick={() => setClicked(!clicked)}>
          {clicked ? "login" : "SignUp"}
        </button>
      </nav>
      <div className="login-box">
        {clicked ? <Signup></Signup> : <Login setIsLogin={setIsLogin} />}
      </div>
    </div>
  );
}
