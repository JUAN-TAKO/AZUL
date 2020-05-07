<template>
    <div class="row h-100 m-0">
        <div class="col-4 p-5">
            <button class="btn btn-primary w-100" @click="startGame">Commencer la partie</button>
        </div>
        <div class="col azul-bg pt-5">
            <div class="player-slots row m-0">
                <div v-for="player in availablePlayers" v-bind:key="player" class="player-slot col-3">
                    <div class="input-group">
                        <input type="text" class="form-control" v-model="player.name" :disabled="!player.selected || player.AI" style="border-radius:0.25rem 0 0 0">
                        <div class="input-group-append">
                            <button @click="toggleAI(player)" class="btn btn-sm btn-primary" :disabled="!player.selected" v-html="player.AI ? 'AI' : 'Humain'" style="border-radius:0 0.25rem 0 0"/>
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
                        AI: false,
                    },
                    {
                        id:2,
                        selected: true,
                        name: "Joueur 2",
                        AI: false,
                    },
                    {
                        id:3,
                        selected: false,
                        name: "Joueur 3",
                        AI: false,
                    },
                    {
                        id:4,
                        selected: false,
                        name: "Joueur 4",
                        AI: false,
                    },
                ]
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
            startGame () {
                let json = {
                    nPlayers: this.numberOfPlayers,
                    AI: this.availablePlayers.filter(a => a.selected).map(a => a.AI),
                }
                axios.post('http://localhost:8000/startGame', json)
                .then(() => {
                    this.$emit("gameStarted");
                })
                .catch(function (error) {
                    console.log(error)
                })
            }
        }
    }
</script>
       
