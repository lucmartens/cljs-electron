clean:
	lein clean && rm -rf dist

build: clean
	lein do cljsbuild once prod-agent prod-client

watch:
	lein do cljsbuild auto dev-agent dev-client

repl:
	lein repl

init:
	yarn install && lein deps

start:
	yarn electron .

dist: build
	yarn electron-builder --mac --publish never

publish: build
	yarn electron-builder --mac --publish always
