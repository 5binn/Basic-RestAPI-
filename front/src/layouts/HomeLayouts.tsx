import { useState } from "react";
import "./Layout.css";
import axios from "../utils/axiosInstance";
import { AxiosError } from "axios";
import { ErrorResponse } from "../types/Types";
import ArticleList from "../pages/ArticleList";
import SingleArticle from "../pages/SingleArticle";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faHome,
  faL,
  faPen,
  faPenToSquare,
} from "@fortawesome/free-solid-svg-icons";
import ArticleForm from "../pages/ArticleForm";

export default function HomeLayout({ setIsLogin }: any) {
  const [selectedArticleId, setSelectedArticleId] = useState<number | null>(
    null
  );
  const [isRegister, setIsRegister] = useState(false);

  async function handleLogout(e: React.FormEvent) {
    const confirmed = window.confirm("로그아웃 하시겠습니까?");
    if (confirmed) {
      e.preventDefault();
      try {
        const response = await axios.post("/members/logout");
        if (response.status === 200) {
          setIsLogin(false);
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
      <nav>
        <button
          className="fixed-button"
          onClick={() => {
            setSelectedArticleId(null);
            setIsRegister(false);
          }}
        >
          <FontAwesomeIcon icon={faHome} />
        </button>
        <div className="title">
          <span>BASIC ARTICLE</span>
        </div>
        <button className="fixed-button" onClick={handleLogout}>
          로그아웃
        </button>
      </nav>
      <div className="register">
        <button
          className="register-button"
          onClick={() => {
            setIsRegister(true);
            setSelectedArticleId(null);
          }}
        >
          글쓰기
          <FontAwesomeIcon icon={faPenToSquare} />
        </button>
      </div>
      <div>
        {selectedArticleId === null ? (
          isRegister ? (
            <ArticleForm setIsRegister={setIsRegister} />
          ) : (
            <ArticleList setSelectedArticleId={setSelectedArticleId} />
          )
        ) : (
          <SingleArticle selectedArticleId={selectedArticleId} />
        )}
      </div>
    </div>
  );
}
