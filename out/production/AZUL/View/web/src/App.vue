<template>
  <div id="app" :class="loading ? 'loading' : ''">
    <template v-if="error">
    <div  class="alert alert-danger m-5">{{ error}}
    <div class="spinner-border text-primary float-right" role="status">
      <span class="sr-only">Loading...</span>
    </div>
    </div>
    </template>
    <template v-else>
<!--      <template v-if="this.onGoing">-->
<!--        <Game/>-->
<!--      </template>-->
<!--      <template v-else-if="!this.loading">-->
<!--        <MainMenu-->
<!--          v-on:gameStarted="onGoing = true"-->
<!--        />-->
<!--      </template>-->
      <template v-if="this.$store.state.jeuxEnCours && !this.$store.state.retourMenu">
        <Game/>
      </template>
        <template v-else-if="!this.loading || this.$store.state.retourMenu">
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
    async wait(time){
        return new Promise(resolve => {
          setTimeout(resolve, time);
        });
    },
    async getGameStatus() {
      this.loading = true;
      do{
        try {
          let res = await axios.get("http://localhost:8000/getGameStatus");
          this.error = "";
          this.onGoing = res.data.onGoing;
          this.$store.state.jeuxEnCours = this.onGoing
        } catch (error) {
          this.error = "Impossible de communiquer avec le serveur."
        }
        this.loading = false;
        await this.wait(2000);
      } while (this.error != "");
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
