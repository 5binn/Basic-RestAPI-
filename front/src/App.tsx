import { useEffect, useState } from "react";
import "./App.css";
import axios from "./utils/axiosInstance";
import LoginLayout from "./layouts/LoginLayout";
import HomeLayout from "./layouts/HomeLayouts";

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
  return (
    <>
      {isLogin ? (
        <HomeLayout setIsLogin={setIsLogin} />
      ) : (
        <LoginLayout setIsLogin={setIsLogin} />
      )}
    </>
  );
}
