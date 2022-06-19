<template>
  <div class="no-container">
    <div class="container bc">📝 <span>글</span> 작성</div>
  <ReviewInputItem type="Reviewwrite"/>
</div>
</template>

<script>
import ReviewInputItem from "~/components/board/review/item/ReviewInputItem.vue";

export default {
  name: "Reviewwrite",
  components: {
    ReviewInputItem,
  },
};
</script>

<style></style>
