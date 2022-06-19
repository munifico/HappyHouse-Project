<template>
  <tr>
    <td style="width:70px; text-align:center; border-right: 3px solid #fff">{{qna.qnano}}</td>
    <th class="text-left" style="width:230px; padding-left:20px;font-weight:600;border-right: 3px solid #fff; ">
        <RouterLink
        :to="`/board/qna/detail/${qna.qnano}`"
        style="text-decoration:none">
      {{ qna.title }}
      </RouterLink>
    </th>
    <td style="width:80px;text-align:center;border-right: 3px solid #fff">{{ qna.userid}}</td>
    <td style="width:120px;text-align:center;border-right: 3px solid #fff">{{ qna.regtime}}</td> 
  </tr>
</template>

<script>
import moment from "moment";

export default {  
  name: "qnaListItem",
  props: {
        qna:{
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
