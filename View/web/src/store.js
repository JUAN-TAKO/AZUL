import Vue from 'vue'
import Axios from 'axios'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        selection:{
            selectionner:false,
            donnees: {

            }
        },
        coupJouer : false,
        board: null,
        charge: false,
        hasAIPlayed : false,
        winner: null,
        jeuxEnCours:false,
        retourMenu:false,
        playersAIStatus:null,
        animationIAEnCours: false
    },
    mutations: {
        setBoard(state, data) {
            if(this.state.hasAIPlayed) {
                this.state.selection.selectionner = false;
                this.state.selection.donnees = {};
            }
            this.state.board = data.GlobalBoard;
            if(this.state.hasAIPlayed) {
                this.state.hasAIPlayed = false
                this.state.animationIAEnCours = false
                this.dispatch("setFrontUpdated");
            }
            this.state.playersAIStatus = data.playersAIStatus;
            if(this.state.coupJouer) {
                this.state.coupJouer = false
                this.state.selection.donnees = {}
                this.state.selection.selectionner = false
            }
            if(!this.state.board.isOnGoing && this.state.winner == null){
                // Find winner
                let winnerI = 0;
                let score = this.state.board.PB[0].score;
                for(let i = 0; i < this.state.board.nPlayers; i++){
                    if(this.state.board.PB[i].score > score){
                        score = this.state.board.PB[i].score;
                        winnerI = i;
                    }
                }
                this.state.winner = {
                    i: winnerI,
                    name: this.state.board.PB[winnerI].name,
                }
                // alert(this.state.winner.name + " gagne la partie !");
            }
        },
        setAIPlayed(state, data) {
            this.state.hasAIPlayed = data;
        }
    },
    actions: {
        getBoard(context) {
            if(!this.state.animationIAEnCours) {
                Axios.get('http://localhost:8000/getBoard')
                    .then(response => response.data)
                    .then( q => {
                        if(q.hasAIPlayed == true && JSON.stringify(q.GlobalBoard) != JSON.stringify(context.state.board) ) {
                            context.commit("setAIPlayed", q.hasAIPlayed);
                            context.state.animationIAEnCours = true;
                            // context.state.selection.donnees = q.moveAI
                            setTimeout(() => {
                                context.state.selection.donnees = {
                                    // line : 3,
                                    color : 3,
                                    factory : 2,
                                    nSelected : 2
                                }
                                context.state.selection.selectionner = true;
                                setTimeout(()=>{
                                    context.commit("setBoard", q)
                                },2000)
                            },50)
                        } else {
                            if(q.GlobalBoard !== context.state.board) {
                                context.commit("setBoard", q)
                            }
                        }
                        // if(q != context.state.board){
                        //     context.commit("setBoard", q)
                        //     if(q.hasAIPlayed != context.state.hasAIPlayed)
                        //         context.commit("setAIPlayed", q.hasAIPlayed);
                        // }
                    })
            }
        },
        jouerCoup(context,ligne) {
            let selection = context.state.selection.donnees;
            selection.line  = ligne
            Axios.post("http://localhost:8000/playMove",selection)
                .then(function(response) {
                    context.state.retourCoup = "";
                    switch(response.data.value) {
                        case 0:
                            context.state.coupJouer = true;
                            break;
                        case -2:
                            context.state.retourCoup = "La couleur jouée n'est pas présente dans la fabrique selectionnée";
                            break;
                        case -3:
                            context.state.retourCoup = "La ligne selectionnée est déjà pleine";
                            break;
                        case -4:
                            context.state.retourCoup = "La couleur choisie ne peut pas aller sur la ligne selectionnée";
                            break;
                        default:
                            break;
                    }
                })
                .catch(function(error) {
                    console.log("Error",error);
                })
        },
        setFrontUpdated() {
            Axios.post("http://localhost:8000/setFrontUpdated", {})
                .then()
                .catch(function(error) {
                    console.log("Error",error);
                })
        }
    }
})