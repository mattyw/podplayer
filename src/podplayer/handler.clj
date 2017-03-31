(ns podplayer.handler
  (:use [podplayer.feeds]
        [podplayer.audio])
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))


(defn home-view []
  (hiccup/html
    (for [x (list-titles)] [:p [:a {:href (str "feed/" x)} x]])))

(defn feed-view [title]
  (let [episodes (get-episodes title)]
    (hiccup/html
      (for [x episodes] 
        [:p [:h1 (:title x)]
          [:a {:href (str "/play?file=" (:file x))} "Play Episode"]
          [:p (:description x)]]))))

(defn entry-view [title episode] "entry")

(defroutes app-routes
  (GET "/" [] (home-view))
  (GET "/feed/:title" [title] (feed-view title))
  (GET "/play" [file] (play-file file))
  (GET "/stop" [] (stop))
  (route/not-found "Not Found"))

(def app
  (wrap-params (wrap-defaults app-routes site-defaults)))

