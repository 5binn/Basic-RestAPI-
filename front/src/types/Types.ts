export type ErrorResponse = {
  msg: string;
};

export type Member = {
  username: string;
  password1: string;
  password2: string;
};

export type Article = {
  id: number;
  title: string;
  content: string;
  author: string;
  createdDate: string;
  modifiedDate: string;
};

export type Comment = {
  id: number;
  content: string;
  author: string;
  articleId: number;
  createdDate: string;
  modifiedDate: string;
};
