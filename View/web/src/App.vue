<template>
  <div id="app" :class="loading ? 'loading' : ''">
    <template v-if="error">
    <div  class="alert alert-danger m-5">{{ error}}</div>
    </template>
    <template v-else>
      <template v-if="this.onGoing">
        <Game/>
      </template>
      <template v-else-if="!this.loading">
        <MainMenu
          v-on:gameStarted="onGoing = true"
        />
      </template>
    </template>
  </div>
</template>

<script>

import axios from "axios";
import Game from '@/components/Game.vue';
import MainMenu from '@/components/MainMenu.vue';

export default {
  name: 'App',
  components: {
    MainMenu,
    Game
  },
  data() {
    return {
      loading: false,
      onGoing: false,
      error: "",
    }
  },
  created() {
    this.getGameStatus();
  },
  methods:{
    getGameStatus(){
      this.loading = true;
      axios.get("http://localhost:8000/getGameStatus")
        .then(response => {
          this.onGoing = response.data.onGoing;
          this.loading = false;
        })
        .catch(() => {
          this.error = "Impossible de communiquer avec le serveur.";
          this.loading = false;
        })
    }
  }
}
</script>

<style>
#app {
  width: 100%;
  height: 100%;
}
</style>
