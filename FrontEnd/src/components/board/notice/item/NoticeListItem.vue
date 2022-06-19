<template>
  <tr>
    <td style="width:70px; text-align:center; border-right: 3px solid #fff">{{article.articleno}}</td>
    <th class="text-left" style="width:230px; padding-left:20px;font-weight:600;border-right: 3px solid #fff; ">
      <RouterLink
        :to="`/board/notice/detail/${article.articleno}`"
        style="text-decoration:none">
      {{ article.subject }}
      </RouterLink>
    </th>
    <td style="width:80px;text-align:center;border-right: 3px solid #fff">{{ article.userid }}</td>
    <td style="width:120px;text-align:center;border-right: 3px solid #fff">{{ article.regtime }}</td> 
  </tr>
</template>

<script>
import moment from "moment";

export default {  
  name: "NoticeListItem",
  props: {
        article:{
            type:Object,
            default:() => ({})
        }
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
};
</script>

<style></style>
