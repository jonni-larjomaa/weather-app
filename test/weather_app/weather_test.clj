(ns weather-app.weather-test
  (:require [clojure.test :refer :all]
            [weather-app.weather :refer :all]))

(deftest custom-params
  (testing "can add custom params"
    (is
      (=
        (:foo
          (:query-params
            (add-custom-params req-params {:foo "bar"})))
              "bar"))))

(def mock-json {:weather [{:id 50}] :main {:temp 12}})

(deftest get-json
  (testing "can parse json"
    (let [data (parse-data mock-json)]
      (is (= (:temp data) 12))
      (is (= (:id data) 50)))))
