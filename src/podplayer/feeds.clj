(ns podplayer.feeds
  (:use feedparser-clj.core))

(def feeds '("https://changelog.com/gotime/feed"
            "http://cavecomedyradio.com/pod-series/last-podcast-on-the-left/feed/"))

(defn fetch-feeds []
  (map parse-feed feeds))

; new-episode returns an episode map given a parsed rss feed.
(defn new-episode [episode]
  {:title (:title episode) :description (get-in episode [:description :value]) :file (:url (first (:enclosures episode)))})

; new-episodes creates a map with all episodes in the given podcast
(defn new-episodes [feed]
  {(:title feed) (map new-episode (:entries feed))})

; load-feeds can be considered the main entry point into the module.
; it takes the list of all urls and parses them into episodes.
(defn load-feeds [urls]
  (let [feeds (map parse-feed urls)]
    (apply merge (map new-episodes feeds))))

(def all-feeds (load-feeds feeds))

(defn get-episodes [title]
  (get all-feeds title))

(defn list-titles []
  (keys all-feeds))

;all-feeds defines all the feeds
;(clojure.pprint/pprint (keys all-feeds))
;(keys all-feeds)
;(get-episodes "Go Time" all-feeds)
;(def got (first (fetch-feeds)))
;(def lp (last (fetch-feeds)))
;(clojure.pprint/pprint (load-feeds feeds))
;(clojure.pprint/pprint (first (:entries got)))
;(clojure.pprint/pprint (new-episodes got))
;(clojure.pprint/pprint (new-episodes lp))
