(ns claudator.core
  (:gen-class)
  (:require [clojure.tools.cli :refer [parse-opts]]
            [claudator.weather :as weather]))

(def cli-options

  [["-ht" "--high TEMP" "Upper Treshold"
    :default 20
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 40) "must be between 0 and 40"]]

   ["-lt" "--low TEMP" "Lower Treshold"
     :default -10
     :parse-fn #(Integer/parseInt %)
     :validate [#(< -40 % 0) "must be between 0 and -40"]]

   ["-c" "--loc LOCATION" "Location e.g. Helsinki,fi"
     :default "Helsinki,fi"]

   ["-u" "--update PERIOD" "Update period in seconds"
    :default 10]

   ["-h" "--help"]])

;; There should be predicates dictating coloring
;; according to high and low values, but ran out of time...
(defn print-info [data]
  (println "\033[0;32m" data "\033[0m"))

(defn -main
  "Fire the program!"
  [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)]
  (cond
    (true? (:help options)) (println summary)
    :else (print-info
            (weather/parse-data
              (weather/get-weather {:q (:loc options)}))))))
