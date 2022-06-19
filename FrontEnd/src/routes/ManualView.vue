<template>

<div class="no-container bc">😀 Happy House 메뉴얼</div>
<div class="container">
    <div class="about">
       <div class="videos">
                <video controls poster="videos/happyhouse.png">
                    <source src="videos/happyhouse.mp4" type="video/mp4">
                </video>
        </div>
      <div class="description">
        1. 메인 화면 <span>[0:00~1:00]</span><br>
        </div>
          <div class="description">
        2. 회원가입, 로그인, 로그아웃, 회원정보 조회,수정,탈퇴 <span>[1:01~2:25]</span><br>
        </div>
          <div class="description">
        3. 아파트 거래정보 조회, 상세 조회 <span>[2:26~3:38]</span><br>
        </div>
          <div class="description">
        4. 공지사항, 익명게시판(수다수다), QnA 게시판 <span>[3:39~5:47]</span>
      </div>
    </div>

</div>


</template>

<script>
import Loader from '~/components/Loader'
export default {
    components:{
        Loader
    },
    computed:{
        loading(){
            return this.$store.state.apt.loading
        }
    }
}
</script>

<style lang="scss" scoped>
@import "~/scss/main";

.bc{

  font-size: 42px;
  font-weight:600;
  text-align: center;
  display: flex;
}
.container{
  display:flex;
  width:900px;
  margin-top: 40px;
    
  .about{
  text-align:  center;
  margin:auto;
  .videos{
    width: 620px;
    height:400px;
    margin:auto;
    padding:30px;
    border:4px solid rgb(168, 188, 224);
    background-color: rgb(247, 251, 255);
    border-radius: 10%;
    box-sizing: border-box;
    margin-bottom: 40px;
  }

  .description{
      margin-left: 30px;
    font-size: 20px;
    margin-bottom: 20px;
    font-weight: 400;
    text-align: left;
    span{
        color:rgb(72, 135, 250);
    }
  }
  .items{
    font-size:18px;
    text-align: center;
  }
}
    }

video {
     max-width: 100%;
      max-height: 100%;
       display: block;
       margin-top:10px;
       }
    
</style>