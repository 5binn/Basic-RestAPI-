import axios from "../utils/axiosInstance";
import { useState } from "react";

export default function Signup({ setIsLogin }: any) {
  const [username, setUsername] = useState("");
  const [password1, setPassword1] = useState("");
  const [password2, setPassword2] = useState("");

  async function handleSignup(e: React.FormEvent) {
    e.preventDefault();
    const response = await axios.post("/members/signup", {
      username,
      password1,
    });
  }

  return (
    <div>
      <h1>회원가입</h1>
      <form onSubmit={handleSignup}>
        <div>
          <label>ID</label>
          <input id="username" onChange={(e) => setUsername(e.target.value)} />
        </div>
        <div>
          <label>PW</label>
          <input
            id="password1"
            type="password"
            onChange={(e) => setPassword1(e.target.value)}
          />
        </div>
        <div>
          <label>PW확인</label>
          <input
            id="password2"
            type="password"
            onChange={(e) => setPassword2(e.target.value)}
          />
        </div>
      </form>
      <button type="submit">회원가입</button>
    </div>
  );
}
