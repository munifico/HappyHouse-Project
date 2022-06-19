import { createRouter, createWebHashHistory } from 'vue-router'
import Main from './MainView'
import About from './AboutView'
import Manual from './ManualView'
import Board from './BoardView'
import Trade from './TradeView'



import NoticeDetail from '../components/board/notice/NoticeDetail'
import NoticeDelete from '../components/board/notice/NoticeDelete'
import NoticeList from '../components/board/notice/NoticeList'
import NoticeModify from '../components/board/notice/NoticeModify'
import NoticeWrite from '../components/board/notice/NoticeWrite'

import ReviewDetail from '../components/board/review/ReviewDetail'
import ReviewDelete from '../components/board/review/ReviewDelete'
import ReviewList from '../components/board/review/ReviewList'
import ReviewModify from '../components/board/review/ReviewModify'
import ReviewWrite from '../components/board/review/ReviewWrite'

import qnaDetail from '../components/board/qna/qnaDetail'
import qnaDelete from '../components/board/qna/qnaDelete'
import qnaList from '../components/board/qna/qnaList'
import qnaModify from '../components/board/qna/qnaModify'
import qnaWrite from '../components/board/qna/qnaWrite'

export default createRouter({
    // Hash
    // https://google.com/#/search
    history: createWebHashHistory(),
    scrollBehavior() {
        return {top:0}
    },
    // pages
    // https://google.com
    routes: [
        {
            path: '/',
            name:"home",
            component : Main
        },
        
        {
            path: "/user",
            name: "user",
            component: () => import("~/routes/MemberView.vue"),
            
            redirect: '/user/signup',
            children: [
                {
                    path: "signup",
                    name: "signup",
                    component: () => import("~/components/User/Register.vue"),
                },
                {
                    path: "mypage",
                    name: "mypage",
                    component: () => import("~/components/User/Mypage.vue"),
                }
            ]
        },
        {
            path: '/about',
            component: About
        },
        {
            path: '/manual',
            component: Manual
        },
        {
            path: '/trade',
            component: Trade
        },
        {
            path: '/board',
            name: 'Board',
            component: Board,
            redirect: '/board/notice/list',
            children: [
                //Notice
                {
                    name: 'Noticelist',
                    path: 'notice/list',
                    component: NoticeList
                },
                {
                    name: 'Noticewrite',
                    path: 'notice/write',
                    component: NoticeWrite
                },
                {
                    name: 'Noticedelete',
                    path: 'notice/delete',
                    component: NoticeDelete
                },
                {
                    name: 'Noticemodify',
                    path: 'notice/modify/:articleno',
                    component: NoticeModify
                },
                {
                    name: "Noticedetail",
                    path: "notice/detail/:articleno",
                    component: NoticeDetail
                },
                // Review
                {
                    name: 'Reviewlist',
                    path: 'review/list',
                    component: ReviewList
                },
                {
                    name: 'Reviewwrite',
                    path: 'review/write',
                    component: ReviewWrite
                },
                {
                    name: 'Reviewdelete',
                    path: 'review/delete',
                    component: ReviewDelete
                },
                {
                    name: 'Reviewmodify',
                    path: 'review/modify/:articleno',
                    component: ReviewModify
                },
                {
                    name: "Reviewdetail",
                    path: "review/detail/:articleno",
                    component: ReviewDetail
                }

               
                ,

                // QnA
                {
                    name: 'qnalist',
                    path: 'qna/list',
                    component: qnaList
                },
                {
                    name: 'qnawrite',
                    path: 'qna/write',
                    component: qnaWrite
                },
                {
                    name: 'qnadelete',
                    path: 'qna/delete',
                    component: qnaDelete
                },
                {
                    name: 'qnamodify',
                    path: 'qna/modify/:articleno',
                    component: qnaModify
                },
                {
                    name: "qnadetail",
                    path: "qna/detail/:articleno",
                    component: qnaDetail
                }
            ]
        }
    ]
}
)