<template>
    <footer>
 

        <div class="container">
        <div class="copyright">
            &copy; Copyright <strong>
                <span>SSAFY</span></strong>. All Rights Reserved
        </div>
        <div class="credits">Designed by 구미 5반 이재환, 한제규
        </div>
        </div>

        <Logo />
        <a
            href="https://edu.ssafy.com/comm/login/SecurityLoginForm.do"
            target="_blank">
            (c){{ new Date().getFullYear()}} ssafy
        </a>

    </footer>
</template>

<script>
import Logo from '~/components/Layout/Logo'

export default {
    components:{
        Logo
    }
}
</script>

<style lang="scss" scoped>

footer{
    background-color:rgb(247, 252, 255);
    margin-top:100px;
    padding: 25px 0;
    text-align: center;
    opacity: .5;
    .logo{
        display:block;
        margin-bottom : 4px;
    }
}
</style>