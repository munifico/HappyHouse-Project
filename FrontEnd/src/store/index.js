import { createStore } from 'vuex'
import modal from './modules/modal'
import member from './modules/member'
import apt from './modules/apt'

import createPersistedState from "vuex-persistedstate";

export default createStore({
    modules: {
        modal,
        member,
        apt
    },
    plugins: [
      createPersistedState({
        // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
        storage: sessionStorage,
    }),
    ]
})