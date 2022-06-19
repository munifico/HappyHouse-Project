<template>
  <div class="apt-container">

    <!-- 분류 Drop-down -->
    <div class="selects1">
      <select v-model="sidoInfo" :key="sidoInfo" class="form-select" @change="getGugun">
        <option v-for="item in filters[0].items" :key="item" :value="[item.sidoName, item.sidoCode]">
          {{ item.sidoName }}
        </option>
      </select>
      <select v-model="gugunInfo" :key="gugunInfo" class="form-select" @change="getDong">
        <option v-for="item in filters[1].items" :key="item" :value="[item.gugunName, item.gugunCode]">
          {{ item.gugunName }}
        </option>
      </select>
      <select v-model="dongInfo" :key="dongInfo" class="form-select">
        <option v-for="item in filters[2].items" :key="item" :value="[item.dongName, item.dongCode]">
          {{ item.dongName }}
        </option>
      </select>
    </div>
    <!-- 내용 input-->
    <input v-model="aptName" class="form-control" type="text" placeholder="Search for Apt" @keyup.enter="apply" />
    <div class="selects2">
      <select v-model="year" :key="year" class="form-select">
        <option v-for="item in filters[3].items" :key="item" :value="item">
          {{ item }}
        </option>
      </select>
      <select v-model="month" :key="month" class="form-select">
        <option v-for="item in filters[4].items" :key="item" :value="item">
          {{ item }}
        </option>
      </select>
    </div>

    <!-- 검색 button-->
    <button class="btn btn-primary" @click="apply">Search</button>
  </div>
</template>

<script>
import http from "../../api/http";

export default {
  data() {
    return {
      aptName: "",
      sidoInfo: ["시도선택", "0000000000"],
      gugunInfo: ["구군선택", "0000000000"],
      dongInfo: ["동선택", "0000000000"],
      year: "2020",
      month: "01",
      filters: [
        {
          name: "sido",
          items: [],
        },
        {
          name: "gugun",
          items: [],
        },
        {
          name: "dong",
          items: [],
        },
        {
          name: "year",
          items: ["2020", "2021"]
        },
        {
          name: "month",
          items: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"]
        },
      ],
    };
  },
  created() {
    this.getSido();
    this.getGugun();
    this.getDong();
  },

  methods: {
    async apply() {
      if (this.sidoInfo[0] === "시도선택"){
        alert("시/도를 선택해주세요.");
        return;
      }
      if (this.gugunInfo[0] === "구군선택"){
        alert("구/군을 선택해주세요.");
        return;
      }
      const searchInfo = {
        sidoInfo : this.sidoInfo,
        gugunInfo : this.gugunInfo,
        dongInfo : this.dongInfo,
        year : this.year,
        month : this.month,
        aptName : this.aptName === "" ? "_" : this.aptName,
      }
      this.$parent.mapSearch(searchInfo);
    },
    async getSido() {
      http.get(`/selector/sido`).then(({ data }) => {
        this.filters[0].items = data;
      });
    },
    async getGugun() {
      await http.get(`/selector/gugun/`+this.sidoInfo[1]).then(({ data }) => {
        this.filters[1].items = data;
      });
      this.gugunInfo = ["구군선택", "0000000000"];
      this.getDong();
    },
    async getDong() {
      await http.get(`/selector/dong/`+this.gugunInfo[1]).then(({ data }) => {
        this.filters[2].items = data;
      });
    },
  },
};
</script>

<style lang="scss" scoped>
.apt-container {
  padding-top:40px;
    justify-content: center;

  display: flex;
  > * {
    margin-right: 10px;
    font-size: 15px;
  }

  .selects1 {
    display: flex;
    select {
      width: 130px;
      margin-right: 10px;
      font-size: 15px;
      font-weight: 700;
      &:last-child {
        margin-right: 0;
      }
    }
  }
  .selects2 {
    display: flex;
    select {
      width: 110px;
      margin-right: 10px;
      font-size: 15px;
      font-weight: 700;
      &:last-child {
        margin-right: 0;
      }
    }
  }
  .form-control{
    width:300px;
  }

  .btn {
    width: 100px;
    height: 50px;
    font-weight: 700;
    flex-shrink: 0;
  }
}
</style>
