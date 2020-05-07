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
                    console.log("Response",response);
                    context.state.coupJouer = true;
                })
                .catch(function(error) {
                    console.log("Error",error);
                })
        }
    }
})