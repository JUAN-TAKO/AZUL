<template>
    <div class="plateau-joueur embed-responsive embed-responsive-4by3" :class="{ 'not-current-player' : !isCurrent }">
        <div class="embed-responsive-item">
            <div class="mozaiques d-flex">
                <MozaiquesGauche :lignes="plateauJoueur.linesNb" :couleurs="plateauJoueur.linesColor" @ajoutplancher="ajoutPlancher" :isCurrent="isCurrent"></MozaiquesGauche>
                <MozaiquesDroite :mur="plateauJoueur.wall"></MozaiquesDroite>
            </div>
            <div class="plancher d-flex">
                <Mozaique v-for="(mozaiqueFloor, index) in plateauJoueur.floor" :key="index" :couleur="mozaiqueFloor"></Mozaique>
            </div>
        </div>
    </div>
</template>

<script>
    import MozaiquesGauche from "./MozaiquesGauche";
    import MozaiquesDroite from "./MozaiquesDroite";
    import Mozaique from "./Mozaique";
    export default {
        name: "PlateauJoueur",
        components: {
            MozaiquesGauche,
            MozaiquesDroite,
            Mozaique
        },
        data() {
            return {
            }
        },
        props: {
            plateauJoueur:{},
            id : Number
        },
        methods: {
            ajoutPlancher(valeur) {
                console.log(valeur)
            }
        },
        computed: {
            isCurrent() {
                return this.$store.state.board.currentPlayer === this.id
            }
        }

    }
</script>

<style scoped>
    .plateau-joueur {
        /*border: solid 1px black;*/
        background: center / contain no-repeat url("/img/plateau-joueur.png");
        position: relative;
    }

    .mozaiques {
        position: absolute;
        top: 13%;
        right: 6%;
        left: 6%;
        bottom: 31%;
        /*border: 3px solid red;*/
    }

    .plancher {
        position: absolute;
        /*border: 3px solid purple;*/
        bottom: 9%;
        right: 31%;
        left: 4%;
        top: 75%;
    }

    .not-current-player {
        background: linear-gradient(black, black),center / contain no-repeat url("/img/plateau-joueur.png");
        background-size: cover;
        background-blend-mode: saturation;
    }
</style>