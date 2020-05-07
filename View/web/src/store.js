import Vue from 'vue'
import Axios from 'axios'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        selection:{
            selectionner:false,
            donnees: {

            },
            coupJouer : false
        },
        board: null,
        charge: false,
    },
    mutations: {
        setBoard(state, data) {
            this.state.board = data.GlobalBoard;
            if(this.state.coupJouer) {
                this.state.coupJouer = false
                this.state.selection.donnees = {}
                this.state.selection.selectionner = false
            }
        }
    },
    actions: {
        getBoard(context) {
            Axios.get('http://localhost:8000/getBoard')
            .then(response=> response.data )
            .then( q => {
                context.commit("setBoard", q)
            })
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
        }
    }
})