(defproject outgo "0.1.0-SNAPSHOT"
  :description "FIXME: outgo for my love"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [clj-time "0.6.0"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [hiccup "1.0.2"]
                 [org.clojure/java.jdbc "0.3.0-alpha5"]
                 [postgresql "9.1-901-1.jdbc4"]]
  :plugins [[lein-ring "0.8.7"]]
  :uberjar-name "outgo-standalone.jar"
  :min-lein-version "2.0.0"
  :ring {:handler outgo.core/application
         :auto-reload? true
         :auto-refresh? true}
  :main outgo.core)
