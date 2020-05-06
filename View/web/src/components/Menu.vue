<template>
    <div id="menu" class="col-7 m-0 p-0">
        <div class="row m-0 p-0">
            <PlateauJoueur v-for="(player, index) in players" :key="index" class="col-6" :plateauJoueur="player" :id="index"></PlateauJoueur>
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