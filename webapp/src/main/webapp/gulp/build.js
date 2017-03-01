'use strict';

var gulp = require('gulp');

var $ = require('gulp-load-plugins')();
var saveLicense = require('uglify-save-license');
var gulp = require('gulp');

gulp.task('styles', function () {
    return gulp.src('app/styles/**/*.scss')
        .pipe($.plumber())
        .pipe($.rubySass({style: 'expanded', sourcemap: true, compass: true}))
        .pipe($.autoprefixer('last 1 version'))
        .pipe(gulp.dest('.tmp/styles'))
        .pipe($.size());
});

gulp.task('scripts', function () {
    return gulp.src('app/scripts/**/*.js')
        .pipe($.jshint())
        .pipe($.jshint.reporter('jshint-stylish'))
        .pipe($.size());
});

gulp.task('phonePluginUtils', function () {
    return gulp.src('app/bower_components/intl-tel-input/build/js/utils.js')
        .pipe(gulp.dest('dist/scripts'))
        .pipe($.size());
});

gulp.task('phonePluginImages', function () {
    return gulp.src('app/bower_components/intl-tel-input/build/img/*.png')
        .pipe(gulp.dest('dist/img'))
        .pipe($.size());
});

gulp.task('data', function () {
    return gulp.src('app/scripts/**/*.json')
        .pipe(gulp.dest('dist/scripts'))
        .pipe($.size());
});

gulp.task('favicon', function () {
    return gulp.src('app/favicon.ico')
        .pipe(gulp.dest('dist/'))
        .pipe($.size());
});

gulp.task('partials', function () {
    return gulp.src('app/partials/**/*.html')
        .pipe($.minifyHtml({
            empty: true,
            spare: true,
            quotes: true
        }))
        .pipe(gulp.dest('dist/partials'))
        .pipe($.size());
});

gulp.task('html', ['styles', 'fonts' ,'scripts', 'partials','images', 'phonePluginUtils', 'phonePluginImages', 'data'], function () {
    var jsFilter = $.filter('**/*.js');
    var cssFilter = $.filter('**/*.css');

    return gulp.src('app/*.html')
        .pipe($.useref.assets())
        .pipe($.rev())
        .pipe(jsFilter)
        .pipe($.replace('bower_components/intl-tel-input/build/js/utils.js', 'scripts/utils.js'))
        .pipe($.ngAnnotate())
        .pipe($.uglify({preserveComments: $.uglifySaveLicense}))
        .pipe(jsFilter.restore())
        .pipe(cssFilter)
        .pipe($.replace('bower_components/bootstrap-sass-official/vendor/assets/fonts/bootstrap', 'fonts'))
        .pipe($.csso())
        .pipe(cssFilter.restore())
        .pipe($.useref.restore())
        .pipe($.useref())
        .pipe($.revReplace())
        .pipe(gulp.dest('dist'))
        .pipe($.size());
});

gulp.task('images', function () {
    return gulp.src('app/images/**/*')
        //.pipe($.imagemin({
        //    optimizationLevel: 3,
        //    progressive: true,
        //    interlaced: true
        //}))
        .pipe(gulp.dest('.tmp/images'))
        .pipe(gulp.dest('dist/images'))
});

gulp.task('fonts', function () {
    return gulp.src('app/fonts/**/*')
        .pipe(gulp.dest('dist/fonts'))
        .pipe($.size());
});

var del = require('del');

gulp.task('clean', function (cb) {
    del([
        '.tmp',
        'dist'
    ], cb);
});

gulp.task('build', ['clean', 'html', 'partials', 'images', 'phonePluginUtils', 'phonePluginImages', 'data', 'favicon']);
