export default function formatDate(localDateTime: string) {
  const date = new Date(localDateTime);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 +1
  const day = String(date.getDate()).padStart(2, "0");
  const hour = date.getHours();
  const minute = date.getMinutes();

  return `${year}-${month}-${day} ${hour}:${minute}`;
}
