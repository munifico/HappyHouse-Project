<template>
  <AptSearch />
  <AptMap ref="aptMap" />
  <AptList ref="aptList" />
  <!-- 컴포넌트 MyModal -->
  <AptDetail @close="closeModal" v-if="modal"> </AptDetail>
</template>

<script>
import AptSearch from "~/components/Trade/AptSearch";
import AptList from "~/components/Trade/AptList";
import AptMap from "~/components/Trade/AptMap";
import AptDetail from "~/components/Trade/AptDetail";

export default {
  components: {
    AptSearch,
    AptMap,
    AptList,
    AptDetail,
  },
  data() {
    return {
      modal: false,
    };
  },
  created() {
    this.$store.commit("apt/resetApts");
  },
  methods: {
    openModal(modalData) {
      this.$store.commit("apt/setModalData", modalData);
      this.modal = true;
    },
    closeModal() {
      this.modal = false;
    },
    mapSearch(searchInfo) {
      this.$refs.aptMap.mapSearch(searchInfo);
    },
  },
};
</script>
