<template>
    <div class="">
        <div v-if="regles" @click="regles = false" class="modal-regles-overlay d-flex row m-0 p-0" style="z-index: 9999">
            <div id="modalRegles" class="col-10 m-auto">
                <iframe src="NO-AZUL.pdf" class="w-100 h-100">
                </iframe>
            </div>
        </div>
        <div class="waiting-reponse" :class="{ 'd-flex' : this.waitingReponse, 'd-none' : !this.waitingReponse, }">
            <div class="spinner-border text-primary m-auto spinner-border-lg" role="status">
                <span class="sr-only">Loading...</span>
            </div>
        </div>
        <div id="setting-aria" class="setting-aria border bg-white d-flex flex-column"
             :style="{
                transform: 'scaleX('+this.scale+')',
                boxShadow: this.boxShadow
        }">
            <h2 class="text-center py-4 text-primary setting-titre">Menu</h2>
            <div class="p-2">
                <div class="alert" :class="this.message.error ? 'alert-danger' : 'alert-success'" v-if="this.message.text != ''">
                    {{this.message.text}}
                    <h2 class="float-right " @click="message.text = ''" style="line-height:0.5em; cursor:pointer">&times;</h2>
                </div>
                <div class="d-flex flex-column menu-liste align-items-st art">
                    <button class="btn btn-outline-primary" @click="retourMenu">Menu</button>
                    <button class="btn btn-outline-primary" @click="recommancer()">Recommencer</button>
                    <button class="btn btn-outline-primary" @click="saveGame()">Sauvegarder</button>
                    <button class="btn btn-outline-primary" @click="changeTuto"> {{ textTuto }} </button>
                    <button class="btn btn-outline-primary" @click="regles = true">Règles</button>
                </div>
            </div>
        </div>
        <div class="setting-btn-aria" @click="openSettingPanel()" :style="{ left : getLeftButton + 'px' }">
            <div id="setting-btn" class="p-3 float-left">
                <img id="setting-img" class="rounded-circle" src="img/menu-icon.svg" alt="">
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "SettingPannel",
        data() {
            return {
                scale: "0",
                waitingReponse: false,
                el: null,
                regles: false,
                message : {
                    text: "",
                    error: false,
                },
            }
        },
        methods: {
            saveGame(){
                axios.post('http://localhost:8000/saveGame', {})
                    .then((r) => {
                        if(!r.data.success){                            
                            this.message.text = "Aucune partie en cours."; this.message.error = true;
                        }
                        else {
                            this.message.text = "Partie sauvegardée !"; this.message.error = false;
                        }
                    });
            },
            openSettingPanel() {
                this.scale === "0" ? this.scale = "1" : this.scale = "0"
            },
            recommancer() {
                this.waitingReponse = true
                let json = {
                    nPlayers: this.$store.state.board.nPlayers,
                    AI: this.$store.state.board.PB.map(function(el) {
                        if(el.name.includes("AI Aléatoire")) {
                            return 1;
                        } else if(el.name.includes("AI Facile")) {
                            return 2;
                        } else if(!el.name.includes("AI")){
                            return 0;
                        } else if(el.name.includes("AI Moyen")) {
                            return 3;
                        }
                    }),
                    names: this.$store.state.board.PB.map(a => a.name),
                };
                axios.post('http://localhost:8000/startGame', json)
                    .then(() => {
                        this.$emit("gameStarted");
                        // this.$store.state.winner = null
                        // this.$store.state.animationIAEnCours = false
                        this.$store.dispatch("reset")
                        this.$store.dispatch('getBoard');
                        setTimeout(() => {
                            this.waitingReponse = false
                        },1000)
                    })
                    .catch(() => {
                        setTimeout(() => {
                            this.waitingReponse = false
                        },1000)
                    });
            },
            retourMenu() {
                this.$store.state.retourMenu = true
            },
            changeTuto() {
                this.$store.state.tutoEnCours = !this.$store.state.tutoEnCours
            }
        },computed: {
            getLeftButton() {
                if(this.el !== null && this.scale !== "0") {
                    return this.el.getElementsByClassName("setting-aria")[0].clientWidth
                } else {
                    return 0
                }
            },
            boxShadow() {
                return this.scale === "1" ? '0px 0 5px #5ac5d4' : 'none'
            },
            textTuto() {
                if(this.$store.state.tutoEnCours)
                    return "Arrêter le tutoriel"
                else
                    return "Tutoriel"
            }
        },
        mounted() {
            this.el = this.$el
        }
    }
</script>

<style scoped>

</style>