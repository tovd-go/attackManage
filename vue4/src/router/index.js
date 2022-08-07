import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from "@/views/Home";
import User from "@/views/User";
import Company from "@/views/Company";
import Domain from "@/views/Domain";
import Host from "@/views/Host";
import Subject from "@/views/Subject";
import Profile from  "@/views/Profile"
import Code from "@/views/Code"
import CompanyChild from "@/views/CompanyChild"
import Word from "@/views/Word"
import SupplyChain from "@/views/SupplyChain"
import Login from '@/views/Login'


Vue.use(VueRouter)

const router= new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes :[
        {
            path: '/home',
            name: 'home',
            component: Home,
            children: [
                {
                    path: '/user',
                    name: 'user',
                    component: User
                },
                {
                    path: '/company',
                    name: 'company',
                    component: Company,
                },
                {
                    path: '/domain',
                    name: 'domain',
                    component: Domain
                },
                {
                    path: '/host',
                    name: 'host',
                    component: Host
                },
                {
                    path: '/subject',
                    name: 'subject',
                    component: Subject
                },
                {
                    path: '/profile',
                    name: 'profile',
                    component: Profile
                },
                {
                    path: '/code',
                    name: 'code',
                    component: Code
                },
                {
                    path: '/word',
                    name: 'word',
                    component: Word
                },
                {
                    path: '/companychild',
                    name: 'companychild',
                    component: CompanyChild
                },
                {
                    path: '/supplychain',
                    name: 'supplychain',
                    component: SupplyChain
                }

            ]

        },
        {
            path: '/login',
            name: 'login',
            component: Login

        }

    ]
})
router.beforeEach((to, from, next) => {
    if (to.path === '/login') {
        next();
    } else {
        let token = localStorage.getItem('token');
        if (token === null || token === '') {
            next('/login');
        } else if (to.path==='/') {
            next('/login')
        }else {
            next()
        }
    }
})


export default router
