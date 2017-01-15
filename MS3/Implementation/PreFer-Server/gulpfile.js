var gulp = require('gulp');
var mongodbData = require('gulp-mongodb-data');

gulp.task('soildb', function() {
  gulp.src('soilData.json')
    .pipe(mongodbData({
      mongoUrl: 'mongodb://localhost:27017/EISWS1617NguyenLe',
      collectionName: 'soils',
      dropCollection: true
    }))
})

gulp.task('plantdb', function() {
  gulp.src('plantData.json')
    .pipe(mongodbData({
      mongoUrl: 'mongodb://localhost:27017/EISWS1617NguyenLe',
      collectionName: 'plants',
      dropCollection: true
    }))
})