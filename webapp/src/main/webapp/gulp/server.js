'use strict';

var gulp = require('gulp');

var browserSync = require('browser-sync');
var modRewrite  = require('connect-modrewrite');
var httpProxy = require('http-proxy');

/* This configuration allow you to configure browser sync to proxy your backend */
var proxyTarget = 'http://localhost:8080'; // The location of your backend
var proxyApiPrefix = '/app/api'; // The element in the URL which differentiate between API request and static file request

var proxy = httpProxy.createProxyServer({
  target: proxyTarget
}).on('error', function(e) {
  console.log(JSON.stringify(e, null, ' '))
});

function proxyMiddleware(req, res, next) {
  if (req.url.indexOf(proxyApiPrefix) !== -1) {
    proxy.web(req, res);
  } else {
    next();
  }
}

function browserSyncInit(baseDir, files, browser) {
  browser = browser === undefined ? 'default' : browser;

  browserSync.instance = browserSync.init(files, {
    startPath: '/',
    server: {
      baseDir: baseDir,
      middleware: [
        proxyMiddleware,
        modRewrite([
          '!\\.html|\\.js|\\.css|\\.scss|\\.png|\\.jpg|\\.gif|\\.map|\\.svg|\\.woff|\\.woff2|\\.ttf$ /index.html [L]'
        ])
      ]
    },
    browser: browser
  });

}

gulp.task('serve', ['watch'], function () {
  browserSyncInit([
    'app',
    '.tmp'
  ], [
    'app/*.html',
    '.tmp/styles/**/*.css',
    'app/scripts/**/*.js',
    'app/partials/**/*.html',
    'app/images/**/*'
  ]);
});

gulp.task('serve:dist', ['build'], function () {
  browserSyncInit('dist');
});

gulp.task('serve:e2e', function () {
  browserSyncInit(['app', '.tmp'], null, []);
});

gulp.task('serve:e2e-dist', ['watch'], function () {
  browserSyncInit('dist', null, []);
});
