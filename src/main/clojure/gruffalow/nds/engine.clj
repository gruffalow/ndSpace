(ns gruffalow.nds.engine
  (:require
    [clojure.pprint :as pprint])
  (:import [gruffalow.nds.interfaces INDSEngine])
  (:gen-class))


(defn incrementer
  "just to prove the test stuff is working"
  [number] (+ number 1))

(defn ndsEngineClass
  "basic engine class"
  []
  (reify
    INDSEngine
    (initialize [this] (println "NDSEngine initialized"))))