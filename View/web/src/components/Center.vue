<template>
    <div class="col-12 m-0 p-0 align-self-center justify-content-center row">
        <div :class="{ 'col-1 m-0 m-2 p-0 p-md-1' : mozaique != 0 }" class="" v-for="(mozaique, index) in mozaiques" :key="index" @mouseover="mouseOver(mozaique)" @mouseout="mouseOut()" @click="clickMozaique()">
            <transition name="bounce">
                <Mozaique v-if="mozaique != 0" :scale="getScale(index)" :couleur="mozaique"></Mozaique>
            </transition>
        </div>
    </div>
</template>
<script>
    import Mozaique from '@/components/Mozaique'
    export default {
        name: "Center",
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
                let selection = {
                    donnees:{
                        factory : this.id,
                        color : this.over,
                        nSelected :  Array.from(this.mozaiques).filter(el => el === this.over).length
                    },
                    selectionner: true
                }
                this.$store.state.selection = selection;
            }
        }
    }
</script>

<style scoped>

    .container-fabrique-mozaiques {
        /*border: 3px solid green !important;*/
    }
</style>