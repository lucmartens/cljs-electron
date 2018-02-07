(ns user
  (:require [figwheel-sidecar.repl-api :as f]))

(defn fig-start
  []
  (f/start-figwheel! "dev-client" "dev-agent"))


(defn fig-stop
  []
  (f/stop-figwheel!))

(defn cljs-repl
  [build-id]
  (f/cljs-repl build-id))

(defn agent-repl
  []
  (cljs-repl "dev-agent"))

(defn client-repl
  []
  (cljs-repl "dev-client"))
