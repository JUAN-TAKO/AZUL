<template>
    <div class="fenetre-winner-overflow d-flex row m-0 p-0">
        <div class="waiting-reponse" :class="{ 'd-flex' : this.waitingReponse, 'd-none' : !this.waitingReponse, }">
            <div class="spinner-border text-primary m-auto spinner-border-lg" role="status">
                <span class="sr-only">Loading...</span>
            </div>
        </div>
        <div class="fenetre-winner m-auto row m-0 p-5">
            <div class="col-12">
                <div class=""><h2 class="text-white text-center victoire-titre text-warning">Victoire {{ $store.state.winner.name }} !</h2></div>
                <div class=""><h2 class="text-white text-center victoire-score">Score : <span class="text-success">{{ getScore }}</span>
                    {{ getSmiley }}</h2></div>
            </div>
            <div class="col-4 mx-auto d-flex flex-row px-5 m-0 my-2">
                <div class="embed-responsive embed-responsive-1by1" :class="{ 'bounce-enter-active' : score > 25 }">
                    <img :src="'img/' + getStar1" alt="" class="embed-responsive-item">
                </div>
                <div class="embed-responsive embed-responsive-1by1" :class="{ 'bounce-enter-active' : score > 50 }">
                    <img :src="'img/' + getStar2" alt="" class="embed-responsive-item">
                </div>
                <div class="embed-responsive embed-responsive-1by1" :class="{ 'bounce-enter-active' : score > 75 }">
                    <img :src="'img/' + getStar3" alt="" class="embed-responsive-item">
                </div>
            </div>
            <div class="col-12 row m-0 p-0">
                <div class="col-12 col-md-10 mx-auto row py-2">
                    <div class="col-2 mx-auto embed-responsive embed-responsive-1by1 btn-victoire"><div class="embed-responsive-item d-flex flex-column" @click="retourMenu()"><p class="text-center text-btn-victoire mb-0 text-break">Menu</p><img class="w-auto h-auto mx-auto my-1 my-md-2" src="img/home.png" alt="image de refresh"></div></div>
                    <div class="col-2 mx-auto embed-responsive embed-responsive-1by1 btn-victoire"><div class="embed-responsive-item d-flex flex-column" @click="recommancer()"><p class="text-center text-btn-victoire mb-0 text-break">Recommencer</p><img class="w-auto h-auto mx-auto my-1 my-md-2" src="img/refresh.png" alt="image de refresh"></div></div>
                    <div class="col-2 mx-auto embed-responsive embed-responsive-1by1 btn-victoire"><div class="embed-responsive-item d-flex flex-column" @click="alert('non implémenté')"><p class="text-center text-btn-victoire mb-0 text-break">Sauvegarder</p><img class="w-auto h-auto mx-auto my-1 my-md-2" src="img/sauvegarde.png" alt="image de refresh"></div></div>
                </div>
            </div>
            <div class="col-12 row m-0 p-0 mt-4">
                <a href="https://docs.google.com/forms/d/e/1FAIpQLSfV7CJbnOHvr_1UqWwEuYN-GYbHxUKnvF2u8gcrWiDH0wU8sw/viewform?usp=sf_link" target="_blank" class="ml-auto btn btn-avis text-white">Donner votre avis</a>
            </div>
        </div>
    </div>
</template>

<script>
    import axios from "axios";

    export default {
        name: "VictoirePannel",
        data() {
          return {
              waitingReponse: false,
              score : 0
          }
        },
        methods: {
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
                        }
                    }),
                    names: this.$store.state.board.PB.map(a => a.name),
                };
                // console.log(json);
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
            augmenterScore() {
                let scoreWinner = this.$store.state.board.PB[this.$store.state.winner.i].score;
                let time = 3000/scoreWinner;
                if(scoreWinner > 0) {
                    if(this.score < scoreWinner) {
                        setTimeout(() => {
                            this.score++;
                            this.augmenterScore();
                        },time)
                    }
                } else {
                    if(this.score > scoreWinner) {
                        setTimeout(() => {
                            this.score--;
                            this.augmenterScore();
                        },time)
                    }
                }
            }
        },
        computed: {
            getScore() {
                return this.score
            },
            getStar1() {
                return this.getScore > 25 ? "star-winned.png" :  "star-unwinned.png"
            },
            getStar2() {
                return this.getScore > 50 ? "star-winned.png" :  "star-unwinned.png"
            },
            getStar3() {
                return this.getScore > 75 ? "star-winned.png" :  "star-unwinned.png"
            },
            getSmiley() {
                if(this.getScore < 25) {
                    return ":("
                } else if(this.getScore >= 25 && this.getScore < 50 ) {
                    return ":|"
                } else if(this.getScore >= 50 && this.getScore < 75) {
                    return ":)"
                } else {
                    return ":D"
                }
            }
        },
        watch: {
        },
        mounted() {
            setTimeout(() => {
                this.augmenterScore()
            },250);
        }
    }
</script>

<style scoped>

</style>