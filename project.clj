(defproject podplayer "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [hiccup "1.0.5"]
                 [http-kit "2.1.12"]
                 [org.craigandera/dynne "0.4.1"]
                 [ring/ring-defaults "0.2.1"]
                 [javax.servlet/servlet-api "2.5"]
                 [org.clojars.beppu/clj-audio "0.3.0"]
                 [org.clojars.freemarmoset/feedparser-clj "0.6.1"]
                 [ring/ring-mock "0.3.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler podplayer.handler/app}
  :main podplayer.handler
  :aot [podplayer.handler])
