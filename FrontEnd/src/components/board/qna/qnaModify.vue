<template>
   <div class="no-container">
    <div class="container bc">📝 <span>답변</span> 달기</div>
    <qnaInputItem
    type="qnamodify"
    />
  </div>

</template>

<script>
import qnaInputItem from "~/components/board/qna/item/qnaInputItem.vue";

export default {
  name: "qnamodify",
  components: {
    qnaInputItem,
  },
};
</script>

<style></style>
