<template>
    <div class="breadcrumb">
        <main>
            <Searchbar />
            <AdvCard class="ad"/>
       
        </main>
        <RightBanner />

    </div>


    <div class="main-container">
        <main>
            <div class="board-card-container">
                <NoticeCard /> 
                <QnaCard/>
            </div>
            <div class="board1">
                <ReviewCard/>
            </div>
        </main>

    </div>

</template>

<script>
import ReviewCard from '~/components/Main/ReviewCard';
import NoticeCard from '~/components/Main/NoticeCard';
import QnaCard from '~/components/Main/QnaCard';
import Searchbar from '~/components/Main/Searchbar';
import RightBanner from '~/components/Main/RightBanner';
import AdvCard from '~/components/Main/AdvCard';

export default{
    components:{
        ReviewCard,
        NoticeCard,
        QnaCard,
        Searchbar,
        RightBanner,
        AdvCard
    },
    created(){
    },
    methods:{
    }
}
</script>


<style lang="scss">

.breadcrumb{
    display:flex;
    margin: auto;
    margin-top:40px;
    max-width: 1100px;
    main{
        width:100%;
        max-width: 736px;;
        
        .ad{
            width:700px;
            margin:auto;
            height:230px;
            border-radius: 15px;
        
        }
    }
}

.main-container{
    display:flex;
    margin: auto;
    max-width: 1100px;
    main{
        width:100%;
        max-width: 736px;;

    }
    .board1{
    width :60%;
    }
    .board-card-container{
    width :100%;
    min-height:150px;
    display:grid;
    grid-template-columns: 1fr 1fr;
    grid-gap: 45px 40px;
    }
}

</style>
