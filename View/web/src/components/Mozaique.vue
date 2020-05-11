<template>
    <div class="embed-responsive embed-responsive-1by1 mozaique" :style="{ scale : this.scale }">
        <img v-if="this.getSrc != null" class="embed-responsive-item" :src="this.getSrc">
    </div>
</template>
<script>
    export default {
        name: "Mozaique",
        data() {
            return {
                libre : false,
                src :  null,
            }
        },
        props: {
            couleur : {
                type : Number,
                default : 0
            },
            scale : {
                type : Number,
                default : 1
            }
        },
        computed: {
            getSrc() {
                let couleur;
                switch (this.couleur) {
                    case 0:
                        couleur = null
                        break;
                    case 1:
                        couleur = "bleu"
                        break;
                    case 2:
                        couleur = "jaune"
                        break;
                    case 3:
                        couleur = "rouge"
                        break;
                    case 4:
                        couleur = "noir"
                        break;
                    case 5:
                        couleur = "sion"
                        break;
                    case 6 :
                        couleur = "premier"
                        break;
                    default:
                        couleur = null;
                        break;
                }
                if(couleur != null)
                    return 'img/mozaique-' + couleur + '.png'
                else
                    return null
            }
        },
        methods: {

        },
        mounted() {

        },
        created() {
        }
    }
</script>

<style scoped>
    .mozaique {
        overflow: visible;
    }

    .ligne > .mozaique {
        width: 20%;
    }
    .plancher > .mozaique {
        width: 13%;
        margin:0.7%;
    }
</style>