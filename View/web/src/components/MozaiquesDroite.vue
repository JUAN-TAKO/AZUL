<template>
    <div class="mozaiques-droite">
        <div v-for="(ligne, index) in mur" :key="index" class="ligne">
            <Mozaique
                    v-for="(mozaique, indexMozaique) in ligne"
                    :key="indexMozaique"
                    :couleur="mozaiqueCouleur(index,indexMozaique)">
            </Mozaique>
        </div>
    </div>
</template>

<script>
    import Mozaique from '@/components/Mozaique'
    export default {
        name: "MozaiquesDroite",
        components : {
            Mozaique
        },
        props: {
            mur: {}
        },
        computed: {
        },
        methods: {
            mozaiqueCouleur(y,x) {
                return this.mur[y][x] ? (((x)-y)+5)%5+1 : 0;
            },
            getCouleur(y,x) {
                let retour = "none";
                if(!this.mur[y][x])
                    return retour;
                switch((((x)-y)+5)%5+1) {
                    case 1:
                    case 5:
                        retour = "blue";
                        break;
                    case 2:
                        retour = "yellow";
                        break;
                    case 3:
                        retour = "red";
                        break;
                    case 4:
                        retour = "white";
                        break;

                }
                return retour;
            }
        }
    }
</script>

<style scoped>
    .mozaiques-droite {
        position: relative;
        /*border: 3px solid yellow;*/
        width: 48%;
        left:5%;
        height:100%;
    }
</style>