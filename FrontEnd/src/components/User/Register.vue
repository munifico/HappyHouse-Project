<template>
  <!-- ======= 페이지 제목 섹션 Start ======= -->
 <section class="PageTitle">
    <div class="my-container">
      <div class="container bc">
        📝 <span>회원</span> 가입
      </div>
    </div>
  </section>
  <!-- 페이지 제목 섹션 End -->

  <!-- ======= 회원가입  섹션 Start ======= -->
  <section id="pages-details" class="pages-details">
    <div class="my-container">
            <div class="row" v-if="newInfo">
              <div class="mb-4">
                <label for="id">아이디<span style="color: red"> *</span></label>
                <input type="text" class="form-control" id="id" name="id" v-model="newInfo.userid" required />
                <!-- <p6 style="color: red;">이미 등록된 아이디입니다.</p6> -->
              </div>
              <div class="mb-4">
                <label for="pw">비밀번호<span style="color: red"> *</span></label>
                <input type="password" class="form-control" id="pw" name="pw" v-model="newInfo.userpwd" required />
              </div>
              <div class="mb-4">
                <label for="pw">비밀번호 확인<span style="color: red"> *</span></label>
                <input type="password" class="form-control" id="pwcheck" name="pwcheck" v-model="newInfo.pwcheck" required />
              </div>
              <div class="mb-4">
                <label for="name">이름<span style="color: red"> *</span></label>
                <input type="text" class="form-control" id="name" name="name" v-model="newInfo.username" required />
              </div>
              <div class="mb-4">
                <label for="email">e-mail<span style="color: red"> *</span></label>
                <input type="email" class="form-control" id="email" name="email" v-model="newInfo.email" placeholder="you@example.com" required />
              </div>
              <!-- <div class="mb-4">
                <label for="phone">전화번호<span style="color: red"> *</span></label>
                <input type="text" class="form-control" id="phone" name="phone" v-model="newInfo.userid" placeholder="010-xxxx-xxxx" required />
              </div> -->
            </div>
            <div class="custom-control custom-checkbox">
              <input type="checkbox" class="custom-control-input" id="agreement" checked required />
              <label class="custom-control-label" for="aggrement">개인정보 수집 및 이용에 동의합니다.</label>
            </div>
              <button type="submit" class="btn btn-primary btn-lg btn-block" id="registerbtn" @click="register">가입하기</button>
            

    
    </div>
  </section>
  <!-- 회원가입  섹션 End -->
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import { registerUser } from "~/api/user/member.js";
const member = "member";
export default {
  name: "register",
  components: {},
  computed: {
    ...mapState(member, ["userInfo"]),
  },
  data() {
    return {
      newInfo: null,
    };
  },
  created() {
    this.newInfo = {
      userid: "",
      userpwd: "",
      pwcheck: "",
      username: "",
      email: "",
    };
  },
  beforeDestroy() {},
  methods: {
    ...mapActions(member, ["userConfirm", "getUserInfo"]),
    ...mapMutations(member, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    async register(){
      console.log(this.newInfo);
      if (this.newInfo.userpwd === ""){
        alert("비밀번호를 입력하세요.");
        return;
      }
      if (this.newInfo.userpwd !== this.newInfo.pwcheck){
        alert("비밀번호가 일치하지 않습니다.");
        return;
      }
      await registerUser(
        this.newInfo,
        () => {
          alert("회원가입에 성공했습니다.");
          if (this.$route.path != "/") this.$router.push({ name: "home" });
        },
        () => {
          alert("회원가입에 실패했습니다.");
        }
      );
    }
  },
};
</script>

<style lang="scss" scoped>
.bc{
  padding-left:0;
  padding-top:40px;
  padding-bottom:30px;
}
.my-container{
  width:800px;
  
    margin:auto;
    max-width:inherit;
    .form-control{
      width:550px;
    }
    
    #registerbtn{
      margin-top:35px;
      float:right;
      margin-right:250px;
    }
    
}
</style>
