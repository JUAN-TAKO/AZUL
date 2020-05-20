<template>
    <div class="mozaiques-gauche" :class="{ 'light-around' : this.$store.state.selection.selectionner && isCurrent }">
        <div v-if="tuto" class="tuto-selection-done">
            <img class="fleche-tuto w-100" src="img/fleche-gauche.png" alt="">
        </div>
        <div class="ligne flex-row-reverse" @click="mouseClick(index,couleurs[index])" @mouseover="mouseOver(index)" @mouseleave="mouseOut()" v-for="(nbMozaiques, index) in this.lignes" :key="index">
            <Mozaique v-for="(mozaique,i) in getNbMozaiqueLigne(nbMozaiques,couleurs[index],index)"
                      :key="i" :couleur="getCouleurMozaiqueLigne(couleurs[index])"
                      :class="{ 'bounce-enter-active' : nouveaux(index) }"></Mozaique>
        </div>
    </div>
</template>

<script>
    import Mozaique from '@/components/Mozaique';
    // import Axios from 'axios'
    export default {
        name: "MozaiquesGauche",
        props: {
            couleurs : {},
            lignes : {},
            isCurrent: Boolean,
            id : Number
        },
        data() {
            return {
                ligneOver : 5
            }
        },
        components : {
            Mozaique
        },
        methods: {
            mouseOver (i) {
                if(this.isCurrent)
                    this.ligneOver = i
            },
            mouseOut () {
                this.ligneOver = 5
                this.$emit("ajoutplancher",0,0);
            },
            getNbMozaiqueLigne(nombre,couleur,ligne) {
                let retour;
                let selection = this.$store.state.selection
                if(selection.selectionner && (couleur === selection.donnees.color || couleur === 0) && this.ligneOver === ligne) {
                    retour = nombre + selection.donnees.nSelected;
                    if(retour > ligne + 1) {
                        let plancher = retour - ( ligne + 1 )
                        retour = ligne + 1;
                        this.$emit("ajoutplancher",plancher,selection.donnees.color)
                    }
                } else {
                    retour = nombre;
                }
                return retour;
            },
            getCouleurMozaiqueLigne(couleur) {
                if(couleur === 0) {
                    return this.$store.state.selection.donnees.color
                } else {
                    return couleur
                }
            },
            mouseClick(ligne, couleur) {
                if(this.isCurrent && !this.$store.state.coupJouer && this.$store.state.selection.selectionner && ( couleur === this.$store.state.selection.donnees.color || couleur == 0)) {
                    this.$store.dispatch("jouerCoup",ligne)
                    if(this.$store.state.retourCoup == "") // Valid play
                        this.$emit("ajoutplancher",0,0);
                }
            },
            nouveaux(ligne) {
                if(this.$store.state.lastMove !== null && this.$store.state.lastMove.line === ligne && this.id === this.$store.state.lastMove.player) {
                    return true
                } else {
                    return false
                }
            }
        },
        computed: {
            tuto() {
                return this.$store.state.selection.selectionner && this.$store.state.tutoEnCours && this.isCurrent
            }
        }
    }
</script>

<style scoped>
    .mozaiques-gauche {
        position: relative;
        /*border: 3px solid yellow;*/
        width: 47%;
        left:0;
        height:100%;
    }

    .ligne {
        /*border: 3px solid pink;*/
        height: 20%;
        cursor: pointer;
    }

    /*.mozaique {*/
    /*    overflow: visible;*/
    /*}*/

    /*.ligne > .mozaique {*/
    /*    width: 20%;*/
    /*}*/

    /*.plancher > .mozaique {*/
    /*    width: 13%;*/
    /*    margin:0.7%;*/
    /*}*/
</style>