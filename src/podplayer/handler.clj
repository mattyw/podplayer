(ns podplayer.handler
  (:gen-class)
  (:use [podplayer.feeds]
        [org.httpkit.server :only [run-server]]
        [podplayer.audio])
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [hiccup.core :as hiccup]
            [hiccup.form :as hf]
            [ring.middleware.params :refer [wrap-params]]
            [ring.util.response :refer [redirect]]
            [ring.util.anti-forgery :refer [anti-forgery-field]]
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
           [:a {:href "/podcasts"} " Podcasts"]
           [:a {:href "/refresh"} " Refresh"]
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

(defn podcast-view []
  (hiccup/html
    (hf/form-to [:post "/update"]
     [:p "Podcasts:"]
     (hf/text-area
      {:rows 20 :cols 100}
      :podcasts
      (clojure.string/join "\n" @subscriptions)
      )
     (anti-forgery-field)
     (hf/submit-button "save")
     )))

(defroutes app-routes
  (GET "/" [] (application "Home" (home-view)))
  (GET "/feed/:title" [title] (application "Feed" (feed-view title)))
  (GET "/play" [file] (application "Play" (play-audio file)))
  (GET "/stop" [] (application "Stop" (stop-audio)))
  (GET "/refresh" [] (application "Refresh" (fetch-feeds)))
  (GET "/podcasts" [] (application "Podcasts" (podcast-view)))
  (POST "/update" [podcasts] 
        (update-feeds (clojure.string/split-lines podcasts))
        (redirect "/")
        )
  (route/not-found (application "Not Found" "Not Found")))

(def app
  (wrap-params (wrap-defaults app-routes site-defaults)))

(defn -main [& args]
  (run-server app {:port 8080})
  (println "podplayer running"))
