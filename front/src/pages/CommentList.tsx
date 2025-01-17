import { Comment } from "../types/Types";
import formatDate from "../utils/formatDate";
import "./CommentList.css";

export default function CommentList({
  commentList,
}: {
  commentList: Comment[];
}) {
  return (
    <div className="commentList-container">
      <ul className="commentList-list">
        {commentList.map((comment) => (
          <li className="commentList-item" key={comment.id}>
            <div className="commentList-top">
              <span className="commentList-content">{comment.content}</span>
              <span>{comment.author}</span>
            </div>
            <div className="commentList-bottom">
              <span>{formatDate(comment.createdDate)}</span>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}
