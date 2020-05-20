<template>
    <div id="menu" class="col-7 m-0 p-0 d-flex flex-column justify-content-end">
        <div v-if="tuto" class="overlay row m-0 p-0 d-flex">
            <div class="col-10 m-auto row p-0">
                <p class="text-white text-tuto">
                    <!--Séléctionnez les mozaiques d'une couleur sur une des fabriques en cliquant sur l'unes d'elles-->
                    {{ $store.state.board.PB[$store.state.board.currentPlayer].name }}
                    <br>
                    Clique sur les tuiles désirées
                </p>
                <div class="d-flex col-4 p-0 div-fleche-tuto ml-auto">
                    <img class="mr-0 fleche-tuto" src="img/fleche.png" alt="">
                </div>
            </div>
        </div>
        <div v-if="this.$store.state.retourCoup" class="alert alert-danger" role="alert">
            {{ this.$store.state.retourCoup }}
            <button type="button" class="close" aria-label="Close" @click="fermerRetourCoup()">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>
        <SettingPannel></SettingPannel>
        <div class="row m-auto p-0 col-11 align-items-center">
            <PlateauJoueur v-for="(player, index) in players" :key="index" class="p-1 col-6" :plateauJoueur="player" :id="index"></PlateauJoueur>
        </div>
    </div>
</template>
<script>
    import PlateauJoueur from '@/components/PlateauJoueur';
    import axios from "axios";
    import SettingPannel from '@/components/SettingPannel';
    export default {
        name: "Menu",
        data() {
            return {

            }
        },
        components : {
            PlateauJoueur,
            SettingPannel
        },
        methods:{
            fermerRetourCoup() {
                this.$store.state.retourCoup = null
            },
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
        },
        props: {
            players: {}
        },
        computed: {
            tuto() {
                return this.$store.state.tutoEnCours && !this.$store.state.selection.selectionner
            }
        },
        updated() {
        },
        created() {
        }
    }
</script>

<style scoped>
    #menu {
        /*background-color: #131417;*/
        border-right:2px solid white;
    }
</style>