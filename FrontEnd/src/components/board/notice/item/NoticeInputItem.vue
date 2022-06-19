<template>
  <div>
    <form @submit="onSubmit" @reset="onReset">
      <div class="form-container">
        <div class="mb-3">
          <label for="userid" class="form-label">작성자 : </label>
          <input type="text" class="form-control" id="userid" v-model="article.userid" aria-describedby="IdHelp" readonly="readonly" />
          <!-- <div id="IdHelp" class="form-text">작성자를 입력하세요</div> -->
        </div>
        <div class="mb-3">
          <label for="subject" class="form-label">제목 :</label>
          <input type="text" class="form-control" id="subject" v-model="article.subject" />
        </div>
        <div class="mb-3">
          <label for="content" class="form-label">내용 :</label>
          <textarea class="form-control" id="content" v-model="article.content" rows="10" max-rows="15"></textarea>
        </div>
        <button type="submit" class="btn btn-primary" id="write-btn" v-if="this.type === 'Noticewrite'">글 작성</button>
        <button type="submit" class="btn btn-primary" id="write-btn" v-else>글 수정</button>
        <button type="reset" class="btn btn-primary" id="write-btn">초기화</button>
      </div>
    </form>
  </div>
</template>

<script>
import http from "~/api/http";

export default {
  name: "NoticeInputItem",
  data() {
    return {
      article: {
        articleno: 0,
        userid: "admin",
        subject: "",
        content: "",
      },
      isUserid: false,
    };
  },
  computed: {
    userInfo() {
      return this.$store.state.member.userInfo;
    },
  },
  props: {
    type: { type: String },
  },

  created() {
    if (this.type === "Noticemodify") {
      http.get(`/board/notice/${this.$route.params.articleno}`).then(({ data }) => {
        // this.article.articleno = data.article.articleno;
        // this.article.userid = data.article.userid;
        // this.article.subject = data.article.subject;
        // this.article.content = data.article.content;
        this.article = data;
      });
      this.isUserid = true;
    }
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      this.article.userid = this.userInfo.userid;
      !this.article.userid && ((msg = "작성자 입력해주세요"), (err = false), this.$refs.userid.focus());
      err && !this.article.subject && ((msg = "제목 입력해주세요"), (err = false), this.$refs.subject.focus());
      err && !this.article.content && ((msg = "내용 입력해주세요"), (err = false), this.$refs.content.focus());

      if (!err) alert(msg);
      else this.type === "Noticewrite" ? this.registArticle() : this.modifyArticle();
    },
    onReset(event) {
      event.preventDefault();
      this.article.articleno = 0;
      this.article.subject = "";
      this.article.content = "";
      this.$router.push({ name: "Noticelist" });
    },
    registArticle() {
      http
        .post(`/board/notice`, {
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    modifyArticle() {
      http
        .put(`/board/notice/${this.article.articleno}`, {
          articleno: this.article.articleno,
          userid: this.article.userid,
          subject: this.article.subject,
          content: this.article.content,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "Noticelist" });
        });
    },
    moveList() {
      this.$router.push({ name: "Noticelist" });
    },
  },
};
</script>

<style>
.form-container {
  width: 600px;

  padding: 38px 5px;
  margin: auto;
  max-width: inherit;
  .form-control {
    width: 600px;
  }
  #write-btn {
    float: right;
    margin-left: 10px;
    font-weight: 600;
    color: black;

    background-color: rgb(246, 248, 255);
  }
}
</style>
