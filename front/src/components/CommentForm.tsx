import { AxiosError } from "axios";
import axios from "../utils/axiosInstance";
import { useState } from "react";
import { ErrorResponse } from "../types/Types";

export default function CommentForm({
  selectedArticleId,
}: {
  selectedArticleId: number;
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
    <div>
      <form onSubmit={handleRegister}>
        <input
          id="content"
          onChange={(e) => {
            setContent(e.target.value);
          }}
          value={content}
        />
        <button type="submit">등록</button>
      </form>
    </div>
  );
}
