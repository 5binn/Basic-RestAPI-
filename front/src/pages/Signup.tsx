import axios from "../utils/axiosInstance";
import { useState } from "react";
import "./Form.css";
import { AxiosError } from "axios";
import { Member, ErrorResponse } from "../types/Types";

export default function Signup({ setIsLogin }: any) {
  const [member, setMember] = useState<Member>({
    username: "",
    password1: "",
    password2: "",
  });

  async function handleSignup(e: React.FormEvent) {
    const username = member.username;
    const password = member.password1;
    try {
      const response = await axios.post("/members/signup", {
        username,
        password,
      });
      if (response.status === 200) {
        setIsLogin(true);
        alert(`${member.username} 님 가입완료`);
      } else {
        alert(response.data.msg);
      }
    } catch (error) {
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

  function handleChange(e: React.ChangeEvent<HTMLInputElement>) {
    setMember({ ...member, [e.target.id]: e.target.value });
    console.log(member);
  }

  return (
    <div className="login-container">
      <h1>회원가입</h1>
      <form onSubmit={handleSignup}>
        <div className="input-item">
          <span>ID입력</span>
          <input id="username" onChange={handleChange} />
        </div>
        {!/^[a-zA-Z0-9]+$/.test(member.username) ? (
          <div>
            <span className="warn">⚠ 유효하지 않은 아이디입니다.</span>
          </div>
        ) : (
          <div>
            <span>　</span>
          </div>
        )}
        <div className="input-item">
          <span>PW입력</span>
          <input id="password1" type="password" onChange={handleChange} />
        </div>
        <div className="input-item">
          <span>PW확인</span>
          <input id="password2" type="password" onChange={handleChange} />
        </div>
        {member.password1 != member.password2 ? (
          <div>
            <span className="warn">⚠ 비밀번호가 일치하지 않습니다.</span>
          </div>
        ) : (
          <div>
            <span>　</span>
          </div>
        )}
        <button
          type="submit"
          className={`btn ${
            member.password1 != member.password2 ||
            member.password1 == "" ||
            member.password2 == "" ||
            !/^[a-zA-Z0-9]+$/.test(member.username)
              ? "btn-disabled"
              : "btn-active"
          }`}
          disabled={
            member.password1 != member.password2 ||
            member.password1 == "" ||
            member.password2 == "" ||
            !/^[a-zA-Z0-9]+$/.test(member.username)
          }
        >
          회원가입
        </button>
      </form>
    </div>
  );
}
