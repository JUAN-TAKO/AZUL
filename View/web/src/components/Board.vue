<template>
    <div v-if="this.$store.state.board" id="board" class="col-5 m-0 p-0 row d-flex">
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
        <Center :mozaiques="Array.from(this.$store.state.board.center).filter(element => element != 0)"></Center>
    </div>
</template>

<script>
    import Fabrique from "./Fabrique";
    import Mozaique from "./Mozaique"
    import Center from "./Center"
    export default {
        name: "Board",
        components: {
            Fabrique,
            Mozaique,
            Center
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
    }
</style>