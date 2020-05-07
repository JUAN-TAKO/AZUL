<template>
    <div id="board" class="col-5 m-0 p-0 row d-flex flex-wrap">
        <div v-if="this.$store.state.selection.selectionner" class="overlay d-flex flex-column justify-content-center">
            <div class="container">
                <button type="button" class="close" aria-label="Close" @click="annullerSelection()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="container d-flex">
                <p class="col-6 text-center">{{nbMozaique}} X </p>
                <Mozaique :couleur="couleurMozaique"></Mozaique>
            </div>
        </div>
        <Fabrique v-for="(fabrique,index) in fabriques" :mozaiques="fabrique" :key="index" :id="index"></Fabrique>
    </div>
</template>

<script>
    import Fabrique from "./Fabrique";
    import Mozaique from "./Mozaique"
    export default {
        name: "Board",
        components: {
            Fabrique,
            Mozaique
        },
        data() {
            return {
            }
        },
        props: {
            fabriques:{}
        },
        computed:{
            nbMozaique () {
                return this.$store.state.selection.donnees.nSelected;
            },
            couleurMozaique () {
                return this.$store.state.selection.donnees.color;
            }
        },
        methods: {
            annullerSelection() {
                let selection = {
                    selectionner : false,
                    donnees: {

                    }
                }
                this.$store.state.selection = selection
            }
        }
    }
</script>

<style scoped>
    #board {
        background: center no-repeat url("/img/fond.png"), radial-gradient(white,#5ac5d4);
        max-height: 100vh;
    }
</style>