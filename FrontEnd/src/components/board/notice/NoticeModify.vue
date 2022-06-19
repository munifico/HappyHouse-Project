<template>
  <div class="no-container">
    <div class="container bc">📝 <span>글</span> 수정</div>
    <NoticeInputItem type="Noticemodify"/>
  </div>

</template>

<script>
import NoticeInputItem from "~/components/board/notice/item/NoticeInputItem.vue";

export default {
  name: "Noticemodify",
  components: {
    NoticeInputItem,
  },
};
</script>

<style>
.no-container{
  padding-top:30px;
  padding-right:100px;
}
.bc{

    padding: 0px 90px;
    padding-top:20px;
    width:800px;
    margin:auto;
    max-width: inherit;
  font-size: 30px;
  font-weight:600;
  span{
    color:rgb(72, 135, 250);
  }
}</style>
