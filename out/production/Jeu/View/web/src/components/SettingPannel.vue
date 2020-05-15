<template>
    <div class="">
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
            <h2 class="text-center py-4 border-bottom border-primary text-primary">Menu</h2>
            <div class="m-5">
                <div class="d-flex flex-column menu-liste align-items-st art">
                    <button class="btn border-secondary" @click="retourMenu">Menu</button>
                    <button class="btn border-secondary" @click="recommancer()">Recommancer</button>
                    <button class="btn border-secondary">Abandonner</button>
                    <button class="btn border-secondary">Annuller mouvement</button>
                    <button class="btn border-secondary">Sauvegarder</button>
                </div>
            </div>
        </div>
        <div class="setting-btn-aria" @click="openSettingPanel()" :style="{ left : getLeftButton + 'px' }">
            <div id="setting-btn" class="p-3 m-2 rounded-circle float-left">
                <img id="setting-img" class="rounded-circle" :src="'img/' + getImage" alt="">
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
                el: null
            }
        },
        methods: {
            openSettingPanel() {
                this.scale === "0" ? this.scale = "1" : this.scale = "0"
            },
            recommancer() {
                this.waitingReponse = true
                let json = {
                    nPlayers: this.$store.state.board.nPlayers,
                    AI: this.$store.state.board.PB.map(a => a.name.includes("IA")),
                    names: this.$store.state.board.PB.map(a => a.name),
                };
                axios.post('http://localhost:8000/startGame', json)
                    .then(() => {
                        this.$emit("gameStarted");
                        this.$store.state.winner = null
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
            }
        },computed: {
            getLeftButton() {
                if(this.el !== null && this.scale !== "0") {
                    return this.el.getElementsByClassName("setting-aria")[0].clientWidth
                } else {
                    return 0
                }
            },
            getImage() {
                if(this.scale !== "0") {
                    return "retour.png"
                } else {
                    return "setting.png"
                }
            },
            boxShadow() {
                return this.scale === "1" ? '0px 0 5px #5ac5d4' : 'none'
            }
        },
        mounted() {
            this.el = this.$el
        }
    }
</script>

<style scoped>

</style>