<template>
  <HeaderNav />
  <RouterView />
  <Footer />

</template>

<script>
import HeaderNav from '~/components/Layout/HeaderNav'
import Footer from '~/components/Layout/Footer'

export default{
  components:{
    HeaderNav,
    Footer
  }
}
</script>

<style lang="scss">
@import "~/scss/main";
</style>