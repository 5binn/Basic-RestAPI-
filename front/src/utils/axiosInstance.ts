import axios from "axios";

axios.defaults.baseURL = "/api";
axios.defaults.withCredentials = true;
axios.defaults.headers.common["Content-Type"] = "application/json";

export default axios;
