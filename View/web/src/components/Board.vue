<template>
    <div v-if="this.$store.state.board" id="board" class="col-5 m-0 p-0 row d-flex">
        <div class="waiting-reponse" :class="{ 'd-flex' : this.waitingReponse, 'd-none' : !this.waitingReponse, }">
            <div class="spinner-border text-primary m-auto spinner-border-lg" role="status">
                <span class="sr-only">Loading...</span>
            </div>
        </div>
        <div class="m-2 btn-annuler-action rounded-pill">
            <div class="btn btn-secondary" @click="goPrevious()"> Annuler la dernière action</div>
        </div>
        <div v-if="this.$store.state.selection.selectionner" class="overlay d-flex flex-column justify-content-center">
            <div class="w-50 mx-auto pb-5 px-1">
                <button type="button" class="close text-white" aria-label="Close" @click="annulerSelection()" v-if="!isCurrentAI">
                    <span class="close-selection" aria-hidden="true">&times;</span>
                </button>
                <p class="text-tuto text-white" v-if="tuto">Annuler la séléction</p>
<!--                <div>-->
<!--                    <h2 class="text-white text-center">Votre séléction</h2>-->
<!--                </div>-->
                <div class="container d-flex mozaique-selected row px-4 m-0">
                    <Mozaique v-for="i in nbMozaique" :key="i" :couleur="couleurMozaique" class="col-3"></Mozaique>
                </div>
            </div>
        </div>
        <Fabrique v-for="(fabrique,index) in fabriques" :mozaiques="fabrique" :key="index" :id="index" class="mx-auto"></Fabrique>
        <Center :mozaiques="this.$store.state.board.center" :id="-1" :pionPremier="pionPremier" class="flex-fill"></Center>
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
                waitingReponse: false,
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
            },
            tuto() {
                return this.$store.state.tutoEnCours && this.$store.state.selection.selectionner
            },
            isCurrentAI(){
                return this.$store.state.board.PB[this.$store.state.board.currentPlayer].name.includes("AI");
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
                this.waitingReponse = true
                axios.post('http://localhost:8000/goPrevious', {})
                    .then((response) => {
                        setTimeout(() => {
                            this.waitingReponse = false
                        },500)
                        console.log(response.data.GlobalBoard);
                        this.$store.state.animationIAEnCours = false;
                        this.$store.state.hasAIPlayed = false;
                        this.$store.state.selection = {
                            selectionner:false,
                            donnees: {

                            }
                        };
                        // this.$store.commit("setBoard",response.data.GlobalBoard)
                        let interval = setInterval(() => {
                            if(JSON.stringify(response.data.GlobalBoard) !== JSON.stringify(this.$store.state.board)){
                                this.$store.state.animationIAEnCours = false;
                                this.$store.state.hasAIPlayed = false;
                                this.$store.dispatch('getBoard');
                            } else {
                                clearInterval(interval)
                            }
                        },1000);
                    })
                    .catch(() => {
                        setTimeout(() => {
                            this.waitingReponse = false
                        },500)
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