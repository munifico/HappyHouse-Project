<template>

  <div class="no-container">
    <div class="container bc">📝 <span>글</span> 작성</div>
    <NoticeInputItem type="Noticewrite"/>
  </div>
</template>

<script>
import NoticeInputItem from "~/components/board/notice/item/NoticeInputItem.vue";

export default {
  name: "Noticewrite",
  components: {
    NoticeInputItem,
  },
};
</script>

<style></style>
