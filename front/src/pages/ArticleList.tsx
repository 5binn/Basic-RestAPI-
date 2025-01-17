import { Article } from "../types/Types";
import axios from "../utils/axiosInstance";
import { useEffect, useState } from "react";
import "./ArticleList.css";
import calculateDate from "../utils/calulateDate";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser } from "@fortawesome/free-solid-svg-icons";

export default function ArticleList({ setSelectedArticleId }: any) {
  const [articleList, setArticleList] = useState<Article[]>([]);
  const [isEmpty, setIsEmpty] = useState(false);

  useEffect(() => {
    axios
      .get("/articles")
      .then((response) => {
        console.log(response.data);
        setArticleList(response.data.data);
        setIsEmpty(response.data.data.length === 0);
      })
      .catch((err) => {
        console.error("데이터 확인 중 오류:", err);
      });
  }, []);

  return (
    <div className="article-container">
      <div className="container">
        <ul className="list">
          {isEmpty ? (
            <span>등록된 게시물이 없습니다.</span>
          ) : (
            articleList.map((article) => (
              <li
                className="item"
                key={article.id}
                onClick={() => {
                  setSelectedArticleId(article.id);
                }}
              >
                <div className="header">
                  <FontAwesomeIcon icon={faUser} className="profile-img" />
                  <div className="info">
                    <span className="username">{article.author}</span>
                    <span className="timestamp">
                      {calculateDate(article.createdDate)}
                    </span>
                  </div>
                </div>
                <div className="content">
                  <p className="article-title">{article.title}</p>
                </div>
                <div className="actions">
                  <button className="actions-btn">Like</button>
                  <button className="actions-btn">Share</button>
                </div>
              </li>
            ))
          )}
        </ul>
      </div>
    </div>
  );
}
