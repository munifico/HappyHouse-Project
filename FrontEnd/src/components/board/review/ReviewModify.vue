<template>
  <div class="no-container">
    <div class="container bc">📝 <span>글</span> 수정</div>
    <ReviewInputItem type="Reviewmodify"/>
  </div>

</template>

<script>
import ReviewInputItem from "~/components/board/review/item/ReviewInputItem.vue";

export default {
  name: "Reviewmodify",
  components: {
    ReviewInputItem,
  },
};
</script>

<style></style>
