(ns podplayer.audio
  (:use [clojure.java.io :only [reader writer]])
  (:import java.lang.ProcessBuilder))

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
  (spawn "mplayer" file))

(defn stop [process]
  (.destroy (:process process)))
