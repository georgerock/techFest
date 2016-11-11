(defproject techfest "0.1.0"
  :description "Real time public transportation"
  :url "https://github.com/georgerock/techFest.git"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.293"]
                 [org.omcljs/om "0.9.0"]
                 ]
  :jvm-opts ^:replace ["-Xmx1g" "-server"]
  :plugins [[lein-npm "0.6.1"]]
  :npm {:dependencies [[source-map-support "0.4.0"]]}
  :source-paths ["src" "target/classes"]
  :clean-targets ["out" "release"]
  :target-path "target")
  :plugins  [
    [lein-cljsbuild "1.1.3" :exclusions [[org.clojure/clojure]]]]
  :cljsbuild {:builds
    [{:id "development"
      :source-paths ["cljs"]
      :compiler {:output-to "resources/public/js/compiled/techfest.min.js"
                 :main chatpiper-om.core
                 :optimizations :advanced
                 :externs ["resources/public/js/externs.js"]
                 :pretty-print false}}]}
