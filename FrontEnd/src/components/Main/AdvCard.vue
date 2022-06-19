<template>
<div>
  <img src="" class="ad" id="advId"/>
  </div>
</template>
<script>
export default {
  mounted (){
    let randomNumber = Math.floor(Math.random() * 3) + 1;
    var selector = document.getElementById("advId");
    // console.log(selector);
    selector.setAttribute('src', '../../../static/adv/adv0' + randomNumber + '.png');
  }

};
</script>
<style lang="scss">
</style>
