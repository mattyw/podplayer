(ns podplayer.audio
  (:use [clojure.java.io :only [reader writer]])
  (:import java.lang.ProcessBuilder))

(def current-process (ref nil))

(defn as-struct [^Process process]
    {:out (-> process
              (.getInputStream)
              (reader))
     :err (-> process
              (.getErrorStream)
              (reader))
     :in (-> process
             (.getOutputStream)
             (writer))
     :process process})

(defn spawn [& args]
  (let [process (-> (ProcessBuilder. args)
                    (.start))]
    (as-struct process)))

(defn play-file [file]
  (let [quoted-file (str "\"" file "\"")]
  (dosync (ref-set current-process 
           (spawn "mplayer" quoted-file)))
  "done"))

(defn stop []
  (.destroy (:process @current-process)))

(play-file "http://feeds.soundcloud.com/stream/314132938-lastpodcastontheleft-episode-263-l-ron-hubbard-part-iii-scientology-begins.mp3")
(stop)
(println @current-process)


(defn foo [a & b]
  (println a & b))

(foo "1" "2")
