import { AxiosError } from "axios";
import axios from "../utils/axiosInstance";
import { useState } from "react";
import { ErrorResponse } from "../types/Types";
import "./CommentForm.css";

export default function CommentForm({
  selectedArticleId,
  fetchComments,
}: {
  selectedArticleId: number;
  fetchComments: () => void;
}) {
  const [content, setContent] = useState("");

  async function handleRegister(e: React.FormEvent) {
    const confirmed = window.confirm("등록하시겠습니까?");
    if (confirmed) {
      e.preventDefault();
      try {
        const response = await axios.post(`/comments/${selectedArticleId}`, {
          content,
        });
        if (response.status === 201) {
          setContent("");
          fetchComments();
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
    <div className="commentForm">
      <form onSubmit={handleRegister}>
        <div className="commentForm-input">
          <input
            id="content"
            onChange={(e) => {
              setContent(e.target.value);
            }}
            value={content}
          />
          <button type="submit">등록</button>
        </div>
      </form>
    </div>
  );
}
