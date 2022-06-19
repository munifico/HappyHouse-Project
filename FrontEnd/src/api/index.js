import axios from "axios";

// axios 객체 생성
function apiInstance() {
  const instance = axios.create({
    baseURL: "http://localhost:9999/vue",
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}
export { apiInstance };
