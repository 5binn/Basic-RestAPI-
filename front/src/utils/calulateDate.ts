export default function calculateDate(createdDate: string) {
  const date = new Date(createdDate);
  const now = Date.now();
  var difference = (now - date.getTime()) / 1000;
  const units = [
    { label: "년", value: 31536000 },
    { label: "일", value: 86400 },
    { label: "시간", value: 3600 },
    { label: "분", value: 60 },
    { label: "초", value: 1 },
  ];

  for (const unit of units) {
    const unitValue = difference / unit.value;
    if (unitValue >= 1) {
      return `${Math.floor(unitValue)}${unit.label} 전`;
    }
  }

  return "방금 전";
}
