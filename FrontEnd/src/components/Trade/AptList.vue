<template>
    <div class = "container">
        <div class="inner">
            <!-- <Loader v-if="loading" />
            <div 
                v-if="message"
                class="message">
                {{message}}
            </div> -->
            <!-- <div class="message">Search for the Apt Name</div> -->
            <!-- <div 
                v-else
                class="apts">
                <AptItem 
                    v-for = "apt in aptlist.aptInfo"
                    :key="apt[0]"
                    :apt="apt"
                    :id="apt[1].address.replace(/ /gi, '')"
                />
            </div> -->

        </div>
        
    </div>
</template>

<script>
import AptItem from '~/components/Trade/AptItem'
import Loader from '~/components/Loader'

export default {
    components: { 
        AptItem,
        Loader
    },
    computed:{
        aptlist(){
            return this.$store.state.apt.apts
        },
        message(){
            return this.$store.state.apt.message
        },
        loading(){
            return this.$store.state.apt.loading
        }
    }
}

</script>

<style lang="scss" scoped>
@import "~/scss/main";
.container{
    margin-top: 30px;
    .inner{
        padding: 10px 0;
        border-radius :4px;
        text-align:center;
        &.no-result{
            padding:70px 0;
        }
    }
    .message{
        color:$gray-400;
        font-size:20px;
    }
    .apts{
        display:flex;
        flex-wrap:wrap;
        justify-content: center;
    }
}
</style>