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
        board: null,
        charge: false,
    },
    mutations: {
        setBoard(state, data) {
            this.state.board = data.GlobalBoard;
        }
    },
    actions: {
        getBoard(context) {
            Axios.get('http://localhost:8000/getBoard')
            .then(response=> response.data )
            .then( q => {
                context.commit("setBoard", q)
            })
        }
    }
})