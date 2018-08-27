
const path = require('path'),
webpack = require('webpack'),
HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
entry: {
    app: ['./src/app/index.tsx', 'webpack-hot-middleware/client'],
    vendor: ['react', 'react-dom']
},
output: {
    path: path.resolve(__dirname, 'dist'),
    filename: 'js/[name].bundle.js'
},
devtool: 'source-map',
mode: 'development',
resolve: {
    extensions: ['.js', '.jsx', '.json', '.ts', '.tsx']
},
module: {
    rules: [
        {
            test: /\.(ts|tsx)$/,
            loader: 'ts-loader'
        },
        {
            test: /\.css$/,
            use: [ 'style-loader', 'css-loader' ]
        },
        {
            test: /\.(ttf|eot|woff|woff2|otf)$/,
            use: {
                loader: "file-loader",
                options: {
                    outputPath: 'fonts/',
                    name: "./src/fonts/[name].[ext]",
                },
            },
        },
        {
            test: /\.(png|jp(e*)g|svg)$/,
            // use: {
            //     loader: "file-loader",
            //     options: {
            //         outputPath: 'pics/',
            //         name: "./src/pics/[name].[ext]",
            //     },
            // }
            use: [{
                loader: 'file-loader',
                options: { 
                    name: 'pics/[name].[ext]'
                },
                // publicPath: 'dist/pics'
            }]        },
        { enforce: "pre", test: /\.js$/, loader: "source-map-loader" }
    ],
},
plugins: [
    new HtmlWebpackPlugin({ template: path.resolve(__dirname, 'src', 'app', 'index.html') }),
    new webpack.HotModuleReplacementPlugin()
]
}