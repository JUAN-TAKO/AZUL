<template>
    <div id="menu" class="col-7 m-0 p-0 d-flex flex-column justify-content-end">
        <div v-if="this.$store.state.retourCoup" class="alert alert-danger" role="alert">
            {{this.$store.state.retourCoup}}
            <button type="button" class="close" aria-label="Close" @click="fermerRetourCoup()">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <div class="row m-auto p-0 col-10">
            <PlateauJoueur v-for="(player, index) in players" :key="index" class="col-6 p-1" :plateauJoueur="player" :id="index"></PlateauJoueur>
        </div>
    </div>
</template>

<script>
    import PlateauJoueur from '@/components/PlateauJoueur';
    export default {
        name: "Menu",
        data() {
            return {

            }
        },
        components : {
            PlateauJoueur
        },
        methods:{
            fermerRetourCoup() {
                this.$store.state.retourCoup = null
            }
        },
        props: {
            players: {}
        },
        computed: {
        }
    }
</script>

<style scoped>
    #menu {
    }
</style>