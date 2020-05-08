<template>
    <div>
        <div>
            <h3 :class="{'text-primary' : isCurrent, 'text-secondary' : !isCurrent}">{{plateauJoueur.name}} - Score : {{ plateauJoueur.score }}</h3>
        </div>
        <div class="plateau-joueur embed-responsive embed-responsive-4by3" :class="{ 'not-current-player' : !isCurrent }">
            <div class="embed-responsive-item">
                <div class="mozaiques d-flex">
                    <MozaiquesGauche :lignes="plateauJoueur.linesNb" :couleurs="plateauJoueur.linesColor" @ajoutplancher="ajoutPlancher" :isCurrent="isCurrent"></MozaiquesGauche>
                    <MozaiquesDroite :mur="plateauJoueur.wall" :isCurrent="isCurrent"></MozaiquesDroite>
                </div>
                <div class="plancher d-flex">
                    <Mozaique v-for="(mozaiqueFloor, index) in plancher" :key="index" :couleur="mozaiqueFloor"></Mozaique>
                </div>
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
                plancherAjout : 0,
                couleurPlancherAjout : 0
            }
        },
        props: {
            plateauJoueur:{},
            id : Number
        },
        methods: {
            ajoutPlancher(valeur,couleur) {
                this.plancherAjout = valeur
                this.couleurPlancherAjout = couleur
            }
        },
        computed: {
            isCurrent() {
                return this.$store.state.board.currentPlayer === this.id
            },
            plancher() {
                let plancher = Array.from(this.$store.state.board.PB[this.id].floor)
                if(this.plancherAjout !== 0) {
                    let cpt = this.plancherAjout;
                    let i = 0;
                    while(i < plancher.length && cpt > 0) {
                        if(plancher[i] === 0) {
                            plancher[i] = this.couleurPlancherAjout
                            cpt--;
                        }
                        i++;
                    }
                }
                return plancher
            }
        }

    }
</script>

<style scoped>
    .plateau-joueur {
        /*border: solid 1px black;*/
        background: center / contain no-repeat url("/img/plateau-joueur.png");
        position: relative;
        border-radius: 3%;
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
        background: center / contain no-repeat url("/img/plateau-joueur-not-current.png");
        border:none;
    }

    .plateauGlobalCurrent {
    }
</style>