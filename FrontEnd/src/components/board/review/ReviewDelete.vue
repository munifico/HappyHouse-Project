<template>
<div class="container">
  <div><h3>글목록</h3></div>
  <div>
    <alert>삭제처리중...</alert>
  </div>
</div>
</template>

<script>
import http from "~/api/http";

export default {
  name: "Reviewdelete",
  created() {
    http.delete(`/board/review/${this.$route.params.articleno}`).then(({ data }) => {
      let msg = "삭제 처리시 문제가 발생했습니다.";
      if (data === "success") {
        msg = "삭제가 완료되었습니다.";
      }
      alert(msg);
      // 현재 route를 /list로 변경.
      this.$router.push({ name: "Reviewlist" });
    });
  },
};
</script>

<style></style>
