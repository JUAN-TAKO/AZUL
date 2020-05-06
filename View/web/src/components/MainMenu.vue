<template>
    <div class="row h-100">
        <div class="col-4 p-5">
            <button class="btn btn-primary w-100" @click="startGame">Commencer la partie</button>
        </div>
        <div class="col azul-bg p-5">
            <div class="player-slots">
                <div v-for="player in availablePlayers" v-bind:key="player" class="player-slot" @click="selectPlayer(player)">
                    <div><input type="text" class="w-100 mb-2" v-model="player.name" :disabled="!player.selected"></div>
                    <img src="/img/plateau-joueur.jpeg" class="player-card" :class="!player.selected ? 'not-selected': ''"/>
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
                        selected: true,
                        name: "Joueur 1",
                        AI: false,
                    },
                    {
                        selected: true,
                        name: "Joueur 2",
                        AI: false,
                    },
                    {
                        selected: false,
                        name: "Joueur 3",
                        AI: false,
                    },
                    {
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
       
