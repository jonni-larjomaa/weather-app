(defproject claudator "0.1"
  :description "claudator weather parser"
  :url "http://example.com/FIXME"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [http-kit "2.1.18"]
                 [org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot claudator.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
