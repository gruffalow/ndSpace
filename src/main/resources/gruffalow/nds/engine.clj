(ns gruffalow.nds.engine
  (:require
    [clojure.pprint :as pprint])
  (:import [gruffalow.nds.interfaces INDSEngine])
  (:gen-class))

(defn ndsEngineClass
  "what does this text do?"
  []
  (reify
    INDSEngine
    (initialize [this] (println "NDSEngine initialized"))))