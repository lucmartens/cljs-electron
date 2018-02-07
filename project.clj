(defproject cljs-electron ""
  :min-lein-version "2.7.1"

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.908"]]

  :plugins [[lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
            [lein-figwheel "0.5.13"]]

  :clean-targets ["target", "app/agent/js" "app/client/js"]

  :cljsbuild
  {:builds
   {:dev-agent
    {:source-paths ["src/agent" "src/common"]
     :figwheel true
     :compiler {:main agent.core
                :output-to "app/agent/js/agent.js"
                :output-dir "app/agent/js/dev"
                :target :nodejs
                :closure-defines {agent.config/env "dev"}
                :optimizations :none}}

    :dev-client
    {:source-paths ["src/client" "src/common"]
     :figwheel true
     :compiler {:main client.core
                :asset-path "js/dev"
                :output-to "app/client/js/client.js"
                :output-dir "app/client/js/dev"
                :preloads [devtools.preload]
                :optimizations :none}}


    :prod-agent
    {:source-paths ["src/agent" "src/common"]
      :compiler {:main agent.core
                 :output-to "app/agent/js/agent.js"
                 :output-dir "app/agent/js/prod"
                 :target :nodejs
                 :optimizations :simple}}

    :prod-client
    {:source-paths ["src/agent" "src/common"]
     :compiler {:main client.core
                :asset-path "js/prod"
                :output-to "app/client/js/client.js"
                :output-dir "app/client/js/prod"
                :optimizations :simple}}}}

  :profiles
  {:dev {:dependencies [[figwheel-sidecar "0.5.13"]
                        [com.cemerick/piggieback "0.2.2"]
                        [binaryage/devtools "0.9.4"]
                        [proto-repl "0.3.1"]]
         :source-paths ["src" "dev"]
         :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}})
