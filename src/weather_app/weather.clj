(ns weather-app.weather
  (:require [org.httpkit.client :as http]
            [clojure.data.json :as json]))

(def req-params { :query-params {:APPID "b2302ca677e216d14d3ec4429e8944fd"
                                 :units "metric"}})

(defn add-custom-params [static custom]
  (assoc static :query-params (conj (:query-params static) custom)))

(defn get-weather
  [options]
  (let [req-options (add-custom-params req-params options)
        {:keys [status error body]} @(http/get "http://api.openweathermap.org/data/2.5/weather" req-options)]
        (json/read-str body
          :key-fn keyword)))

; return seq of weather data!
(defn parse-data [json]
  (conj (get (:weather json) 0) (:main json)))
