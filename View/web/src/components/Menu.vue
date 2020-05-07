<template>
    <div id="menu" class="col-7 m-0 p-0 d-flex flex-column">
        <div v-if="this.$store.state.retourCoup" class="alert alert-danger" role="alert">
            {{this.$store.state.retourCoup}}
        </div>
        <div class="flex-grow-1">

        </div>
        <div class="row mx-auto p-0 col-10">
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