<template>
    <div class="embed-responsive embed-responsive-1by1 mozaique" v-bind:style="styleObject">
        <div class="embed-responsive-item "></div>
    </div>
</template>

<script>
    export default {
        name: "Mozaique",
        data() {
            return {
                libre : false

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
            styleObject() {
                let couleur;
                switch (this.couleur) {
                    case 0:
                        couleur = "none"
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
                }
                let back = this.libre ? "none" : (couleur !=  "none" ? 'center / contain no-repeat url(img/mozaique-' + couleur + '.png)' : 'none')
                return {
                    background : back,
                    transform : 'scale(' + this.scale + ')'
                }
            }
        },
        methods: {
        }
    }
</script>

<style scoped>
    .mozaique {
        border: 3px blue solid !important;
    }

    .ligne > .mozaique {
        width: 20%;
    }
    .plancher > .mozaique {
        width: 13%;
        margin:0.7%;
    }
</style>