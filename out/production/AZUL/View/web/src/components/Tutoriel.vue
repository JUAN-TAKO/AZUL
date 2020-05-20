<template>
    <div class="">
        <div class="tutoriel-component m-0 d-inline-block w-100 h-100">
            <div class="row m-5">
                <button class="btn btn-primary w-100 mb-5" @click="compris">J'ai compris les règles, je veux apprendre a jouer.</button>
                <div class="col-9 row m-0">
                    <div class="col-5 h-100">
                        <h2 class="text-center text-uppercase">But Du jeu</h2>
                        <p>Être le joueur avec le plus de points à la fin de la partie.
                            <br>
                        La Partie se termine à la fin d'une manche, lorsqu'au moins un joueur aura complété sur son mur une ligne de 5 tuiles.</p>
                        <img src="img/butDuJeu.png" alt="" class="w-100">
                        <h2 class="text-center text-uppercase">Fin de tour</h2>
                        <p>
                            Un tour prends fin quand il ne reste plus de tuiles au centre et sur les fabriques. <br>
                            Pour chaque ligne complète, vous gagnez un tuile de la même couleur sur votre mur à la ligne correspondante.
                            La ligne complète se vide.
                            <br>
                            Toutes les autres lignes demeurent en place pour la suite du jeu.</p>
                        <img src="img/finDeTour.png" alt="" class="w-100">
                    </div>
                    <div class="col-6 mx-auto text-center">
                        <h2 class="text-center text-uppercase">Le plateau</h2>
                        <img src="img/plateau.png" alt="" class="w-75">
                        <h2 class="text-center text-uppercase">Le plancher</h2>
                        <p>Pour chaque tuile située sur le Plancher, vous perdrez le nombre de points indiqué directement au-dessus.
                            <br>
                        (Vous ne pouvez pas descendre sous la marque des 0 points).</p>
                        <img src="img/plancherPoint.png" alt="" class="w-75 mx-auto">
                    </div>
                </div>
                <div class="col-3">
                    <img class="w-100" src="img/decomptePoint.png" alt="">
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Tutoriel",
        methods: {
            compris() {
                this.$store.state.jaiCompris = true
            }
        }
    }
</script>

<style scoped>
    .tutoriel-component {
        /*background: rgba(50,50,255,0.4);*/
        /*background: rgba(255,0,0,1);*/
    }

    p {
        font-size: 1.2vw;
        font-weight: bold;
        color: #005d80;
    }

    img {
        margin-bottom: 30px;
    }

    h2 {
        color: #005d80;
        font-weight: bold;
        text-decoration: underline;
    }
</style>