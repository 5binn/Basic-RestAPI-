import axios from "../utils/axiosInstance";
import { useState } from "react";
import { AxiosError } from "axios";
import { ErrorResponse } from "../types/Types";
import "./ArticleList.css";

export default function ArticleForm({ setIsRegister }: any) {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  async function handleRegister(e: React.FormEvent) {
    const confirmed = window.confirm("등록하시겠습니까?");
    if (confirmed) {
      e.preventDefault();
      try {
        const response = await axios.post("/articles", { title, content });
        if (response.status === 201) {
          setTitle("");
          setContent("");
          setIsRegister(false);
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
  }

  return (
    <div className="container">
      <div className="list">
        <form className="item" onSubmit={handleRegister}>
          <div className="input-div">
            <span className="input-label">제목</span>
            <input id="title" onChange={(e) => setTitle(e.target.value)} />
          </div>
          <div className="input-div">
            <span className="input-label">내용</span>
            <textarea
              id="content"
              onChange={(e) => setContent(e.target.value)}
            />
          </div>
          <div className="actions">
            <button className="create-btn" type="submit">
              <span>등록</span>
            </button>
            <div
              className="cancel-btn"
              onClick={() => {
                const confirmed = window.confirm("취소하시겠습니까?");
                confirmed ? setIsRegister(false) : setIsRegister(true);
              }}
            >
              <span>취소</span>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
