import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    getters: {},
    actions: {},
    modules: {},
    state: {
        //每次都从本地中获取token，这里存的token字段名为Authorization
        Authorization: localStorage.getItem('token') ? localStorage.getItem('token') : ''
    },
    mutations: {
        //改变Authorization的值同时改变localStorage的Authorization值
        changeLogin(state, token) {
            state.Authorization = token;
            localStorage.setItem('token', token);
        }
    }
})
