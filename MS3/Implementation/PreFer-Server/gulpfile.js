var gulp = require('gulp');
var mongodbData = require('gulp-mongodb-data');

gulp.task('filldb', function() {
  gulp.src('data.json')
    .pipe(mongodbData({
      mongoUrl: 'mongodb://localhost:27017/EISWS1617NguyenLe',
      collectionName: 'soils',
      dropCollection: true
    }))
})