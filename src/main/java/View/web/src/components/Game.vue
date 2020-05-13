<template>
    <div class="row m-0 p-0 h-100">
        <div class="fenetre-winner-overflow d-flex row m-0 p-0" v-if="this.$store.state.winner !== null">
            <div class="fenetre-winner m-auto border row m-0 p-5">
                <div class="col-12">
                    <div class=""><img class="couronne-victoire" src="img/couronne.png" alt=""><h2 class="text-white">{{ $store.state.winner.name }}</h2></div>
                </div>
                <div class="col-12 row m-0 p-0">
                    <div class="col-3 mx-auto embed-responsive embed-responsive-1by1"><button type="button" class="btn bo rder bg-w hite rounded-circle embed-responsive-item" @click="recommancer()"><img class="w-75" src="img/refresh.png" alt="image de refresh"></button></div>
                </div>
            </div>
        </div>
        <div v-if="!hasLoaded" class="loading"></div>
        <Menu :players="players"></Menu>
        <Board :fabriques="fabriques" :pionPremier="pionPremier"></Board>
    </div>
</template>
<script>
    import Menu from '@/components/Menu.vue';
    import Board from '@/components/Board.vue';
    import axios from "axios";

    export default {
        name: "Game",
        components:{
            Menu,
            Board
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
            recommancer() {
                let json = {
                    nPlayers: this.$store.state.board.nPlayers,
                    AI: this.$store.state.board.PB.map(a => a.name.includes("IA")),
                    names: this.$store.state.board.PB.map(a => a.name),
                };
                axios.post('http://localhost:8000/startGame', json)
                    .then(() => {
                        this.$emit("gameStarted");
                        this.$store.state.winner = null
                    })
                    .catch(() => {
                    });
            }
        }
    }
</script>
       
