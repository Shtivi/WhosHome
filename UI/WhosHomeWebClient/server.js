
const path = require('path'), 
   express = require('express'),
   webpack = require('webpack'),
   webpackConfig = require('./webpack.config.js'),
   app = express(),
   port = process.env.PORT || 3000;

app.listen(port, () => { console.log(`App is listening on port ${port}`) });

app.get('/js/app.bundle.js', (req, res) => {
    res.sendfile(path.resolve(__dirname, 'dist', 'js', 'app.bundle.js'));
})

app.get('/js/vendor.bundle.js', (req, res) => {
    res.sendfile(path.resolve(__dirname, 'dist', 'js', 'vendor.bundle.js'));
})

let compiler = webpack(webpackConfig);
app.use(require('webpack-dev-middleware')(compiler, {
   noInfo: true, publicPath: webpackConfig.output.publicPath, stats:    { colors: true }
}));
app.use(require('webpack-hot-middleware')(compiler));

app.get('/*', (req, res) => {
   res.sendFile(path.resolve(__dirname, 'dist', 'index.html'));
});
app.use(express.static(path.resolve(__dirname, 'dist')));