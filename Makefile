clean:
	lein clean && rm -rf dist

build: clean
	lein do cljsbuild once prod-agent prod-client

watch:
	lein do cljsbuild auto dev-agent dev-client

repl:
	lein repl

start:
	yarn electron .

dist:
	yarn electron-builder
