<template>
<div class="container">
  <div>
    <alert><h3>글작성</h3></alert>
  </div>
  <qnaInputItem type="qnawrite"/>
</div>
</template>

<script>
import qnaInputItem from "~/components/board/qna/item/qnaInputItem.vue";

export default {
  name: "qnawrite",
  components: {
    qnaInputItem,
  },
};
</script>

<style></style>
