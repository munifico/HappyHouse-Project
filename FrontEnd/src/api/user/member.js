import { apiInstance } from "../index";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function update(user, success, fail) {
  await api.post(`/user/update`, JSON.stringify(user)).then(success).catch(fail);
}

async function deleteUser(user, success, fail) {
  await api.post(`/user/delete`, JSON.stringify(user)).then(success).catch(fail);
}

async function registerUser(user, success, fail) {
  await api.post(`/user/register`, JSON.stringify(user)).then(success).catch(fail);
}

// function logout(success, fail)

export { login, findById, update, deleteUser, registerUser };
