module.exports = {
    
    // GET /
    getIndex: function(req, res, next) {
        res.status(200).json({ message: 'Connected!' });
    }
}