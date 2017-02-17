(ns podplayer.handler
  (:use podplayer.feeds)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def feeds (fetch-feeds))

(defn home []
  (println "foo"))

(defroutes app-routes
  (GET "/" [] (home))
  (GET "/test/:idx" [idx] (nth idx (items)))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

