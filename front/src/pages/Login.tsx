import axios, { AxiosError } from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

interface ErrorResponse {
  msg: string;
}

export default function Login({ setIsLogin }: any) {
  const navigate = useNavigate();

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
    <div>
      <h1>LOGIN 화면</h1>
      <form onSubmit={handleLogin}>
        <div>
          <label>ID</label>
          <input
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </div>
        <div>
          <label>PW</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button type="submit">로그인</button>
      </form>
      <div className="loginBottom mt-3">
        <a href="">회원가입</a>
        <a href="">ID/PW 찾기</a>
      </div>
    </div>
  );
}
