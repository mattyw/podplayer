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

(defn feed [f]
    {:title (:title f)
     :entries (entries f)})

(defn titles [feeds]
    (map :title feeds))

(defn as-map [feeds]
  (zipmap (titles feeds) feeds))

(defn get-episode [feeds feed-name episode]
  (let [feed (get (as-map feeds) feed-name)]
    (:value (get (zipmap (titles (entries feed)) (entries feed)) episode))))

(get-episode (fetch-feeds) "Cave Comedy RadioThe Last Podcast on the Left – Cave Comedy Radio" "The Puerto Rican Chupacabra")

; new-episode returns an episode map given a parsed rss feed.
(defn new-episode [episode]
  {:title (:title episode) :description (get-in episode [:description :value]) :file (:url (first (:enclosures episode)))})

(defn new-episodes [feed]
  {:title (:title feed) :episodes (map new-episode (:entries feed))})


(def got (first (fetch-feeds)))
(clojure.pprint/pprint (first (:entries got)))
(clojure.pprint/pprint (new-episodes got))
(clojure.pprint/pprint (feed-entries (first (fetch-feeds))))
;(titles (entries a))
;(def feed-map (as-map (fetch-feeds)))
;(titles (entries (get feed-map "Go Time")))
