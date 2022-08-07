import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/gloable.css'
import axios from 'axios'

Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(ElementUI, {size: "mini"});

axios.interceptors.request.use(
    config => {

        if (localStorage.getItem('token')) {

            config.headers.token = localStorage.getItem('token');
        }
        return config;
    },
    error => {
        return Promise.reject(error);
    }
)
new Vue({
    router,
    store,
    render: h => h(App),
}).$mount('#app')


