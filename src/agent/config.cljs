(ns agent.config)

(goog-define env "prod")

(def client-url
  (if (= env "prod")
    (str "file://" js/__dirname "/../../client/index.html")
    (str "file://" js/__dirname "/../../../../client/index.html")))
