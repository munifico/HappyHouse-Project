<template>
    <RouterLink 
    to="/" 
    class="logo">
    <span>
    Happy 
    </span>
    House
    </RouterLink>
</template>

<style lang="scss" scoped>
@import "~/scss/main";

.logo {
    font-family: "Oswald", sans-serif;
    font-size: 22px;
    color: $black;
    text-decoration: none;
    &:hover{
        color:$black;
    }
    span{
        color:$primary;
    }
}
</style>