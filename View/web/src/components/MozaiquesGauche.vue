<template>
    <div class="mozaiques-gauche" :class="{ 'light-around' : this.$store.state.selection.selectionner && isCurrent }">
        <div class="ligne flex-row-reverse" @click="mouseClick(index,couleurs[index])" @mouseover="mouseOver(index)" @mouseleave="mouseOut()" v-for="(nbMozaiques, index) in this.lignes" :key="index">
            <Mozaique v-for="(mozaique,i) in getNbMozaiqueLigne(nbMozaiques,couleurs[index],index)" :key="i" :couleur="getCouleurMozaiqueLigne(couleurs[index])"></Mozaique>
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
                this.$emit("ajoutplancher")
            },
            getNbMozaiqueLigne(nombre,couleur,ligne) {
                let retour;
                let selection = this.$store.state.selection
                if(selection.selectionner && (couleur === selection.donnees.color || couleur === 0) && this.ligneOver === ligne) {
                    retour = nombre + selection.donnees.nSelected;
                    if(retour > ligne + 1) {
                        let plancher = retour - (ligne+1)
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
                if(!this.$store.state.coupJouer && this.$store.state.selection.selectionner && ( couleur === this.$store.state.selection.donnees.color || couleur == 0)) {
                    this.$store.dispatch("jouerCoup",ligne)
                }
            }
        },
        computed: {
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
    }

    .light-around {
        animation: clignotage 1.5s infinite;
    }
    
    @keyframes clignotage {
        0% {
            box-shadow: 0px 0px 5px yellow;
        }

        50% {
            box-shadow: 0px 0px 75px yellow;
        }

        100% {
            box-shadow: 0px 0px 5px yellow;
        }
    }
</style>