<template>
    <div class="row m-0 p-0 h-100">
        <template v-if="tutoriel">
            <Tutoriel></Tutoriel>
        </template>
        <template v-else>
            <VictoirePannel v-if="this.$store.state.board !== null && this.$store.state.winner !== null"></VictoirePannel>
            <div v-if="!hasLoaded" class="loading"></div>
            <Menu :players="players"></Menu>
            <Board :fabriques="fabriques" :pionPremier="pionPremier"></Board>
        </template>
    </div>
</template>
<script>
    import Menu from '@/components/Menu.vue';
    import Board from '@/components/Board.vue';
    import VictoirePannel from "./VictoirePannel";
    import Tutoriel from "./Tutoriel";

    export default {
        name: "Game",
        date() {
            return {
                interval : null
            }
        },
        components:{
            Menu,
            Board,
            VictoirePannel,
            Tutoriel
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
            },
            isCurrentAI(){
                return this.$store.state.board.PB[this.$store.state.board.currentPlayer].name.includes("AI");
            },
            tutoriel() {
                return this.$store.state.tutoEnCours && !this.$store.state.jaiCompris
            }
        },
        mounted(){
            this.$store.dispatch('getBoard');
            this.$store.dispatch("setFrontUpdated");
            this.interval = setInterval(() => {
                    if(this.isCurrentAI && !this.$store.state.winner){
                        this.$store.dispatch('getBoard');
                    }
                },1000);
        },
        beforeDestroy() {
            clearInterval(this.interval)
        },
        methods: {
        }
    }
</script>
       
