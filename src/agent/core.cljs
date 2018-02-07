(ns agent.core
  (:require [cljs.nodejs :as nodejs]
            [agent.config :as config]))

(defonce electron       (js/require "electron"))
(defonce app            (.-app electron))
(defonce browser-window (.-BrowserWindow electron))

(nodejs/enable-util-print!)

(def main-window (atom nil))

(defn init-browser []
  (reset! main-window (browser-window.
                        (clj->js {:width 800
                                  :height 600})))

  (.loadURL ^js/electron.BrowserWindow @main-window config/client-url)
  (.on ^js/electron.BrowserWindow @main-window "closed" #(reset! main-window nil)))

(.on app "window-all-closed" #(when-not (= js/process.platform "darwin")
                                        (.quit app)))
(.on app "ready" init-browser)
