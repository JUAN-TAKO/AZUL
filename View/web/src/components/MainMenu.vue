<template>
    <div class="row h-100 m-0">
        <div v-if="regles" @click="setRegles(false)" class="modal-regles-overlay d-flex row m-0 p-0">
            <div id="modalRegles" class="col-10 m-auto">
                <iframe src="NO-AZUL.pdf" class="w-100 h-100">

                </iframe>
            </div>
        </div>
        <div class="col-4 p-sm-3 p-1 p-xl-5 liste-btn-mainMenu">
            <button v-if="this.$store.state.jeuxEnCours" class="btn btn-primary w-100" @click="revenirJeu">Reprendre la partie</button>
            <button class="btn btn-primary w-100" @click="startGame">Commencer la partie</button>
            <button class="btn btn-primary w-100" @click="setRegles(true)" type="button" data-toggle="modal" data-target="#modalRegles">Règles</button>
            <button class="btn btn-primary w-100" @click="startTuto" type="button" data-toggle="modal" data-target="#modalRegles">Tutoriel</button>
        </div>
        <div class="col azul-bg pt-5">
            <div class="player-slots row m-0">
                <div v-for="(player,index) in availablePlayers" v-bind:key="index" class="player-slot col-sm-6 col-xl-3 mb-3">
                    <div class="input-main-menu" :class="{ 'not-selected-input' : !player.selected }">
                        <div class="input-group">
                            <input type="text" class="form-control" v-model="player.name" :disabled="!player.selected || player.AI!=0" style="border-radius:0.25rem 0.25rem 0 0">
                        </div>
                        <div class="btn-group w-100 group-btn-main-menu" role="group" aria-label="Basic example">
                            <button @click="setAI(player,0)" class="btn btn-sm" :class="player.AI === 0 ? 'btn-primary' : 'btn-secondary'" :disabled="!player.selected" style="border-radius:0 0 0 0">Humain</button>
                            <button @click="setAI(player,1)" class="btn btn-sm" :class="player.AI === 1 ? 'btn-primary' : 'btn-secondary'" :disabled="!player.selected" style="border-radius:0 0 0 0">AI Aléatoire</button>
                            <button @click="setAI(player,2)" class="btn btn-sm" :class="player.AI === 2 ? 'btn-primary' : 'btn-secondary'" :disabled="!player.selected" style="border-radius:0 0 0 0">AI Facile</button>
                            <button @click="setAI(player,2)" class="btn btn-sm" :class="player.AI === 2 ? 'btn-primary' : 'btn-secondary'" :disabled="!player.selected" style="border-radius:0 0 0 0">AI Moyen</button>
                        </div>
                    </div>
                    <div class="player-card" @click="selectPlayer(player)">
                        <img src="/img/plateau-joueur.png" class="w-100" :class="!player.selected ? 'not-selected': ''"/>
                        <p class="tooltip" v-html="player.selected ? 'Supprimer le joueur' : 'Ajouter le joueur'"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import axios from "axios";
    export default {
        name: "MainMenu",
        data(){
            return {
                availablePlayers: [
                    {
                        id:1,
                        selected: true,
                        name: "Joueur 1",
                        AI: 0,
                    },
                    {
                        id:2,
                        selected: true,
                        name: "Joueur 2",
                        AI: 0,
                    },
                    {
                        id:3,
                        selected: false,
                        name: "Joueur 3",
                        AI: 0,
                    },
                    {
                        id:4,
                        selected: false,
                        name: "Joueur 4",
                        AI: 0,
                    },
                ],
                regles : false
            }
        },
        computed:{
            numberOfPlayers(){
                let count = 0;
                for(let i = 0; i < this.availablePlayers.length; i++){
                    if(this.availablePlayers[i].selected)
                        count++;
                }      
                return count;
            }
        },
        methods:{
            selectPlayer(player){
                if(!player.selected){
                    player.selected = true;
                } else {
                    if(this.numberOfPlayers >= 3){
                        player.selected = false;
                    }
                }
            },
            toggleAI(player){
                player.AI = !player.AI;
                if(player.AI)
                    player.name = "AI " + player.id;
                else
                    player.name = "Joueur " + player.id;
            },
            setAI(player,valeur) {
                player.AI = valeur;
                switch (valeur) {
                    case 0:
                        player.name = "Joueur "
                        break;
                    case 1:
                        player.name = "AI Aléatoire "
                        break;
                    case 2:
                        player.name = "AI Facile "
                        break;
                    case 3:
                        player.name = "AI Moyen "
                        break;
                }
                player.name = player.name + player.id
            },
            startGame () {
                this.$store.state.tutoEnCours = false;
                let json = {
                    nPlayers: this.numberOfPlayers,
                    AI: this.availablePlayers.filter(a => a.selected).map(a => a.AI),
                    names: this.availablePlayers.filter(a => a.selected).map(a => a.name),
                };
                axios.post('http://localhost:8000/startGame', json)
                .then(() => {
                    // this.$emit("gameStarted");
                    this.$store.dispatch("reset")
                    this.$store.state.jeuxEnCours = true;
                })
                .catch(() => {
                });
            },
            revenirJeu() {
                this.$store.state.retourMenu = false
            },
            startTuto() {
                this.startGame()
                this.$store.state.tutoEnCours = true
            },
            setRegles(valeur) {
                this.regles = valeur;
            }
        },
        mounted() {
            this.$el.getElementsByClassName("input-main-menu")[0].style.maxHeight = this.$el.getElementsByClassName("input-main-menu")[0].clientHeight
        }
    }
</script>
       
