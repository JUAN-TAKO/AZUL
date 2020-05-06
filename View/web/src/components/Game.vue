<template>
    <div class="row m-0 p-0 h-100">
        <div v-if="!hasLoaded" class="loading"></div>
        <Menu :players="players"></Menu>
        <Board :fabriques="fabriques"></Board>
    </div>
</template>
<script>
    import Menu from '@/components/Menu.vue';
    import Board from '@/components/Board.vue';

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
            }
        },

        mounted(){
            setInterval(() => {
                this.$store.dispatch('getBoard')
                },1000)
            }
    }
</script>
       
