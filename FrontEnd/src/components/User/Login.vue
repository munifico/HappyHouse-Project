<template>
  <div v-if="modal.login.show" class="modal modal-outside">
    <div id="login-modal">
      <div class="head">
        <h5>로그인</h5>
        <a @click.prevent="$store.commit('modal/SET_LOGIN_MODAL_CLOSE')" class="close-btn">
          <img src="/icon/close.png" alt="닫기" />
        </a>
      </div>
      <div class="body">
        <div class="row">
          <label for="userid">사용자 계정</label>
          <input id="userid" type="text" v-model="user.userid" />
        </div>
        <div class="row">
          <label for="userpwd">비밀번호</label>
          <input id="userpwd" type="password" v-model="user.userpwd" @keyup.enter="loginWithID" />
        </div>
        <button class="login-btn" @click="loginWithID">로그인</button>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const member = "member";

export default {
  data() {
    return {
      user: {
        userid: null,
        userpwd: null,
      },
    };
  },
  computed: {
    ...mapState(["modal"]),
    ...mapState(member, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(member, ["userConfirm", "getUserInfo"]),
    async loginWithID() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$store.commit("modal/SET_LOGIN_MODAL_CLOSE");
        alert("로그인에 성공했습니다.");
        if (this.$route.path != "/") this.$router.push({ name: "home" });
      }
      else{
        alert("로그인에 실패했습니다.");
      }
    },
  },
};
</script>

<style lang="scss" scoped>
#login-modal {
  background: white;
  width: 520px;
  .head {
    color: rgb(34, 34, 34);
    font-size: 18px;
    font-weight: 700;
    padding: 23px 30px;
    border-bottom: solid 1px rgb(223, 225, 229);
    h5 {
      margin: 0;
    }
  }
  .body {
    font-size: 16px;
    padding: 0 30px;
    line-height: 24px;
    p {
      padding: 20px 0;
      margin: 0;
    }
    .info {
      color: rgb(148, 150, 155);
      font-size: 14px;
      letter-spacing: -0.1px;
      margin-top: 20px;
      line-height: 21px;
    }

    .row {
      margin: 20px 0;
      label {
        display: block;
      }
      input {
        width: 100%;
        box-sizing: border-box;
        padding: 12px;
      }
    }
    .login-btn {
      display: flex;
      justify-content: center;
      align-items: center;
      background: rgb(55, 172, 201);
      border: none;
      color: white;
      font-size: 24px;
      font-weight: 400;
      width: 100%;
      height: 64px;
      margin-bottom: 30px;
    }
    .left-time {
      text-align: center;
      color: rgb(55, 172, 201);
      font-size: 14px;
      font-weight: 700;
      margin-top: 16px;
    }
  }

  .foot {
    color: rgb(160, 160, 174);
    font-size: 14px;
    line-height: 17.5px;
    text-align: center;
    text-decoration: underline;
    padding: 30px;
  }
}
</style>
