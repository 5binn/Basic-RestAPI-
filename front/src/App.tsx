import { useEffect, useState } from "react";
import "./App.css";
import axios from "./utils/axiosInstance";
import Login from "./pages/Login";
import Home from "./pages/Home";
import LoginLayout from "./layouts/LoginLayout";

export default function App() {
  const [isLogin, setIsLogin] = useState<boolean | null>(null);

  useEffect(() => {
    axios
      .get("/members/session")
      .then((response) => {
        const loggedIn = response.data.data;
        setIsLogin(loggedIn);
        console.log(response.data);
      })
      .catch((err) => {
        console.error("세션 확인 중 오류:", err);
        setIsLogin(false);
      });
  }, [isLogin]);

  if (isLogin === null) {
    return <div>LOADING...</div>;
  }
  return <>{isLogin ? <Home /> : <LoginLayout setIsLogin={setIsLogin} />}</>;
}
