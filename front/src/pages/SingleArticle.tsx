import CommentForm from "../components/CommentForm";
import { Article, Comment } from "../types/Types";
import axios from "../utils/axiosInstance";
import { useEffect, useState } from "react";
import formatDate from "../utils/formatDate";
import "./SingleArticle.css";

export default function SingleArticle({ selectedArticleId }: any) {
  const [article, setArticle] = useState<Article | null>(null);
  const [commentList, setCommentList] = useState<Comment[]>([]);
  useEffect(() => {
    axios.get(`/articles/${selectedArticleId}`).then((response) => {
      console.log(response.data);
      if (response.data) {
        setArticle(response.data.data);
      }
    });
  }, []);

  useEffect(() => {
    axios.get(`/comments/${selectedArticleId}`).then((response) => {
      setCommentList(response.data.data);
    });
  }, []);

  return (
    <>
      <div className="single-container">
        <div className="article-top">
          <span className="article-title">{article?.title}</span>
          <span>작성자 {article?.author}</span>
        </div>
        <div className="article-middle">
          <span>{article?.content}</span>
        </div>
        <div className="article-bottom">
          <span>등록일 {formatDate(article?.createdDate || "")}</span>
          <span>수정일 {formatDate(article?.modifiedDate || "")}</span>
        </div>
      </div>
      <div>
        <CommentForm selectedArticleId={selectedArticleId} />
      </div>
      <div>
        <ul>
          {commentList.map((comment) => (
            <li>
              <span>{comment.content}</span>
              <br />
              <span>{comment.author}</span>
              <br />
              <span>{comment.createdDate}</span>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
}
