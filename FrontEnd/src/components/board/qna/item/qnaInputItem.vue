<template>
  <div>
    <form v-if="userInfo" @submit="onSubmit" @reset="onReset">
            <div class="form-container">
      <div class="mb-3">
        <label for="userid" class="form-label">작성자 : </label>
        <input type="text" class="form-control" id="userid" v-model="qna.userid" aria-describedby="IdHelp" readonly="readonly" />
        <!-- <div id="IdHelp" class="form-text">작성자를 입력하세요</div> -->
      </div>
      <div class="mb-3">
        <label for="subject" class="form-label">제목 :</label>
        <input type="text" class="form-control" id="subject" v-model="qna.title" />
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">내용 :</label>
        <textarea v-if="userInfo.userid !== 'admin'" class="form-control" id="content" v-model="qna.content" rows="10" max-rows="15"></textarea>
        <textarea v-else-if="qna.userid === 'admin'" class="form-control" id="content" v-model="qna.content" rows="10" max-rows="15"></textarea>
        <textarea v-else class="form-control" id="content" v-model="qna.content" rows="10" max-rows="15" readonly="readonly"></textarea>
      </div>
      <div class="mb-3">
        <label for="content" class="form-label">답변 :</label>
        <textarea v-if="userInfo.userid !== 'admin'" class="form-control" id="content" v-model="qna.answer" rows="10" max-rows="15" readonly="readonly"></textarea>
        <textarea v-else class="form-control" id="content" v-model="qna.answer" rows="10" max-rows="15"></textarea>
      </div>
      <button type="submit" class="btn btn-primary" id="write-btn" v-if="this.type === 'qnawrite'">글 작성</button>
      <button type="submit" class="btn btn-primary" id="write-btn" v-else-if="userInfo.userid !== 'admin'">글 수정</button>
      <button type="submit" class="btn btn-primary" id="write-btn" v-else>답변 등록</button>
      <!-- <button type="reset" class="btn btn-primary">초기화</button> -->
    </div>
    </form>
    <div v-else>로그인이 필요한 기능입니다.</div>
  </div>
</template>

<script>
import http from "~/api/http";

export default {
  name: "qnaInputItem",
  data() {
    return {
      qna: {
        qnano: 0,
        userid: "",
        title: "",
        content: "",
        answer: "",
      },
    };
  },
  props: {
    type: { type: String },
  },
  created() {
    if (this.type === "qnamodify") {
      http.get(`/board/qna/get/${this.$route.params.qnano}`).then(({ data }) => {
        console.log(this.qna);
        console.log(this.$route.params);
        console.log(data);
        // this.article.articleno = data.article.articleno;
        // this.article.userid = data.article.userid;
        // this.article.subject = data.article.subject;
        // this.article.content = data.article.content;
        this.qna = data;
      });
    }
    else{
      this.qna.userid = this.userInfo.userid;
    }
  },
  computed: {
    userInfo() {
      return this.$store.state.member.userInfo;
    },
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();

      let err = true;
      let msg = "";
      err && !this.qna.title && ((msg = "제목을 입력해주세요"), (err = false));
      err && !this.qna.content && ((msg = "내용을 입력해주세요"), (err = false));

      if (!err){
        alert(msg);
        return;
      }
      if (this.type === "qnawrite"){
        this.qna.userid = this.userInfo.userid;
        this.registQna();
      }
      else{
        this.modifyQna();
      }
    },
    onReset(event) {
      event.preventDefault();
      this.qna.title = "";
      this.qna.content = "";
      this.$router.push({ name: "qnalist" });
    },
    async registQna() {
      await http
        .post(`/board/qna/write`, {
          userid: this.qna.userid,
          title: this.qna.title,
          content: this.qna.content,
          answer: this.qna.answer,
        })
        .then(({ data }) => {
          let msg = "등록 처리시 문제가 발생했습니다.";
          if (data === "Success") {
            msg = "등록이 완료되었습니다.";
          }
          alert(msg);
          this.moveList();
        });
    },
    modifyQna() {
      http
        .put(`/board/qna/modify`, {
          qnano: this.qna.qnano,
          userid: this.qna.userid,
          title: this.qna.title,
          content: this.qna.content,
          answer: this.qna.answer,
        })
        .then(({ data }) => {
          let msg = "수정 처리시 문제가 발생했습니다.";
          if (data === "Success") {
            msg = "수정이 완료되었습니다.";
          }
          alert(msg);
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "qnalist" });
        });
    },
    moveList() {
      this.$router.push({ name: "qnalist" });
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
}</style>
