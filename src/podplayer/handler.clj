(ns podplayer.handler
  (:use podplayer.feeds)
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def all-feeds (fetch-feeds))
(def feed-map (as-map all-feeds))

(defn home-view []
  (hiccup/html
    (for [x (titles all-feeds)] [:p [:a {:href (str "feed/" x)} x]])))

(defn feed-view [title]
  (let [items (titles (entries (get feed-map title)))]
    (hiccup/html
      (for [x items] [:p [:a {:href (str title "/entry/" x)} x]]))))

(defn entry-view [title episode]
    (get-episode all-feeds title episode))

(defroutes app-routes
  (GET "/" [] (home-view))
  (GET "/feed/:title" [title] (feed-view title))
  (GET "/feed/:title/entry/:episode" [title episode] (entry-view title episode))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

