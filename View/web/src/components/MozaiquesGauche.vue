<template>
    <div class="mozaiques-gauche">
        <div class="ligne flex-row-reverse" @mouseover="mouseOver(index)" @mouseout="mouseOut()" v-for="(nbMozaiques, index) in this.lignes" :key="index">
            <Mozaique v-for="(mozaique,i) in getNbMozaiqueLigne(nbMozaiques,couleurs[index],index)" :key="i" :couleur="getCouleurMozaiqueLigne(couleurs[index])"></Mozaique>
        </div>
    </div>
</template>

<script>
    import Mozaique from '@/components/Mozaique'
    export default {
        name: "MozaiquesGauche",
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
                this.ligneOver = i
            },
            mouseOut () {
                this.ligneOver = 5
            },
            getNbMozaiqueLigne(nombre,couleur,ligne) {
                let retour;
                let selection = this.$store.state.selection
                if(selection.selectionner && (couleur === selection.donnees.couleur || couleur === 0) && this.ligneOver === ligne) {
                    retour = nombre + selection.donnees.nbSelection;
                    if(retour > ligne + 1) {
                        let plancher = retour - (ligne+1)
                        retour = ligne + 1;
                        this.$emit("ajoutplancher",plancher)
                    }
                } else {
                    retour = nombre;
                }
                return retour;
            },
            getCouleurMozaiqueLigne(couleur) {
                if(couleur === 0) {
                    return this.$store.state.selection.donnees.couleur
                } else {
                    return 0
                }
            },
            testAjoutPlancher() {
                console.log("teste")
            }
        },
        props: {
            couleurs : {},
            lignes : {}
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
        border: 3px solid pink;
        height: 20%;
    }
</style>