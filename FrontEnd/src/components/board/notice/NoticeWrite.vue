<template>

  <div class="no-container">
    <div class="container bc">π <span>κΈ</span> μμ±</div>
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
