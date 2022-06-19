// 모달 상태


export default {
    namespaced: true,
    state : () => ({
        login: { show: false}
    }),

    getters: {},

    mutations: {
        SET_LOGIN_MODAL_OPEN(state) {
            state.login.show = true;
        },
        SET_LOGIN_MODAL_CLOSE(state) {
            state.login = {
                show: false
            }
        }
    },

    actions: {
        
    }
}
