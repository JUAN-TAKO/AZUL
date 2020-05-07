<template>
    <div v-if="this.$store.state.board" id="board" class="col-5 m-0 p-0 row d-flex">
        <div v-if="this.$store.state.selection.selectionner" class="overlay d-flex flex-column justify-content-center">
            <div class="container d-flex mozaique-selected">
                <Mozaique v-for="i in nbMozaique" :key="i" :couleur="couleurMozaique"></Mozaique>
                <button type="button" class="close" aria-label="Close" @click="annulerSelection()">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
        <Fabrique v-for="(fabrique,index) in fabriques" :mozaiques="fabrique" :key="index" :id="index"></Fabrique>
        <Center :mozaiques="Array.from(this.$store.state.board.center).filter(element => element != 0)" :id="-1"></Center>
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
            annulerSelection() {
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