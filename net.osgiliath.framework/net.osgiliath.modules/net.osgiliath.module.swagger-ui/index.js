var pack = require('./package');
var path = require('path');

module.exports = {
    version: pack.version,
    dist: path.resolve(__dirname, 'target/net.osgiliath.helpers.swagger.ui')
};