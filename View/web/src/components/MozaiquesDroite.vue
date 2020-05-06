<template>
    <div class="mozaiques-droite">
        <div v-for="(ligne, index) in mur" :key="index" class="ligne">
            <Mozaique v-for="(mozaique, indexMozaique) in ligne" :key="indexMozaique" :couleur="mozaiqueCouleur(index,indexMozaique)"></Mozaique>
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