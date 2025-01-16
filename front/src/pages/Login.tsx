import axios, { AxiosError } from "axios";
import { useState } from "react";
import { ErrorResponse } from "../types/Types";
import "./Form.css";

export default function Login({ setIsLogin }: any) {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  async function handleLogin(e: React.FormEvent) {
    e.preventDefault();
    try {
      const response = await axios.post("/members/login", {
        username,
        password,
      });
      if (response.status === 200) {
        setIsLogin(true);
      } else {
        alert(response.data.msg);
      }
    } catch (error) {
      setPassword("");
      const axiosError = error as AxiosError<ErrorResponse>;
      if (axiosError.response) {
        console.error(axiosError.response.data.msg);
        alert(axiosError.response.data.msg);
      } else {
        console.error("Unknown error:", axiosError.message);
        alert("An unknown error occurred.");
      }
    }
  }

  return (
    <div className="login-container">
      <h1>로그인</h1>
      <form onSubmit={handleLogin}>
        <div className="input-item">
          <span>ID</span>
          <input
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div className="input-item">
          <span>PW</span>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button className="login-button" type="submit">
          로그인
        </button>
      </form>
    </div>
  );
}
