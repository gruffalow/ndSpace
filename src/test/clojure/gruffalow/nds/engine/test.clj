(ns gruffalow.nds.engine.test
  (:use clojure.test gruffalow.nds.engine))

(deftest increment-test
  "just to prove clojure testing is working"
  (is (= 2 (incrementer 1))))
