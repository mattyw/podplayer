(ns podplayer.handler
  (:use [podplayer.feeds]
        [podplayer.audio])
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn application [title & content]
  (hiccup/html
         [:head
          [:title title]
         ]
         [:body
          [:div {:class "container"} 
           [:a {:href "/"} "Home "]
           [:a {:href "/stop"} " Stop"]
           [:hr]
           ]]
          [:div {:class "container"} content ]))

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
  (GET "/" [] (application "Home" (home-view)))
  (GET "/feed/:title" [title] (application "Feed" (feed-view title)))
  (GET "/play" [file] (application "Play" (play-audio file)))
  (GET "/stop" [] (application "Stop" (stop-audio)))
  (route/not-found "Not Found"))

(def app
  (wrap-params (wrap-defaults app-routes site-defaults)))

