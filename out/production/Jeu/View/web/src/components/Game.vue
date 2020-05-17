<template>
    <div class="row m-0 p-0 h-100">
        <VictoirePannel v-if="this.$store.state.board !== null && this.$store.state.winner !== null"></VictoirePannel>
        <div v-if="!hasLoaded" class="loading"></div>
        <Menu :players="players"></Menu>
        <Board :fabriques="fabriques" :pionPremier="pionPremier"></Board>
    </div>
</template>
<script>
    import Menu from '@/components/Menu.vue';
    import Board from '@/components/Board.vue';
    import VictoirePannel from "./VictoirePannel";

    export default {
        name: "Game",
        components:{
            Menu,
            Board,
            VictoirePannel
        },
        computed: {
            hasLoaded(){
                return this.$store.state.board != null;
            },
            players() {
                return this.hasLoaded ? this.$store.state.board.PB : null;
            },
            fabriques() {
                return this.hasLoaded ? this.$store.state.board.factories : null;
            },
            pionPremier() {
                return this.hasLoaded ? this.$store.state.board.futureFirstPlayer === -1 : null;
            }
        },
        mounted(){
            setInterval(() => {
                this.$store.dispatch('getBoard');
                },1000);
            this.$store.dispatch("setFrontUpdated");
        },
        methods: {
        }
    }
</script>
       
