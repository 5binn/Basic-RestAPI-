import axios from "../utils/axiosInstance";
import { useEffect, useState } from "react";

export default function Home() {
  const [articleList, setArticleList] = useState();

  useEffect(() => {
    axios.get("/articles").then((response) => {
      console.log(response.data);
    });
  });

  return (
    <>
      <h1>HOME</h1>
    </>
  );
}
