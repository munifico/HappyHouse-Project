<template>
    <div class="nav-container">
        <nav>
            <div class="side-block nav nav-pills ">
                <Logo />
                <div
                    v-for="nav in navigations"
                    :key="nav.name"
                    class="nav-item">
                    <RouterLink 
                        :to="nav.href"
                        active-class="active"
                        :class="{active:isMatch(nav.path)}"
                        class="nav-link text-menu">
                        {{nav.name}}
                    </RouterLink>
                </div>
            </div>

            <div class="side-block" v-if="userInfo">
              <div>{{userInfo.userid}}님 반갑습니다.</div>
                <a @click.prevent="clickMypageButton" id="mypage-btn">회원정보</a>
                <a @click.prevent="clickLogoutButton" id="login-btn"> 로그아웃
                </a>

            </div>
            <div class="side-block" v-else>
                <a @click.prevent="clickRegisterButton" id="register-btn">회원가입</a>
                <a @click.prevent="clickLoginButton" id="login-btn"> 로그인
                </a>

            </div>


            <LoginModal />
            <!-- <WritingModal /> -->
        </nav>
    </div>
</template>

<script>
import Logo from '~/components/Layout/Logo'
import LoginModal from "~/components/User/Login";
import { mapState, mapMutations } from "vuex";

const member = "member";

export default{
    name:"HeaderNav",
    components:{
        Logo,
        LoginModal
    },

    computed: {
    ...mapState(member, ["isLogin", "userInfo"]),
    },
    data(){
        return{
            navigations: [
                {
                    name: 'Home',
                    href: '/'
                },
                 {
                     name: 'Manual',
                    href: '/manual',
                    path:  /^\/manual/ //'/apt'
                },
                     {
                        name: 'Trade',
                        href: '/trade'
                    },
                 {
                     name: 'Board',
                    href: '/board'
                },
                {
                    name: 'About',
                    href: '/about'
                },
               
            ]
        }
    },

    methods:{
        isMatch(path){
            if(!path) return false
            return path.test(this.$route.fullPath)
        },
        ...mapMutations(member, ["SET_IS_LOGIN", "SET_USER_INFO"]),

        //로그아웃 버튼
        clickLogoutButton() {
            // console.log("memberStore : ", ms);
            this.SET_IS_LOGIN(false);
            this.SET_USER_INFO(null);
            sessionStorage.removeItem("access-token");
            alert("로그아웃에 성공했습니다.");
            if (this.$route.path != "/") this.$router.push({ name: "home" });
        },
        //회원정보 버튼
        clickMypageButton(){
            this.$router.push({ name: "mypage" });
        },
        //로그인 버튼
        clickLoginButton(){
                this.$store.commit("modal/SET_LOGIN_MODAL_OPEN");
           
        },
        //회원가입 버튼
        clickRegisterButton(){ 
            this.$router.push({ name: "signup" });
           
        }

    }
}
</script>

<style lang="scss" scoped>
.nav-container{
    border-bottom: 2.3px solid #6ea1e4; 
}
nav{
    display: flex;
    justify-content: space-between;
    height: 90px;
    padding: 0 40px;
    align-items:center;
    width:100%;
    margin:auto;
    padding: 20px;
    max-width:1200px;
    .side-block{
        display: flex;
        align-items: center;
        height:100%;
        .text-menu{
            font-size:20px;
            margin-right: 20px;
            font-weight: 600;

        }

        #register-btn{
            background: rgb(60, 127, 252);
            color:white;
            font-size:14px;
            height:40px;
            margin-left:10px;
            width:82px;
            display:inline-flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            border-radius: 5px;

        }
        #mypage-btn{
            background: rgb(60, 127, 252);
            color:white;
            font-size:14px;
            height:40px;
            margin-left:10px;
            width:82px;
            display:inline-flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            border-radius: 5px;

        }
         #login-btn{
            background: white;
            color:rgb(34,34,34);
            font-size:14px;
            border:solid 1px rgb(212,212,212);
            height:40px;
            margin-left:10px;
            width:82px;
            display:inline-flex;
            align-items: center;
            justify-content: center;
            font-weight: 700;
            border-radius: 5px;

        }
    }
    .logo{
        margin-right:50px;
    }
}
</style>