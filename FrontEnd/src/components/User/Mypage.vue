<template>
  <!-- ======= 페이지 제목 섹션 Start ======= -->
  <section class="PageTitle">
    <div class="no-container">
      <div class="container bc">
        📝 <span>My</span> 페이지
      </div>
    </div>
  </section>
  <!-- 페이지 제목 섹션 End -->

  <!-- ======= 회원 수정  섹션 Start ======= -->
  <section id="pages-details" class="pages-details">
     <div class="my-container">
        <div class="col-lg-8 pages-details-info p-3">
          <form v-if="userInfo" id="form" action="" method="Post" class="bg-white rounded shadow-5-strong p-5">
            <div class="row">
              <div class="mb-3">
                <label for="id">아이디</label>
                <input type="text" class="form-control" id="id" name="id" placeholder="" v-model="userInfo.userid" readonly="readonly" />
              </div>
              <div class="mb-3">
                <label for="pw">비밀번호<span style="color: red"> *</span></label>
                <input type="password" class="form-control" id="pw" name="pw" v-model="userInfo.userpwd" required />
              </div>
              <div class="mb-3">
                <label for="pw">비밀번호 확인<span style="color: red"></span></label>
                <input type="password" class="form-control" id="pwcheck" name="pwcheck" v-model="userInfo.pwcheck" />
              </div>
              <div class="mb-3">
                <label for="name">이름<span style="color: red"> *</span></label>
                <input type="text" class="form-control" id="name" name="name" v-model="userInfo.username" required />
              </div>
              <div class="mb-3">
                <label for="email">e-mail<span style="color: red"> *</span></label>
                <input type="email" class="form-control" id="email" name="email" v-model="userInfo.email" required />
              </div>
            </div>
            <div class="btns">
              <button type="button" id="deleteBtn" class="btn btn-secondary" @click="deleteUser">탈퇴하기</button>
              <button type="button" id="updateBtn" class="btn btn-warning" @click="modifyInfo">수정하기</button>
            </div>
          </form>
        </div>
      
    
     </div>
  </section>
  <!-- 회원 수정  섹션 End -->
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import { update, deleteUser } from "~/api/user/member.js";

const member = "member";

export default {
  name: "mypage",
  components: {},
  computed: {
    ...mapState(member, ["userInfo"]),
  },
  data() {
    return {
      originPw: null,
    };
  },
  created() {
    console.log(this.userInfo);
    this.originPw = this.userInfo.userpwd;
  },
  beforeDestroy(){
    this.userInfo.userpwd = this.originPw;
    delete this.userInfo.pwcheck;
  },
  methods: {
    ...mapActions(member, ["userConfirm", "getUserInfo"]),
    ...mapMutations(member, ["SET_IS_LOGIN", "SET_USER_INFO"]),
    async modifyInfo() {
      if (this.userInfo.hasOwnProperty("pwcheck")) {
        // 비밀번호 변경 시도를 했을 경우
        if (this.userInfo.pwcheck !== "") {
          // 시도를 하는 경우
          if (this.userInfo.pwcheck !== this.userInfo.userpwd) {
            alert("변경할 비밀번호가 일치하지 않습니다.");
            return;
          }
        } else {
          // 시도를 했지만 빈칸으로 둔 경우(비밀번호 확인 해야함)
          if (this.userInfo.userpwd !== this.originPw) {
            alert("비밀번호를 확인해주세요.");
            return;
          }
        }
      } else {
        // 비밀번호 변경이 아니면 비밀번호 확인 해야함
        if (this.userInfo.userpwd !== this.originPw) {
          alert("비밀번호를 확인해주세요.");
          return;
        }
      }
      await update(
        this.userInfo,
        () => {
          this.originPw = this.userInfo.userpwd;
          alert("정보 변경에 성공했습니다.");
        },
        () => {
          alert("정보 변경에 실패했습니다.");
        }
      );
    },
    async deleteUser() {
      if (!this.userInfo.hasOwnProperty("pwcheck")) {
        alert("비밀번호를 확인해주세요.");
        return;
      }
      if (this.userInfo.userpwd !== this.originPw) {
        alert("비밀번호를 확인해주세요.");
        return;
      }
      if (this.userInfo.pwcheck !== this.userInfo.userpwd) {
        alert("비밀번호를 확인해주세요.");
        return;
      }
      await deleteUser(
        this.userInfo,
        () => {
          alert("회원 탈퇴에 성공했습니다.");
          this.SET_IS_LOGIN(false);
          this.SET_USER_INFO(null);
          sessionStorage.removeItem("access-token");
          if (this.$route.path != "/") this.$router.push({ name: "home" });
        },
        () => {
          alert("회원 탈퇴에 실패했습니다.");
        }
      );
    },
    
  },
};
</script>

<style lang="scss" scoped>
.my-container{
  width:800px;
  
    margin:auto;
    max-width:inherit;
    .form-control{
      width:550px;
    }
    #updateBtn{
      margin-top:35px;
      float:right;
      margin-right:25px;
    }
    
    #deleteBtn{
      margin-top:35px;
      float:right;
      margin-right:25px;
    }
    
}</style>
