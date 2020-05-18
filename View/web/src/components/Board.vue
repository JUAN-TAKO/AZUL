<template>
    <div v-if="this.$store.state.board" id="board" class="col-5 m-0 p-0 row d-flex">
        <div class="m-2 btn-annuler-action rounded-pill">
            <button class="btn btn-secondary" @click="goPrevious()"> Annuler la dernière action</button>
        </div>
        <div v-if="this.$store.state.selection.selectionner" class="overlay d-flex flex-column justify-content-center">
            <div class="w-50 mx-auto pb-5 px-1">
                <button type="button" class="close text-white" aria-label="Close" @click="annulerSelection()">
                    <span aria-hidden="true">&times;</span>
                </button>
                <div class="container d-flex mozaique-selected row px-4 m-0">
                    <Mozaique v-for="i in nbMozaique" :key="i" :couleur="couleurMozaique" class="col-3"></Mozaique>
                </div>
            </div>
        </div>
        <Fabrique v-for="(fabrique,index) in fabriques" :mozaiques="fabrique" :key="index" :id="index"></Fabrique>
<!--        <div class="col-12 row m-0 p-0">-->
            <Center :mozaiques="this.$store.state.board.center" :id="-1" :pionPremier="pionPremier"></Center>
<!--        </div>-->
    </div>
</template>

<script>
    import Fabrique from "./Fabrique";
    import Mozaique from "./Mozaique"
    import Center from "./Center"
    import axios from "axios";
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
            fabriques:{},
            pionPremier: {
                type: Boolean,
            }
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
            },
            goPrevious(){
                axios.post('http://localhost:8000/goPrevious', {})
                    .then((response) => {
                        console.log(response);
                        if(response.data.GlobalBoard != null)
                            this.$store.commit("setBoard", response.data.GlobalBoard);
                    })
                    .catch(() => {
                    });
            }
        }
    }
</script>

<style scoped>
    #board {
        background: center no-repeat url("/img/fond.png"), radial-gradient(white,#5ac5d4);
    }
</style>