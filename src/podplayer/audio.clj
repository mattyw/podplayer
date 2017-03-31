(ns podplayer.audio
  (:use [clojure.java.io :only [as-url]])
  (:use [clj-audio.core]))

(defn decode-play [url]
  (-> (->stream (as-url url)) decode play))

(defn play-audio [url]
  (.start (Thread. 
            (fn [] (decode-play url)))))

(defn stop-audio []
  (stop)
  "ok")
