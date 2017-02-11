(ns podplayer.feeds
  (:use feedparser-clj.core))

(defn fetch [feed]
  (parse-feed feed))

(def feeds '("https://changelog.com/gotime/feed"
            "http://cavecomedyradio.com/pod-series/last-podcast-on-the-left/feed/"))

(defn fetch-feeds []
  (map parse-feed feeds))

