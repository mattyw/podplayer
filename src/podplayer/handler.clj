(ns podplayer.handler
  (:use podplayer.feeds)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn home-view []
  (hiccup/html
    (for [x (list-titles)] [:p [:a {:href (str "feed/" x)} x]])))

(defn feed-view [title]
  (let [episodes (get-episodes title)]
    (hiccup/html
      (for [x episodes] 
        [:p [:h1 (:title x)]
          [:a {:href (:file x)} "Play Episode"]
          [:p (:description x)]]))))

(defn entry-view [title episode] "entry")

(defroutes app-routes
  (GET "/" [] (home-view))
  (GET "/feed/:title" [title] (feed-view title))
  (GET "/feed/:title/entry/:episode" [title episode] (entry-view title episode))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

