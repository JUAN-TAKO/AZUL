<template>
    <div class="col-3 m-0 p-0 align-self-center">
        <div class="embed-responsive embed-responsive-1by1">
            <div class="container-fabrique-mozaiques fabrique embed-responsive-item d-flex">
                <div class="row m-auto p-0 col-8">
                    <div class="col-6 m-0 p-0 p-md-1" v-for="(mozaique, index) in mozaiques" :key="index" @mouseover="mouseOver(mozaique)" @mouseout="mouseOut()" @click="clickMozaique()">
                        <transition name="bounce" v-on:after-leave="animationFinished()">
                            <Mozaique v-if="mozaique != 0 && !isSelected(mozaique)" :scale="getScale(index)" :couleur="mozaique"></Mozaique>
                        </transition>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    import Mozaique from '@/components/Mozaique'
    export default {
        name: "Fabrique",
        components: {
            Mozaique
        },
        data() {
            return {
                over: 0
            }
        },
        props:{
            mozaiques: {},
            id : Number
        },
        computed:{
            isAI() {
                return this.$store.state.board.PB[this.$store.state.board.currentPlayer].name.includes("AI");
            }
        },
        methods: {
            mouseOver (el) {
                this.over = el;
            },
            mouseOut() {
                this.over = 0;
            },
            getScale(i) {
                return this.mozaiques[i] === this.over || (this.mozaiques[i] === this.$store.state.selection.donnees.color && this.id === this.$store.state.selection.donnees.factory) ? 1.3 : 1;
            },
            clickMozaique() {
                if(this.isAI)
                    return;

                let selection = {
                    donnees:{
                        factory : this.id,
                        color : this.over,
                        nSelected :  Array.from(this.mozaiques).filter(el => el === this.over).length
                    },
                    selectionner: true
                }
                this.$store.state.selection = selection;
            },
            animationFinished() {
                if(this.$store.state.hasAIPlayed) {
                    this.$store.state.animationDone = true;
                }
            },
            isSelected(couleur) {
                return this.id == this.$store.state.selection.donnees.factory && couleur == this.$store.state.selection.donnees.color
            }
        }
    }
</script>

<style scoped>
    .fabrique {
        background: center / contain no-repeat url("/img/fabrique.png");
    }

    .container-fabrique-mozaiques {
        /*border: 3px solid green !important;*/
    }
</style>