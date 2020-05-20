// vue.config.js
module.exports = {
    publicPath: process.env.NODE_ENV === 'production' ? './' : '/',
    chainWebpack: config => {
      if(config.plugins.has('extract-css')) {
        const extractCSSPlugin = config.plugin('extract-css')
        extractCSSPlugin && extractCSSPlugin.tap(() => [{
          filename: 'css/css.css',
          chunkFilename: 'css/css-chunk.css'
        }])
      }
    },
    configureWebpack: {
      output: {
        filename: 'js/app.js',
        chunkFilename: 'js/app-chunk.js'
      }
    }
  }