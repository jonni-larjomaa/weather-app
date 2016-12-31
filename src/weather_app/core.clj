(ns weather-app.core
  (:gen-class)
  (:require [clojure.tools.cli :refer [parse-opts]]
            [weather-app.weather :as weather]))

(def cli-options

  [["-ht" "--high TEMP" "Upper Treshold"
    :default 20
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 40) "must be between 0 and 40"]]

   ["-lt" "--low TEMP" "Lower Treshold"
    :default -10
    :parse-fn #(Integer/parseInt %)
    :validate [#(< -40 % 100) "must be between 0 and -40"]]

   ["-c" "--loc LOCATION" "Location e.g. Helsinki,fi"
    :default "Helsinki,fi"]

   ["-u" "--update PERIOD" "Update period in seconds"
    :default 10]

   ["-h" "--help"]])



(defn is-out-of-bounds? [current-temp boundaries]
  (cond
    (< current-temp (:low boundaries)) (str "\033[1;34m")
    (> current-temp (:high boundaries)) (str "\033[1;31m")
    :else (str "\033[1;32m")))

;; There should be predicates dictating coloring
;; according to high and low values, but ran out of time...
(defn print-info [data bound-temps]
  (let [curr-temp (:temp data)]
    (println
      (is-out-of-bounds? curr-temp bound-temps)
      "current temperature: "
      curr-temp
      "\033[0m")))

(defn -main
  "Fire the program!"
  [& args]
  (let [{:keys [options arguments summary errors]} (parse-opts args cli-options)
        location (:loc options)
        temp-boundaries (select-keys options [:high :low])]
    (cond
      (true? (:help options)) (println summary)
      :else (print-info
              (weather/parse-data
                (weather/get-weather {:q location}))
              temp-boundaries))))
