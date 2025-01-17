import CommentForm from "../components/CommentForm";
import { Article, Comment } from "../types/Types";
import axios from "../utils/axiosInstance";
import { useEffect, useState } from "react";
import formatDate from "../utils/formatDate";
import "./SingleArticle.css";
import CommentList from "./CommentList";

export default function SingleArticle({ selectedArticleId }: any) {
  const [article, setArticle] = useState<Article | null>(null);
  const [commentList, setCommentList] = useState<Comment[]>([]);
  const [isEmpty, setIsEmpty] = useState(false);

  useEffect(() => {
    axios.get(`/articles/${selectedArticleId}`).then((response) => {
      console.log(response.data);
      setArticle(response.data.data);
    });
    fetchComments();
  }, [selectedArticleId]);

  function fetchComments() {
    axios.get(`/comments/${selectedArticleId}`).then((response) => {
      console.log(response.data);
      setIsEmpty(response.data.data.length === 0);
      setCommentList(response.data.data);
    });
  }

  return (
    <>
      <div className="single-container">
        <div className="article-top">
          <div className="article-title">
            <span>{article?.title}</span>
          </div>
          <div>
            <span>작성자 {article?.author}</span>
          </div>
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
        <CommentForm
          selectedArticleId={selectedArticleId}
          fetchComments={fetchComments}
        />
      </div>
      <div>
        {isEmpty ? (
          <span>등록된 댓글이 없습니다.</span>
        ) : (
          <CommentList commentList={commentList} />
        )}
      </div>
    </>
  );
}
