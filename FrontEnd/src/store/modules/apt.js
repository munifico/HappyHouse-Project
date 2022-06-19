import http from "../../api/http";
import axios from "axios";
import _uniqBy from "lodash/uniqBy";

const _defaultMessage = "Search for the Apt Name";

export default {
  namespaced: true,

  state: () => ({
    apts: [],
    message: _defaultMessage,
    loading: false,
    theApt: {},
    // AptMap marker
    mouseInfoMessage: "",
    // AptDetail
    modalData: null,
  }),

  getters: {},

  mutations: {
    updateState(state, payload) {
      // ['apts', 'message', 'loading']
      Object.keys(payload).forEach((key) => {
        state[key] = payload[key];
      });
    },
    setMouseInfoMessage(state, infoMessageObj) {
      state.mouseInfoMessage = infoMessageObj.infoMessage;
    },
    setModalData(state, modalData) {
      state.modalData = modalData;
      console.log(state.modalData);
    },
    enrollResults(state, aptInfo) {
      state.apts = aptInfo;
      state.message = "";
      state.loading = false;
    },
    resetApts(state) {
      state.apts = [];
      state.message = _defaultMessage;
      state.loading = false;
    },
  },

  actions: {
    async searchApts({ state, commit }, payload) {
      if (state.loading) return;

      commit("updateState", {
        message: "",
        loading: true,
      });

      try {
        const OMDB_API_KEY = "7035c60c";
        const res = await _fetchApt({
          ...payload,
          page: 1,
        });
        const { Search, totalResults } = res.data;
        commit("updateState", {
          // apts: Search,
          apts: _uniqBy(Search, "imdbID"),
        });
        console.log(totalResults); //304 => 27
        console.log(typeof totalResults); //string
        console.log(res.data);
        const total = parseInt(totalResults, 10);
        const pageLength = Math.ceil(total / 10);

        // 추가 요청!
        if (pageLength > 1) {
          for (let page = 2; page <= pageLength; page += 1) {
            if (page > payload.number / 10) break;
            const res = await _fetchApt({
              ...payload,
              page,
            });
            const { Search } = res.data;
            commit("updateState", {
              apts: [...state.apts, ..._uniqBy(Search, "imdbID")],
            });
          }
        }
      } catch (message) {
        commit("updateState", {
          apts: [],
          message,
        });
      } finally {
        commit("updateState", {
          loading: false,
        });
      }
    },

    async searchAptWithId({ state, commit }, payload) {
      if (state.loading) return;
      commit("updateState", {
        theApt: {},
        loading: true,
      });
      try {
        const res = await _fetchApt(payload);
        commit("updateState", {
          theApt: res.data,
        });
      } catch (error) {
        commit("updateState", {
          theApt: {},
        });
      } finally {
        commit("updateState", {
          loading: false,
        });
      }
    },
  },
};

function _fetchApt(payload) {
  const { title, type, year, page, id } = payload;
  const OMDB_API_KEY = "7035c60c";
  const url = id ? `https://www.omdbapi.com/?apikey=${OMDB_API_KEY}&i=${id}` : `https://www.omdbapi.com/?apikey=${OMDB_API_KEY}&s=${title}&type=${type}&y=${year}&page=${page}`;

  return new Promise((resolve, reject) => {
    axios
      .get(url)
      .then((res) => {
        if (res.data.Error) {
          reject(res.data.Error);
        }
        resolve(res);
      })
      .catch((err) => {
        reject(err.message);
      });
  });
}
