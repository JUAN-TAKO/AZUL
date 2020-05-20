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
        lastMove:null,
        coupJouer : false,
        board: null,
        charge: false,
        hasAIPlayed : false,
        winner: null,
        // animationDone:true,
        jeuxEnCours:false,
        retourMenu:false,
        // animationOnGoing: false,
        // playersAIStatus:null,
        animationIAEnCours:false,
        tutoEnCours: false,
        jaiCompris: false
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
            }
        },
        setAIPlayed(state, data) {
            this.state.hasAIPlayed = data;
            // this.state.animationDone = false;
        }
    },
    actions: {
        getBoard(context) {
            if(!this.state.animationIAEnCours) {
                Axios.get('http://localhost:8000/getBoard')
                    .then(response => response.data)
                    .then( q => {
                        if(context.state.board != null && q.hasAIPlayed === true && JSON.stringify(q.GlobalBoard) != JSON.stringify(context.state.board) ) {
                            context.commit("setAIPlayed", q.hasAIPlayed);
                            context.state.animationIAEnCours = true;

                            setTimeout(() => {
                                let color = parseInt(q.GlobalBoard.lastMove.color,10)
                                let factory = parseInt(q.GlobalBoard.lastMove.factory,10)
                                let line = parseInt(q.GlobalBoard.lastMove.line,10)
                                let nSelected
                                if(factory === -1)
                                    nSelected = Array.from(context.state.board.center).filter(el => el === color).length
                                else
                                    nSelected = Array.from(context.state.board.factories[factory]).filter(el => el === color).length

                                let selection = {
                                    donnees : {
                                        color : color,
                                        factory : factory,
                                        line : line,
                                        nSelected : nSelected,
                                        player : this.state.board.currentPlayer
                                    },
                                    selectionner : true
                                }
                                context.state.selection = selection

                                context.state.lastMove = selection.donnees;

                                setTimeout(()=>{
                                    context.commit("setBoard", q)
                                },2000)
                            },50)
                        } else if( context.state.board != null ){
                            if(JSON.stringify(q.GlobalBoard) !== JSON.stringify(context.state.board) ) {
                                context.commit("setBoard", q)
                            }
                        } else if( context.state.board == null ){
                            if(q.hasAIPlayed === true)
                                context.state.hasAIPlayed = true
                            context.commit("setBoard", q)
                        }
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
                            context.commit("setBoard", response.data);
                            context.state.selection = {
                                selectionner: false,
                                donnees:{}
                            };
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
        },
        reset() {
            this.state.animationIAEnCours = false;
            this.state.hasAIPlayed = false;
            this.state.selection = {
                selectionner:false,
                    donnees: {

                }
            };
            this.state.board =  null;
            this.state.lastMove = null;
            this.state.coupJouer = false;
            this.state.retourMenu = false;
            this.state.winner = null;
            this.state.jaiCompris = false
        }
    }
})