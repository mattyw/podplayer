(ns podplayer.feeds
  (:use feedparser-clj.core))

(defn fetch [feed]
  (parse-feed feed))

(def feeds '("https://changelog.com/gotime/feed"
            "http://cavecomedyradio.com/pod-series/last-podcast-on-the-left/feed/"))

(defn fetch-feeds []
  (map parse-feed feeds))

(defn feed-entries [feed]
  (map (fn [x] (get-in x [:description :value])) (:entries feed)))

(defn items []
  (map (fn [x] (get-in x [:description :value])) (:entries (first (fetch-feeds)))))

(defn feed-entry [fe]
  {:title (:title fe)
   :value (get-in fe [:description :value] )})

(defn title [feed]
  (:title feed))

(defn entries [feed]
  (map feed-entry (:entries feed)))



(def a (first (fetch-feeds)))

(defn feed [f]
    {:title (:title f)
     :entries (entries f)})

(defn titles [feeds]
    (map :title feeds))

(titles (fetch-feeds))
(titles (entries a))
